package br.com.codiub.enderecos.filter;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class CidadesFilter {
  private BigDecimal id;
  private String nome;
  
  private EstadosFilter estadosFilter;
}

