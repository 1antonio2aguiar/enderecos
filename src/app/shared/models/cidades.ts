import { BaseResourceModel } from './base-resource.model';
import { Estados } from './estados';

export class Cidades extends BaseResourceModel {
constructor(
public codigo_ibge?: number,
public codigo_inep?: number,
public codigo_sicom?: number,
public dataAlteracao?: Date,
public estado?: Estados,
public id?: number,
public nome?: string,
public sigla?: string,
public usuario?: string,
) {
super();
}
static fromJson(jsonData: any): Cidades {
    return Object.assign(new Cidades(), jsonData);
}
}
