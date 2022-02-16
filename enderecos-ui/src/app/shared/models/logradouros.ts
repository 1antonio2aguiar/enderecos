import { BaseResourceModel } from './base-resource.model';
import { Distritos } from './distritos';
import { TiposLogradouros } from './tipos-logradouros';

export class Logradouros extends BaseResourceModel {
constructor(
public complemento?: string,
public dataAlteracao?: Date,
public distrito?: Distritos,
public id?: number,
public nome?: string,
public nomeReduzido?: string,
public nomeSimplificado?: string,
public preposicao?: string,
public tipoLogradouro?: TiposLogradouros,
public tituloPatente?: string,
public usuario?: string,
) {
super();
}
static fromJson(jsonData: any): Logradouros {
    return Object.assign(new Logradouros(), jsonData);
}
}
