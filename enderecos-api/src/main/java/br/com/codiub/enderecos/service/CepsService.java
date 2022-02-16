package br.com.codiub.enderecos.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.codiub.enderecos.entity.Bairros;
import br.com.codiub.enderecos.entity.Ceps;
import br.com.codiub.enderecos.entity.Distritos;
import br.com.codiub.enderecos.entity.Logradouros;
import br.com.codiub.enderecos.input.CepsInput;
import br.com.codiub.enderecos.input.LogradourosInput;
import br.com.codiub.enderecos.repository.BairrosRepository;
import br.com.codiub.enderecos.repository.CepsRepository;
import br.com.codiub.enderecos.repository.DistritosRepository;
import br.com.codiub.enderecos.repository.LogradourosRepository;

@Service
public class CepsService {

  @Autowired private CepsRepository cepsRepository;
  
  //AA
  @Autowired private BairrosRepository bairrosRepository;
  @Autowired private LogradourosRepository logradourosRepository;

  public Ceps atualizar(Long codigo, Ceps ceps) {
    Ceps cepsSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(ceps, cepsSalva, "id");
    return cepsRepository.save(cepsSalva);
  }

  public Ceps buscarPeloCodigo(Long codigo) {
    Optional<Ceps> cepsSalva = cepsRepository.findById(codigo);
    if (cepsSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return cepsSalva.get();
  }
  
  // AA - Insere em ceps
  @Transactional
  public ResponseEntity<Object> save(CepsInput cepsInput) {
	Ceps ceps = saveCeps(cepsInput);
	
	return ResponseEntity.status(HttpStatus.CREATED).body(ceps);
		
  }
  
  //Tonhas 16042021 - upd
  @Transactional
  public ResponseEntity<Object> atualizar(Long codigo,CepsInput cepsInput){
	  
	Ceps cepsSalva = buscarPeloCodigo(codigo);
	BeanUtils.copyProperties(cepsInput, cepsSalva, "id");
	cepsSalva.setDataAlteracao(new Date());
	
	Ceps save = cepsRepository.save(cepsSalva);
	return ResponseEntity.ok(save);
	  
  }
  
  public Ceps saveCeps(CepsInput cepsInput) {
	  
	Ceps cepsSalva = new Ceps();
	BeanUtils.copyProperties(cepsInput, cepsSalva, "id");
	
	// BUSCO CODIGO DO LOGRADOURO E INSIRO NO OBJ CEPS
	//Logradouros logradouros = logradourosRepository.findById(cepsInput.getLogradouros().getId()).get();
	Logradouros logradouros = logradourosRepository.findById(cepsInput.getLogradouros()).get();
	
	// BUSCO CODIGO DO BAIRRO E INSIRO NO OBJ CEPS
	//Bairros bairros = bairrosRepository.findById(cepsInput.getBairros().getId()).get();
	Bairros bairros = bairrosRepository.findById(cepsInput.getBairros()).get();
	
	cepsSalva.setDataAlteracao(new Date());
	cepsSalva.setLogradouros(logradouros);
	cepsSalva.setBairros(bairros);
	
	return cepsRepository.save(cepsSalva);
	  
  }
}
