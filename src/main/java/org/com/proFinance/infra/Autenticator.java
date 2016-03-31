package org.com.proFinance.infra;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.com.proFinance.dao.LoginUserDao;
import org.com.proFinance.entity.LoginUser;
import org.primefaces.context.RequestContext;

@ManagedBean(name="autenticator")
@SessionScoped
public class Autenticator implements Serializable{
	
	private static final long serialVersionUID = -6775769722079847376L;
	
	@Inject
	LoginUser login;
	
	@Inject
	LoginUserDao loginUserDao;
	
	public void loginProject() throws IOException {
		
		setLogin(loginUserDao.buscaUserByLogin(getLogin().getLogin()));
		if(getLogin().getId()!= null){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	        session.setAttribute("usuarioLogado", getLogin());
	        
	        String renderKitId = FacesContext.getCurrentInstance().getViewRoot().getRenderKitId();       
	        if(renderKitId.equalsIgnoreCase(UtilUser.renderKitPrimefaces)){
	        	FacesContext.getCurrentInstance().getExternalContext().redirect(UtilUser.homeMobilePage);	            
	        }
	        FacesContext.getCurrentInstance().getExternalContext().redirect(UtilUser.homePage);
		}else{
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Usuário não encontrado!"));
			RequestContext.getCurrentInstance().update("messages");
		}
		RequestContext.getCurrentInstance().execute("PF('load').hide()");
	}

	
    public String logout() {
        UtilUser.getSession().invalidate();
        return UtilUser.loginPage;
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
