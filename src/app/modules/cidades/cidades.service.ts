 import { Injectable, Injector } from '@angular/core';
 import { Cidades } from './../../shared/models/cidades';
 import { BaseResourceService } from './../../shared/services/base-resource.service';
 import { CidadesFiltro } from './cidades-pesquisa/cidades-filtro';
 import { environment } from 'src/environments/environment';

 @Injectable({
   providedIn: 'root'
 })
 export class CidadesService extends BaseResourceService<Cidades> {

 	constructor(protected injector: Injector) {
 	 super(environment.apiUrl + 'cidades', injector, Cidades.fromJson);
 	}

 	pesquisar(filtro: CidadesFiltro): Promise<any> {
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
        const cidades = response.content;
        const resultado = {
          cidades,
          total: response.totalElements
        };
        return resultado;
      });
	}

	listAll(): Promise<any> { 
     return this.http.get<any>( this.apiPath + '/' ) 
       .toPromise() 
       .then(response => response.content); 
	} 

}
