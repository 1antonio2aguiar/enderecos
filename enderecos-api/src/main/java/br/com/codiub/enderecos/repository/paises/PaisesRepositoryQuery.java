package br.com.codiub.enderecos.repository.paises;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.enderecos.entity.Paises;
import br.com.codiub.enderecos.filter.PaisesFilter;

public interface PaisesRepositoryQuery {

  public Page<Paises> filtrar(PaisesFilter paisesFilter, Pageable pageable);
  public List<Paises> filtrar(PaisesFilter paisesFilter);
}
