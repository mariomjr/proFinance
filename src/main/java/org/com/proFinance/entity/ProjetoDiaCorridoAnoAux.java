package org.com.proFinance.entity;

import java.util.ArrayList;
import java.util.List;

import org.com.proFinance.enuns.EnumCreditoDebito;

public class ProjetoDiaCorridoAnoAux {
	
	
	private Integer ano;
	
	private List<DiaCorridoProjeto> listDiasCorridosProjeto;
	
	private List<Empresa> listEmpresaTotal;
	
	private Double valorTotalCredito;
	
	private Double valorTotalDebito;
	
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
	
	public List<Empresa> getListEmpresaTotal() {
		listEmpresaTotal = new ArrayList<Empresa>();
		Empresa empresaAux;
		int indexEmpresa;
		for(DiaCorridoProjeto diaCorridoProjeto : getListDiasCorridosProjeto()){
			for(OcorrenciaProjeto ocorrenciaProjeto : diaCorridoProjeto.getListOcorrenciasProjeto()){
				if(ocorrenciaProjeto.getEmpresa()!= null && listEmpresaTotal.contains(ocorrenciaProjeto.getEmpresa())){
					indexEmpresa = listEmpresaTotal.indexOf(ocorrenciaProjeto.getEmpresa());
					empresaAux = listEmpresaTotal.get(indexEmpresa);
					if(ocorrenciaProjeto.getCreditoDebito().equals(EnumCreditoDebito.CREDITO)){
						empresaAux.setValorTotalCredito(empresaAux.getValorTotalCredito()+ocorrenciaProjeto.getValor());
					}else{
						empresaAux.setValorTotalDebito(empresaAux.getValorTotalDebito()+ocorrenciaProjeto.getValor());
					}
				}else{
					empresaAux = ocorrenciaProjeto.getEmpresa();
					if(empresaAux!= null){
						if(ocorrenciaProjeto.getCreditoDebito().equals(EnumCreditoDebito.CREDITO)){
							empresaAux.setValorTotalCredito(ocorrenciaProjeto.getValor());
						}else{
							empresaAux.setValorTotalDebito(ocorrenciaProjeto.getValor());
						}
						listEmpresaTotal.add(empresaAux);
					}
				}
			}
		}
		
		return listEmpresaTotal;
	}
	public void setListEmpresaTotal(List<Empresa> listEmpresaTotal) {
		this.listEmpresaTotal = listEmpresaTotal;
	}

}
