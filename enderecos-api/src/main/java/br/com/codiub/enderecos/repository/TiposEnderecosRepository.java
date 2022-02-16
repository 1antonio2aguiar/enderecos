package br.com.codiub.enderecos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codiub.enderecos.entity.TiposEnderecos;
import br.com.codiub.enderecos.repository.tiposEnderecos.TiposEnderecosRepositoryQuery;

public interface TiposEnderecosRepository
    extends JpaRepository<TiposEnderecos, Long>, TiposEnderecosRepositoryQuery {}
