package br.com.codiub.enderecos.repository.logradouros;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.enderecos.entity.Bairros;
import br.com.codiub.enderecos.entity.Logradouros;
import br.com.codiub.enderecos.filter.BairrosFilter;
import br.com.codiub.enderecos.filter.LogradourosFilter;

public interface LogradourosRepositoryQuery {

  public Page<Logradouros> filtrar(LogradourosFilter logradourosFilter, Pageable pageable);
  public List<Logradouros> filtrar(LogradourosFilter logradourosFilter);
  
}
