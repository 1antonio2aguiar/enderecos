package br.com.codiub.enderecos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codiub.enderecos.entity.Cidades;
import br.com.codiub.enderecos.repository.cidades.CidadesRepositoryQuery;

public interface CidadesRepository extends JpaRepository<Cidades, Long>, CidadesRepositoryQuery {}
