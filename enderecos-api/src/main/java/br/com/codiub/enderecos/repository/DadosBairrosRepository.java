package br.com.codiub.enderecos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codiub.enderecos.entity.DadosBairros;
import br.com.codiub.enderecos.repository.dadosBairros.DadosBairrosRepositoryQuery;

public interface DadosBairrosRepository extends JpaRepository<DadosBairros, Long>, DadosBairrosRepositoryQuery {
	
	//AA
	List<DadosBairros> findByBairrosId(Long codigo);
	
}