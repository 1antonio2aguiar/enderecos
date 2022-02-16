import { BaseResourceModel } from './base-resource.model';

export class VwPessoas extends BaseResourceModel {
  constructor(
    public cnpj?: number,
    public cpf?: number,
    public fisicaJuridica?: string,
    public id?: number,
    public nome?: string,
    public nomeVereador?: string,
    public nomeLoteadora?: string,
    public situacao?: string,
  ) {
    super();
  }
  static fromJson(jsonData: any): VwPessoas {
    return Object.assign(new VwPessoas(), jsonData);
  }
}
