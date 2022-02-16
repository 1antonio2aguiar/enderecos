package br.com.codiub.enderecos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codiub.enderecos.entity.Bairros;
import br.com.codiub.enderecos.repository.bairros.BairrosRepositoryQuery;

public interface BairrosRepository 
	extends JpaRepository<Bairros, Long>, BairrosRepositoryQuery {}
