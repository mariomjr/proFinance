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
import org.com.proFinance.entity.Empresa;
import org.com.proFinance.entity.SocioEmpresa;
import org.com.proFinance.enuns.SimNao;
import org.com.proFinance.infra.UtilUser;
import org.com.proFinance.util.Uteis;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.mobile.event.SwipeEvent;
import org.primefaces.model.LazyDataModel;

@ViewScoped
@ManagedBean(name="socioEmpresaBean")
public class SocioEmpresaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6759853547066249202L;
	
	@Inject
	SocioEmpresaDao socioEmpresaDao;
	
	private SocioEmpresa socioEmpresaSelect;
	
	private Empresa empresaSelect;
	
	private LazyDataModel<SocioEmpresa> lazySocioEmpresa;
	
	private List<SelectItem> listSocioEmpresaItens;

	private List<SocioEmpresa> listSocioEmpresa;

	@PostConstruct
	public void init(){
		if(UtilUser.isMobile()){
			listSocioEmpresa = socioEmpresaDao.getListSocioEmpresaMobile();
		}else{
			lazySocioEmpresa = new LazySocioEmpresaDataModel(socioEmpresaDao);
		}
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
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Investidor foi salvo!"));
			RequestContext.getCurrentInstance().execute("PF('socioEmpresaDialog').hide();");
			
		}else{
			RequestContext.getCurrentInstance().update("messagesMdlSocioEmpresa");
		}
	}
	
	public void salvarSocioEmpresaMobile(){
		if(validarDados()){
			getSocioEmpresaSelect().setAtivo(SimNao.SIM);
			socioEmpresaDao.salvarSocioEmpresa(getSocioEmpresaSelect());
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Investidor foi salvo!"));
			listSocioEmpresa = socioEmpresaDao.getListSocioEmpresaMobile();
		}
	}
	
	public void removerSocioEmpresa(){
		getSocioEmpresaSelect().setAtivo(SimNao.NAO);
		socioEmpresaDao.salvarSocioEmpresa(getSocioEmpresaSelect());
		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Investidor foi inativado!"));
	}
	
	public void adicionarEmpresa(){
		
		if(validaDadosEmpresa()){
			if(getEmpresaSelect().getRandomId() == null){
				getEmpresaSelect().setRandomId(Uteis.randomId());
				getEmpresaSelect().setAtivo(SimNao.SIM);
				getEmpresaSelect().setSocioEmpresa(getSocioEmpresaSelect());
				getSocioEmpresaSelect().getListEmpresa().add(getEmpresaSelect());
			}
			RequestContext.getCurrentInstance().execute("PF('empresaDialog').hide();");
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Empresa foi inativado!"));
		}else{
			RequestContext.getCurrentInstance().update("messagesMdlEmpresa");
		}
	}
	
	public void setRemoverEmpresa(Integer index){
		
		Empresa empresa = getSocioEmpresaSelect().getListEmpresaAtivas().get(index.intValue());
		empresa.setAtivo(SimNao.NAO);
		
//		getProjetoSelect().getListOcorrenciasProjeto().remove(index.intValue());
	}
	
	private boolean validaDadosEmpresa() {
		if(Uteis.validaNullVazio(getEmpresaSelect().getNome())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O campo Nome é obrigatório!"));
			return false;
		}else if(Uteis.validaNullVazio(getEmpresaSelect().getCnpj())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O campo CNPJ é obrigatório!"));
			return false;
		}
		return true;
	}
	
	private boolean validarDados() {
		if(Uteis.validaNullVazio(getSocioEmpresaSelect().getNome())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O campo Nome é obrigatório!"));
			return false;
		}else if(Uteis.validaNullVazio(getSocioEmpresaSelect().getSigla())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O campo Sigla é obrigatório!"));
			return false;
		}
		return true;
	}

	public void limparSocioEmpresa(){
		setSocioEmpresaSelect(new SocioEmpresa());
	}
	
	public void limparEmpresa(){
		setEmpresaSelect(new Empresa());
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

	public Empresa getEmpresaSelect() {
		if(empresaSelect == null){
			empresaSelect = new Empresa();
		}
		return empresaSelect;
	}

	public void setEmpresaSelect(Empresa empresaSelect) {
		this.empresaSelect = empresaSelect;
	}
}
