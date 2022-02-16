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

import { TiposLogradourosRoutingModule } from './tipos-logradouros-routing.module';
import { TiposLogradourosCadastroComponent } from './tipos-logradouros-cadastro/tipos-logradouros-cadastro.component';
import { TiposLogradourosPesquisaComponent } from './tipos-logradouros-pesquisa/tipos-logradouros-pesquisa.component';


@NgModule({
declarations: [TiposLogradourosCadastroComponent, TiposLogradourosPesquisaComponent],
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
    TiposLogradourosRoutingModule
  ]
})
export class TiposLogradourosModule { }
