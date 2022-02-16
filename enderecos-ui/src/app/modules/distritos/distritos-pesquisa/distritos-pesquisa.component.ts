import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { DistritosService } from './../distritos.service';
import { Distritos} from './../../../shared/models/distritos';
import { Component, ViewChild } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { DistritosFiltro } from './distritos-filtro';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-distritos-pesquisa',
  templateUrl: './distritos-pesquisa.component.html',
  styleUrls: ['./distritos-pesquisa.component.css']
})
export class DistritosPesquisaComponent extends BaseResourceListComponent<Distritos> {
  filtro = new DistritosFiltro();
  resources = [];
  loading = true;
  constructor(
		private distritosService: DistritosService,
		public confirmationService: ConfirmationService,
		public messageService: MessageService
	 ) {
    super(distritosService, confirmationService, messageService);
  }
  pesquisar(pagina = 0) {
    this.filtro.pagina = pagina;
    this.distritosService.pesquisar(this.filtro)
      .then(resultado => {
        this.loading = false;
        this.filtro.totalRegistros = resultado.total;
        this.resources = resultado.distritos;
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
      this.filtro.params = this.filtro.params.append('cidadesFilter.nome', event.filters.cidade.value);
    }

    if (event.filters.nome) {
      this.filtro.params = this.filtro.params.append('nome', event.filters.nome.value);
    }

    if (event.filters.codigo_inep) {
      this.filtro.params = this.filtro.params.append('codigo_inep', event.filters.codigo_inep.value);
    }

    this.pesquisar(pagina);
  }

  deleteResource(resource: Distritos) {
    this.confirmationService.confirm({
      accept: () => {
        this.delete(resource, this.deleteSucess, this.deleteFail);
      },
      reject: () => {

      }
    });
  }

  deleteSucess(messageService: MessageService) {
    //console.log('deletado');
    messageService.add({ severity: 'success', summary: 'Successo', detail: 'Deletado Com Sucesso!' });
    this.pesquisar(0);
  }

  deleteFail(error: any, messageService: MessageService) {
    //console.log('error');
    //console.log(error.error[0].mensagemUsuario);
    messageService.add({ severity: 'error', summary: 'Erro', detail: error.error[0].mensagemUsuario });
    this.pesquisar(0);
  }
}
