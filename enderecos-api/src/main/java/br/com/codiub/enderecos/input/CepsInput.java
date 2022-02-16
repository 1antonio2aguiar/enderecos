package br.com.codiub.enderecos.input;

import lombok.Data;

@Data
public class CepsInput {
	private Long id;
	private long bairros;
	private long logradouros;
	private String cep;
	private Long numeroIni;
	private Long numeroFim;
	private String identificacao;
	private String usuario;
	
	public String getIdentificacao() {
		return identificacao == null ? "null" :identificacao.toUpperCase();
	}
	
	public String getCep() {
		return cep == null ? "null" :cep.toUpperCase();
	}
	
	public String getUsuario() {
		return usuario == null ? "null" :usuario.toUpperCase();
	}
}
