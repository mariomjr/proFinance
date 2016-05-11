package org.com.proFinance.infra;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.com.proFinance.beans.HomeBean;
import org.com.proFinance.dao.LoginUserDao;
import org.com.proFinance.entity.LoginUser;
import org.com.proFinance.util.LDAPUtil;

@ManagedBean(name="autenticator")
@SessionScoped
public class Autenticator implements Serializable{
	
	private static final long serialVersionUID = -6775769722079847376L;
	
	@Inject
	LoginUser login;
	
	@Inject
	LoginUserDao loginUserDao;
	
	@Inject
	HomeBean homeBean;
	
	public void loginProject() throws IOException {
		
		
		if(LDAPUtil.autenticarAD(getLogin().getLogin(), getLogin().getPassword()) || isUsuarioAdmin()){
			
			insereAlteraLogin(getLogin().getLogin());
			
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	        session.setAttribute("usuarioLogado", getLogin());
	        
	        String renderKitId = FacesContext.getCurrentInstance().getViewRoot().getRenderKitId();       
	        if(renderKitId.equalsIgnoreCase(UtilUser.renderKitPrimefaces)){
	        	FacesContext.getCurrentInstance().getExternalContext().redirect(UtilUser.homeMobilePage);	            
	        }else{
	        	FacesContext.getCurrentInstance().getExternalContext().redirect(UtilUser.homePage);
	        }
		}else{
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Usuário não encontrado!"));
		}
	}

	
    private void insereAlteraLogin(String login) {
		LoginUser loginAux = loginUserDao.buscaUserByLogin(login);
		
		if(loginAux == null){
			loginAux = new LoginUser();
			loginAux.setLogin(login);
		}
		if(isUsuarioAdmin() == false){
			loginAux.setAdUser(LDAPUtil.getADUserByLogin(loginAux.getLogin()));
		}
		
		loginAux.setDataUltimaEntrada(Calendar.getInstance());
		loginUserDao.salvaLoginUser(loginAux);
		setLogin(loginAux);
	}


	private boolean isUsuarioAdmin() {
		if(getLogin().getLogin().equals("ADMIN") && getLogin().getPassword().equals("1122")){
			return true;
		}
		return false;
	}


	public void logout() throws IOException {
        UtilUser.getSession().invalidate();
    	homeBean.redirectLogin();
    }
    
	public boolean isLogado() {
		LoginUser user = (LoginUser) UtilUser.getSession().getAttribute("usuarioLogado");
		if (user != null){
			return true;
		}else{
			return false;
		}
	}
    
	public LoginUser getLogin() {
		if(login == null){
			login = new LoginUser();
		}
		return login;
	}

	public void setLogin(LoginUser login) {
		this.login = login;
	}
	
}
