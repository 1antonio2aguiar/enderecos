import { MessageService } from 'primeng/api';
import { TiposEnderecos } from './../../../shared/models/tipos-enderecos';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { TiposEnderecosService } from '../tipos-enderecos.service';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-tipos-enderecos-cadastro',
  templateUrl: './tipos-enderecos-cadastro.component.html',
  styleUrls: ['./tipos-enderecos-cadastro.component.css']
})
export class TiposEnderecosCadastroComponent extends BaseResourceFormComponent<TiposEnderecos> {


  constructor(
    protected tiposEnderecosService: TiposEnderecosService,
    protected injector: Injector) {

    super(injector, new TiposEnderecos(), tiposEnderecosService, TiposEnderecos.fromJson, new MessageService());

  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
      id: [null],
      descricao: [null, [Validators.required, Validators.minLength(3)]],
    });
  }

  protected creationPageTitle(): string {
    return 'Cadastro de Novo TiposEnderecos';
  }

  protected editionPageTitle(): string {
    const tiposEnderecosName = this.resource.descricao || '';
    return 'Editando TiposEnderecos: ' + tiposEnderecosName;
  }

}
