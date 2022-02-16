package br.com.codiub.enderecos.output;

import lombok.Data;

@Data
public class PessoasOutput {
	
	private Long id;	
	private String nome;	
	private String nomeVereador;
	private String nomeLoteadora;
	private String fisicaJuridica;	
	private String situacao;	
	private Long cpf;	
	private Long cnpj;
	
}
