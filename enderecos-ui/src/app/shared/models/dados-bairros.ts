import { VwPessoas } from './vw-pessoas';
import { BaseResourceModel } from './base-resource.model';

export class DadosBairros {
  constructor(
    dataCriacao?: Date,
    dataDecreto?: Date,
    dataPortaria?: Date,
    decreto?: string,
    leiCriacao?: string,
    nomePopular?: string,
    pessoaLoteadora?: number,
    pessoaVereador?: number,
    portaria?: string,
    usuario?: string,
    zonaRural?: string,
    vwPessoas?: VwPessoas,
  ) {
  }
  static fromJson(jsonData: any): DadosBairros {
    return Object.assign(new DadosBairros(), jsonData);
  }
}
