package br.com.codiub.enderecos.filter;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class EstadosFilter {

private BigDecimal id;
private String nome;
private String sigla;

private PaisesFilter paisesFilter;
}
