package org.com.proFinance.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.com.proFinance.enuns.EnumCreditoDebito;
import org.com.proFinance.enuns.SimNao;

@Entity
public class Projeto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9160867114450100205L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
	private String nome;
	
	private Calendar dataInicial;
	
	private Calendar dataFinalPrevistaAtual;
	
	private Calendar dataFinalPrevistaNova;
	
	@OneToMany(mappedBy = "projeto", targetEntity= DiaCorridoProjeto.class, cascade=CascadeType.ALL)
	private List<DiaCorridoProjeto> listDiasCorridosProjeto;
	
	private Double juroMes;
	
	@ManyToOne
	@JoinColumn(name = "indexador_id") 
	private Indexador indexador;
	
	@OneToMany(mappedBy = "projeto", targetEntity= OcorrenciaProjeto.class, cascade=CascadeType.ALL)
	private List<OcorrenciaProjeto> listOcorrenciasProjeto;
	
	@Transient
	private Double valorInicial;
	
	@Transient
	private Double valorSimulador;
	
	@Transient
	private String tabTitle;
	
	@Enumerated(EnumType.STRING)
	private SimNao ativo;
	
	@Transient
	private Double ultimoSaldo;
	
	@Transient
	private Double valorTotalCredito;
	
	@Transient
	private Double valorTotalDebito;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Calendar getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Calendar dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Calendar getDataFinalPrevistaAtual() {
		return dataFinalPrevistaAtual;
	}

	public void setDataFinalPrevistaAtual(Calendar dataFinalPrevistaAtual) {
		this.dataFinalPrevistaAtual = dataFinalPrevistaAtual;
	}

	public Calendar getDataFinalPrevistaNova() {
		return dataFinalPrevistaNova;
	}

	public void setDataFinalPrevistaNova(Calendar dataFinalPrevistaNova) {
		this.dataFinalPrevistaNova = dataFinalPrevistaNova;
	}

	public List<DiaCorridoProjeto> getListDiasCorridosProjeto() {
		if(listDiasCorridosProjeto == null){
			listDiasCorridosProjeto = new ArrayList<DiaCorridoProjeto>();
		}
		return listDiasCorridosProjeto;
	}

	public void setListDiasCorridosProjeto(
			List<DiaCorridoProjeto> listDiasCorridosProjeto) {
		this.listDiasCorridosProjeto = listDiasCorridosProjeto;
	}

	public Double getJuroMes() {
		return juroMes;
	}

	public void setJuroMes(Double juroMes) {
		this.juroMes = juroMes;
	}

	public Indexador getIndexador() {
		return indexador;
	}

	public void setIndexador(Indexador indexador) {
		this.indexador = indexador;
	}

	public Double getValorInicial() {
		valorInicial = 0.0;
		for(OcorrenciaProjeto ocorrencia : getListOcorrenciasProjeto()){
			if(ocorrencia.getCreditoDebito()!= null && ocorrencia.getCreditoDebito().equals(EnumCreditoDebito.DEBITO)){
				valorInicial += (ocorrencia.getValor()*(-1));
			}else{
				valorInicial += ocorrencia.getValor();
			}
		}
		return valorInicial;
	}

	public void setValorInicial(Double valorInicial) {
		this.valorInicial = valorInicial;
	}

	public List<OcorrenciaProjeto> getListOcorrenciasProjeto() {
		if(listOcorrenciasProjeto == null){
			listOcorrenciasProjeto = new ArrayList<OcorrenciaProjeto>();
		}
		return listOcorrenciasProjeto;
	}

	public void setListOcorrenciasProjeto(List<OcorrenciaProjeto> listOcorrenciasProjeto) {
		this.listOcorrenciasProjeto = listOcorrenciasProjeto;
	}
	
	public Double getValorSimulador() {
		return valorSimulador;
	}

	public void setValorSimulador(Double valorSimulador) {
		this.valorSimulador = valorSimulador;
	}
	
	public String getTabTitle() {
		return tabTitle;
	}

	public void setTabTitle(String tabTitle) {
		this.tabTitle = tabTitle;
	}

	public SimNao getAtivo() {
		return ativo;
	}

	public void setAtivo(SimNao ativo) {
		this.ativo = ativo;
	}

	public Double getUltimoSaldo() {
		ultimoSaldo = 0.0;
		Collections.sort(listDiasCorridosProjeto,new Comparator<DiaCorridoProjeto>(){
			
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public int compare(DiaCorridoProjeto  o1, DiaCorridoProjeto  o2) {
				Comparable c1 = (Comparable) o1.getOrdem();
				Comparable c2 = (Comparable) o2.getOrdem();
				return c1.compareTo(c2);
			}
			
		});
		DiaCorridoProjeto diaCorridoProjeto = listDiasCorridosProjeto.get(listDiasCorridosProjeto.size()-1);
		return diaCorridoProjeto.getValorSaldo();
	}

	public void setUltimoSaldo(Double ultimoSaldo) {
		this.ultimoSaldo = ultimoSaldo;
	}

	public Double getValorTotalCredito() {
		valorTotalCredito = 0.0;
		for(DiaCorridoProjeto diaCorrido: getListDiasCorridosProjeto()){
			if(diaCorrido.getValorCredito()!= null){
				valorTotalCredito += diaCorrido.getValorCredito();
			}
		}
		return valorTotalCredito;
	}

	public void setValorTotalCredito(Double valorTotalCredito) {
		this.valorTotalCredito = valorTotalCredito;
	}

	public Double getValorTotalDebito() {
		valorTotalDebito = 0.0;
		for(DiaCorridoProjeto diaCorrido: getListDiasCorridosProjeto()){
			if(diaCorrido.getValorDebito()!= null){
				valorTotalDebito += diaCorrido.getValorDebito();
			}
		}
		return valorTotalDebito;
	}

	public void setValorTotalDebito(Double valorTotalDebito) {
		this.valorTotalDebito = valorTotalDebito;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Projeto))
			return false;
		Projeto other = (Projeto) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
	
}
