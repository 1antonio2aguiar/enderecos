package br.com.codiub.enderecos.repository.cidades;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.enderecos.entity.Cidades;
import br.com.codiub.enderecos.filter.CidadesFilter;

public interface CidadesRepositoryQuery {

  public Page<Cidades> filtrar(CidadesFilter cidadesFilter, Pageable pageable);
  public List<Cidades> filtrar(CidadesFilter cidadesFilter);
}
