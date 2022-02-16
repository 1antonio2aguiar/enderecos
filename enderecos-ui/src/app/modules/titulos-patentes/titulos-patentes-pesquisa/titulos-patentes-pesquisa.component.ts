import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { TitulosPatentesService } from './../titulos-patentes.service';
import { TitulosPatentes } from './../../../shared/models/titulos-patentes';
import { Component, ViewChild } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { TitulosPatentesFiltro } from './titulos-patentes-filtro';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-titulos-patentes-pesquisa',
  templateUrl: './titulos-patentes-pesquisa.component.html',
  styleUrls: ['./titulos-patentes-pesquisa.component.css']
})
export class TitulosPatentesPesquisaComponent extends BaseResourceListComponent<TitulosPatentes> {
  filtro = new TitulosPatentesFiltro();
  resources = [];
  loading = true;
  constructor(
    private titulosPatentesService: TitulosPatentesService,
    public confirmationService: ConfirmationService,
    public messageService: MessageService
  ) {
    super(titulosPatentesService, confirmationService, messageService);
  }
  pesquisar(pagina = 0) {
    this.filtro.pagina = pagina;
    this.titulosPatentesService.pesquisar(this.filtro)
      .then(resultado => {
        this.loading = false;
        this.filtro.totalRegistros = resultado.total;
        this.resources = resultado.titulosPatentes;
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
    if (event.filters.descricao) {
      this.filtro.params = this.filtro.params.append('descricao', event.filters.descricao.value);
    }
    if (event.filters.id) {
      this.filtro.params = this.filtro.params.append('id', event.filters.id.value);
    }
    this.pesquisar(pagina);
  }
  deleteResource(resource: TitulosPatentes) {
    this.confirmationService.confirm({
      accept: () => {
        this.delete(resource, this.deleteSucess, this.deleteFail);
      },
      reject: () => {

      }
    });
  }
  deleteSucess(messageService: MessageService) {
    console.log('deletado');
    messageService.add({ severity: 'success', summary: 'Successo', detail: 'Deletado Com Sucesso!' });
    this.pesquisar(0);
  }

  deleteFail(error: any, messageService: MessageService) {
    console.log('error');
    console.log(error.error[0].mensagemUsuario);
    messageService.add({ severity: 'error', summary: 'Erro', detail: error.error[0].mensagemUsuario });
    this.pesquisar(0);
  }
}
