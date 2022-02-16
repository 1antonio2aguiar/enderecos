package br.com.codiub.enderecos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codiub.enderecos.entity.DadosLogradouros;
import br.com.codiub.enderecos.repository.dadosLogradouros.DadosLogradourosRepositoryQuery;

public interface DadosLogradourosRepository
    extends JpaRepository<DadosLogradouros, Long>, DadosLogradourosRepositoryQuery {
	
	//AA
	List<DadosLogradouros> findByLogradourosId(Long codigo);
}
