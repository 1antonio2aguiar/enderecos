import { BaseResourceModel } from './base-resource.model';
import { Cidades } from './cidades';

export class Distritos extends BaseResourceModel {
constructor(
public cidade?: Cidades,
public codigo_inep?: number,
public dataAlteracao?: Date,
public id?: number,
public nome?: string,
public usuario?: string,
) {
super();
}
static fromJson(jsonData: any): Distritos {
    return Object.assign(new Distritos(), jsonData);
}
}
