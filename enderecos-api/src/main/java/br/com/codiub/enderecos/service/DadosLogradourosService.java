package br.com.codiub.enderecos.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.codiub.enderecos.entity.DadosLogradouros;
import br.com.codiub.enderecos.repository.DadosLogradourosRepository;

@Service
public class DadosLogradourosService {

  @Autowired private DadosLogradourosRepository dadosLogradourosRepository;

  public DadosLogradouros atualizar(Long codigo, DadosLogradouros dadosLogradouros) {
    DadosLogradouros dadosLogradourosSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(dadosLogradouros, dadosLogradourosSalva, "id");
    return dadosLogradourosRepository.save(dadosLogradourosSalva);
  }

  public DadosLogradouros buscarPeloCodigo(Long codigo) {
    Optional<DadosLogradouros> dadosLogradourosSalva = dadosLogradourosRepository.findById(codigo);
    if (dadosLogradourosSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return dadosLogradourosSalva.get();
  }
}
