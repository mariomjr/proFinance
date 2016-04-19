package org.com.proFinance.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.com.proFinance.dao.ProjetoDao;
import org.com.proFinance.dao.SocioEmpresaDao;
import org.com.proFinance.dataModel.LazyProjetoDataModel;
import org.com.proFinance.entity.DiaCorridoProjeto;
import org.com.proFinance.entity.OcorrenciaProjeto;
import org.com.proFinance.entity.Projeto;
import org.com.proFinance.enuns.EnumCreditoDebito;
import org.com.proFinance.infra.UtilUser;
import org.com.proFinance.services.ProjetoService;
import org.com.proFinance.util.Uteis;
import org.com.proFinance.util.UtilSelectItem;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@ManagedBean
@SessionScoped
public class ProjetoBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6268786937292128796L;

	@Inject
	ProjetoDao projetoDao;
	
	@Inject
	ProjetoService projetoService;
	
	@Inject
	SocioEmpresaDao socioEmpresaDao;
	
	private Projeto projetoSelect;
	
	private OcorrenciaProjeto ocorrenciaSelect;
	
	private DiaCorridoProjeto diaCorridoSelect;
	
	private LazyDataModel<Projeto> lazyProjeto;

	@PostConstruct
	public void init(){
		lazyProjeto = new LazyProjetoDataModel(projetoDao);
	}
	
	public void onRowSelect(SelectEvent event) throws IOException {
		projetoSelect = projetoDao.loadProjetoById(((Projeto)event.getObject()).getId());
		projetoService.recalcularProjeto(getProjetoSelect());
		redirecionarTelaEdit();
	}
	
	public void salvarProjeto(){
		if(validarDados()){
			projetoDao.salvarProjeto(getProjetoSelect());
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Projeto foi salvo!"));
		}
		RequestContext.getCurrentInstance().update("messages");
	}
	
	private boolean validarDados() {
		if(Uteis.validaNullVazio(getProjetoSelect().getNome())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O campo Nome é obrigatório!"));
			return false;
		}
		return true;
	}

	public void novoProjeto() throws IOException{
		setProjetoSelect(new Projeto());
		redirecionarTelaEdit();
	}
	
	public void redirecionarTelaEdit() throws IOException{
		FacesContext.getCurrentInstance().getExternalContext().redirect("ProjetosEdit.jsf");
	}
	public void redirecionarTela() throws IOException{
		FacesContext.getCurrentInstance().getExternalContext().redirect("Projetos.jsf");
	}
	
	public void gerarPlanilha(){
		
		if(validaDadosPlanilha()){
			getProjetoSelect().setListDiasCorridosProjeto(new ArrayList<DiaCorridoProjeto>());
			projetoService.gerarNovoInvestimento(getProjetoSelect());
		}
		
	}

	private boolean validaDadosPlanilha() {
		if(getProjetoSelect().getDataInicial()== null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O campo Data Inicial é obrigatório!"));
			return false;
		}else if(getProjetoSelect().getDataFinalPrevista()== null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O campo Data Final é obrigatório!"));
			return false;
		}else if(getProjetoSelect().getDataInicial().after(getProjetoSelect().getDataFinalPrevista())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Data inicial maior que data final!"));
			return false;
		}else if(getProjetoSelect().getJuroMes()== null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O campo Juro/Mês é obrigatório!"));
			return false;
		}
		return true;
	}
	
	public List<SelectItem> getTipoItem(){
		return UtilSelectItem.getListEnum(EnumCreditoDebito.values());
	}

	public void limparOcorrencia(){
		setOcorrenciaSelect(new OcorrenciaProjeto());
	}
	
	public void adicionarOcorrenciaDiaCorrido(){
		getOcorrenciaSelect().setDiaCorridoProjeto(getDiaCorridoSelect());
		getOcorrenciaSelect().setData(getDiaCorridoSelect().getData());
		getOcorrenciaSelect().setDataInclusao(Calendar.getInstance());
		getOcorrenciaSelect().setLoginUser(UtilUser.getUserLogado());
		
//		if(getOcorrenciaSelect().getCreditoDebito().equals(EnumCreditoDebito.DEBITO)){
//			if(getDiaCorridoSelect().getValorDebito()!= null){
//				getDiaCorridoSelect().setValorDebito(getDiaCorridoSelect().getValorDebito()+(getOcorrenciaSelect().getValor()*(-1)));
//			}else{
//				getDiaCorridoSelect().setValorDebito(getOcorrenciaSelect().getValor()*(-1));
//			}
//		}else{
//			if(getDiaCorridoSelect().getValorCredito()!= null){
//				getDiaCorridoSelect().setValorCredito(getDiaCorridoSelect().getValorCredito()+getOcorrenciaSelect().getValor());
//			}else{
//				getDiaCorridoSelect().setValorCredito(getOcorrenciaSelect().getValor());
//			}
//		}
		projetoService.recalcularProjeto(getProjetoSelect(), getDiaCorridoSelect());
		
		getDiaCorridoSelect().getListOcorrenciasProjeto().add(getOcorrenciaSelect());
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

	public OcorrenciaProjeto getOcorrenciaSelect() {
		if(ocorrenciaSelect == null){
			ocorrenciaSelect = new OcorrenciaProjeto();
		}
		return ocorrenciaSelect;
	}

	public void setOcorrenciaSelect(OcorrenciaProjeto ocorrenciaSelect) {
		this.ocorrenciaSelect = ocorrenciaSelect;
	}

	public DiaCorridoProjeto getDiaCorridoSelect() {
		if(diaCorridoSelect == null){
			diaCorridoSelect = new DiaCorridoProjeto();
		}
		return diaCorridoSelect;
	}

	public void setDiaCorridoSelect(DiaCorridoProjeto diaCorridoSelect) {
		this.diaCorridoSelect = diaCorridoSelect;
	}

}
