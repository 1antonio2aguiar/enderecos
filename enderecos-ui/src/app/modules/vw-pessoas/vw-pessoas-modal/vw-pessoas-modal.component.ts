import { VwPessoasFiltro } from './VwPessoasFiltro';
import { VwPessoas } from './../../../shared/models/vw-pessoas';
import { Component, OnInit } from '@angular/core';

import { VwPessoasService } from './../../vw-Pessoas/vw-Pessoas.service';

import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { HttpParams } from '@angular/common/http';
import { DynamicDialogRef } from 'primeng';

@Component({
  selector: 'app-vw-pessoas-modal',
  templateUrl: './vw-pessoas-modal.component.html',
})
export class VwPessoasModalComponent extends BaseResourceListComponent<VwPessoas> {

  filtro = new VwPessoasFiltro();
  resources = [];
  loading = true;

  constructor(
    private vwPessoasService: VwPessoasService,
    public confirmationService: ConfirmationService,
    public messageService: MessageService,
    public ref: DynamicDialogRef
  ) {
    super(vwPessoasService, confirmationService, messageService);
  }

  pesquisar(pagina = 0) {
    this.filtro.pagina = pagina;
    this.vwPessoasService.pesquisar(this.filtro)
      .then(resultado => {
        console.log(resultado);

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

    if (event.filters.id) {
      this.filtro.params = this.filtro.params.append('id', event.filters.id.value);
    }

    if (event.filters.nome) {
      this.filtro.params = this.filtro.params.append('nome', event.filters.nome.value);
    }

    if (event.filters.cpf) {
      this.filtro.params = this.filtro.params.append('cpf', event.filters.cpf.value);
    }

    if (event.filters.cnpj) {
      this.filtro.params = this.filtro.params.append('cnpj', event.filters.cnpj.value);
    }

    this.pesquisar(pagina);
  }

  selecItem(vwPessoas){
    this.ref.close(vwPessoas);
  }

}
