package org.com.proFinance.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

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
		exContext.redirect(exContext.getApplicationContextPath()+"/home.jsf");
	}

	public void redirectSocios() throws IOException{
		exContext.redirect(exContext.getApplicationContextPath()+"/pages/SocioEmpresa.jsf");
	}
	
	public void redirectProjetos() throws IOException{
		exContext.redirect(exContext.getApplicationContextPath()+"/pages/Projetos.jsf");
	}
	
	public void redirectOcorrenciaProjeto() throws IOException{
		exContext.redirect(exContext.getApplicationContextPath()+"/pages/OcorrenciaProjeto.jsf");
	}
	
	public void redirectLogin() throws IOException{
		exContext.redirect(exContext.getApplicationContextPath()+"/home.jsf");
	}

}
