package br.com.codiub.enderecos.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codiub.enderecos.entity.Pessoas;
import br.com.codiub.enderecos.filter.PessoasFilter;
import br.com.codiub.enderecos.repository.PessoasRepository;


@RestController
@RequestMapping("/pessoas")
public class PessoasResource {
	
	@Autowired
	private PessoasRepository pessoasRepository;
	
	@GetMapping
	public Page<Pessoas> pesquisar(PessoasFilter pessoasFilter, Pageable pageable) {
		return pessoasRepository.filtrar(pessoasFilter, pageable);
	}

}
