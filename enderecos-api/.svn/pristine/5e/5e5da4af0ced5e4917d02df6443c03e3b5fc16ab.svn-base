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

import br.com.codiub.enderecos.entity.Paises;
import br.com.codiub.enderecos.filter.PaisesFilter;
import br.com.codiub.enderecos.repository.PaisesRepository;
import br.com.codiub.enderecos.service.PaisesService;

@RestController
@RequestMapping("/paises")
public class PaisesResource {

  @Autowired private PaisesRepository paisesRepository;

  @Autowired private PaisesService paisesService;

  @PostMapping
  public ResponseEntity<Paises> criar(@RequestBody Paises paises, HttpServletResponse response) {
    Paises paisesSalva = paisesRepository.save(paises);
    return ResponseEntity.status(HttpStatus.CREATED).body(paisesSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<Paises> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<Paises> paises = paisesRepository.findById(codigo);
    return paises != null ? ResponseEntity.ok(paises.get()) : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    paisesRepository.deleteById(codigo);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<Paises> atualizar(
      @PathVariable Long codigo, @Validated @RequestBody Paises paises) {
    Paises paisesSalva = paisesService.atualizar(codigo, paises);
    return ResponseEntity.ok(paisesSalva);
  }

  @GetMapping
  public Page<Paises> pesquisar(PaisesFilter paisesFilter, Pageable pageable) {
    return paisesRepository.filtrar(paisesFilter, pageable);
  }
  
  // AA
  @GetMapping("/list")
  public List<Paises> pesquisar(PaisesFilter paisesFilter) {
    return paisesRepository.filtrar(paisesFilter);
  }
  
}
