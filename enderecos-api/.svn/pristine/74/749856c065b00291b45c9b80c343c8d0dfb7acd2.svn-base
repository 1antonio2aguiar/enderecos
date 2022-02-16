import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { VwPessoasService } from './../vw-pessoas.service';
import { VwPessoas} from './../../../shared/models/vw-pessoas';
import { Component, ViewChild } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { VwPessoasFiltro } from './vw-pessoas-filtro';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-vw-pessoas-pesquisa',
  templateUrl: './vw-pessoas-pesquisa.component.html',
  styleUrls: ['./vw-pessoas-pesquisa.component.css']
})
export class VwPessoasPesquisaComponent extends BaseResourceListComponent<VwPessoas> {
  filtro = new VwPessoasFiltro();
  resources = [];
  loading = true;
  constructor(
		private vwPessoasService: VwPessoasService,
		public confirmationService: ConfirmationService,
		public messageService: MessageService
	 ) {
    super(vwPessoasService, confirmationService, messageService);
  }
  pesquisar(pagina = 0) {
    this.filtro.pagina = pagina;
    this.vwPessoasService.pesquisar(this.filtro)
      .then(resultado => {
        this.loading = false;
        this.filtro.totalRegistros = resultado.total;
        this.resources = resultado.vwPessoas;
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
    if (event.filters.cnpj) {
      this.filtro.params = this.filtro.params.append('cnpj', event.filters.cnpj.value);
    }
    if (event.filters.cpf) {
      this.filtro.params = this.filtro.params.append('cpf', event.filters.cpf.value);
    }
    if (event.filters.fisica_juridica) {
      this.filtro.params = this.filtro.params.append('fisica_juridica', event.filters.fisica_juridica.value);
    }
    if (event.filters.id) {
      this.filtro.params = this.filtro.params.append('id', event.filters.id.value);
    }
    if (event.filters.nome) {
      this.filtro.params = this.filtro.params.append('nome', event.filters.nome.value);
    }
    if (event.filters.situacao) {
      this.filtro.params = this.filtro.params.append('situacao', event.filters.situacao.value);
    }
    this.pesquisar(pagina);
  }
deleteResource(resource: VwPessoas) {
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