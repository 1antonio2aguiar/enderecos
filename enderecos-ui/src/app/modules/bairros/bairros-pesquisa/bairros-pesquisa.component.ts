import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { BairrosService } from './../bairros.service';
import { Bairros} from './../../../shared/models/bairros';
import { Component, ViewChild } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { BairrosFiltro } from './bairros-filtro';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-bairros-pesquisa',
  templateUrl: './bairros-pesquisa.component.html',
  styleUrls: ['./bairros-pesquisa.component.css']
})
export class BairrosPesquisaComponent extends BaseResourceListComponent<Bairros> {
  filtro = new BairrosFiltro();
  resources = [];
  loading = true;
  constructor(
		private bairrosService: BairrosService,
		public confirmationService: ConfirmationService,
		public messageService: MessageService
	 ) {
    super(bairrosService, confirmationService, messageService);
  }

  pesquisar(pagina = 0) {
    this.filtro.pagina = pagina;
    this.bairrosService.pesquisar(this.filtro)
      .then(resultado => {
        this.loading = false;
        this.filtro.totalRegistros = resultado.total;
        this.resources = resultado.bairros;
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
      this.filtro.params = this.filtro.params.append('distritosFilter.cidadesFilter.nome', event.filters.cidade.value);
    }

    if (event.filters.distrito) {
      this.filtro.params = this.filtro.params.append('distritosFilter.nome', event.filters.distrito.value);
    }

    if (event.filters.nome) {
      this.filtro.params = this.filtro.params.append('nome', event.filters.nome.value);
    }
    if (event.filters.nomeAbreviado) {
      this.filtro.params = this.filtro.params.append('nomeAbreviado', event.filters.nomeAbreviado.value);
    }
    this.pesquisar(pagina);
  }

  deleteResource(resource: Bairros) {
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
