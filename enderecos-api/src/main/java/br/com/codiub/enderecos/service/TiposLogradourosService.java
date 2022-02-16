package br.com.codiub.enderecos.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.codiub.enderecos.entity.TiposLogradouros;
import br.com.codiub.enderecos.repository.TiposLogradourosRepository;

@Service
public class TiposLogradourosService {

  @Autowired private TiposLogradourosRepository tiposLogradourosRepository;

  public TiposLogradouros atualizar(Long codigo, TiposLogradouros tiposLogradouros) {
    TiposLogradouros tiposLogradourosSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(tiposLogradouros, tiposLogradourosSalva, "id");
    return tiposLogradourosRepository.save(tiposLogradourosSalva);
  }

  public TiposLogradouros buscarPeloCodigo(Long codigo) {
    Optional<TiposLogradouros> tiposLogradourosSalva = tiposLogradourosRepository.findById(codigo);
    if (tiposLogradourosSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return tiposLogradourosSalva.get();
  }
}
