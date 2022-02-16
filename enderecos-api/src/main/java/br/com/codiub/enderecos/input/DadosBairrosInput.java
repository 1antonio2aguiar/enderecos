package br.com.codiub.enderecos.input;

import java.util.Date;

import br.com.codiub.enderecos.entity.Pessoas;
import br.com.codiub.enderecos.output.PessoasOutput;
import lombok.Data;

@Data
public class DadosBairrosInput {
	
	//Adamis 24022021
	private Date dataCriacao;
	private String leiCriacao;
	private Date dataDecreto;
	private String decreto;
	private Date dataPortaria;
	private String portaria;
	private Long pessoaVereador;
	private Long pessoaLoteadora;
	private String nomePopular;
	private String zonaRural;
	private String usuario;
	private PessoasOutput vwPessoas;
	
	public String getNomePopular() {
		return nomePopular == null ? "null" :nomePopular.toUpperCase();
	}
	
	public String getLeiCriacao() {
		return leiCriacao == null ? "null" :leiCriacao.toUpperCase();
	}
	
	public String getDecreto() {
		return decreto == null ? "null" :decreto.toUpperCase();
	}
	
	public String getPortaria() {
		return portaria == null ? "null" :portaria.toUpperCase();
	}
	
	public String getUsuario() {
		return usuario == null ? "null" :usuario.toUpperCase();
	}
	
}
