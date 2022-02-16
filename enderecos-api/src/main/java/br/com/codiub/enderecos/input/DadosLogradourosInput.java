package br.com.codiub.enderecos.input;

import java.util.Date;

import br.com.codiub.enderecos.output.PessoasOutput;
import lombok.Data;

@Data
public class DadosLogradourosInput {
	
	private Date dataCriacao;
	private String leiCriacao;
	private Date dataDecreto;
	private String decreto;
	private Date dataPortaria;
	private String portaria;
	private Long pessoaVereador;
	private String observacao;
	private String usuario;
	private PessoasOutput vwPessoas;
	
	public String getLeiCriacao() {
		return leiCriacao == null ?"null" :leiCriacao.toUpperCase();
	}
	
	public String getDecreto() {
		return decreto == null ?"null" :decreto.toUpperCase();
	}
	
	public String getPortaria() {
		return portaria == null ? "null" :portaria.toUpperCase();
	}
	
	public String getObservacao() {
		return observacao == null ? "null" :observacao.toUpperCase();
	}
	
	public String getUsuario() {
		return usuario == null ? "null" :usuario.toUpperCase();
	}
}
