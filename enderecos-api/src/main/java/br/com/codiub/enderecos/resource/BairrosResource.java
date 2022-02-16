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

import br.com.codiub.enderecos.entity.Bairros;
import br.com.codiub.enderecos.entity.DadosBairros;
import br.com.codiub.enderecos.entity.Pessoas;
import br.com.codiub.enderecos.filter.BairrosFilter;
import br.com.codiub.enderecos.input.BairrosInput;
import br.com.codiub.enderecos.input.DadosBairrosInput;
import br.com.codiub.enderecos.output.PessoasOutput;
import br.com.codiub.enderecos.repository.BairrosRepository;
import br.com.codiub.enderecos.repository.DadosBairrosRepository;
import br.com.codiub.enderecos.repository.PessoasRepository;
import br.com.codiub.enderecos.service.BairrosService;

@RestController
@RequestMapping("/bairros")
public class BairrosResource {

  @Autowired private BairrosRepository bairrosRepository;
  @Autowired private DadosBairrosRepository dadosBairrosRepository;
  @Autowired private PessoasRepository pessoasRepository;
  @Autowired private BairrosService bairrosService;
  
  @PostMapping
  public ResponseEntity<Bairros> criar(@RequestBody Bairros bairros, HttpServletResponse response) {
    Bairros bairrosSalva = bairrosRepository.save(bairros);
    return ResponseEntity.status(HttpStatus.CREATED).body(bairrosSalva);
  }
  
  @PutMapping("/{codigo}")
  public ResponseEntity<Object> atualizar(
	@PathVariable Long codigo, @Validated @RequestBody BairrosInput bairrosInput) {
	  
	ResponseEntity<Object> bairrosSalva = bairrosService.atualizar(codigo, bairrosInput);
    return ResponseEntity.ok(bairrosSalva); 
    
  }

  //Adamis 24022021
  @PostMapping("/completo")
  public ResponseEntity<Object> criar(@RequestBody BairrosInput bairrosInput, HttpServletResponse response) {

	  ResponseEntity<Object> resp = bairrosService.save(bairrosInput);
	  return resp;
	  
  }
  
  @GetMapping("/{codigo}")
  public ResponseEntity<BairrosInput> buscarPeloCodigo(@PathVariable Long codigo, BairrosInput bairrosInput) {
	  
    // Busca bairros
	Optional<Bairros> bairros = bairrosRepository.findById(codigo);
	
    if(bairros.isPresent()) {
    	BeanUtils.copyProperties(bairros.get(), bairrosInput);
    	
    	// Busca dados bairros
    	List<DadosBairros> dadosBairrosList = dadosBairrosRepository.findByBairrosId(codigo);
    	
    	if(dadosBairrosList.size() > 0) {
			DadosBairrosInput dadosBairrosInput = new DadosBairrosInput();
			
			// Verifica se pessoaVereador esta preenchido
   			if(dadosBairrosList.get(0).getPessoaVereador() != null && dadosBairrosList.get(0).getPessoaVereador() != 0) {
   				// Busca dados da pessoa pelo codigo gravado em pessoa_vereador
   				Optional<Pessoas> pessoas = pessoasRepository.findById(dadosBairrosList.get(0).getPessoaVereador());
   				PessoasOutput pessoasOutput = new PessoasOutput();
   				// copia dados retornados para pessoa output
   				BeanUtils.copyProperties(pessoas.get(), pessoasOutput);
   				pessoasOutput.setNomeVereador(pessoas.get().getNome());
   				
   				// copia pessoa output para dados bairros imput
   				dadosBairrosInput.setVwPessoas(pessoasOutput);
   			} else {
   				// Mandar o pessoa output mesmo que esteja nulo
   				dadosBairrosInput.setVwPessoas(new PessoasOutput());
   			}
   			
   			// Verifica se pessoaLoteadora esta preenchido
   			if(dadosBairrosList.get(0).getPessoaLoteadora() != null && dadosBairrosList.get(0).getPessoaLoteadora() != 0) {
   				// Busca dados da pessoa pelo codigo gravado em pessoaLoteadora
   				Optional<Pessoas> pessoas = pessoasRepository.findById(dadosBairrosList.get(0).getPessoaLoteadora());
   				PessoasOutput pessoasOutput = new PessoasOutput();
   				// copia dados retornados para pessoa output
   				BeanUtils.copyProperties(pessoas.get(), pessoasOutput);
   				pessoasOutput.setNomeLoteadora(pessoas.get().getNome());
   				
   				// copia pessoa output para dados bairros imput
   				dadosBairrosInput.setVwPessoas(pessoasOutput);
   			}
   			
   			// Copia dadosBairrosList para dadosBairrosInput
			BeanUtils.copyProperties(dadosBairrosList.get(0), dadosBairrosInput );
    		bairrosInput.setDadosBairros(dadosBairrosInput);
    	} 
    	
    }
    
    return bairrosInput != null ? ResponseEntity.ok(bairrosInput) : ResponseEntity.notFound().build();
  }
  
  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    bairrosRepository.deleteById(codigo);
  }
  
  @GetMapping
  public Page<Bairros> pesquisar(BairrosFilter bairrosFilter, Pageable pageable) {
    return bairrosRepository.filtrar(bairrosFilter, pageable);
  }
}
