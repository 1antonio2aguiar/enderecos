package br.com.codiub.enderecos.repository;

import br.com.codiub.enderecos.entity.Ceps;
import br.com.codiub.enderecos.repository.ceps.CepsRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CepsRepository extends JpaRepository<Ceps, Long>, CepsRepositoryQuery {}
