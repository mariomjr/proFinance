package org.com.proFinance.services;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.axis.AxisFault;
import org.com.proFinance.wsBancoCentral.FachadaWSSGSService;
import org.com.proFinance.wsBancoCentral.FachadaWSSGSServiceLocator;
import org.com.proFinance.wsBancoCentral.WSSerieVO;

public class WsBancoCentroService {

	
	FachadaWSSGSService wsBancoCentroService;
	
	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			
	public double getValorByCodigoAndData(Long idValor, Calendar data){
		try{
			BigDecimal vo = getWsBancoCentroService().getFachadaWSSGS().getValor(idValor, formato.format(data.getTime()));
			return vo.doubleValue();
		}catch(Exception e){
			System.out.println("Erro Webservice");
			e.printStackTrace();
			return 0.0;
		}
		
	}
	
	public WSSerieVO getValorByCodigoAndDataInicialFinal(Long idValor, Calendar dataInicial, Calendar dataFinal){
		try{
			WSSerieVO[] listSeries = getValorByCodigoAndDataInicialFinal(new long[]{idValor.longValue()},dataInicial, dataFinal );
			return listSeries[0];
		}catch(Exception e){
			System.out.println("Erro Webservice");
			e.printStackTrace();
			return null;
		}
	}
	
	public WSSerieVO[] getValorByCodigoAndDataInicialFinal(long[] idValores, Calendar dataInicial, Calendar dataFinal){
		try{
			WSSerieVO[] listValores= getWsBancoCentroService().getFachadaWSSGS().getValoresSeriesVO(idValores, formato.format(dataInicial.getTime()), formato.format(dataFinal.getTime()));
			return listValores;
		}catch(Exception e){
			System.out.println("Erro Webservice");
			e.printStackTrace();
			return null;
		}
	}
	
	public double getUltimoValor(Long idValor){
		try{
			WSSerieVO serieVo = getWsBancoCentroService().getFachadaWSSGS().getUltimoValorVO(idValor);
			return serieVo.getUltimoValor().getValor().doubleValue();
		}catch(Exception e){
			System.out.println("Erro Webservice");
			e.printStackTrace();
			return 0.0;
		}
	}
	
	public boolean getExisteCodigo(Long idValor){
		try{
			WSSerieVO serieVo = getWsBancoCentroService().getFachadaWSSGS().getUltimoValorVO(idValor);
			if(serieVo.isPossuiBloqueios()){
				return false;
			}
			return true;
		}catch(Exception e){
			System.out.println("Erro Webservice");
			e.printStackTrace();
			return false;
		}
	}
	
	public FachadaWSSGSService getWsBancoCentroService() throws AxisFault{
		if(wsBancoCentroService == null){
			wsBancoCentroService = new FachadaWSSGSServiceLocator();
		}
		return wsBancoCentroService;
	}
	
	
	
	
}
