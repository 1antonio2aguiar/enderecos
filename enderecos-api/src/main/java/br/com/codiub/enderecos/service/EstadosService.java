package br.com.codiub.enderecos.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.codiub.enderecos.entity.Estados;
import br.com.codiub.enderecos.entity.Paises;
import br.com.codiub.enderecos.input.EstadosInput;
import br.com.codiub.enderecos.repository.EstadosRepository;
import br.com.codiub.enderecos.repository.PaisesRepository;

@Service
public class EstadosService {

	@Autowired private EstadosRepository estadosRepository;

	@Autowired private PaisesRepository paisesRepository;


	public Estados atualizar(Long codigo, Estados estados) {
		Estados estadosSalva = buscarPeloCodigo(codigo);

		BeanUtils.copyProperties(estados, estadosSalva, "id");
		return estadosRepository.save(estadosSalva);
	}

	public Estados buscarPeloCodigo(Long codigo) {
		Optional<Estados> estadosSalva = estadosRepository.findById(codigo);
		if (estadosSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return estadosSalva.get();
	}
	
	// Adamis
	public Estados save(EstadosInput estadosInput) {

		Estados estados = new Estados();
		BeanUtils.copyProperties(estadosInput, estados, "id");
		Paises paises = paisesRepository.findById(estadosInput.getPaises().getId()).get();
		estados.setPaises(paises );

		return estadosRepository.save(estados);	
	}
}
