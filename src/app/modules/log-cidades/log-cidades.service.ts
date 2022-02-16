 import { Injectable, Injector } from '@angular/core';
 import { LogCidades } from './../../shared/models/log-cidades';
 import { BaseResourceService } from './../../shared/services/base-resource.service';
 import { LogCidadesFiltro } from './log-cidades-pesquisa/log-cidades-filtro';
 import { environment } from 'src/environments/environment';

 @Injectable({
   providedIn: 'root'
 })
 export class LogCidadesService extends BaseResourceService<LogCidades> {

 	constructor(protected injector: Injector) {
 	 super(environment.apiUrl + 'logCidades', injector, LogCidades.fromJson);
 	}

 	pesquisar(filtro: LogCidadesFiltro): Promise<any> {
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
        const logCidades = response.content;
        const resultado = {
          logCidades,
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
