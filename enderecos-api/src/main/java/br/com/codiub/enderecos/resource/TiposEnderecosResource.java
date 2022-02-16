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

import br.com.codiub.enderecos.entity.TiposEnderecos;
import br.com.codiub.enderecos.filter.TiposEnderecosFilter;
import br.com.codiub.enderecos.repository.TiposEnderecosRepository;
import br.com.codiub.enderecos.service.TiposEnderecosService;

@RestController
@RequestMapping("/tiposEnderecos")
public class TiposEnderecosResource {

  @Autowired private TiposEnderecosRepository tiposEnderecosRepository;

  @Autowired private TiposEnderecosService tiposEnderecosService;

  @PostMapping
  public ResponseEntity<TiposEnderecos> criar(
      @RequestBody TiposEnderecos tiposEnderecos, HttpServletResponse response) {
    TiposEnderecos tiposEnderecosSalva = tiposEnderecosRepository.save(tiposEnderecos);
    return ResponseEntity.status(HttpStatus.CREATED).body(tiposEnderecosSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<TiposEnderecos> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<TiposEnderecos> tiposEnderecos = tiposEnderecosRepository.findById(codigo);
    return tiposEnderecos != null
        ? ResponseEntity.ok(tiposEnderecos.get())
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    tiposEnderecosRepository.deleteById(codigo);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<TiposEnderecos> atualizar(
      @PathVariable Long codigo, @Validated @RequestBody TiposEnderecos tiposEnderecos) {
    TiposEnderecos tiposEnderecosSalva = tiposEnderecosService.atualizar(codigo, tiposEnderecos);
    return ResponseEntity.ok(tiposEnderecosSalva);
  }

  @GetMapping
  public Page<TiposEnderecos> pesquisar(
      TiposEnderecosFilter tiposEnderecosFilter, Pageable pageable) {
    return tiposEnderecosRepository.filtrar(tiposEnderecosFilter, pageable);
  }
}
