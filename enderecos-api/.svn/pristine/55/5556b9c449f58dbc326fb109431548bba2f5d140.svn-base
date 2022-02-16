package br.com.codiub.enderecos.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.codiub.enderecos.entity.Paises;
import br.com.codiub.enderecos.repository.PaisesRepository;

@Service
public class PaisesService {

  @Autowired private PaisesRepository paisesRepository;

  public Paises atualizar(Long codigo, Paises paises) {
    Paises paisesSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(paises, paisesSalva, "id");
    return paisesRepository.save(paisesSalva);
  }

  public Paises buscarPeloCodigo(Long codigo) {
    Optional<Paises> paisesSalva = paisesRepository.findById(codigo);
    if (paisesSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return paisesSalva.get();
  }
}
