package br.com.codiub.enderecos.resource;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.codiub.enderecos.entity.Bairros;
import br.com.codiub.enderecos.entity.Ceps;
import br.com.codiub.enderecos.entity.Logradouros;
import br.com.codiub.enderecos.filter.CepsFilter;
import br.com.codiub.enderecos.input.CepsInput;
import br.com.codiub.enderecos.output.CepsOutput;
import br.com.codiub.enderecos.repository.BairrosRepository;
import br.com.codiub.enderecos.repository.CepsRepository;
import br.com.codiub.enderecos.repository.LogradourosRepository;
import br.com.codiub.enderecos.service.CepsService;

@RestController
@RequestMapping("/ceps")
public class CepsResource {
	
  @Autowired private LogradourosRepository logradourosRepository;	
  @Autowired private BairrosRepository bairrosRepository;
  @Autowired private CepsRepository cepsRepository;
  @Autowired private CepsService cepsService;
  
  //AA - Inserir
  @PostMapping
  public ResponseEntity<Object> criar(@RequestBody CepsInput cepsInput, HttpServletResponse response) {
	  
	  ResponseEntity<Object> resp = cepsService.save(cepsInput);
	  return resp;
	  
  }
  
  //AA - edit
  @GetMapping("/{codigo}")
  public ResponseEntity<CepsOutput> buscarPeloCodigo(@PathVariable Long codigo) {
	
	//busca dados do cep 
    Optional<Ceps> ceps = cepsRepository.findById(codigo);
    
    CepsOutput cepsOutput = null;
    
    if(ceps.isPresent()) {
    	// Se tiver o cep cadastrado copia para logradouros output
    	cepsOutput = new CepsOutput();
    	
    	BeanUtils.copyProperties(ceps.get(), cepsOutput);
    	
    	cepsOutput.setDistritos(ceps.get().getBairros().getDistritos());
    	
    }
    return cepsOutput != null ? ResponseEntity.ok(cepsOutput) : ResponseEntity.notFound().build();
  }
  
  //Tonhas upd - 28042021
  @PutMapping("/{codigo}")
  public ResponseEntity<Object> atualizar(@PathVariable Long codigo, @Validated @RequestBody CepsInput cepsInput) {
	ResponseEntity<Object> cepsSalva = cepsService.atualizar(codigo, cepsInput);
    return ResponseEntity.ok(cepsSalva); 
    
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    cepsRepository.deleteById(codigo);
  }

  @GetMapping
  public Page<Ceps> pesquisar(CepsFilter cepsFilter, Pageable pageable) {
    return cepsRepository.filtrar(cepsFilter, pageable);
  }
}
