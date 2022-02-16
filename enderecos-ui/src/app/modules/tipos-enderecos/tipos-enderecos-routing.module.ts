import { TiposEnderecosCadastroComponent } from './tipos-enderecos-cadastro/tipos-enderecos-cadastro.component';
import { TiposEnderecosPesquisaComponent } from './tipos-enderecos-pesquisa/tipos-enderecos-pesquisa.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [

{  path: '',        component: TiposEnderecosPesquisaComponent },
{  path: 'new',    component: TiposEnderecosCadastroComponent },
{  path: ':id/edit', component: TiposEnderecosCadastroComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TiposEnderecosRoutingModule { }
