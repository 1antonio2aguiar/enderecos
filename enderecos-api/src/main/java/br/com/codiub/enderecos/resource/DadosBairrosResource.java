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

import br.com.codiub.enderecos.entity.DadosBairros;
import br.com.codiub.enderecos.filter.DadosBairrosFilter;
import br.com.codiub.enderecos.repository.DadosBairrosRepository;
import br.com.codiub.enderecos.service.DadosBairrosService;

@RestController
@RequestMapping("/dadosBairros")
public class DadosBairrosResource {

  @Autowired private DadosBairrosRepository dadosBairrosRepository;

  @Autowired private DadosBairrosService dadosBairrosService;

  @PostMapping
  public ResponseEntity<DadosBairros> criar(
      @RequestBody DadosBairros dadosBairros, HttpServletResponse response) {
    DadosBairros dadosBairrosSalva = dadosBairrosRepository.save(dadosBairros);
    return ResponseEntity.status(HttpStatus.CREATED).body(dadosBairrosSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<DadosBairros> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<DadosBairros> dadosBairros = dadosBairrosRepository.findById(codigo);
    return dadosBairros != null
        ? ResponseEntity.ok(dadosBairros.get())
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    dadosBairrosRepository.deleteById(codigo);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<DadosBairros> atualizar(
      @PathVariable Long codigo, @Validated @RequestBody DadosBairros dadosBairros) {
    DadosBairros dadosBairrosSalva = dadosBairrosService.atualizar(codigo, dadosBairros);
    return ResponseEntity.ok(dadosBairrosSalva);
  }

  @GetMapping
  public Page<DadosBairros> pesquisar(DadosBairrosFilter dadosBairrosFilter, Pageable pageable) {
    return dadosBairrosRepository.filtrar(dadosBairrosFilter, pageable);
  }
}
