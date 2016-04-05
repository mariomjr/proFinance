package org.com.proFinance.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.com.proFinance.dao.SocioEmpresaDao;
import org.com.proFinance.dataModel.LazySocioEmpresaDataModel;
import org.com.proFinance.entity.SocioEmpresa;
import org.com.proFinance.enuns.SimNao;
import org.com.proFinance.util.Uteis;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.mobile.event.SwipeEvent;
import org.primefaces.model.LazyDataModel;

@ViewScoped
@ManagedBean
public class SocioEmpresaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6759853547066249202L;
	
	@Inject
	SocioEmpresaDao socioEmpresaDao;
	
	private SocioEmpresa socioEmpresaSelect;
	
	private LazyDataModel<SocioEmpresa> lazySocioEmpresa;
	
	private List<SelectItem> listSocioEmpresaItens;

	private List<SocioEmpresa> listSocioEmpresa;

	@PostConstruct
	public void init(){
		lazySocioEmpresa = new LazySocioEmpresaDataModel(socioEmpresaDao);
	}
	
	public void onRowSelect(SelectEvent event) {
		socioEmpresaSelect = socioEmpresaDao.loadSocioEmpresaById(((SocioEmpresa)event.getObject()).getId());
	}
	
	public void onRowSwipeLeft(SwipeEvent event) {
		socioEmpresaSelect = socioEmpresaDao.loadSocioEmpresaById(((SocioEmpresa)event.getData()).getId());
	}
	
	public void salvarSocioEmpresa(){
		if(validarDados()){
			getSocioEmpresaSelect().setAtivo(SimNao.SIM);
			socioEmpresaDao.salvarSocioEmpresa(getSocioEmpresaSelect());
			
			RequestContext.getCurrentInstance().execute("PF('socioEmpresaDialog').hide();");
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Sócio/Empresa foi salvo!"));
			RequestContext.getCurrentInstance().update("messages");
		}else{
			RequestContext.getCurrentInstance().update("messagesMdlSocioEmpresa");
		}
	}
	
	public void removerSocioEmpresa(){
		getSocioEmpresaSelect().setAtivo(SimNao.NAO);
		socioEmpresaDao.salvarSocioEmpresa(getSocioEmpresaSelect());
		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Sócio/Empresa foi inativado!"));
	}
	
	private boolean validarDados() {
		if(Uteis.validaNullVazio(getSocioEmpresaSelect().getNome())){
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

	public void limparSocioEmpresa(){
		setSocioEmpresaSelect(new SocioEmpresa());
	}

	public List<SelectItem> getListSocioEmpresaItens() {
		listSocioEmpresaItens = new ArrayList<SelectItem>();
		for(SocioEmpresa socioEmpresa : socioEmpresaDao.getListSocioEmpresaAtivo()){
			listSocioEmpresaItens.add(new SelectItem(socioEmpresa, socioEmpresa.getNome()));
		}
		
		return listSocioEmpresaItens;
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

	public LazyDataModel<SocioEmpresa> getLazySocioEmpresa() {
		return lazySocioEmpresa;
	}

	public void setLazySocioEmpresa(LazyDataModel<SocioEmpresa> lazySocioEmpresa) {
		this.lazySocioEmpresa = lazySocioEmpresa;
	}

	public List<SocioEmpresa> getListSocioEmpresa() {
		return listSocioEmpresa;
	}

	public void setListSocioEmpresa(List<SocioEmpresa> listSocioEmpresa) {
		this.listSocioEmpresa = listSocioEmpresa;
	}
	
}
