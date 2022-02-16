import { Usuarios } from 'src/app/shared/models/usuarios';
import { DadosBairros } from './../../../shared/models/dados-bairros';
import { DialogService } from 'primeng';
import { MessageService, SelectItem } from "primeng/api";
import { Component, Injector } from "@angular/core";
import { Validators } from "@angular/forms";
import { VwPessoasModalComponent } from './../../vw-pessoas/vw-pessoas-modal/vw-pessoas-modal.component';
import { DistritosService } from "./../../distritos/distritos.service";
import { BaseResourceFormComponent } from "../../../shared/components/base-resource-form/base-resource-form.component";
import { CidadesModalComponent } from './../../cidades/cidades-modal/cidades-modal.component';
import { Bairros } from "./../../../shared/models/bairros";
import { BairrosService } from "../bairros.service";

import * as moment from 'moment';


@Component({
  selector: "app-bairros-cadastro",
  templateUrl: "./bairros-cadastro.component.html",
  styleUrls: ["./bairros-cadastro.component.css"],
})

export class BairrosCadastroComponent extends BaseResourceFormComponent<Bairros> {

  distritoList = [];
  botaoOnOf = false;

  zonaRural = [
    { value: 'N', label: 'Não' },
    { value: 'S', label: 'Sim' }
  ];

  ptBrLocale;

  masks = {
    //[imask]="masks" [unmask]="true"
    mask: [
      {
        mask: '000.000.000'
      },
      {
        mask: '000.000.000-0'
      },
      {
        mask: '000.000.000-00'
      },
      {
        mask: '00.000.000/0000-00'
      }
    ]
  };

  constructor(
    protected bairrosService: BairrosService,
    protected distritosService: DistritosService,
    protected injector: Injector,
    public dialogService: DialogService,
    public messageService: MessageService
  ) {
    super(injector, new Bairros(), bairrosService, Bairros.fromJson, messageService);

    this.loadDistrito();

    this.ptBrLocale = this.loadLocale();

  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
      id:[null],
      distritos: this.formBuilder.group({
        cidades: this.formBuilder.group({
          nome: [null],
          estados: this.formBuilder.group({
            sigla: [null]
          })
        }),
        id: [null],
        nome: [null]
      }),
      nome: [null, [Validators.required, Validators.minLength(5)]],
      nomeAbreviado: [null],
      usuario: [null],
      dadosBairros: this.formBuilder.group({
        zonaRural: ['N'],
        nomePopular: [null],
        dataCriacao: [null],
        leiCriacao: [null],
        dataDecreto: [null],
        decreto: [null],
        dataPortaria: [null],
        portaria: [null],
        pessoaVereador: [null],
        pessoaLoteadora:[null],
        usuario: JSON.parse(sessionStorage.getItem("usuario")).nome,
        vwPessoas: this.formBuilder.group({
          id: [null],
          nomeVereador: [null],
          cpf: [null],
          nomeLoteadora: [null],
          cnpj: [null]
        })
      })
    });
    this.resourceForm.patchValue({
      usuario: JSON.parse(sessionStorage.getItem("usuario")).nome,
    });
  }

  protected creationPageTitle(): string {
    this.botaoOnOf = false;
    return "Cadastro de Novo Bairro";
  }

  protected editionPageTitle(): string {
    const bairrosName = this.resource.dataAlteracao || "";
    this.botaoOnOf = true;
    return "Editando Bairro: " ;
  }

  loadDistrito() {
    this.distritosService
      .listAll()
      .then((distritos) => {
        this.distritoList = distritos.map((c) => ({ label: c.nome, value: c.id }));
      })
      .catch((erro) => erro);
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

  // Modal pessoas vereador
  showPessoas($event) {

    const ref = this.dialogService.open(VwPessoasModalComponent, {
      header: 'Selecione a Pessoa/Vereador',
      width: '70%'
    });

    ref.onClose.subscribe((vwPessoas) => {
      //console.log(vwPessoas);

      if (vwPessoas.fisicaJuridica == 'F') {
        this.resourceForm.patchValue(
          {
            dadosBairros: {
              vwPessoas: {
                id: vwPessoas.id,
                nomeVereador: vwPessoas.nome,
                cpf: vwPessoas.cpf + ''
              },
              pessoaVereador: vwPessoas.id
            }
          }
        );
      } else {
        this.resourceForm.patchValue(
          {
            dadosBairros: {
              vwPessoas: {
                id: vwPessoas.id,
                nomeLoteadora: vwPessoas.nome,
                cnpj: vwPessoas.cnpj+''
              }
            }
          }
        );
      }
    });
  }

  submitForm() {
    //this.submittingForm = true;
    if (this.currentAction === 'new') {
      this.createResource();
    } else {
      this.updateResource();
    }
  }

  // resolver problemas de datas
  protected updateResource(){
    const replacer = function (key, value) {
      if (this[key] instanceof Date) {
        return moment(this[key]).format('DD/MM/YYYY');
      }
      return value;
    };

    // Qundo tem valor pega assim:
    //console.log('ATENÇÃO! ' + this.resourceForm.get('zonaRural').value);

    /*if(this.resourceForm.get('zonaRural') == null){
      this.resourceForm.patchValue({ dadosBairros:{zonaRural: "N"} });
    }*/

    // Move o usuario para bairros
    this.resourceForm.patchValue({
      usuario: JSON.parse(sessionStorage.getItem("usuario")).nome
    });

    // Move o usuario para dados bairros
    this.resourceForm.patchValue({
      dadosBairros:{usuario: JSON.parse(sessionStorage.getItem("usuario")).nome}
    });

    const resource: Bairros = this.jsonDataToResourceFn(this.resourceForm.value);
    let bairros = JSON.stringify(resource, replacer);

    // Chama a funcao que grava/ faz update
    this.bairrosService.updateBairro(bairros)
      .then(response => {
        this.messageService.add({severity:'success', summary: 'Sucesso', detail: 'Bairro atualizado com sucesso!'});

        // redireciona para lista
        const baseComponentPath = this.route.snapshot.parent.url[0].path;
        this.router.navigateByUrl(baseComponentPath, { skipLocationChange: true }).then(() => {
          console.log(this.router);
          return this.router.navigate(["/" + baseComponentPath]);
        });

      }).
      catch(error => {
        console.log(error);
        console.log('fail');
        this.messageService.add({severity:'error', summary: 'Erro', detail:error.error[0].mensagemUsuario});
    });
  }

  // resolver problemas de datas
  protected createResource() {

    const replacer = function (key, value) {

      if (this[key] instanceof Date) {
        return moment(this[key]).format('DD/MM/YYYY');
      }

      return value;
    }

    const resource: Bairros = this.jsonDataToResourceFn(this.resourceForm.value);
    // copia os dados de Bairros para bairros
    let bairros = JSON.stringify(resource, replacer);
    //console.log(bairros);

    // Chama a funcao que grava
    this.bairrosService.createBairro(bairros)
      .then(response => {
        this.messageService.add({severity:'success', summary: 'Sucesso', detail: 'Bairro inserido com sucesso!'});

        //this.buildResourceForm(); -- limpa o formulario

        // redireciona para lista
        const baseComponentPath = this.route.snapshot.parent.url[0].path;
        this.router.navigateByUrl(baseComponentPath, { skipLocationChange: true }).then(() => {
          console.log(this.router);
          return this.router.navigate(["/" + baseComponentPath]);
        });

      }).
      catch(error => {
        console.log(error);
        console.log('fail');
        this.messageService.add({severity:'error', summary: 'Erro', detail:error.error[0].mensagemUsuario});
    });
  }
}
