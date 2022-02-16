package br.com.codiub.enderecos.filter;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class DadosBairrosFilter {
  private BairrosFilter bairrosFilter;
  private BigDecimal id;
  private String nomePopular;
}
