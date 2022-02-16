package br.com.codiub.enderecos.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.codiub.enderecos.entity.Cidades;
import br.com.codiub.enderecos.entity.Distritos;
import br.com.codiub.enderecos.entity.Estados;
import br.com.codiub.enderecos.input.CidadesInput;
import br.com.codiub.enderecos.repository.CidadesRepository;
import br.com.codiub.enderecos.repository.DistritosRepository;
import br.com.codiub.enderecos.repository.EstadosRepository;

@Service
public class CidadesService {

	@Autowired
	private CidadesRepository cidadesRepository;

	// AA
	@Autowired
	private EstadosRepository estadosRepository;
	
	//AA
	@Autowired
	private DistritosRepository distritosRepository;
	
	public Cidades atualizar(Long codigo, CidadesInput cidadesInput) {
		Cidades cidadesSalva = buscarPeloCodigo(cidadesInput.getId());

		BeanUtils.copyProperties(cidadesInput, cidadesSalva, "id");

		Estados estados = estadosRepository.findById(cidadesInput.getEstadosId()).get();
		cidadesSalva.setEstados(estados);

		return cidadesRepository.save(cidadesSalva);
	}

	public Cidades buscarPeloCodigo(Long codigo) {
		Optional<Cidades> cidadesSalva = cidadesRepository.findById(codigo);
		if (cidadesSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return cidadesSalva.get();
	}

	// AA - Insere em cidades - Toda vez que insere um registro em cidades tambem insere em distritos. Segue lógica
	@Transactional
	public Cidades save(CidadesInput cidadesInput) {
		
		Cidades cidades = new Cidades();
		BeanUtils.copyProperties(cidadesInput, cidades, "id");
		
		// BUSCO ESTADO E INSIRO NO CIDADES
		Estados estados = estadosRepository.findById(cidadesInput.getEstadosId()).get();
		cidades.setDataAlteracao(new Date());
		cidades.setEstados(estados);
		
		//System.err.println("Indo gravar!!");
		// INSERINDO EM CIDADES
		Cidades cidadesSalva = cidadesRepository.save(cidades);
		
		//System.err.println("Voltou hehehe!! " + cidadesSalva.getId() + " " + cidadesSalva.getNome());
		
		// Insere uma linha em Distritos
		Distritos distritos = new Distritos();
		
		distritos.setCidades(cidadesSalva);
		
		distritos.setNome(cidadesSalva.getNome().toUpperCase());
		distritos.setUsuario(cidadesSalva.getUsuario());
		distritos.setDataAlteracao(cidadesSalva.getDataAlteracao());
		
		Distritos distritosSalva = distritosRepository.save(distritos);
		
		return cidadesSalva;
		
	}
}
