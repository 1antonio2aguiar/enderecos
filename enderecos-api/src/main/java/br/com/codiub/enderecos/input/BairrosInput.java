package br.com.codiub.enderecos.input;

import br.com.codiub.enderecos.entity.Distritos;
import lombok.Data;

@Data
public class BairrosInput {
	
	//Adamis 24022021
	private Long id;
	private Distritos distritos;
	private String nome;
	private String nomeAbreviado;
	private String usuario;
	//private Date dataAlteracao;
	private DadosBairrosInput dadosBairros;
	
	public String getNome() {
		return nome == null ? "null" :nome.toUpperCase();
	}
	
	public String getnomeAbreviado() {
		return nomeAbreviado == null ? "null" :nomeAbreviado.toUpperCase();
	}
	
	public String getUsuario() {
		return usuario == null ? "null" :usuario.toUpperCase();
	}
}
