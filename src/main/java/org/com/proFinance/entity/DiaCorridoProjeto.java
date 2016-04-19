package org.com.proFinance.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

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
	
	private Double juroMes;
	
	private Double taxaJuro;
	
	private Double fatorDiario;
	
	@OneToMany(mappedBy = "diaCorridoProjeto", targetEntity= OcorrenciaProjeto.class, cascade=CascadeType.ALL)
	private List<OcorrenciaProjeto> listOcorrenciasProjeto;
	
	@ManyToOne
	@JoinColumn(name = "investimentoProjeto_id") 
	private Projeto projeto;
	
	private Integer ordem;
	
	private Double valorIndexador;
	
	@OneToMany(mappedBy = "diaCorridoProjeto", targetEntity= OcorrenciaSocioEmpresa.class, cascade=CascadeType.ALL)
	private List<OcorrenciaSocioEmpresa> listOcorrenciasSocioEmpresa;
	
	@Transient
	private boolean valorSaldoNegativo;
	
	@Transient
	private Double valorSaldoTotal;

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

	public List<OcorrenciaSocioEmpresa> getListOcorrenciasSocioEmpresa() {
		if(listOcorrenciasSocioEmpresa == null){
			listOcorrenciasSocioEmpresa = new ArrayList<OcorrenciaSocioEmpresa>();
		}
		return listOcorrenciasSocioEmpresa;
	}

	public void setListOcorrenciasSocioEmpresa(List<OcorrenciaSocioEmpresa> listOcorrenciasSocioEmpresa) {
		this.listOcorrenciasSocioEmpresa = listOcorrenciasSocioEmpresa;
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
		if(listOcorrenciasProjeto == null){
			listOcorrenciasProjeto = new ArrayList<OcorrenciaProjeto>();
		}
		return listOcorrenciasProjeto;
	}

	public void setListOcorrenciasProjeto(
			List<OcorrenciaProjeto> listOcorrenciasProjeto) {
		this.listOcorrenciasProjeto = listOcorrenciasProjeto;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public Double getValorIndexador() {
		return valorIndexador;
	}

	public void setValorIndexador(Double valorIndexador) {
		this.valorIndexador = valorIndexador;
	}

	public boolean isValorSaldoNegativo() {
		if(getValorSaldoTotal()<0){
			return true;
		}else{
			return false;
		}
	}

	public void setValorSaldoNegativo(boolean valorSaldoNegativo) {
		this.valorSaldoNegativo = valorSaldoNegativo;
	}

	public Double getValorSaldoTotal() {
		valorSaldoTotal = 0.0;
		for(OcorrenciaSocioEmpresa ocorrenciaSocioEmpresa: getListOcorrenciasSocioEmpresa()){
			if(ocorrenciaSocioEmpresa.getValorCredito() != null){
				valorSaldoTotal = valorSaldoTotal + ocorrenciaSocioEmpresa.getValorCredito();
			}if(ocorrenciaSocioEmpresa.getValorDebito()!= null){
				valorSaldoTotal = valorSaldoTotal + ocorrenciaSocioEmpresa.getValorDebito();
			}
		}
		if(getValorSaldo()!= null){
			valorSaldoTotal = valorSaldoTotal + getValorSaldo();
		}
		return valorSaldoTotal;
	}

	public void setValorSaldoTotal(Double valorSaldoTotal) {
		this.valorSaldoTotal = valorSaldoTotal;
	}
	
	
	
}
