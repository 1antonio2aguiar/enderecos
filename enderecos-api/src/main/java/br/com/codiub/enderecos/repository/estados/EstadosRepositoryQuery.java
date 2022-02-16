package br.com.codiub.enderecos.repository.estados;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.enderecos.entity.Estados;
import br.com.codiub.enderecos.filter.EstadosFilter;

public interface EstadosRepositoryQuery {

  public Page<Estados> filtrar(EstadosFilter estadosFilter, Pageable pageable);
  public List<Estados> filtrar(EstadosFilter paisesFilter);
}
