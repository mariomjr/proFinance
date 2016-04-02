package org.com.proFinance.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.com.proFinance.dao.OcorrenciaProjetoDao;
import org.com.proFinance.dao.ProjetoDao;
import org.com.proFinance.dataModel.LazyOcorrenciaProjetoDataModel;
import org.com.proFinance.entity.DiaCorridoProjeto;
import org.com.proFinance.entity.OcorrenciaProjeto;
import org.com.proFinance.entity.Projeto;
import org.com.proFinance.infra.UtilUser;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@ViewScoped
@ManagedBean
public class OcorrenciaProjetoBean  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 779267191964267854L;

	@Inject
	OcorrenciaProjetoDao ocorrenciaProjetoDao;
	
	@Inject
	ProjetoDao projetoDao;
	
	private OcorrenciaProjeto ocorrenciaProjetoSelect;
	
	private LazyDataModel<OcorrenciaProjeto> lazyOcorrenciaProjeto;
	
	private Projeto projetoSelect;
	
	private List<SelectItem> listProjetoSelectItem;

	@PostConstruct
	public void init(){
		lazyOcorrenciaProjeto = new LazyOcorrenciaProjetoDataModel(ocorrenciaProjetoDao);
	}
	
	public void onRowSelect(SelectEvent event) {
		ocorrenciaProjetoSelect = ocorrenciaProjetoDao.loadOcorrenciaProjetoById(((OcorrenciaProjeto)event.getObject()).getId());
	}
	
	public void salvarOcorrenciaProjeto(){
		if(validarDados()){
			
			getOcorrenciaProjetoSelect().setDataInclusao(Calendar.getInstance());
			getOcorrenciaProjetoSelect().setLoginUser(UtilUser.getUserLogado());
			
			ocorrenciaProjetoDao.salvarOcorrenciaEmpresa(getOcorrenciaProjetoSelect());
			
			RequestContext.getCurrentInstance().execute("PF('socioEmpresaDialog').hide();");
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Ocorrência salva!"));
			RequestContext.getCurrentInstance().update("messages");
		}else{
			RequestContext.getCurrentInstance().update("messagesMdlSocioEmpresa");
		}
	}
	
	private boolean validarDados() {
		if(getProjetoSelect()== null || (getProjetoSelect()!= null && getProjetoSelect().getId()== null)){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O campo Projeto é obrigatório!"));
			return false;
		}else if(getOcorrenciaProjetoSelect().getData()!= null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O campo Data é obrigatório!"));
			return false;
		}else{
			DiaCorridoProjeto diaCorridoBusca = ocorrenciaProjetoDao.buscarDiaCorridoByProjetoAndData(getProjetoSelect(), getOcorrenciaProjetoSelect().getData());
			if(diaCorridoBusca == null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O campo CPF/CNPJ é obrigatório!"));
				return false;
			}else{
				getOcorrenciaProjetoSelect().setDiaCorridoProjeto(diaCorridoBusca);
			}
		}
		return true;
	}

	public void limparOcorrenciaProjeto(){
		setOcorrenciaProjetoSelect(new OcorrenciaProjeto());
	}
	
	public List<SelectItem> getListProjetoSelectItem(){
		
		if(listProjetoSelectItem == null){
			listProjetoSelectItem = new ArrayList<SelectItem>();
			for(Projeto projeto : projetoDao.getListTodosProjetos()){
				listProjetoSelectItem.add(new SelectItem(projeto, projeto.getNome()));
			}
		}
		return listProjetoSelectItem;
		
	}

	public OcorrenciaProjeto getOcorrenciaProjetoSelect() {
		if(ocorrenciaProjetoSelect == null){
			ocorrenciaProjetoSelect = new OcorrenciaProjeto();
		}
		return ocorrenciaProjetoSelect;
	}

	public void setOcorrenciaProjetoSelect(OcorrenciaProjeto ocorrenciaProjetoSelect) {
		this.ocorrenciaProjetoSelect = ocorrenciaProjetoSelect;
	}

	public LazyDataModel<OcorrenciaProjeto> getLazyOcorrenciaProjeto() {
		return lazyOcorrenciaProjeto;
	}

	public Projeto getProjetoSelect() {
		return projetoSelect;
	}

	public void setProjetoSelect(Projeto projetoSelect) {
		this.projetoSelect = projetoSelect;
	}
	
}
