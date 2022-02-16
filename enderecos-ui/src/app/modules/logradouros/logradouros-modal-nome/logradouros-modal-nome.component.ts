import { Component, OnInit } from '@angular/core';

import { Logradouros } from './../../../shared/models/logradouros';
import { LogradourosService } from './../../logradouros/logradouros.service';
import { LogradourosFiltro } from './../logradouros-pesquisa/logradouros-filtro';

import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { HttpParams } from '@angular/common/http';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';

@Component({
  selector: 'app-logradouros-modal-nome',
  templateUrl: './logradouros-modal-nome.component.html',
  styleUrls: ['./logradouros-modal-nome.component.css']
})
export class LogradourosModalNomeComponent extends BaseResourceListComponent<Logradouros> {

  filtro = new LogradourosFiltro();
  loading = true;

  // parte de filtrar logradouros do distrito
  idDistrito;


  constructor(
    private logradourosService: LogradourosService,
    public confirmationService: ConfirmationService,
    public messageService: MessageService,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig
  ) {
    super(logradourosService, confirmationService, messageService);

    // parte de filtrar bairros do distrito
    this.idDistrito = config.data.idDistrito;

  }

  pesquisar(pagina = 0) {
    this.filtro.pagina = pagina;

    // parte de filtrar logradouros do distrito
    this.filtro.params = this.filtro.params.append('distritosFilter.id',this.idDistrito);

    this.logradourosService.pesquisar(this.filtro)
      .then(resultado => {
        this.loading = false;
        this.filtro.totalRegistros = resultado.total;
        this.resources = resultado.logradouros;
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

    if (event.filters.nome) {
      this.filtro.params = this.filtro.params.append('nome', event.filters.nome.value);
    }

    this.pesquisar(pagina);
  }

  selecItem(logradouros){
    this.ref.close(logradouros);
  }

}

