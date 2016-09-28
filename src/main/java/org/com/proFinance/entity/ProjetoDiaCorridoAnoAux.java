package org.com.proFinance.entity;

import java.util.ArrayList;
import java.util.List;

import org.com.proFinance.enuns.EnumCreditoDebito;

public class ProjetoDiaCorridoAnoAux {
	
	
	private Integer ano;
	
	private List<DiaCorridoProjeto> listDiasCorridosProjeto;
	
	private List<SocioEmpresa> listSocioEmpresa;
	
	private Double valorTotalCredito;
	
	private Double valorTotalDebito;
	
	private Double valorTotalCreditoEmpresa;
	
	private Double valorTotalDebitoEmpresa;
	
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
	
	public List<SocioEmpresa> getListSocioEmpresa() {
		listSocioEmpresa = new ArrayList<SocioEmpresa>();
		SocioEmpresa socioEmpresaAux;
		int indexSocioEmpresa;
		for(DiaCorridoProjeto diaCorridoProjeto : getListDiasCorridosProjeto()){
			for(OcorrenciaProjeto ocorrenciaProjeto : diaCorridoProjeto.getListOcorrenciasProjeto()){
				if(ocorrenciaProjeto.getEmpresa() != null && ocorrenciaProjeto.getEmpresa().getSocioEmpresa()!= null){
					if(listSocioEmpresa.contains(ocorrenciaProjeto.getEmpresa().getSocioEmpresa())){
						indexSocioEmpresa = listSocioEmpresa.indexOf(ocorrenciaProjeto.getEmpresa().getSocioEmpresa());
						socioEmpresaAux = listSocioEmpresa.get(indexSocioEmpresa);
						if(ocorrenciaProjeto.getCreditoDebito().equals(EnumCreditoDebito.CREDITO)){
							socioEmpresaAux.setValorTotalCredito(socioEmpresaAux.getValorTotalCredito()+ocorrenciaProjeto.getValor());
						}else{
							socioEmpresaAux.setValorTotalDebito(socioEmpresaAux.getValorTotalDebito()+ocorrenciaProjeto.getValor());
						}
					}else{
						socioEmpresaAux = ocorrenciaProjeto.getEmpresa().getSocioEmpresa();
						if(socioEmpresaAux!= null){
							if(ocorrenciaProjeto.getCreditoDebito().equals(EnumCreditoDebito.CREDITO)){
								socioEmpresaAux.setValorTotalCredito(ocorrenciaProjeto.getValor());
							}else{
								socioEmpresaAux.setValorTotalDebito(ocorrenciaProjeto.getValor());
							}
							listSocioEmpresa.add(socioEmpresaAux);
						}
					}
				}
			}
		}
		
		return listSocioEmpresa;
	}
	
	public void setListSocioEmpresa(List<SocioEmpresa> listSocioEmpresa) {
		this.listSocioEmpresa = listSocioEmpresa;
	}
	
	public Double getValorTotalCreditoEmpresa() {
		valorTotalCreditoEmpresa = 0.0;
		for(SocioEmpresa socioEmpresa: getListSocioEmpresa()){
			if(socioEmpresa.getValorTotalCredito()!= null){
				valorTotalCreditoEmpresa += socioEmpresa.getValorTotalCredito();
			}
		}
		return valorTotalCreditoEmpresa;
	}
	
	public void setValorTotalCreditoEmpresa(Double valorTotalCreditoEmpresa) {
		this.valorTotalCreditoEmpresa = valorTotalCreditoEmpresa;
	}
	
	public Double getValorTotalDebitoEmpresa() {
		valorTotalDebitoEmpresa = 0.0;
		for(SocioEmpresa socioEmpresa: getListSocioEmpresa()){
			if(socioEmpresa.getValorTotalDebito()!= null){
				valorTotalDebitoEmpresa += socioEmpresa.getValorTotalDebito();
			}
		}
		return valorTotalDebitoEmpresa;
	}
	
	public void setValorTotalDebitoEmpresa(Double valorTotalDebitoEmpresa) {
		this.valorTotalDebitoEmpresa = valorTotalDebitoEmpresa;
	}

}
