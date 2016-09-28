package org.com.proFinance.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

import org.com.proFinance.dao.SocioEmpresaDao;
import org.com.proFinance.entity.Empresa;

@Named
public class EmpresaConverter implements Converter  {
	
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
			Empresa empresa = socioEmpresaDao.loadEmpresaById(id);
			
			return empresa;	
		}
		
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) {
			return null;
		} else if (arg2 instanceof Empresa) {
			Empresa p = (Empresa) arg2;
			String val = String.valueOf(p.getId());
			return val;
		} else if(arg2 instanceof String){
			
			return "";
		}
		else
			throw new ConverterException(new FacesMessage("Tipo inválido"));
	}

}
