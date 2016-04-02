package org.com.proFinance.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

import org.com.proFinance.dao.SocioEmpresaDao;
import org.com.proFinance.entity.SocioEmpresa;

@Named
public class SocioEmpresaConverter implements Converter  {
	
	@Inject
	private SocioEmpresaDao socioEmpresaDao;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(arg2 == null || (arg2!= null && arg2.equals("null"))){
			return null;
		}
		long id = Long.valueOf(arg2).longValue();
		if (id == 0){ 	
			return null;
		}else{
			SocioEmpresa socioEmpresa = socioEmpresaDao.loadSocioEmpresaById(id);
			
			return socioEmpresa;	
		}
		
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) {
			return null;
		} else if (arg2 instanceof SocioEmpresa) {
			SocioEmpresa p = (SocioEmpresa) arg2;
			String val = String.valueOf(p.getId());
			return val;
		} else if(arg2 instanceof String){
			
			return "";
		}
		else
			throw new ConverterException(new FacesMessage("Tipo inválido"));
	}

}
