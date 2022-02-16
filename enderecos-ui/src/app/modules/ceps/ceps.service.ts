import { HttpHeaders } from '@angular/common/http';
 import { Injectable, Injector } from '@angular/core';
 import { Ceps } from './../../shared/models/ceps';
 import { BaseResourceService } from './../../shared/services/base-resource.service';
 import { CepsFiltro } from './ceps-pesquisa/ceps-filtro';
 import { environment } from 'src/environments/environment';

 @Injectable({
   providedIn: 'root'
 })
 export class CepsService extends BaseResourceService<Ceps> {

 	constructor(protected injector: Injector) {
 	 super(environment.apiUrl + 'ceps', injector, Ceps.fromJson);
 	}

  header = new HttpHeaders(
  {
    'Content-Type': 'application/json'
  });

 	pesquisar(filtro: CepsFiltro): Promise<any> {
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
      const ceps = response.content;
      const resultado = {
        ceps,
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

  createCep(jsonForm){
    return this.http.post(this.apiPath, JSON.stringify(jsonForm),{ headers: this.header })
    .toPromise()
    .then(response => {return response; });
  }

  updateCep(jsonForm): Promise<any> {
    return this.http.put(this.apiPath+'/'+jsonForm.id, jsonForm, { headers: this.header })
    .toPromise()
    .then(response => response);
  }

}
