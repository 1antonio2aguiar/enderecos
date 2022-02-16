import { BaseResourceModel } from './base-resource.model';

export class LogCidades extends BaseResourceModel {
constructor(
public alteracao?: string,
public cidade?: number,
public codigo_ibge?: number,
public codigo_inep?: number,
public codigo_sicom?: number,
public dataAlteracao?: Date,
public estado?: number,
public id?: number,
public nome?: string,
public sigla?: string,
public usuario?: string,
) {
super();
}
static fromJson(jsonData: any): LogCidades {
    return Object.assign(new LogCidades(), jsonData);
}
}
