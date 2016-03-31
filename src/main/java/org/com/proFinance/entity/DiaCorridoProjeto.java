package org.com.proFinance.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class DiaCorridoProjeto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 459793890733539891L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Calendar data;
	
	private Double valorSaldo;
	
	private Double valorDebito;
	
	private Double valorCredito;
	
	private Double juroMes;
	
	private Double taxaJuro;
	
	private Double fatorDiario;
	
	private Projeto projeto;
	
	@OneToMany(mappedBy = "diaCorridoProjeto", targetEntity= OcorrenciaProjeto.class)
	private List<OcorrenciaProjeto> listOcorrenciasProjeto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Double getValorSaldo() {
		return valorSaldo;
	}

	public void setValorSaldo(Double valorSaldo) {
		this.valorSaldo = valorSaldo;
	}

	public Double getValorDebito() {
		return valorDebito;
	}

	public void setValorDebito(Double valorDebito) {
		this.valorDebito = valorDebito;
	}

	public Double getValorCredito() {
		return valorCredito;
	}

	public void setValorCredito(Double valorCredito) {
		this.valorCredito = valorCredito;
	}

	public Double getJuroMes() {
		return juroMes;
	}

	public void setJuroMes(Double juroMes) {
		this.juroMes = juroMes;
	}

	public Double getTaxaJuro() {
		return taxaJuro;
	}

	public void setTaxaJuro(Double taxaJuro) {
		this.taxaJuro = taxaJuro;
	}

	public Double getFatorDiario() {
		return fatorDiario;
	}

	public void setFatorDiario(Double fatorDiario) {
		this.fatorDiario = fatorDiario;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public List<OcorrenciaProjeto> getListOcorrenciasProjeto() {
		return listOcorrenciasProjeto;
	}

	public void setListOcorrenciasProjeto(
			List<OcorrenciaProjeto> listOcorrenciasProjeto) {
		this.listOcorrenciasProjeto = listOcorrenciasProjeto;
	}
	
}
