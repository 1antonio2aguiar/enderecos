package br.com.codiub.enderecos.input;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.codiub.enderecos.entity.Cidades;
import lombok.Data;

@Data
public class DistritosInput {
	private Long id;
	private Cidades cidades;
	@NotNull
	@Length(min = 3)
	private String nome;
	private Long codigoInep;
	private String usuario;
	
	public String getNome() {
		return nome == null ? "null" :nome.toUpperCase();
	}
	
	public String getUsuario() {
		return usuario == null ? "null" :usuario.toUpperCase();
	}
}

