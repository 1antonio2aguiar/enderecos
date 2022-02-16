import { DadosBairros } from './dados-bairros';
import { BaseResourceModel } from './base-resource.model';
import { Distritos } from './distritos';

export class Bairros extends BaseResourceModel {
  constructor(
    public dataAlteracao?: Date,
    public distrito?: Distritos,
    public id?: number,
    public nome?: string,
    public nomeAbreviado?: string,
    public usuario?: string,
    public dadosBairros?: DadosBairros,
  ) {
    super();
  }
  static fromJson(jsonData): Bairros {
    //delete jsonData.dadosBairros.vwPessoas;
    return Object.assign(new Bairros(), jsonData);
  }
}
