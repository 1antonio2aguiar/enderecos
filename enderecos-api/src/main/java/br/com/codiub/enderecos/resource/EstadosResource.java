package br.com.codiub.enderecos.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

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

import br.com.codiub.enderecos.entity.Estados;
import br.com.codiub.enderecos.filter.EstadosFilter;
import br.com.codiub.enderecos.input.EstadosInput;
import br.com.codiub.enderecos.repository.EstadosRepository;
import br.com.codiub.enderecos.service.EstadosService;

@RestController
@RequestMapping("/estados")
public class EstadosResource {

	@Autowired private EstadosRepository estadosRepository;
	
	@Autowired private EstadosService estadosService;
	
	//
	@PostMapping
	public ResponseEntity<Estados> criar(@RequestBody EstadosInput estados, HttpServletResponse response) {
		Estados estadosSalva = estadosService.save(estados);    
		return ResponseEntity.status(HttpStatus.CREATED).body(estadosSalva);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Estados> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Estados> estados = estadosRepository.findById(codigo);
		return estados != null ? ResponseEntity.ok(estados.get()) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		estadosRepository.deleteById(codigo);
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Estados> atualizar(
			@PathVariable Long codigo, @Validated @RequestBody Estados estados) {
		Estados estadosSalva = estadosService.atualizar(codigo, estados);
		return ResponseEntity.ok(estadosSalva);
	}

	@GetMapping
	public Page<Estados> pesquisar(EstadosFilter estadosFilter, Pageable pageable) {
		return estadosRepository.filtrar(estadosFilter, pageable);
	}
	
	@GetMapping("/list")
	public List<Estados> pesquisar(EstadosFilter estadosFilter) {
	  return estadosRepository.filtrar(estadosFilter);
	}
}
