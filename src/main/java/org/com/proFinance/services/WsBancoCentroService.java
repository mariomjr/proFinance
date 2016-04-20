package org.com.proFinance.services;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.axis.AxisFault;
import org.com.proFinance.wsBancoCentral.FachadaWSSGSService;
import org.com.proFinance.wsBancoCentral.FachadaWSSGSServiceLocator;

public class WsBancoCentroService {

	
	FachadaWSSGSService wsBancoCentroService;
	
	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			
	public double getValorByCodigoAndData(Long idValor, Calendar data){
		try{
			BigDecimal vo = getWsBancoCentroService().getFachadaWSSGS().getValor(idValor, formato.format(data.getTime()));
			return vo.doubleValue();
		}catch(Exception e){
			e.printStackTrace();
			return 0.0;
		}
		
	}
	
	public FachadaWSSGSService getWsBancoCentroService() throws AxisFault{
		if(wsBancoCentroService == null){
			wsBancoCentroService = new FachadaWSSGSServiceLocator();
		}
		return wsBancoCentroService;
	}
	
	
	
	
}
