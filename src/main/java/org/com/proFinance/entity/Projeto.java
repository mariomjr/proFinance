package org.com.proFinance.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
	
	private Calendar dataFinalPrevista;
	
	@OneToMany(mappedBy = "projeto", targetEntity= DiaCorridoProjeto.class, cascade=CascadeType.ALL)
	private List<DiaCorridoProjeto> listDiasCorridosProjeto;
	
	private Double juroMes;
	
	@ManyToOne
	@JoinColumn(name = "indexador_id") 
	private Indexador indexador;
	
	private Double valorInicial;
	
	@Enumerated(EnumType.STRING)
	private EnumCreditoDebito creditoDebito;

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

	public Calendar getDataFinalPrevista() {
		return dataFinalPrevista;
	}

	public void setDataFinalPrevista(Calendar dataFinalPrevista) {
		this.dataFinalPrevista = dataFinalPrevista;
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
		return valorInicial;
	}

	public void setValorInicial(Double valorInicial) {
		this.valorInicial = valorInicial;
	}

	public EnumCreditoDebito getCreditoDebito() {
		return creditoDebito;
	}

	public void setCreditoDebito(EnumCreditoDebito creditoDebito) {
		this.creditoDebito = creditoDebito;
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
