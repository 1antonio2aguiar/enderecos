package br.com.codiub.enderecos.entity;
// Generated 2 de dez. de 2020 14:47:27 by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * BairrosCeps generated by hbm2java
 */
@Entity
@Table(name = "BAIRROS_CEPS", schema = "DBO_CC_ENDERECOS")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })	
public class BairrosCeps implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7285584608982980968L;
	private Long id;
	private Long cidade;
	private Long distrito;
	private Long bairro;
	private Long logradouro;
	private Long cdCcmLogradouro;
	private Long cdCcmBairro;
	private Long cdCcmCep;
	private String identificacao;
	private String banco;

	public BairrosCeps() {
	}

	public BairrosCeps(Long id) {
		this.id = id;
	}

	public BairrosCeps(Long id, Long cidade, Long distrito, Long bairro, Long logradouro, Long cdCcmLogradouro,
			Long cdCcmBairro, Long cdCcmCep, String identificacao, String banco) {
		this.id = id;
		this.cidade = cidade;
		this.distrito = distrito;
		this.bairro = bairro;
		this.logradouro = logradouro;
		this.cdCcmLogradouro = cdCcmLogradouro;
		this.cdCcmBairro = cdCcmBairro;
		this.cdCcmCep = cdCcmCep;
		this.identificacao = identificacao;
		this.banco = banco;
	}

	@Id

	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CIDADE", precision = 10, scale = 0)
	public Long getCidade() {
		return this.cidade;
	}

	public void setCidade(Long cidade) {
		this.cidade = cidade;
	}

	@Column(name = "DISTRITO", precision = 10, scale = 0)
	public Long getDistrito() {
		return this.distrito;
	}

	public void setDistrito(Long distrito) {
		this.distrito = distrito;
	}

	@Column(name = "BAIRRO", precision = 10, scale = 0)
	public Long getBairro() {
		return this.bairro;
	}

	public void setBairro(Long bairro) {
		this.bairro = bairro;
	}

	@Column(name = "LOGRADOURO", precision = 10, scale = 0)
	public Long getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(Long logradouro) {
		this.logradouro = logradouro;
	}

	@Column(name = "CD_CCM_LOGRADOURO", precision = 10, scale = 0)
	public Long getCdCcmLogradouro() {
		return this.cdCcmLogradouro;
	}

	public void setCdCcmLogradouro(Long cdCcmLogradouro) {
		this.cdCcmLogradouro = cdCcmLogradouro;
	}

	@Column(name = "CD_CCM_BAIRRO", precision = 10, scale = 0)
	public Long getCdCcmBairro() {
		return this.cdCcmBairro;
	}

	public void setCdCcmBairro(Long cdCcmBairro) {
		this.cdCcmBairro = cdCcmBairro;
	}

	@Column(name = "CD_CCM_CEP", precision = 10, scale = 0)
	public Long getCdCcmCep() {
		return this.cdCcmCep;
	}

	public void setCdCcmCep(Long cdCcmCep) {
		this.cdCcmCep = cdCcmCep;
	}

	@Column(name = "IDENTIFICACAO", length = 1)
	public String getIdentificacao() {
		return this.identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	@Column(name = "BANCO", length = 30)
	public String getBanco() {
		return this.banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

}
