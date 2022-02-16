import { Injectable, Injector } from '@angular/core';
import { Paises } from './../../shared/models/paises';
import { BaseResourceService } from './../../shared/services/base-resource.service';
import { PaisesFiltro } from './paises-pesquisa/paises-filtro';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PaisesService extends BaseResourceService<Paises> {

  constructor(protected injector: Injector) {
    super(environment.apiUrl + 'paises', injector, Paises.fromJson);
  }

  pesquisar(filtro: PaisesFiltro): Promise<any> {
    let params = filtro.params;
    params = params
      .append('page', filtro.pagina.toString())
      .append('size', filtro.itensPorPagina.toString());
    return this.http.get<any>(
      this.apiPath,
      { params }
    )
      .toPromise()
      .then(response => {
        const paises = response.content;
        const resultado = {
          paises,
          total: response.totalElements
        };
        return resultado;
      });
  }

  listAll(): Promise<any> {
    return this.http.get<any>(this.apiPath + '/list')
      .toPromise()
      .then(response => response);
  }

}
