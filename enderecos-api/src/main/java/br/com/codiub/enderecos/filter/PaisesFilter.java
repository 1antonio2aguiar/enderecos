package br.com.codiub.enderecos.filter;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PaisesFilter {

	private BigDecimal id;
	private String nome;
	private String nacionalidade;
  
}
