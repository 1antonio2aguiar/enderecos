package br.com.codiub.enderecos.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.codiub.enderecos.entity.Bairros;
import br.com.codiub.enderecos.entity.DadosBairros;
import br.com.codiub.enderecos.entity.Distritos;
import br.com.codiub.enderecos.exceptionhandler.PersonalExceptionHandler.Erro;
import br.com.codiub.enderecos.input.BairrosInput;
import br.com.codiub.enderecos.input.DadosBairrosInput;
import br.com.codiub.enderecos.repository.BairrosRepository;
import br.com.codiub.enderecos.repository.DadosBairrosRepository;
import br.com.codiub.enderecos.repository.DistritosRepository;

@Service
public class BairrosService {

	//Adamis 24022021
	@Autowired private BairrosRepository bairrosRepository;
	@Autowired private DadosBairrosRepository dadosBairrosRepository;
	@Autowired private DistritosRepository distritosRepository;

	public Bairros buscarPeloCodigo(Long codigo) {

		Optional<Bairros> bairrosSalva = bairrosRepository.findById(codigo);
		if (bairrosSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return bairrosSalva.get();
	}

	// AA 06042021
	@Transactional
	//public BairrosInput atualizar(Long codigo, BairrosInput bairrosInput) {
	public ResponseEntity<Object> atualizar(Long codigo,BairrosInput bairrosInput) {

		//System.err.println("OPA TA NA AREA! " + bairrosInput);

		try {

			List<DadosBairros> dadosBairrosList = dadosBairrosRepository.findByBairrosId(codigo);

			for (DadosBairros dadosBairros : dadosBairrosList) {

				BeanUtils.copyProperties(bairrosInput.getDadosBairros(), dadosBairros , "id");	
				
				dadosBairros.setDataAlteracao(new Date());
				
				if(dadosBairros.getZonaRural() == null) {
					dadosBairros.setZonaRural("N");
				}
				
				dadosBairrosRepository.save(dadosBairros);
			}
			
			Bairros bairrosSalva = buscarPeloCodigo(codigo);
			BeanUtils.copyProperties(bairrosInput, bairrosSalva, "id");
			
			bairrosSalva.setDataAlteracao(new Date());
			
			Bairros save = bairrosRepository.save(bairrosSalva);
			return ResponseEntity.ok(save);

		} catch (Exception e) {			
			String mensagemUsuario = "Não foi possivel Atualizar estas Informações";
			String mensagemDesenvolvedor = ""+e.getMessage();
			List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
			return ResponseEntity.badRequest().body(erros);
		}
	}

	// Adamis 24022021
	@Transactional
	public ResponseEntity<Object> save(BairrosInput bairrosInput) {

		Bairros bairros = saveBairros(bairrosInput);

		//DadosBairros dadosBairros = saveDadosBairros(bairros, bairrosInput.getDadosBairros());
		saveDadosBairros(bairros, bairrosInput.getDadosBairros());

		return ResponseEntity.status(HttpStatus.CREATED).body(bairros);

	}

	private Bairros saveBairros(BairrosInput bairrosInput) {
		//BAIRRO
		Bairros bairrosSalva = new Bairros();
		BeanUtils.copyProperties(bairrosInput, bairrosSalva , "id");

		Distritos distritos = distritosRepository.findById(bairrosInput.getDistritos().getId()).get();
		bairrosSalva.setDistritos(distritos);

		bairrosSalva.setDataAlteracao(new Date());
		return bairrosRepository.save(bairrosSalva);			
	}

	private DadosBairros saveDadosBairros(Bairros bairros,DadosBairrosInput dadosBairrosInput) {
		// DADOS BAIRROS
		DadosBairros dadosBairrosSalva = new DadosBairros();

		if(dadosBairrosInput != null) {
			BeanUtils.copyProperties(dadosBairrosInput, dadosBairrosSalva);

			//System.err.println("Data criação " + dadosBairrosSalva.getDataCriacao());
		}

		dadosBairrosSalva.setDataAlteracao(new Date());		
		dadosBairrosSalva.setBairros(bairros);


		//System.err.println(""+dadosBairros.getBairros().getId());
		//System.err.println(""+dadosBairros.getBairros().getNome());
		//System.err.println(""+dadosBairros.getBairros().getDataAlteracao());

		return dadosBairrosRepository.save(dadosBairrosSalva);
	}
}
