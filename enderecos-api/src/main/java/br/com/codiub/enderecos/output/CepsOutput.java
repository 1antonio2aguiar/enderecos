package br.com.codiub.enderecos.output;

import br.com.codiub.enderecos.entity.Bairros;
import br.com.codiub.enderecos.entity.Distritos;
import br.com.codiub.enderecos.entity.Logradouros;
import lombok.Data;

@Data
public class CepsOutput {
	
	private Long id;
	private Distritos distritos;
	private Bairros bairros;
	private Logradouros logradouros;
	private String cep;
	private Long numeroIni;
	private Long numeroFim;
	private String identificacao;
	private String usuario;

}
