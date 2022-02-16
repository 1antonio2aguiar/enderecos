 import { Injectable, Injector } from '@angular/core';
 import { VwPessoas } from './../../shared/models/vw-pessoas';
 import { BaseResourceService } from './../../shared/services/base-resource.service';
 import { VwPessoasFiltro } from './vw-pessoas-pesquisa/vw-pessoas-filtro';
 import { environment } from 'src/environments/environment';

 @Injectable({
   providedIn: 'root'
 })
 export class VwPessoasService extends BaseResourceService<VwPessoas> {

 	constructor(protected injector: Injector) {
 	 super(environment.apiUrl + 'vwPessoas', injector, VwPessoas.fromJson);
 	}

 	pesquisar(filtro: VwPessoasFiltro): Promise<any> {
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
        const vwPessoas = response.content;
        const resultado = {
          vwPessoas,
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
