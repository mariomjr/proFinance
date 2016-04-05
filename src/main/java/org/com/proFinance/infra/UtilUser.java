package org.com.proFinance.infra;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.com.proFinance.entity.LoginUser;

public class UtilUser {
	
	public static final String loginPage = "login.jsf";
	public static final String loginMobilePage = "login.jsf";
	public static final String homePage = "home.jsf";
	public static final String homeMobilePage = "homeMobile.jsf";
	public static final String renderKitPrimefaces = "PRIMEFACES_MOBILE";
	
	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
       
	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
 
	public static LoginUser getUserLogado() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return (LoginUser)session.getAttribute("usuarioLogado");
	}
	
	public static String getRenderKitBrowser(){
		return FacesContext.getCurrentInstance().getViewRoot().getRenderKitId();
	}
	
	public static boolean isMobile(){
		if(getRenderKitBrowser().equals(renderKitPrimefaces)){
			return true;
		}else{
			return false;
		}
	}
}
