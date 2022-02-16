 import { Injectable, Injector } from '@angular/core';
 import { TitulosPatentes } from './../../shared/models/titulos-patentes';
 import { BaseResourceService } from './../../shared/services/base-resource.service';
 import { TitulosPatentesFiltro } from './titulos-patentes-pesquisa/titulos-patentes-filtro';
 import { environment } from 'src/environments/environment';

 @Injectable({
   providedIn: 'root'
 })
 export class TitulosPatentesService extends BaseResourceService<TitulosPatentes> {

 	constructor(protected injector: Injector) {
 	 super(environment.apiUrl + 'titulosPatentes', injector, TitulosPatentes.fromJson);
 	}

 	pesquisar(filtro: TitulosPatentesFiltro): Promise<any> {
  	let params = filtro.params;
   	params = params
                .append('page', filtro.pagina.toString())
                .append('size', filtro.itensPorPagina.toString());
    	return this.http.get<any>(
	    	this.apiPath,
	        {params}
      )
      .toPromise()
      .then(response => {
        const titulosPatentes = response.content;
        const resultado = {
          titulosPatentes,
          total: response.totalElements
        };
        return resultado;
      });
	}

	listAll(): Promise<any> {
     return this.http.get<any>( this.apiPath + '/list' )
       .toPromise()
       .then(response => response);
	}

}
