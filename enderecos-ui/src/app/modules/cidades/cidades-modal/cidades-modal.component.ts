import { DistritosService } from './../../distritos/distritos.service';
import { CidadesFiltro } from './../cidades-pesquisa/cidades-filtro';
import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { Cidades} from './../../../shared/models/cidades';
import { Component, ViewChild } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { HttpParams } from '@angular/common/http';
import { DynamicDialogRef } from 'primeng';


@Component({
  selector: 'app-cidades-modal',
  templateUrl: './cidades-modal.component.html',
  styleUrls: ['./cidades-modal.component.css']
})
export class CidadesModalComponent extends BaseResourceListComponent<Cidades> {
  filtro = new CidadesFiltro();
  resources = [];
  loading = true;

  constructor(
    private distritosService: DistritosService,
    public confirmationService: ConfirmationService,
    public messageService: MessageService,
    public ref: DynamicDialogRef
  ) {
    super(distritosService, confirmationService, messageService);
  }

  pesquisar(pagina = 0) {
    this.filtro.pagina = pagina;
    this.distritosService.pesquisar(this.filtro)
      .then(resultado => {
        //console.log(resultado);

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
      this.filtro.params = this.filtro.params.append('cidadesFilter.id', event.filters.id.value);
    }

    if (event.filters.cidade) {
      this.filtro.params = this.filtro.params.append('cidadesFilter.nome', event.filters.cidade.value);
    }

    if (event.filters.nome) {
      this.filtro.params = this.filtro.params.append('nome', event.filters.nome.value);
    }

    this.pesquisar(pagina);
  }

  selecItem(cidades){
    this.ref.close(cidades);
  }

}
