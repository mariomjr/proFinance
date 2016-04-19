package org.com.proFinance.services;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.axis.AxisFault;
import org.com.proFinance.wsBancoCentral.FachadaWSSGSSoapBindingStub;
import org.com.proFinance.wsBancoCentral.FachadaWSSGS_PortType;

public class WsBancoCentroService {

	
	FachadaWSSGS_PortType wsBancoCentroService;
	
	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			
	public double getValorByCodigoAndData(Long idValor, Calendar data){
		try{
			BigDecimal vo = getWsBancoCentroService().getValor(idValor, formato.format(data.getTime()));
			return vo.doubleValue();
		}catch(Exception e){
			e.printStackTrace();
			return 0.0;
		}
		
	}
	
	public FachadaWSSGS_PortType getWsBancoCentroService() throws AxisFault{
		if(wsBancoCentroService == null){
			wsBancoCentroService = new FachadaWSSGSSoapBindingStub();
		}
		return wsBancoCentroService;
	}
	
	
	
	
}
