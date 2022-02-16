import { BaseResourceModel } from './base-resource.model';

export class Paises extends BaseResourceModel {
constructor(
public codigo_inep?: number,
public id?: number,
public nacionalidade?: string,
public nome?: string,
public sigla?: string,
) {
super();
}
static fromJson(jsonData: any): Paises {
    return Object.assign(new Paises(), jsonData);
}
}
