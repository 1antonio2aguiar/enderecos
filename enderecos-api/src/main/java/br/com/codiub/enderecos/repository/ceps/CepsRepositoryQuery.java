package br.com.codiub.enderecos.repository.ceps;

import br.com.codiub.enderecos.entity.Ceps;
import br.com.codiub.enderecos.filter.CepsFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CepsRepositoryQuery {

  public Page<Ceps> filtrar(CepsFilter cepsFilter, Pageable pageable);
}
