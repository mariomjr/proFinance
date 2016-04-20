package org.com.proFinance.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.com.proFinance.infra.UtilUser;

@ManagedBean
@ViewScoped
public class HomeBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4051634263776770614L;
	
	ExternalContext exContext;
	
	@PostConstruct
	public void init(){
		exContext = FacesContext.getCurrentInstance().getExternalContext();
	}
	
	public void redirectHome() throws IOException{
		if(UtilUser.isMobile()){
			exContext.redirect(exContext.getApplicationContextPath()+"/homeMobile.jsf");
		}else{
			exContext.redirect(exContext.getApplicationContextPath()+"/home.jsf");
		}
	}

	public void redirectSocios() throws IOException{
		if(UtilUser.isMobile()){
			exContext.redirect(exContext.getApplicationContextPath()+"/mobile/SocioEmpresa.jsf");
		}else{
			exContext.redirect(exContext.getApplicationContextPath()+"/pages/SocioEmpresa.jsf");
		}
	}
	
	public void redirectProjetos() throws IOException{
		exContext.redirect(exContext.getApplicationContextPath()+"/pages/Projetos.jsf");
	}
	
	public void redirectOcorrenciaProjeto() throws IOException{
		if(UtilUser.isMobile()){
			exContext.redirect(exContext.getApplicationContextPath()+"/mobile/OcorrenciaProjeto.jsf");
		}else{
			exContext.redirect(exContext.getApplicationContextPath()+"/pages/OcorrenciaProjeto.jsf");
		}
	}
	
	public void redirectLogin() throws IOException{
		if(UtilUser.isMobile()){
			exContext.redirect(exContext.getApplicationContextPath()+"/loginMobile.jsf");
		}else{			
			exContext.redirect(exContext.getApplicationContextPath()+"/login.jsf");
		}
	}
	
	public void redirectIndexador() throws IOException{
		if(UtilUser.isMobile()){
			exContext.redirect(exContext.getApplicationContextPath()+"/mobile/Indexador.jsf");
		}else{			
			exContext.redirect(exContext.getApplicationContextPath()+"/pages/Indexador.jsf");
		}
	}

}
