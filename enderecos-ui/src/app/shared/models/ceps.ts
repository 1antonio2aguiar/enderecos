import { BaseResourceModel } from './base-resource.model';
import { Bairros } from './bairros';
import { Logradouros } from './logradouros';

export class Ceps extends BaseResourceModel {
constructor(
public bairro?: Bairros,
public cep?: string,
public codigo_correios?: number,
public dataAlteracao?: Date,
public id?: number,
public identificacao?: string,
public logradouro?: Logradouros,
public numero_fim?: number,
public numero_ini?: number,
public usuario?: string,
) {
super();
}
static fromJson(jsonData: any): Ceps {
    return Object.assign(new Ceps(), jsonData);
}
}
