import { PaisesCadastroComponent } from './paises-cadastro/paises-cadastro.component';
import { PaisesPesquisaComponent } from './paises-pesquisa/paises-pesquisa.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [

{  path: '',        component: PaisesPesquisaComponent },
{  path: 'new',    component: PaisesCadastroComponent },
{  path: ':id/edit', component: PaisesCadastroComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PaisesRoutingModule { }
