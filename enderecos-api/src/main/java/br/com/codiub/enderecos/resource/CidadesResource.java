package br.com.codiub.enderecos.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

import br.com.codiub.enderecos.entity.Cidades;
import br.com.codiub.enderecos.entity.Distritos;
import br.com.codiub.enderecos.filter.CidadesFilter;
import br.com.codiub.enderecos.input.CidadesInput;
import br.com.codiub.enderecos.repository.CidadesRepository;
import br.com.codiub.enderecos.repository.DistritosRepository;
import br.com.codiub.enderecos.service.CidadesService;
import br.com.codiub.enderecos.service.DistritosService;

@RestController
@RequestMapping("/cidades")
public class CidadesResource {

	@Autowired private CidadesRepository cidadesRepository;
	@Autowired private CidadesService cidadesService;

	// AA - Inserir
	@PostMapping
	public ResponseEntity<Cidades> criar(@Valid @RequestBody CidadesInput cidadesInput, HttpServletResponse response) {
		Cidades cidadesSalva = cidadesService.save(cidadesInput);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cidadesSalva);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Cidades> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Cidades> cidades = cidadesRepository.findById(codigo);
		return cidades != null ? ResponseEntity.ok(cidades.get()) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		cidadesRepository.deleteById(codigo);
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Cidades> atualizar(@PathVariable Long codigo,
			@Validated @RequestBody CidadesInput cidadesInput) {
		Cidades cidadesSalva = cidadesService.atualizar(codigo, cidadesInput);
		return ResponseEntity.ok(cidadesSalva);
	}

	@GetMapping
	public Page<Cidades> pesquisar(CidadesFilter cidadesFilter, Pageable pageable) {
		return cidadesRepository.filtrar(cidadesFilter, pageable);
	}

	@GetMapping("/list")
	public List<Cidades> pesquisar(CidadesFilter cidadesFilter) {
		return cidadesRepository.filtrar(cidadesFilter);
	}
}
