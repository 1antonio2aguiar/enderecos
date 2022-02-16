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

import br.com.codiub.enderecos.entity.TiposLogradouros;
import br.com.codiub.enderecos.entity.TitulosPatentes;
import br.com.codiub.enderecos.filter.TiposLogradourosFilter;
import br.com.codiub.enderecos.filter.TitulosPatentesFilter;
import br.com.codiub.enderecos.repository.TitulosPatentesRepository;
import br.com.codiub.enderecos.service.TitulosPatentesService;

@RestController
@RequestMapping("/titulosPatentes")
public class TitulosPatentesResource {

  @Autowired private TitulosPatentesRepository titulosPatentesRepository;

  @Autowired private TitulosPatentesService titulosPatentesService;

  @PostMapping
  public ResponseEntity<TitulosPatentes> criar(
      @RequestBody TitulosPatentes titulosPatentes, HttpServletResponse response) {
    TitulosPatentes titulosPatentesSalva = titulosPatentesRepository.save(titulosPatentes);
    return ResponseEntity.status(HttpStatus.CREATED).body(titulosPatentesSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<TitulosPatentes> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<TitulosPatentes> titulosPatentes = titulosPatentesRepository.findById(codigo);
    return titulosPatentes != null
        ? ResponseEntity.ok(titulosPatentes.get())
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    titulosPatentesRepository.deleteById(codigo);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<TitulosPatentes> atualizar(
      @PathVariable Long codigo, @Validated @RequestBody TitulosPatentes titulosPatentes) {
    TitulosPatentes titulosPatentesSalva =
        titulosPatentesService.atualizar(codigo, titulosPatentes);
    return ResponseEntity.ok(titulosPatentesSalva);
  }

  @GetMapping
  public Page<TitulosPatentes> pesquisar(
      TitulosPatentesFilter titulosPatentesFilter, Pageable pageable) {
    return titulosPatentesRepository.filtrar(titulosPatentesFilter, pageable);
  }
  
  @GetMapping("/list")
  public List<TitulosPatentes> pesquisar(TitulosPatentesFilter titulosPatentesFilter) {
	return titulosPatentesRepository.filtrar(titulosPatentesFilter);
  }
}
