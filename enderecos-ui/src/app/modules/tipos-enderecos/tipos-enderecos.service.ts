 import { Injectable, Injector } from '@angular/core';
 import { TiposEnderecos } from './../../shared/models/tipos-enderecos';
 import { BaseResourceService } from './../../shared/services/base-resource.service';
 import { TiposEnderecosFiltro } from './tipos-enderecos-pesquisa/tipos-enderecos-filtro';
 import { environment } from 'src/environments/environment';

 @Injectable({
   providedIn: 'root'
 })
 export class TiposEnderecosService extends BaseResourceService<TiposEnderecos> {

 	constructor(protected injector: Injector) {
 	 super(environment.apiUrl + 'tiposEnderecos', injector, TiposEnderecos.fromJson);
 	}

 	pesquisar(filtro: TiposEnderecosFiltro): Promise<any> {
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
        const tiposEnderecos = response.content;
        const resultado = {
          tiposEnderecos,
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
