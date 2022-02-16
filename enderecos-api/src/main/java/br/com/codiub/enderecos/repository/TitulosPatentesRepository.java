package br.com.codiub.enderecos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codiub.enderecos.entity.TitulosPatentes;
import br.com.codiub.enderecos.repository.titulosPatentes.TitulosPatentesRepositoryQuery;

public interface TitulosPatentesRepository
    extends JpaRepository<TitulosPatentes, Long>, TitulosPatentesRepositoryQuery {}
