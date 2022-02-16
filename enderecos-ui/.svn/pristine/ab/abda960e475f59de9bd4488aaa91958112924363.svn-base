import { DistritosCadastroComponent } from './distritos-cadastro/distritos-cadastro.component';
import { DistritosPesquisaComponent } from './distritos-pesquisa/distritos-pesquisa.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [

{  path: '',        component: DistritosPesquisaComponent },
{  path: 'new',    component: DistritosCadastroComponent },
{  path: ':id/edit', component: DistritosCadastroComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DistritosRoutingModule { }
