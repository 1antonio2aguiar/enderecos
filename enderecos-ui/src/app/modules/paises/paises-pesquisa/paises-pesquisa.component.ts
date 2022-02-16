import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { PaisesService } from './../paises.service';
import { Paises} from './../../../shared/models/paises';
import { Component, ViewChild } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { PaisesFiltro } from './paises-filtro';
import { HttpParams } from '@angular/common/http';


@Component({
  selector: 'app-paises-pesquisa',
  templateUrl: './paises-pesquisa.component.html',
  styleUrls: ['./paises-pesquisa.component.css']
})
export class PaisesPesquisaComponent extends BaseResourceListComponent<Paises> {
  filtro = new PaisesFiltro();
  resources = [];
  loading = true;
  constructor(
    private paisesService: PaisesService,
    public confirmationService: ConfirmationService,
    public messageService: MessageService
  ) {
    super(paisesService, confirmationService, messageService);
  }
  pesquisar(pagina = 0) {
    this.filtro.pagina = pagina;
    this.paisesService.pesquisar(this.filtro)
      .then(resultado => {
        this.loading = false;
        this.filtro.totalRegistros = resultado.total;
        this.resources = resultado.paises;
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
    if (event.filters.codigo_inep) {
      this.filtro.params = this.filtro.params.append('codigo_inep', event.filters.codigo_inep.value);
    }
    if (event.filters.id) {
      this.filtro.params = this.filtro.params.append('id', event.filters.id.value);
    }
    if (event.filters.nacionalidade) {
      this.filtro.params = this.filtro.params.append('nacionalidade', event.filters.nacionalidade.value);
    }
    if (event.filters.nome) {
      this.filtro.params = this.filtro.params.append('nome', event.filters.nome.value);
    }
    if (event.filters.sigla) {
      this.filtro.params = this.filtro.params.append('sigla', event.filters.sigla.value);
    }
    this.pesquisar(pagina);
  }
deleteResource(resource: Paises) {
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
