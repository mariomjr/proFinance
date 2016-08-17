package org.com.proFinance.entity;

import java.util.List;

public class ProjetoDiaCorridoAnoAux {
	
	
	private Integer ano;
	
	private List<DiaCorridoProjeto> listDiasCorridosProjeto;
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public List<DiaCorridoProjeto> getListDiasCorridosProjeto() {
		return listDiasCorridosProjeto;
	}
	public void setListDiasCorridosProjeto(List<DiaCorridoProjeto> listDiasCorridosProjeto) {
		this.listDiasCorridosProjeto = listDiasCorridosProjeto;
	}
}
