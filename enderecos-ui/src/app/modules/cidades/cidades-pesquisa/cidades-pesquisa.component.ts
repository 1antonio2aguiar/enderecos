import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { CidadesService } from './../cidades.service';
import { Cidades} from './../../../shared/models/cidades';
import { Component, ViewChild } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { CidadesFiltro } from './cidades-filtro';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-cidades-pesquisa',
  templateUrl: './cidades-pesquisa.component.html',
  styleUrls: ['./cidades-pesquisa.component.css']
})
export class CidadesPesquisaComponent extends BaseResourceListComponent<Cidades> {
  filtro = new CidadesFiltro();
  resources = [];
  loading = true;
  constructor(
    private cidadesService: CidadesService,
    public confirmationService: ConfirmationService,
    public messageService: MessageService
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
    if (event.filters.nome) {
      this.filtro.params = this.filtro.params.append('nome', event.filters.nome.value);
    }
    if (event.filters.estado) {
      this.filtro.params = this.filtro.params.append('estadosFilter.sigla', event.filters.estado.value);
    }
    if (event.filters.sigla) {
      this.filtro.params = this.filtro.params.append('sigla', event.filters.sigla.value);
    }

    if (event.filters.codigoIbge) {
      this.filtro.params = this.filtro.params.append('codigoIbge', event.filters.codigoIbge.value);
    }

    if (event.filters.codigoInep) {
      this.filtro.params = this.filtro.params.append('codigoInep', event.filters.codigoInep.value);
    }

    if (event.filters.codigoSicom) {
      this.filtro.params = this.filtro.params.append('codigoSicom', event.filters.codigoSicom.value);
    }

    this.pesquisar(pagina);
  }

  deleteResource(resource: Cidades) {
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
