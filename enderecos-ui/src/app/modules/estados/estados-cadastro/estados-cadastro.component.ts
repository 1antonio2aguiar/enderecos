import { MessageService } from 'primeng/api';
import { Estados } from './../../../shared/models/estados';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { EstadosService } from '../estados.service';
import { Validators } from '@angular/forms';
import { PaisesService } from './../../paises/paises.service';

@Component({
  selector: 'app-estados-cadastro',
  templateUrl: './estados-cadastro.component.html',
  styleUrls: ['./estados-cadastro.component.css']
})
export class EstadosCadastroComponent extends BaseResourceFormComponent<Estados> {

  paisList = [];
  paisesSelect;

  constructor(
    protected estadosService: EstadosService,
    protected paisesService: PaisesService,
    protected injector: Injector) {

    super(injector, new Estados(), estadosService, Estados.fromJson, new MessageService());
    this.loadPais();

  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
      id: [null],
      paises:
      this.formBuilder.group({
        id: [null]
      }),
      nome: [null, [Validators.required, Validators.minLength(3)]],
      sigla: [null],
      codigoInep: [null],
    });
  }

  protected creationPageTitle(): string {
    return 'Cadastro de Novo Estados';
  }

  protected editionPageTitle(): string {
    const estadosName = this.resource.codigo_inep || '';
    console.log(this.resourceForm);
    return 'Editando Estados: ' + estadosName;
  }


  loadPais() {
    this.paisesService.listAll()
    .then(paises => {
      this.paisList = paises.map(c =>
        ({ label: c.nome, value: c.id })
      );
    })
    .catch(erro => erro);
  }
}
