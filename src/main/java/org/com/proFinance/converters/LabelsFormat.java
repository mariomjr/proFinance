package org.com.proFinance.converters;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

import javax.inject.Named;

@Named
public class LabelsFormat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1313031922327098627L;

	
	public static String formatDouble(Double valor){
		NumberFormat nf = NumberFormat.getIntegerInstance(new Locale("pt", "BR"));
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		String s= nf.format(Double.valueOf(valor.toString()));
		
		return s;
	}
	
}
