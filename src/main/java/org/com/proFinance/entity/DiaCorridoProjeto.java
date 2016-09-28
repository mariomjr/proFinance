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
	
	private String randomId;
	
	@Temporal(TemporalType.DATE)
	private Calendar data;
	
	private Double valorSaldo;
	
	private Double juroMes;
	
	private Double juroAno;
	
	private Double taxaJuro;
	
	private Double fatorDiario;
	
	@OneToMany(mappedBy = "diaCorridoProjeto", targetEntity= OcorrenciaProjeto.class, cascade=CascadeType.ALL)
	private List<OcorrenciaProjeto> listOcorrenciasProjeto;
	
	@ManyToOne
	@JoinColumn(name = "investimentoProjeto_id") 
	private Projeto projeto;
	
	private Integer ordem;
	
	private Double valorIndexador;
	
	private Double valorDebito;
	
	private Double valorCredito;
	
	private Double valorSaldoAnterior;
	
	@ManyToOne
	@JoinColumn(name = "indexador_id") 
	private Indexador indexador;
	
	@Transient
	private boolean valorSaldoNegativo;
	
	@Transient
	private Double valorSaldoTotal;
	
	@Transient
	private Double somaJuroMesIndexador;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getRandomId() {
		return randomId;
	}

	public void setRandomId(String randomId) {
		this.randomId = randomId;
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

	public Double getJuroMes() {
		return juroMes;
	}

	public void setJuroMes(Double juroMes) {
		this.juroMes = juroMes;
	}

	public Double getJuroAno() {
		return juroAno;
	}

	public void setJuroAno(Double juroAno) {
		this.juroAno = juroAno;
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

	public Double getSomaJuroMesIndexador() {
		return somaJuroMesIndexador;
	}

	public void setSomaJuroMesIndexador(Double somaJuroMesIndexador) {
		this.somaJuroMesIndexador = somaJuroMesIndexador;
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

	public Double getValorSaldoAnterior() {
		if(valorSaldoAnterior == null){
			valorSaldoAnterior = 0.0;
		}
		return valorSaldoAnterior;
	}

	public void setValorSaldoAnterior(Double valorSaldoAnterior) {
		this.valorSaldoAnterior = valorSaldoAnterior;
	}

	public Indexador getIndexador() {
		return indexador;
	}

	public void setIndexador(Indexador indexador) {
		this.indexador = indexador;
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
		if(getValorCredito()!= null){
			valorSaldoTotal = valorSaldoTotal + getValorCredito();
		}
		if(getValorDebito()!= null){
			valorSaldoTotal = valorSaldoTotal + getValorDebito();
		}
		if(getFatorDiario()!= null){
			valorSaldoTotal = valorSaldoTotal + (getFatorDiario()*getValorSaldoAnterior());
		}
		return valorSaldoTotal;
	}

	public void setValorSaldoTotal(Double valorSaldoTotal) {
		this.valorSaldoTotal = valorSaldoTotal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((randomId == null) ? 0 : randomId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DiaCorridoProjeto other = (DiaCorridoProjeto) obj;
		if (getRandomId() != null) {
			if (getRandomId() == null || getRandomId().equals(other.getRandomId()) == false)return false;
			if (getRandomId().equals(other.getRandomId()))return true;
		}
		if (getId() != null) {
			if (other.getId() == null || getId().equals(other.getId()) == false)return false;
			if (getId().equals(other.getId()))return true;
		}
		return false;
	}

	
	
}
