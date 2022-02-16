package br.com.codiub.enderecos.repository.bairros;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.enderecos.entity.Bairros;
import br.com.codiub.enderecos.entity.Cidades;
import br.com.codiub.enderecos.filter.BairrosFilter;
import br.com.codiub.enderecos.filter.CidadesFilter;

public interface BairrosRepositoryQuery {

  public Page<Bairros> filtrar(BairrosFilter bairrosFilter, Pageable pageable);
  public List<Bairros> filtrar(BairrosFilter bairrosFilter);
  
}
