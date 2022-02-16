package br.com.codiub.enderecos.repository.titulosPatentes;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.enderecos.entity.TiposLogradouros;
import br.com.codiub.enderecos.entity.TitulosPatentes;
import br.com.codiub.enderecos.filter.TiposLogradourosFilter;
import br.com.codiub.enderecos.filter.TitulosPatentesFilter;

public interface TitulosPatentesRepositoryQuery {

  public Page<TitulosPatentes> filtrar(
    TitulosPatentesFilter titulosPatentesFilter, Pageable pageable);
  	public List<TitulosPatentes> filtrar(TitulosPatentesFilter titulosPatentesFilter);
}
