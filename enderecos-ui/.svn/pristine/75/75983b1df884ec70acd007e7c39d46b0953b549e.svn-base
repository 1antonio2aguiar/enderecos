import { TitulosPatentesCadastroComponent } from './titulos-patentes-cadastro/titulos-patentes-cadastro.component';
import { TitulosPatentesPesquisaComponent } from './titulos-patentes-pesquisa/titulos-patentes-pesquisa.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [

{  path: '',        component: TitulosPatentesPesquisaComponent },
{  path: 'new',    component: TitulosPatentesCadastroComponent },
{  path: ':id/edit', component: TitulosPatentesCadastroComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TitulosPatentesRoutingModule { }
