import { MessageService } from 'primeng/api';
import { VwPessoas } from './../../../shared/models/vw-pessoas';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { VwPessoasService } from '../vw-pessoas.service';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-vw-pessoas-cadastro',
  templateUrl: './vw-pessoas-cadastro.component.html',
  styleUrls: ['./vw-pessoas-cadastro.component.css']
})
export class VwPessoasCadastroComponent extends BaseResourceFormComponent<VwPessoas> {


  constructor(
    protected vwPessoasService: VwPessoasService
  , protected injector: Injector) {

    super(injector, new VwPessoas(), vwPessoasService, VwPessoas.fromJson, new MessageService());

  }
 
  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
		cnpj: [null, [Validators.required, Validators.minLength(5)]],
		cpf: [null, [Validators.required, Validators.minLength(5)]],
		fisicaJuridica: [null, [Validators.required, Validators.minLength(5)]],
		id: [null, [Validators.required, Validators.minLength(5)]],
		nome: [null, [Validators.required, Validators.minLength(5)]],
		situacao: [null, [Validators.required, Validators.minLength(5)]],
    });
  }
 
  protected creationPageTitle(): string {
    return 'Cadastro de Novo VwPessoas';
  }
 
  protected editionPageTitle(): string {
    const vwPessoasName = this.resource.cnpj || '';
    return 'Editando VwPessoas: ' + vwPessoasName;
  }

}