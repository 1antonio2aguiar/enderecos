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
import {DropdownModule} from 'primeng/dropdown';
import { DialogService } from 'primeng/dynamicdialog';


import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DistritosRoutingModule } from './distritos-routing.module';
import { DistritosCadastroComponent } from './distritos-cadastro/distritos-cadastro.component';
import { DistritosPesquisaComponent } from './distritos-pesquisa/distritos-pesquisa.component';


@NgModule({
declarations: [DistritosCadastroComponent, DistritosPesquisaComponent],
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
  DropdownModule,
  CommonModule,
  DistritosRoutingModule
  ],
  providers: [DialogService]
})
export class DistritosModule { }
