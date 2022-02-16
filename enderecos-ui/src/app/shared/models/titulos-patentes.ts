import { BaseResourceModel } from './base-resource.model';

export class TitulosPatentes extends BaseResourceModel {
constructor(
public descricao?: string,
public id?: number,
) {
super();
}
static fromJson(jsonData: any): TitulosPatentes {
    return Object.assign(new TitulosPatentes(), jsonData);
}
}
