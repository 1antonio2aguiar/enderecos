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

import br.com.codiub.enderecos.entity.DadosLogradouros;
import br.com.codiub.enderecos.filter.DadosLogradourosFilter;
import br.com.codiub.enderecos.repository.DadosLogradourosRepository;
import br.com.codiub.enderecos.service.DadosLogradourosService;

@RestController
@RequestMapping("/dadosLogradouros")
public class DadosLogradourosResource {

  @Autowired private DadosLogradourosRepository dadosLogradourosRepository;

  @Autowired private DadosLogradourosService dadosLogradourosService;

  @PostMapping
  public ResponseEntity<DadosLogradouros> criar(
      @RequestBody DadosLogradouros dadosLogradouros, HttpServletResponse response) {
    DadosLogradouros dadosLogradourosSalva = dadosLogradourosRepository.save(dadosLogradouros);
    return ResponseEntity.status(HttpStatus.CREATED).body(dadosLogradourosSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<DadosLogradouros> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<DadosLogradouros> dadosLogradouros = dadosLogradourosRepository.findById(codigo);
    return dadosLogradouros != null
        ? ResponseEntity.ok(dadosLogradouros.get())
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    dadosLogradourosRepository.deleteById(codigo);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<DadosLogradouros> atualizar(
      @PathVariable Long codigo, @Validated @RequestBody DadosLogradouros dadosLogradouros) {
    DadosLogradouros dadosLogradourosSalva =
        dadosLogradourosService.atualizar(codigo, dadosLogradouros);
    return ResponseEntity.ok(dadosLogradourosSalva);
  }

  @GetMapping
  public Page<DadosLogradouros> pesquisar(
      DadosLogradourosFilter dadosLogradourosFilter, Pageable pageable) {
    return dadosLogradourosRepository.filtrar(dadosLogradourosFilter, pageable);
  }
}
