package br.com.codiub.enderecos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codiub.enderecos.entity.Estados;
import br.com.codiub.enderecos.repository.estados.EstadosRepositoryQuery;

public interface EstadosRepository extends JpaRepository<Estados, Long>, EstadosRepositoryQuery {}
