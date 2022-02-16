import { MessageService } from 'primeng/api';
import { LogCidades } from './../../../shared/models/log-cidades';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { LogCidadesService } from '../log-cidades.service';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-log-cidades-cadastro',
  templateUrl: './log-cidades-cadastro.component.html',
  styleUrls: ['./log-cidades-cadastro.component.css']
})
export class LogCidadesCadastroComponent extends BaseResourceFormComponent<LogCidades> {


  constructor(
    protected logCidadesService: LogCidadesService
  , protected injector: Injector) {

    super(injector, new LogCidades(), logCidadesService, LogCidades.fromJson, new MessageService());

  }
 
  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
		alteracao: [null, [Validators.required, Validators.minLength(5)]],
		cidade: [null, [Validators.required, Validators.minLength(5)]],
		codigoIbge: [null, [Validators.required, Validators.minLength(5)]],
		codigoInep: [null, [Validators.required, Validators.minLength(5)]],
		codigoSicom: [null, [Validators.required, Validators.minLength(5)]],
		dataAlteracao: [null, [Validators.required, Validators.minLength(5)]],
		estado: [null, [Validators.required, Validators.minLength(5)]],
		id: [null, [Validators.required, Validators.minLength(5)]],
		nome: [null, [Validators.required, Validators.minLength(5)]],
		sigla: [null, [Validators.required, Validators.minLength(5)]],
		usuario: [null, [Validators.required, Validators.minLength(5)]],
    });
  }
 
  protected creationPageTitle(): string {
    return 'Cadastro de Novo LogCidades';
  }
 
  protected editionPageTitle(): string {
    const logCidadesName = this.resource.alteracao || '';
    return 'Editando LogCidades: ' + logCidadesName;
  }

}