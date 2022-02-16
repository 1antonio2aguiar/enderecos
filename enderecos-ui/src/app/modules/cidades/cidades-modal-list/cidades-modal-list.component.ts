import { Component, OnInit } from '@angular/core';
import { CidadesFiltro } from './../cidades-pesquisa/cidades-filtro';
import { CidadesService } from './../../cidades/cidades.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DynamicDialogRef } from 'primeng';
import { LazyLoadEvent } from 'primeng/api';
import { HttpParams } from '@angular/common/http';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { Cidades} from './../../../shared/models/cidades';

@Component({
  selector: 'app-cidades-modal-list',
  templateUrl: './cidades-modal-list.component.html',
  styleUrls: ['./cidades-modal-list.component.css']
})

export class CidadesModalListComponent extends BaseResourceListComponent<Cidades> {
  filtro = new CidadesFiltro();
  resources = [];
  loading = true;

  constructor(
    private cidadesService: CidadesService,
    public confirmationService: ConfirmationService,
    public messageService: MessageService,
    public ref: DynamicDialogRef
  ) {
    super(cidadesService, confirmationService, messageService);
  }

  pesquisar(pagina = 0) {
    this.filtro.pagina = pagina;
    this.cidadesService.pesquisar(this.filtro)
    .then(resultado => {

      this.loading = false;
      this.filtro.totalRegistros = resultado.total;
      this.resources = resultado.cidades;
    })
    .catch(erro => {
      erro = 'Erro';
      this.loading = false;
      }
    );
  }

  aoMudarPagina(event: LazyLoadEvent) {
    const pagina = event.first / event.rows;
    this.filtro.params = new HttpParams();

    if (event.filters.id) {
      this.filtro.params = this.filtro.params.append('id', event.filters.id.value);
    }

    if (event.filters.cidade) {
      this.filtro.params = this.filtro.params.append('nome', event.filters.cidade.value);
    }

    this.pesquisar(pagina);
  }

  selecItem(cidades){
    this.ref.close(cidades);
  }

}
