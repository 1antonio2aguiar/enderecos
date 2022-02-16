package br.com.codiub.enderecos.filter;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class LogradourosFilter {
  private DistritosFilter distritosFilter;
  private BigDecimal id;
  private String nome;
  private String nomeReduzido;
  private String nomeSimplificado;
  private TiposLogradourosFilter tiposLogradourosFilter;
}
