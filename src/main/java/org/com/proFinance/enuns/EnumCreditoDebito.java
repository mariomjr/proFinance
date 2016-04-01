package org.com.proFinance.enuns;

public enum EnumCreditoDebito {
	
	CREDITO("Crédito"),
	DEBITO("Débito");
	
	private String label;
	
	private EnumCreditoDebito(String label){
		this.label = label;
	}
	
	public String getLabel(){
		return label;
	}
}
