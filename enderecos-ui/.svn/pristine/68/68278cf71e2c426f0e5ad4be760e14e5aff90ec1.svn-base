import { MessageService } from 'primeng/api';
import { TitulosPatentes } from './../../../shared/models/titulos-patentes';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { TitulosPatentesService } from '../titulos-patentes.service';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-titulos-patentes-cadastro',
  templateUrl: './titulos-patentes-cadastro.component.html',
  styleUrls: ['./titulos-patentes-cadastro.component.css']
})
export class TitulosPatentesCadastroComponent extends BaseResourceFormComponent<TitulosPatentes> {


  constructor(
    protected titulosPatentesService: TitulosPatentesService,
    protected injector: Injector) {

    super(injector, new TitulosPatentes(), titulosPatentesService, TitulosPatentes.fromJson, new MessageService());

  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
      id: [null],
      descricao: [null, [Validators.required, Validators.minLength(5)]],
    });
  }

  protected creationPageTitle(): string {
    return 'Cadastro de Novo TitulosPatentes';
  }

  protected editionPageTitle(): string {
    const titulosPatentesName = this.resource.descricao || '';
    return 'Editando TitulosPatentes: ' + titulosPatentesName;
  }

}
