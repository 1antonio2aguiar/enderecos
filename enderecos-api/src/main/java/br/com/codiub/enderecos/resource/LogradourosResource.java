package br.com.codiub.enderecos.resource;

import java.util.List;
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

import br.com.codiub.enderecos.entity.DadosLogradouros;
import br.com.codiub.enderecos.entity.Logradouros;
import br.com.codiub.enderecos.entity.Pessoas;
import br.com.codiub.enderecos.filter.LogradourosFilter;
import br.com.codiub.enderecos.input.BairrosInput;
import br.com.codiub.enderecos.input.DadosLogradourosInput;
import br.com.codiub.enderecos.input.LogradourosInput;
import br.com.codiub.enderecos.output.PessoasOutput;
import br.com.codiub.enderecos.repository.LogradourosRepository;
import br.com.codiub.enderecos.repository.PessoasRepository;
import br.com.codiub.enderecos.service.LogradourosService;
import br.com.codiub.enderecos.repository.DadosLogradourosRepository;

@RestController
@RequestMapping("/logradouros")
public class LogradourosResource {

  @Autowired private LogradourosRepository logradourosRepository;
  @Autowired private LogradourosService logradourosService;
  @Autowired private DadosLogradourosRepository dadosLogradourosRepository;	
  @Autowired private PessoasRepository pessoasRepository;
  
  //Tonhas 16042021 - upd
  @PutMapping("/{codigo}")
  public ResponseEntity<Object> atualizar(@PathVariable Long codigo, @Validated @RequestBody LogradourosInput logradourosInput) {
	  
	ResponseEntity<Object> logradourosSalva = logradourosService.atualizar(codigo, logradourosInput);
    return ResponseEntity.ok(logradourosSalva); 
    
  }
  
  //Tonhas 14042021 - insert
  @PostMapping("/completo")
  public ResponseEntity<Object> criar(@RequestBody LogradourosInput logradourosInput, HttpServletResponse response) {
	  
	  ResponseEntity<Object> resp = logradourosService.save(logradourosInput);
	  return resp;
	  
  }

  @PostMapping
  public ResponseEntity<Logradouros> criar(
    @RequestBody Logradouros logradouros, HttpServletResponse response) {

    Logradouros logradourosSalva = logradourosRepository.save(logradouros);
    return ResponseEntity.status(HttpStatus.CREATED).body(logradourosSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<LogradourosInput> buscarPeloCodigo(@PathVariable Long codigo, LogradourosInput logradourosInput) {
	 //System.err.println("BATEI AQUI 1");
	// Busca o logradouro  
    Optional<Logradouros> logradouros = logradourosRepository.findById(codigo);
    
    if(logradouros.isPresent()) {
    	// Se tiver o logradouro cadastrado copia para logradouros input
    	BeanUtils.copyProperties(logradouros.get(), logradourosInput);
    	
    	// Busca dados logradouros
    	List<DadosLogradouros> dadoslogradourosList = dadosLogradourosRepository.findByLogradourosId(codigo);
    	
    	if(dadoslogradourosList.size() > 0) {
    		DadosLogradourosInput dadosLogradourosInput = new DadosLogradourosInput();
    		
    		// Verifica se pessoaVereador esta preenchido
    		if(dadoslogradourosList.get(0).getPessoaVereador() != null && 
    		   dadoslogradourosList.get(0).getPessoaVereador() != 0) {
    			// Busca dados da pessoa pelo codigo gravado em pessoa_vereador
   				Optional<Pessoas> pessoas = pessoasRepository.findById(dadoslogradourosList.get(0).getPessoaVereador());
   				PessoasOutput pessoasOutput = new PessoasOutput();
   				// copia dados retornados para pessoa output
   				BeanUtils.copyProperties(pessoas.get(), pessoasOutput);
   				pessoasOutput.setNomeVereador(pessoas.get().getNome());
   				
   				// copia pessoa output para dados logradouros imput
   				dadosLogradourosInput.setVwPessoas(pessoasOutput);
   			} else {
   				// Mandar o pessoa output mesmo que esteja nulo
   				dadosLogradourosInput.setVwPessoas(new PessoasOutput());
    		}
    		
    		// Copia dadoslogradourosList para dadosLogradourosInput
			BeanUtils.copyProperties(dadoslogradourosList.get(0), dadosLogradourosInput );
			logradourosInput.setDadosLogradouros(dadosLogradourosInput);
    	}
    }

    return logradourosInput != null ? ResponseEntity.ok(logradourosInput) : ResponseEntity.notFound().build();
    
  }
  

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    logradourosRepository.deleteById(codigo);
  }
  
  @GetMapping
  public Page<Logradouros> pesquisar(LogradourosFilter logradourosFilter, Pageable pageable) {
    return logradourosRepository.filtrar(logradourosFilter, pageable);
  }
}
