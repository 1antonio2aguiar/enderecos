import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { PaginaNaoEncontradaComponent } from "./core/pagina-nao-encontrada.component";

const routes: Routes = [
	{path: 'ceps',  loadChildren: () => import('./modules/ceps/ceps.module').then(m => m.CepsModule)},
	{path: 'logradouros',  loadChildren: () => import('./modules/logradouros/logradouros.module').then(m => m.LogradourosModule)},
	{path: 'vw-pessoas',  loadChildren: () => import('./modules/vw-pessoas/vw-pessoas.module').then(m => m.VwPessoasModule)},
	{path: 'distritos',  loadChildren: () => import('./modules/distritos/distritos.module').then(m => m.DistritosModule)},
	{path: 'bairros',  loadChildren: () => import('./modules/bairros/bairros.module').then(m => m.BairrosModule)},
	{path: 'bairros',  loadChildren: () => import('./modules/bairros/bairros.module').then(m => m.BairrosModule)},
  {
    path: "cidades",
    loadChildren: () => import("./modules/cidades/cidades.module").then((m) => m.CidadesModule),
  },
  {
    path: "estados",
    loadChildren: () => import("./modules/estados/estados.module").then((m) => m.EstadosModule),
  },
  {
    path: "titulos-patentes",
    loadChildren: () =>
      import("./modules/titulos-patentes/titulos-patentes.module").then(
        (m) => m.TitulosPatentesModule
      ),
  },
  {
    path: "tipos-logradouros",
    loadChildren: () =>
      import("./modules/tipos-logradouros/tipos-logradouros.module").then(
        (m) => m.TiposLogradourosModule
      ),
  },
  {
    path: "tipos-enderecos",
    loadChildren: () =>
      import("./modules/tipos-enderecos/tipos-enderecos.module").then(
        (m) => m.TiposEnderecosModule
      ),
  },
  {
    path: "paises",
    loadChildren: () => import("./modules/paises/paises.module").then((m) => m.PaisesModule),
  },
  {
    path: "home",
    loadChildren: () => import("./modules/home/home.module").then((m) => m.HomeModule),
  },
  { path: "", redirectTo: "login", pathMatch: "full" },
  {
    path: "login",
    loadChildren: () => import("./seguranca/seguranca.module").then((m) => m.SegurancaModule),
  },
  {
    path: "troca-senha",
    loadChildren: () => import("./seguranca/seguranca.module").then((m) => m.SegurancaModule),
  },
  { path: "pagina-nao-encontrada", component: PaginaNaoEncontradaComponent },
  { path: "**", redirectTo: "pagina-nao-encontrada" },
];

@NgModule({
  //imports: [RouterModule.forRoot(routes)],
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule],
})
export class AppRoutingModule {}
