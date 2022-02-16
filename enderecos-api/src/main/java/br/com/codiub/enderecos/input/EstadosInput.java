package br.com.codiub.enderecos.input;

import lombok.Data;

@Data
public class EstadosInput {	
	private Long id;
	private PaisesInput paises;
	private String nome;
	private String sigla;
	private Long codigoInep;
	
	public String getNome() {
		return nome == null ? "null" :nome.toUpperCase();
	}
	
	public String getSigla() {
		return sigla == null ? "null" :sigla.toUpperCase();
	}
}

