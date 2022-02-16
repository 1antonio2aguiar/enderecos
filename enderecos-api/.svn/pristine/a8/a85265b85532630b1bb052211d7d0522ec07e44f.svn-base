package br.com.codiub.enderecos.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.codiub.enderecos.entity.BairrosCeps;
import br.com.codiub.enderecos.repository.BairrosCepsRepository;

@Service
public class BairrosCepsService {

  @Autowired private BairrosCepsRepository bairrosCepsRepository;

  public BairrosCeps atualizar(Long codigo, BairrosCeps bairrosCeps) {
    BairrosCeps bairrosCepsSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(bairrosCeps, bairrosCepsSalva, "id");
    return bairrosCepsRepository.save(bairrosCepsSalva);
  }

  public BairrosCeps buscarPeloCodigo(Long codigo) {
    Optional<BairrosCeps> bairrosCepsSalva = bairrosCepsRepository.findById(codigo);
    if (bairrosCepsSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return bairrosCepsSalva.get();
  }
}
