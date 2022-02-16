import { MessageService } from 'primeng/api';
import { Cidades } from './../../../shared/models/cidades';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { CidadesService } from '../cidades.service';
import { Validators } from '@angular/forms';
import { EstadosService } from './../../estados/estados.service';

@Component({
  selector: 'app-cidades-cadastro',
  templateUrl: './cidades-cadastro.component.html',
  styleUrls: ['./cidades-cadastro.component.css']
})
export class CidadesCadastroComponent extends BaseResourceFormComponent<Cidades> {

estadoList = [];

  constructor(
    protected cidadesService: CidadesService
  , protected estadosService: EstadosService
  , protected injector: Injector) {

    super(injector, new Cidades(), cidadesService, Cidades.fromJson, new MessageService());
	this.loadEstado();

  }
 
  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
		codigoIbge: [null, [Validators.required, Validators.minLength(5)]],
		codigoInep: [null, [Validators.required, Validators.minLength(5)]],
		codigoSicom: [null, [Validators.required, Validators.minLength(5)]],
		dataAlteracao: [null, [Validators.required, Validators.minLength(5)]],
		estado: 
this.formBuilder.group({
id: [null]
}),
		id: [null, [Validators.required, Validators.minLength(5)]],
		nome: [null, [Validators.required, Validators.minLength(5)]],
		sigla: [null, [Validators.required, Validators.minLength(5)]],
		usuario: [null, [Validators.required, Validators.minLength(5)]],
    });
  }
 
  protected creationPageTitle(): string {
    return 'Cadastro de Novo Cidades';
  }
 
  protected editionPageTitle(): string {
    const cidadesName = this.resource.codigo_ibge || '';
    return 'Editando Cidades: ' + cidadesName;
  }

 loadEstado() { 
     this.estadosService.listAll() 
       .then(estados => { 
         this.estadoList = estados.map(c => 
                 ({ label: c.nome, value: c.id }) 
           ); 
       }) 
       .catch(erro => erro); 
   } 
}