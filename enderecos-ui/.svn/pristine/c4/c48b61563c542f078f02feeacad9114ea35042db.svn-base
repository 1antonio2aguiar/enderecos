import { BairrosCadastroComponent } from './bairros-cadastro/bairros-cadastro.component';
import { BairrosPesquisaComponent } from './bairros-pesquisa/bairros-pesquisa.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [

{  path: '',        component: BairrosPesquisaComponent },
{  path: 'new',    component: BairrosCadastroComponent },
{  path: ':id/edit', component: BairrosCadastroComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BairrosRoutingModule { }
