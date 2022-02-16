package br.com.codiub.enderecos.input;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data	
public class CidadesInput {
	private Long id;
	private Long estadosId;
	
	@NotNull
	@Length(min = 1)
	private String nome;
	private String sigla;
	private Long codigoSicom;
	private Long codigoIbge;
	private Long codigoInep;
	private String usuario;
	
	
	public String getNome() {
		return nome == null ? "null" :nome.toUpperCase();
	}
	
	public String getSigla() {
		return sigla == null ? "null" :sigla.toUpperCase();
	}	
	
	public String getUsuario() {
		return usuario == null ? "null" :usuario.toUpperCase();
	}
	
}
