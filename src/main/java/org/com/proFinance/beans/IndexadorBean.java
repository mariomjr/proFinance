package org.com.proFinance.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.com.proFinance.dao.IndexadorDao;
import org.com.proFinance.dataModel.LazyIndexadorDataModel;
import org.com.proFinance.entity.Indexador;
import org.com.proFinance.enuns.SimNao;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@ViewScoped
@ManagedBean
public class IndexadorBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -103956264563304883L;

	@Inject
	IndexadorDao indexadorDao;
	
	public Indexador indexadorSelect;
	
	public LazyDataModel<Indexador> lazyIndexador;

	@PostConstruct
	public void init(){
		lazyIndexador = new LazyIndexadorDataModel(indexadorDao);
	}
	
	public void onRowSelect(SelectEvent event) {
		indexadorSelect = indexadorDao.loadIndexadorById(((Indexador)event.getObject()).getId());
	}
	
	public void salvarIndexador(){
		if(validarDados()){
			getIndexadorSelect().setAtivo(SimNao.SIM);
			indexadorDao.salvarIndexador(getIndexadorSelect());
			FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Indexador foi salvo!"));
			RequestContext.getCurrentInstance().execute("PF('indexadorDialog').hide();");
			RequestContext.getCurrentInstance().update("messages");
		}else{
			RequestContext.getCurrentInstance().update("messagesMdlIndexador");
		}
	}
	
	public void inativarIndexador(){
		getIndexadorSelect().setAtivo(SimNao.NAO);
		indexadorDao.salvarIndexador(getIndexadorSelect());
		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Indexador foi inativado!"));
	}
	
	private boolean validarDados() {
		if(StringUtils.isNotBlank(getIndexadorSelect().getDescricao())== false){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O campo Descrição é obrigatório!"));
			return false;
		}else if(getIndexadorSelect().getCodigo()== null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O campo Código é obrigatório!"));
			return false;
		}
		return true;
	}

	public void limparIndexador(){
		setIndexadorSelect(new Indexador());
	}
	
	public Indexador getIndexadorSelect() {
		if(indexadorSelect == null){
			indexadorSelect = new Indexador();
		}
		return indexadorSelect;
	}

	public void setIndexadorSelect(Indexador indexadorSelect) {
		this.indexadorSelect = indexadorSelect;
	}

	public LazyDataModel<Indexador> getLazyIndexador() {
		return lazyIndexador;
	}

}

