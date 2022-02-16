package br.com.codiub.enderecos.repository.tiposLogradouros;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.enderecos.entity.TiposLogradouros;
import br.com.codiub.enderecos.filter.TiposLogradourosFilter;

public interface TiposLogradourosRepositoryQuery {

  public Page<TiposLogradouros> filtrar(
    TiposLogradourosFilter tiposLogradourosFilter, Pageable pageable);
  	public List<TiposLogradouros> filtrar(TiposLogradourosFilter tiposLogradourosFilter);
}
