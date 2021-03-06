package br.com.codiub.enderecos.resource;

import java.util.Date;
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

import br.com.codiub.enderecos.entity.Cidades;
import br.com.codiub.enderecos.entity.Distritos;
import br.com.codiub.enderecos.filter.DistritosFilter;
import br.com.codiub.enderecos.input.CidadesInput;
import br.com.codiub.enderecos.input.DistritosInput;
import br.com.codiub.enderecos.repository.DistritosRepository;
import br.com.codiub.enderecos.service.DistritosService;

@RestController
@RequestMapping("/distritos")
public class DistritosResource {

	@Autowired private DistritosRepository distritosRepository;
	@Autowired private DistritosService distritosService;

    //AA - Inserir
    @PostMapping
    public ResponseEntity<Distritos> criar(@RequestBody DistritosInput distritosInput, HttpServletResponse response) {
    	Distritos distritosSalva = distritosService.save(distritosInput);
		return ResponseEntity.status(HttpStatus.CREATED).body(distritosSalva);
	}

  @GetMapping("/{codigo}")
  public ResponseEntity<Distritos> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<Distritos> distritos = distritosRepository.findById(codigo);
    return distritos != null
        ? ResponseEntity.ok(distritos.get())
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    distritosRepository.deleteById(codigo);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<Distritos> atualizar(
      @PathVariable Long codigo, @Validated @RequestBody Distritos distritos) {
	  distritos.setDataAlteracao(new Date());
      Distritos distritosSalva = distritosService.atualizar(codigo, distritos);
      
      return ResponseEntity.ok(distritosSalva);
  }

  @GetMapping
  public Page<Distritos> pesquisar(DistritosFilter distritosFilter, Pageable pageable) {
    return distritosRepository.filtrar(distritosFilter, pageable);
  }
}
