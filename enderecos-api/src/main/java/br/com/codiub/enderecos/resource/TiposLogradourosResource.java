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
import br.com.codiub.enderecos.entity.TiposLogradouros;
import br.com.codiub.enderecos.filter.EstadosFilter;
import br.com.codiub.enderecos.filter.TiposLogradourosFilter;
import br.com.codiub.enderecos.repository.TiposLogradourosRepository;
import br.com.codiub.enderecos.service.TiposLogradourosService;

@RestController
@RequestMapping("/tiposLogradouros")
public class TiposLogradourosResource {

  @Autowired private TiposLogradourosRepository tiposLogradourosRepository;

  @Autowired private TiposLogradourosService tiposLogradourosService;

  @PostMapping
  public ResponseEntity<TiposLogradouros> criar(
      @RequestBody TiposLogradouros tiposLogradouros, HttpServletResponse response) {
    TiposLogradouros tiposLogradourosSalva = tiposLogradourosRepository.save(tiposLogradouros);
    return ResponseEntity.status(HttpStatus.CREATED).body(tiposLogradourosSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<TiposLogradouros> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<TiposLogradouros> tiposLogradouros = tiposLogradourosRepository.findById(codigo);
    return tiposLogradouros != null
        ? ResponseEntity.ok(tiposLogradouros.get())
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    tiposLogradourosRepository.deleteById(codigo);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<TiposLogradouros> atualizar(
      @PathVariable Long codigo, @Validated @RequestBody TiposLogradouros tiposLogradouros) {
    TiposLogradouros tiposLogradourosSalva =
        tiposLogradourosService.atualizar(codigo, tiposLogradouros);
    return ResponseEntity.ok(tiposLogradourosSalva);
  }

  @GetMapping
  public Page<TiposLogradouros> pesquisar(
      TiposLogradourosFilter tiposLogradourosFilter, Pageable pageable) {
    return tiposLogradourosRepository.filtrar(tiposLogradourosFilter, pageable);
  }
  
  @GetMapping("/list")
  public List<TiposLogradouros> pesquisar(TiposLogradourosFilter tiposLogradourosFilter) {
	return tiposLogradourosRepository.filtrar(tiposLogradourosFilter);
  }
}
