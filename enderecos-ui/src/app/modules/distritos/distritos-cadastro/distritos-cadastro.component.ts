import { MessageService } from 'primeng/api';
import { Distritos } from './../../../shared/models/distritos';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { DistritosService } from '../distritos.service';
import { Validators } from '@angular/forms';
import { DialogService } from 'primeng';

import { CidadesModalListComponent } from './../../cidades/cidades-modal-list/cidades-modal-list.component';

@Component({
  selector: 'app-distritos-cadastro',
  templateUrl: './distritos-cadastro.component.html',
  styleUrls: ['./distritos-cadastro.component.css']
})
export class DistritosCadastroComponent extends BaseResourceFormComponent<Distritos> {

  cidadeList = [];
  botaoOnOf = false;

  constructor(
    protected distritosService: DistritosService,
    protected injector: Injector,
    public dialogService: DialogService ,
    public messageService: MessageService
  ) {

    super(injector, new Distritos(), distritosService, Distritos.fromJson, new MessageService());

  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
        id: [null],
        nome: [null, [Validators.required, Validators.minLength(4)]],
        codigoInep: [null],
        usuario: JSON.parse(sessionStorage.getItem("usuario")).nome,
        cidades: this.formBuilder.group({
          nome: [null],
          id: [null],
          estados: this.formBuilder.group({
            sigla: [null]
        })
      }),
    });

  }

  protected creationPageTitle(): string {
    this.botaoOnOf = false;
    return 'Cadastro de Novo Distrito';
  }

  protected editionPageTitle(): string {
    this.botaoOnOf = true;

    this.resourceForm.patchValue({
      usuario: JSON.parse(sessionStorage.getItem("usuario")).nome,
    });

    const distritosName = this.resource.cidade || '';
    return 'Editando Distrito: ' + distritosName;
  }

  // Modal Cidade/estado
  showCidades($event) {
    const ref = this.dialogService.open(CidadesModalListComponent, {
      header: 'Selecione a Cidade',
      width: '70%'
    });

    ref.onClose.subscribe((cidade) => {
      //console.log(distrito);

      this.resourceForm.patchValue(
        {
          cidades: {
            nome: cidade.nome,
            id: cidade.id,
            estados: {
              sigla: cidade.estados.sigla
            }
          },
        }
      );
    });
  }

}
