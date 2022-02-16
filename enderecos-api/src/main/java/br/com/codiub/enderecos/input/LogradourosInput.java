package br.com.codiub.enderecos.input;

import br.com.codiub.enderecos.entity.Distritos;
import br.com.codiub.enderecos.entity.TiposLogradouros;
import lombok.Data;

@Data
public class LogradourosInput {
	private Long id;
	private Distritos distritos;
	private TiposLogradouros tiposLogradouros;
	private String nome;
	private String preposicao;
	private String tituloPatente;
	private String nomeReduzido;
	private String nomeSimplificado;
	private String complemento;
	private String usuario;
	private DadosLogradourosInput dadosLogradouros;
	
	public String getNome() {
		return nome == null ? "null" :nome.toUpperCase();
	}
	
	public String getNomeReduzido() {
		return nomeReduzido == null ? "null" :nomeReduzido.toUpperCase();
	}
	
	public String getNomeSimplificado() {
		return nomeSimplificado == null ? "null" :nomeSimplificado.toUpperCase();
	}
	
	public String getComplemento() {
		return complemento == null ? "null" :complemento.toUpperCase();
	}
	
	public String getUsuario() {
		return usuario == null ? "null" :usuario.toUpperCase();
	} 
	
}
