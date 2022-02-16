package br.com.codiub.enderecos.resource;

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

import br.com.codiub.enderecos.entity.BairrosCeps;
import br.com.codiub.enderecos.filter.BairrosCepsFilter;
import br.com.codiub.enderecos.repository.BairrosCepsRepository;
import br.com.codiub.enderecos.service.BairrosCepsService;

@RestController
@RequestMapping("/bairrosCeps")
public class BairrosCepsResource {

  @Autowired private BairrosCepsRepository bairrosCepsRepository;

  @Autowired private BairrosCepsService bairrosCepsService;

  @PostMapping
  public ResponseEntity<BairrosCeps> criar(
      @RequestBody BairrosCeps bairrosCeps, HttpServletResponse response) {
    BairrosCeps bairrosCepsSalva = bairrosCepsRepository.save(bairrosCeps);
    return ResponseEntity.status(HttpStatus.CREATED).body(bairrosCepsSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<BairrosCeps> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<BairrosCeps> bairrosCeps = bairrosCepsRepository.findById(codigo);
    return bairrosCeps != null
        ? ResponseEntity.ok(bairrosCeps.get())
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    bairrosCepsRepository.deleteById(codigo);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<BairrosCeps> atualizar(
      @PathVariable Long codigo, @Validated @RequestBody BairrosCeps bairrosCeps) {
    BairrosCeps bairrosCepsSalva = bairrosCepsService.atualizar(codigo, bairrosCeps);
    return ResponseEntity.ok(bairrosCepsSalva);
  }

  @GetMapping
  public Page<BairrosCeps> pesquisar(BairrosCepsFilter bairrosCepsFilter, Pageable pageable) {
    return bairrosCepsRepository.filtrar(bairrosCepsFilter, pageable);
  }
}
