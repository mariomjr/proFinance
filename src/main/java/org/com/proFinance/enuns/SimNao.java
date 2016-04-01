package org.com.proFinance.enuns;

public enum SimNao {
	
	SIM("Sim"),
	NAO("Não");
	
	private String label;
	
	private SimNao(String label){
		this.label = label;
	}
	
	public String getLabel(){
		return label;
	}
}
