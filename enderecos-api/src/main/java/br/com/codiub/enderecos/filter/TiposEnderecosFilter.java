package br.com.codiub.enderecos.filter;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TiposEnderecosFilter {
  private String descricao;
  private BigDecimal id;
}
