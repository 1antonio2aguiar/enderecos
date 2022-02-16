import { CepsCadastroComponent } from './ceps-cadastro/ceps-cadastro.component';
import { CepsPesquisaComponent } from './ceps-pesquisa/ceps-pesquisa.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [

  {  path: '',        component: CepsPesquisaComponent },
  {  path: 'new',    component: CepsCadastroComponent },
  {  path: ':id/edit', component: CepsCadastroComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CepsRoutingModule { }
