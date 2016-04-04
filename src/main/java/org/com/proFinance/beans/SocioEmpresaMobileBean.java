package org.com.proFinance.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.com.proFinance.dao.SocioEmpresaDao;
import org.com.proFinance.entity.SocioEmpresa;
import org.primefaces.mobile.event.SwipeEvent;

@ViewScoped
@ManagedBean
public class SocioEmpresaMobileBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2961866897851968845L;
	
	@Inject
	SocioEmpresaDao socioEmpresaDao;
	
	private SocioEmpresa socioEmpresaSelect;
	
	private List<SocioEmpresa> listSocioEmpresa;
	
	@PostConstruct
	public void init(){
		listSocioEmpresa = socioEmpresaDao.getListSocioEmpresaMobile();
	}
	
	 public void onRowSwipeLeft(SwipeEvent event) {
		socioEmpresaSelect = socioEmpresaDao.loadSocioEmpresaById(((SocioEmpresa)event.getData()).getId());
    }

	public List<SocioEmpresa> getListSocioEmpresa() {
		return listSocioEmpresa;
	}

	public SocioEmpresa getSocioEmpresaSelect() {
		if(socioEmpresaSelect == null){
			socioEmpresaSelect = new SocioEmpresa();
		}
		return socioEmpresaSelect;
	}

	public void setSocioEmpresaSelect(SocioEmpresa socioEmpresaSelect) {
		this.socioEmpresaSelect = socioEmpresaSelect;
	}	

}
