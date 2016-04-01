package org.com.proFinance.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.com.proFinance.dao.ProjetoDao;
import org.com.proFinance.dao.SocioEmpresaDao;
import org.com.proFinance.dataModel.LazyProjetoDataModel;
import org.com.proFinance.dataModel.LazySocioEmpresaDataModel;
import org.com.proFinance.entity.Projeto;
import org.com.proFinance.entity.SocioEmpresa;
import org.com.proFinance.enuns.SimNao;
import org.com.proFinance.util.Uteis;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@ManagedBean
@ViewScoped
public class ProjetoBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6268786937292128796L;

	@Inject
	ProjetoDao projetoDao;
	
	private Projeto projetoSelect;
	
	private LazyDataModel<Projeto> lazyProjeto;

	@PostConstruct
	public void init(){
		lazyProjeto = new LazyProjetoDataModel(projetoDao);
	}
	
	public void onRowSelect(SelectEvent event) {
		projetoSelect = projetoDao.loadProjetoById(((Projeto)event.getObject()).getId());
	}
	
	public void salvarProjeto(){
		if(validarDados()){
			projetoDao.salvarProjeto(getProjetoSelect());
			
			RequestContext.getCurrentInstance().execute("PF('socioEmpresaDialog').hide();");
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Sócio/Empresa foi salvo!"));
			RequestContext.getCurrentInstance().update("messages");
		}else{
			RequestContext.getCurrentInstance().update("messagesMdlSocioEmpresa");
		}
	}
	
	private boolean validarDados() {
		if(Uteis.validaNullVazio(getProjetoSelect().getNome())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O campo Nome é obrigatório!"));
			return false;
		}else if(Uteis.validaNullVazio(getSocioEmpresaSelect().getSigla())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O campo Sigla é obrigatório!"));
			return false;
		}else if(Uteis.validaNullVazio(getSocioEmpresaSelect().getCpfCnpj())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O campo CPF/CNPJ é obrigatório!"));
			return false;
		}
		return true;
	}

	public void limparProjeto(){
		setProjetoSelect(new Projeto());
	}

	public Projeto getProjetoSelect() {
		if(projetoSelect == null){
			projetoSelect = new Projeto();
		}
		return projetoSelect;
	}

	public void setProjetoSelect(Projeto projetoSelect) {
		this.projetoSelect = projetoSelect;
	}

	public LazyDataModel<Projeto> getLazyProjeto() {
		return lazyProjeto;
	}

	public void setLazyProjeto(LazyDataModel<Projeto> lazyProjeto) {
		this.lazyProjeto = lazyProjeto;
	}

}
