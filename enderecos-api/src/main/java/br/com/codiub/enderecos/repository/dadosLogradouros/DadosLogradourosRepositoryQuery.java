package br.com.codiub.enderecos.repository.dadosLogradouros;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.enderecos.entity.DadosLogradouros;
import br.com.codiub.enderecos.filter.DadosLogradourosFilter;

public interface DadosLogradourosRepositoryQuery {

  public Page<DadosLogradouros> filtrar(
      DadosLogradourosFilter dadosLogradourosFilter, Pageable pageable);
}
