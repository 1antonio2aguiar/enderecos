import { BaseResourceModel } from "./base-resource.model";
import { Estados } from "./estados";

export class Cidades extends BaseResourceModel {
  constructor(
    public codigo_ibge?: number,
    public codigo_inep?: number,
    public codigo_sicom?: number,
    public dataAlteracao?: Date,
    public estado?: Estados,
    public estadosId?: number,
    public id?: number,
    public nome?: string,
    public sigla?: string,
    public usuario?: string
  ) {
    super();
  }


  static fromJson(jsonData: any): Cidades {
    //jsonData["estadosId"] = jsonData["estados"]["id"];

    //const { cidades } = jsonData;

    const cidades = {
      ...jsonData,
      estadosId: jsonData["estados"]["id"],
    };
    return Object.assign(new Cidades(), cidades);
  }


  static toJson(obj: any): Cidades{
    delete obj['estados'];
    return Object.assign(new Cidades(), obj);
  }

}
