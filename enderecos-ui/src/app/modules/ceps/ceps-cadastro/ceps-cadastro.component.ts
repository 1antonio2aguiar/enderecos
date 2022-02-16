import { catchError } from 'rxjs/operators';
import { TiposLogradouros } from './../../../shared/models/tipos-logradouros';
import { MessageService } from 'primeng/api';
import { DialogService } from 'primeng';
import { Ceps } from './../../../shared/models/ceps';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { CepsService } from '../ceps.service';
import { Validators } from '@angular/forms';
import { BairrosService } from './../../bairros/bairros.service';
import { LogradourosService } from './../../logradouros/logradouros.service';
import { CidadesModalComponent } from './../../cidades/cidades-modal/cidades-modal.component';
import { BairrosModalNomeComponent } from './../../bairros/bairros-modal-nome/bairros-modal-nome.component';
import { LogradourosModalNomeComponent } from './../../logradouros/logradouros-modal-nome/logradouros-modal-nome.component';

@Component({
  selector: 'app-ceps-cadastro',
  templateUrl: './ceps-cadastro.component.html',
  styleUrls: ['./ceps-cadastro.component.css']
})
export class CepsCadastroComponent extends BaseResourceFormComponent<Ceps> {

  bairroList = [];
  logradouroList = [];
  botaoOnOf = false;

  identificacao = [
    { value: 'U', label: 'Único' },
    { value: 'D', label: 'Direita' },
    { value: 'E', label: 'Esquerda' },
    { value: 'I', label: 'Impar' },
    { value: 'P', label: 'par' },
    { value: 'A', label: 'A' }

  ];

  masks = {
    mask: [
      {
        mask: '00.000-000'
      }
    ]
  };

  constructor(
    protected cepsService: CepsService
  , protected bairrosService: BairrosService
  , protected logradourosService: LogradourosService
  , protected injector: Injector
  , public dialogService: DialogService ) {

    super(injector, new Ceps(), cepsService, Ceps.fromJson, new MessageService());

  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
      id: [null],
      distritos: this.formBuilder.group({
        cidades: this.formBuilder.group({
          nome: [null],
          estados: this.formBuilder.group({
            sigla: [null]
          })
        }),
        id: [null, [Validators.required, Validators.maxLength(15)]],
        nome: [null]
      }),

      bairros: this.formBuilder.group({
        id: [null, [Validators.required, Validators.maxLength(15)]],
        nome:[null],
      }),

      logradouros: this.formBuilder.group({
        id: [null, [Validators.required, Validators.maxLength(15)]],
        nome: [null],
        tiposLogradouros: this.formBuilder.group({
          sigla: [null],
        })
      }),
      cep: [null, [Validators.required, Validators.minLength(8),Validators.maxLength(20)]],
      numeroIni:[1],
      numeroFim:[999999999],
      identificacao: ["U"]

    });

    this.resourceForm.patchValue({
      usuario: JSON.parse(sessionStorage.getItem("usuario")).nome,
    });

  }

  protected creationPageTitle(): string {
    this.botaoOnOf = false;
    this.resourceForm.get('cep').enable();
    return 'Cadastro de Novo Ceps';
  }

  protected editionPageTitle(): string {
    const cepsName = this.resource.bairro || '';
    this.botaoOnOf = true;
    this.resourceForm.get('cep').disable();
    return 'Editando Ceps: ' ;
  }

  // Modal Cidade/distrito/estado
  showCidades($event) {
    const ref = this.dialogService.open(CidadesModalComponent, {
      header: 'Selecione a Cidade/Distrito',
      width: '70%'
    });

    ref.onClose.subscribe((distrito) => {
      //console.log(distrito);

      this.resourceForm.patchValue(
        {
          distritos: {
            cidades: {
              id: distrito.cidades.id,
              nome: distrito.cidades.nome,

              estados: {
                sigla: distrito.cidades.estados.sigla
              }

            },
            id: distrito.id,
            nome: distrito.nome
          }
        }
      );
    });
  }

  // Modal Bairro
  showBairros($event) {
    //console.log(this.resourceForm.get('distritos').get('id').value);
    const ref = this.dialogService.open(BairrosModalNomeComponent, {
      header: 'Selecione o Bairro',
      width: '70%',
      data:{
        // parte de filtrar bairros do distrito
        idDistrito: this.resourceForm.get('distritos').get('id').value
      }
    });

    ref.onClose.subscribe((bairro) => {
      this.resourceForm.patchValue({
        bairros: {
          id: bairro.id,
            nome: bairro.nome
        }
      });
    });
  }

  // Modal Logradouro
  showLogradouros($event) {
    const ref = this.dialogService.open(LogradourosModalNomeComponent, {
      header: 'Selecione o Logradouro',
      width: '70%',
      data:{
        // parte de filtrar logradouros do distrito
        idDistrito: this.resourceForm.get('distritos').get('id').value
      }
    });

    ref.onClose.subscribe((logradouro) => {
      this.resourceForm.patchValue({
        logradouros: {
          id: logradouro.id,
          nome: logradouro.nome,

          tiposLogradouros:{
            sigla: logradouro.tiposLogradouros.sigla
          }
        }
      });
    });
  }

  salvarCeps(){
    //let temp = this.resourceForm.value;
    //console.log(temp);

    let temp = {
      "bairros": this.resourceForm.get('bairros').get('id').value,
      "logradouros": this.resourceForm.get('logradouros').get('id').value,
      "id": this.resourceForm.get('id').value == null ? null: this.resourceForm.get('id').value,
      "cep": this.resourceForm.get('cep').value,
      "identificacao": this.resourceForm.get('identificacao').value,
      "numeroIni": this.resourceForm.get('numeroIni').value,
      "numeroFim": this.resourceForm.get('numeroFim').value,
      "usuario": JSON.parse(sessionStorage.getItem("usuario")).nome
    };

    if (this.currentAction == "new") {
      botaoOnOf: false;
      this.cepsService.createCep(temp)
      .then(result =>{
        this.messageService.add({severity:'success', summary: 'Sucesso', detail: 'CEP inserido com sucesso!'});

        // redireciona para lista
        const baseComponentPath = this.route.snapshot.parent.url[0].path;
        this.router.navigateByUrl(baseComponentPath, { skipLocationChange: true }).then(() => {
          //console.log(this.router);
          return this.router.navigate(["/" + baseComponentPath]);
        });
      })
      .catch(error=>{
        console.log(error);
        this.messageService.add({severity:'error', summary: 'Erro', detail:error.error[0].mensagemUsuario});
      });
    } else {
      this.cepsService.updateCep(temp)
      .then(result => {
        this.messageService.add({severity:'success', summary: 'Sucesso', detail: 'CEP alterado com sucesso!'});

        // redireciona para lista
        const baseComponentPath = this.route.snapshot.parent.url[0].path;
        this.router.navigateByUrl(baseComponentPath, { skipLocationChange: true }).then(() => {
          //console.log(this.router);
          return this.router.navigate(["/" + baseComponentPath]);
        });
      })
      .catch(error => {
        console.log(error);
        this.messageService.add({severity:'error', summary: 'Erro', detail:error.error[0].mensagemUsuario});
      });
    }
  }
}
