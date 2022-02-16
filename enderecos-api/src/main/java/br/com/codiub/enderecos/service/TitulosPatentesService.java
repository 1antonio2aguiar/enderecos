package br.com.codiub.enderecos.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.codiub.enderecos.entity.TitulosPatentes;
import br.com.codiub.enderecos.repository.TitulosPatentesRepository;

@Service
public class TitulosPatentesService {

  @Autowired private TitulosPatentesRepository titulosPatentesRepository;

  public TitulosPatentes atualizar(Long codigo, TitulosPatentes titulosPatentes) {
    TitulosPatentes titulosPatentesSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(titulosPatentes, titulosPatentesSalva, "id");
    return titulosPatentesRepository.save(titulosPatentesSalva);
  }

  public TitulosPatentes buscarPeloCodigo(Long codigo) {
    Optional<TitulosPatentes> titulosPatentesSalva = titulosPatentesRepository.findById(codigo);
    if (titulosPatentesSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return titulosPatentesSalva.get();
  }
}
