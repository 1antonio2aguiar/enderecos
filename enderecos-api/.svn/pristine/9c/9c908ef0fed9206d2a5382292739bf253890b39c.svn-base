package br.com.codiub.enderecos.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.codiub.enderecos.entity.DadosBairros;
import br.com.codiub.enderecos.repository.DadosBairrosRepository;

@Service
public class DadosBairrosService {

  @Autowired private DadosBairrosRepository dadosBairrosRepository;

  public DadosBairros atualizar(Long codigo, DadosBairros dadosBairros) {
    DadosBairros dadosBairrosSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(dadosBairros, dadosBairrosSalva, "id");
    return dadosBairrosRepository.save(dadosBairrosSalva);
  }

  public DadosBairros buscarPeloCodigo(Long codigo) {
    Optional<DadosBairros> dadosBairrosSalva = dadosBairrosRepository.findById(codigo);
    if (dadosBairrosSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return dadosBairrosSalva.get();
  }
}
