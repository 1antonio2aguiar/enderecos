package br.com.codiub.enderecos.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.codiub.enderecos.entity.TiposEnderecos;
import br.com.codiub.enderecos.repository.TiposEnderecosRepository;

@Service
public class TiposEnderecosService {

  @Autowired private TiposEnderecosRepository tiposEnderecosRepository;

  public TiposEnderecos atualizar(Long codigo, TiposEnderecos tiposEnderecos) {
    TiposEnderecos tiposEnderecosSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(tiposEnderecos, tiposEnderecosSalva, "id");
    return tiposEnderecosRepository.save(tiposEnderecosSalva);
  }

  public TiposEnderecos buscarPeloCodigo(Long codigo) {
    Optional<TiposEnderecos> tiposEnderecosSalva = tiposEnderecosRepository.findById(codigo);
    if (tiposEnderecosSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return tiposEnderecosSalva.get();
  }
}
