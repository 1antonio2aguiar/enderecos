import { BaseResourceModel } from './base-resource.model';
import { Paises } from './paises';

export class Estados extends BaseResourceModel {
constructor(
public codigo_inep?: number,
public id?: number,
public nome?: string,
public pais?: Paises,
public sigla?: string,
) {
super();
}
static fromJson(jsonData: any): Estados {
    return Object.assign(new Estados(), jsonData);
}
}
