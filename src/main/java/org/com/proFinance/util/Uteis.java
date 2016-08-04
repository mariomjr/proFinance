package org.com.proFinance.util;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;

import javax.inject.Named;

@Named
public class Uteis {

	public static boolean validaNullVazio(String a){
		if(a== null || (a!= null && a.trim().equals(""))){
			return true;
		}
		return false;
	}
	
	public static String formatDoubleDinheiro(Double valor){
		
		NumberFormat nf = NumberFormat.getIntegerInstance(new Locale("pt", "BR"));
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		String s = nf.format(valor);
		
		return "R$"+s;
	}
	
	public static String randomId(){
		return UUID.randomUUID().toString().substring(0, 20);
	}
}
