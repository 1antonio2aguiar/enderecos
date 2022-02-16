package br.com.codiub.enderecos.entity;
// Generated 2 de dez. de 2020 14:47:27 by Hibernate Tools 4.3.5.Final

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DadosLogradouros generated by hbm2java
 */
@Entity
@Table(name = "DADOS_LOGRADOUROS", schema = "DBO_CC_ENDERECOS")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })	
public class DadosLogradouros implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7560091774908791258L;
	private Long id;
	private Logradouros logradouros;
	private Date dataCriacao;
	private String leiCriacao;
	private Long pessoaVereador;
	private Date dataDecreto;
	private String decreto;
	private Date dataPortaria;
	private String portaria;
	private String observacao;
	private String usuario;
	private Date dataAlteracao;
	
	@Id
	@GenericGenerator(
			name = "SEQ_DADOS_LOGRADOUROS", 
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", 
			parameters = @Parameter(name = "sequence", value = "SEQ_DADOS_LOGRADOUROS")
	)
	@GeneratedValue(generator = "SEQ_DADOS_LOGRADOUROS")	
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LOGRADOURO", nullable = false)
	public Logradouros getLogradouros() {
		return this.logradouros;
	}

	public void setLogradouros(Logradouros logradouros) {
		this.logradouros = logradouros;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CRIACAO", length = 7)
	public Date getDataCriacao() {
		return this.dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Column(name = "LEI_CRIACAO", length = 50)
	public String getLeiCriacao() {
		return this.leiCriacao;
	}

	public void setLeiCriacao(String leiCriacao) {
		this.leiCriacao = leiCriacao;
	}

	@Column(name = "PESSOA_VEREADOR", precision = 10, scale = 0)
	public Long getPessoaVereador() {
		return this.pessoaVereador;
	}

	public void setPessoaVereador(Long pessoaVereador) {
		this.pessoaVereador = pessoaVereador;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_DECRETO", length = 7)
	public Date getDataDecreto() {
		return this.dataDecreto;
	}

	public void setDataDecreto(Date dataDecreto) {
		this.dataDecreto = dataDecreto;
	}

	@Column(name = "DECRETO", length = 50)
	public String getDecreto() {
		return this.decreto;
	}

	public void setDecreto(String decreto) {
		this.decreto = decreto;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_PORTARIA", length = 7)
	public Date getDataPortaria() {
		return this.dataPortaria;
	}

	public void setDataPortaria(Date dataPortaria) {
		this.dataPortaria = dataPortaria;
	}

	@Column(name = "PORTARIA", length = 50)
	public String getPortaria() {
		return this.portaria;
	}

	public void setPortaria(String portaria) {
		this.portaria = portaria;
	}

	@Column(name = "OBSERVACAO", length = 500)
	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Column(name = "USUARIO", length = 50)
	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_ALTERACAO")
	public Date getDataAlteracao() {
		return this.dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

}
