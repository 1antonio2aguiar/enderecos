import { MessageService } from 'primeng/api';
import { Paises } from './../../../shared/models/paises';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { PaisesService } from '../paises.service';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-paises-cadastro',
  templateUrl: './paises-cadastro.component.html',
  styleUrls: ['./paises-cadastro.component.css']
})
export class PaisesCadastroComponent extends BaseResourceFormComponent<Paises> {


  constructor(
    protected paisesService: PaisesService,
    protected injector: Injector) {

    super(injector, new Paises(), paisesService, Paises.fromJson, new MessageService());

  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
      codigoInep: [null],
      id: [null],
      nacionalidade: [null],
      nome: [null, [Validators.required, Validators.minLength(5)]],
      sigla: [null],
    });
  }

  protected creationPageTitle(): string {
    return 'Cadastro de Novo Paises';
  }

  protected editionPageTitle(): string {
    const paisesName = this.resource.codigo_inep || '';
    return 'Editando Paises: ' + paisesName;
  }

}
