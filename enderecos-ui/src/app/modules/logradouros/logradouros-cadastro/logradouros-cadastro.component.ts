import { SelectItem } from 'primeng/api';
import { DialogService } from 'primeng';
import { MessageService } from 'primeng/api';
import { Logradouros } from './../../../shared/models/logradouros';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { LogradourosService } from '../logradouros.service';
import { Validators } from '@angular/forms';
import { TiposLogradourosService } from './../../tipos-logradouros/tipos-logradouros.service';
import { TitulosPatentesService } from './../../titulos-patentes/titulos-patentes.service';
import { CidadesModalComponent } from './../../cidades/cidades-modal/cidades-modal.component';
import { VwPessoasModalComponent } from './../../vw-pessoas/vw-pessoas-modal/vw-pessoas-modal.component';

import * as moment from 'moment';

@Component({
  selector: 'app-logradouros-cadastro',
  templateUrl: './logradouros-cadastro.component.html',
  styleUrls: ['./logradouros-cadastro.component.css']
})

export class LogradourosCadastroComponent extends BaseResourceFormComponent<Logradouros> {

  tipoLogradouroList: SelectItem[];
  tituloPatenteList: SelectItem[];

  preposicaoList = [
    {value: "AO",  label: "AO", selected: false},
    {value: "ATÉ", label: "ATÉ", selected: false},
    {value: "D",   label: "D",selected: false},
    {value: "DA",  label: "DA",selected: false},
    {value: "DE",  label: "DE",selected: false},
    {value: "DO",  label: "DO",selected: false},
    {value: "DAS", label: "DAS",selected: false},
    {value: "DOS", label: "DOS",selected: false},
    {value: "E",   label: "E",selected: false},
    {value: "MÃE", label: "MÃE",selected: false},
    {value: "PAR", label: "PAR",selected: false},
    {value: "RIO", label: "RIO",selected: false},
    {value: "SEM", label: "SEM",selected: false}
  ];

  zonaRural = [
    { value: 'N', label: 'Não' },
    { value: 'S', label: 'Sim' }
  ];

  masks = {
    mask: [
      {
        mask: '000.000.000-00'
      }
    ]
  };

  botaoOnOf = false;
  ptBrLocale;

  constructor(
    protected logradourosService: LogradourosService,
    protected tiposLogradourosService: TiposLogradourosService,
    protected titulosPatentesService: TitulosPatentesService,
    protected injector: Injector,
    public dialogService: DialogService,
    public messageService: MessageService
  ) {

    super(injector, new Logradouros(), logradourosService, Logradouros.fromJson, new MessageService());
	  this.loadTipoLogradouro();
    this.loadTituloPatente();
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
        id: [null, [Validators.required, Validators.minLength(10)]],
        nome: [null]
      }),

      tiposLogradouros:
        this.formBuilder.group({
          descricao:[null],
          id: [null, [Validators.required, Validators.minLength(5)]],
          sigla: [null]
      }),

      tituloPatente: [null],
      preposicao:[null],
      nome: [null, [Validators.required, Validators.minLength(5)]],
      usuario: [null],
      nomeReduzido:[null],
      nomeSimplificado:[null],
      complemento: [null],

      dadosLogradouros: this.formBuilder.group({
        dataCriacao: [null],
        leiCriacao: [null],
        dataDecreto: [null],
        decreto: [null],
        dataPortaria: [null],
        portaria: [null],
        pessoaVereador: [null],
        usuario: JSON.parse(sessionStorage.getItem("usuario")).nome,
        observacao: [null],
        vwPessoas: this.formBuilder.group({
          id: [null],
          nomeVereador: [null],
          cpf: [null],
        }),
      }),
    });
    this.resourceForm.patchValue({
      usuario: JSON.parse(sessionStorage.getItem("usuario")).nome,
    });
  }

  protected creationPageTitle(): string {
    this.botaoOnOf = false;
    return 'Cadastro de Novo Logradouros';
  }

  protected editionPageTitle(): string {
    const logradourosName = this.resource.complemento || '';
    this.botaoOnOf = true;
    return 'Editando Logradouros: ' ;
  }

  loadTipoLogradouro() {
    this.tiposLogradourosService.listAll()
    .then(tiposLogradouros => {
      this.tipoLogradouroList = tiposLogradouros.map(c =>
        ({ label: c.sigla + ' - ' + c.descricao, value: c.id })

      );
    })
    .catch(erro => erro);
  }

  loadTituloPatente(){
    this.titulosPatentesService.listAll()
    .then(titulosPatentes => {
      this.tituloPatenteList = titulosPatentes.map(c => ({ label: c.descricao, value: c.descricao }))
    })
    .catch(erro => erro);
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
      this.resourceForm.patchValue(
        {
          dadosLogradouros: {
            vwPessoas: {
              id: vwPessoas.id,
              nomeVereador: vwPessoas.nome,
              cpf: vwPessoas.cpf
            },
            pessoaVereador: vwPessoas.id
          }
        }
      );
    });
  }

  submitForm() {
    //this.submittingForm = true;
    if (this.currentAction === 'new') {
      // clicou no + (novo)
      this.createResource();
    } else {
        // clicou no update
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

    // Move o usuario para logradouros
    this.resourceForm.patchValue({
      usuario: JSON.parse(sessionStorage.getItem("usuario")).nome
    });

    // Move o usuario para dados logradouros
    this.resourceForm.patchValue({
      dadosLogradouros:{usuario: JSON.parse(sessionStorage.getItem("usuario")).nome}
    });

    const resource: Logradouros = this.jsonDataToResourceFn(this.resourceForm.value);
    let logradouros = JSON.stringify(resource, replacer);

    // Chama a funcao que grava/ faz update
    this.logradourosService.updateLogradouro(logradouros)
      .then(response => {
        this.messageService.add({severity:'success', summary: 'Sucesso', detail: 'Logradouro atualizado com sucesso!'});

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
        //console.log('ENTROU RESOLVER DATA ' + moment(this[key]).format('DD/MM/YYYY'))
        return moment(this[key]).format('DD/MM/YYYY');
      }
      return value;
    };

    const resource: Logradouros = this.jsonDataToResourceFn(this.resourceForm.value);
    // copia os dados de Logradouros para logradouros
    let logradouros = JSON.stringify(resource, replacer);
    //console.log(logradouros);

    // Chama a funcao que grava
    this.logradourosService.createLogradouro(logradouros)
      .then(response => {
        //console.log('VAI GAROTO ' + logradouros);
        this.messageService.add({severity:'success', summary: 'Sucesso', detail: 'Logradouro inserido com sucesso!'});

        //this.buildResourceForm(); -- limpa o formulario

        // redireciona para lista
        const baseComponentPath = this.route.snapshot.parent.url[0].path;
        this.router.navigateByUrl(baseComponentPath, { skipLocationChange: true }).then(() => {
          //console.log(this.router);
          return this.router.navigate(["/" + baseComponentPath]);
        });

      }).
      catch(error => {
        //console.log('VAI GAROTO ' + logradouros);
        console.log(error);
        this.messageService.add({severity:'error', summary: 'Erro', detail:error.error[0].mensagemUsuario});
    });
  }
}
