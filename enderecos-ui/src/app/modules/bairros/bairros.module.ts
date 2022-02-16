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
import { DropdownModule } from 'primeng/dropdown';
import { DialogService } from 'primeng/dynamicdialog';

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BairrosRoutingModule } from './bairros-routing.module';
import { BairrosCadastroComponent } from './bairros-cadastro/bairros-cadastro.component';
import { BairrosPesquisaComponent } from './bairros-pesquisa/bairros-pesquisa.component';
import { BairrosModalNomeComponent } from './bairros-modal-nome/bairros-modal-nome.component';


@NgModule({
  declarations: [BairrosCadastroComponent, BairrosPesquisaComponent, BairrosModalNomeComponent],
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
    BairrosRoutingModule
  ],
  exports:[
    BairrosModalNomeComponent
  ],
  providers: [
    DialogService
  ],

})
export class BairrosModule { }
