package org.com.proFinance.util;

public class Uteis {

	public static boolean validaNullVazio(String a){
		if(a== null || (a!= null && a.trim().equals(""))){
			return true;
		}
		return false;
	}
}
