package br.com.codiub.enderecos.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.codiub.enderecos.entity.Cidades;
import br.com.codiub.enderecos.entity.Distritos;
import br.com.codiub.enderecos.input.DistritosInput;
import br.com.codiub.enderecos.repository.CidadesRepository;
import br.com.codiub.enderecos.repository.DistritosRepository;

@Service
public class DistritosService {

	@Autowired private DistritosRepository distritosRepository;
	@Autowired private CidadesRepository cidadesRepository;

	public Distritos atualizar(Long codigo, Distritos distritos) {
		Distritos distritosSalva = buscarPeloCodigo(codigo);

		BeanUtils.copyProperties(distritos, distritosSalva, "id");
		return distritosRepository.save(distritosSalva);
	}

	public Distritos buscarPeloCodigo(Long codigo) {
		Optional<Distritos> distritosSalva = distritosRepository.findById(codigo);
		if (distritosSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return distritosSalva.get();
	}
  
  	//AA
	public Distritos save(DistritosInput distritosInput) {

		Distritos distritos = new Distritos();
		BeanUtils.copyProperties(distritosInput, distritos, "id");
		
		// BUSCO A CIDADE E INSIRO EM DISTRITOS
		Cidades cidades = cidadesRepository.findById(distritosInput.getCidades().getId()).get();
		
		distritos.setDataAlteracao(new Date());
		distritos.setCidades(cidades);
		
		return distritosRepository.save(distritos);
	}
	
	//AA Esta função e para quando cadastra uma nova cidade. ai cadastra uma linha tambem em distritos
	public Distritos insereDistrito(Distritos distritos) {
		return distritosRepository.save(distritos);
	}
}
