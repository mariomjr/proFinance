package org.com.proFinance.infra;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.com.proFinance.dao.LoginUserDao;
import org.com.proFinance.entity.LoginUser;

@ManagedBean(name="autenticator")
@SessionScoped
public class Autenticator implements Serializable{
	
	private static final long serialVersionUID = -6775769722079847376L;
	
	@Inject
	LoginUser login;
	
	@Inject
	LoginUserDao loginUserDao;
	
	public String loginProject() {
//		setLogin(usuarioDao.buscaUserByLogin(getLogin().getLogin()));
//		if(getLogin().getId()!= null){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	        session.setAttribute("usuarioLogado", getLogin());
	        return UtilUser.homePage;
//		}else{
//			FacesContext.getCurrentInstance().addMessage("Usuário não encontrado!", null);
//			return null;
//		}
 
       
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
