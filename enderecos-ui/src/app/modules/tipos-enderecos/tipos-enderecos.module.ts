import { SharedModule } from './../../shared/shared.module';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { IMaskModule } from 'angular-imask';
import { CalendarModule } from 'primeng/calendar';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { KeyFilterModule } from 'primeng/keyfilter';
import { TableModule } from 'primeng/table';
import { PanelModule } from 'primeng/panel';


import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TiposEnderecosRoutingModule } from './tipos-enderecos-routing.module';
import { TiposEnderecosCadastroComponent } from './tipos-enderecos-cadastro/tipos-enderecos-cadastro.component';
import { TiposEnderecosPesquisaComponent } from './tipos-enderecos-pesquisa/tipos-enderecos-pesquisa.component';


@NgModule({
declarations: [TiposEnderecosCadastroComponent, TiposEnderecosPesquisaComponent],
imports: [
	SharedModule,
   IMaskModule,
   CalendarModule,
   CardModule,
   InputTextModule,
   KeyFilterModule,
   TableModule,
   PanelModule,
   MessagesModule,
   MessageModule,
   ToastModule,
    CommonModule,
    TiposEnderecosRoutingModule
  ]
})
export class TiposEnderecosModule { }
