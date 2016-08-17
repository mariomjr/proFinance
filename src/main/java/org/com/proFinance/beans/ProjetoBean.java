package org.com.proFinance.beans;

import java.io.IOException;
import java.io.InputStream;
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

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.com.proFinance.dao.IndexadorDao;
import org.com.proFinance.dao.ProjetoDao;
import org.com.proFinance.dao.SocioEmpresaDao;
import org.com.proFinance.dataModel.LazyProjetoDataModel;
import org.com.proFinance.entity.Anexo;
import org.com.proFinance.entity.DiaCorridoProjeto;
import org.com.proFinance.entity.Indexador;
import org.com.proFinance.entity.OcorrenciaProjeto;
import org.com.proFinance.entity.Projeto;
import org.com.proFinance.entity.ProjetoDiaCorridoAnoAux;
import org.com.proFinance.enuns.EnumCreditoDebito;
import org.com.proFinance.enuns.SimNao;
import org.com.proFinance.infra.UtilUser;
import org.com.proFinance.services.ProjetoService;
import org.com.proFinance.util.Uteis;
import org.com.proFinance.util.UtilSelectItem;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
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
	
	@Inject
	IndexadorDao indexadorDao;
	
	private Projeto projetoSelect;
	
	private OcorrenciaProjeto ocorrenciaSelect;
	
	private DiaCorridoProjeto diaCorridoSelect;
	
	private LazyDataModel<Projeto> lazyProjeto;
	
	private List<Projeto> listProjetoMobile;
	
	private List<SelectItem> listIndexadorItens;
	
	private Boolean mudouDataFinal;
	
	private List<ProjetoDiaCorridoAnoAux> listProjetoDiaCorridoAno;

	@PostConstruct
	public void init(){
		atualizarListIndexadores();
		if(UtilUser.isMobile()){
			listProjetoMobile = projetoDao.getListProjetosMobile();
		}else{
			lazyProjeto = new LazyProjetoDataModel(projetoDao);
		}
	}
	
	public void onRowSelect(SelectEvent event) throws IOException {
		setMudouDataFinal(Boolean.FALSE);
		setListProjetoDiaCorridoAno(new ArrayList<ProjetoDiaCorridoAnoAux>());
		
		projetoSelect = projetoDao.loadProjetoById(((Projeto)event.getObject()).getId());
		projetoService.recalcularProjeto(getProjetoSelect());
		inserirListProjetoDiaCorridoAno(projetoSelect);
		redirecionarTelaEdit();
	}

	public void onRowSelectMobile(Projeto projeto) throws IOException{
		projetoSelect = projetoDao.loadProjetoById(projeto.getId());
		projetoService.recalcularProjeto(getProjetoSelect());
		redirecionarTelaEdit();
	}
	
	public void salvarProjeto(){
		if(validarDados()){
			getProjetoSelect().setDataFinalPrevistaAtual(getProjetoSelect().getDataFinalPrevistaNova());
			getProjetoSelect().setAtivo(SimNao.SIM);
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
	
	public void redirecionarTelaEdit() throws IOException{
		FacesContext.getCurrentInstance().getExternalContext().redirect("ProjetosEdit.jsf");
	}
	public void redirecionarTela() throws IOException{
		FacesContext.getCurrentInstance().getExternalContext().redirect("Projetos.jsf");
	}
	
	public void voltarTela() throws IOException{
		limparProjeto();
		redirecionarTela();
	}
	
	public void gerarPlanilha() throws IOException{
		
		if(validaDadosPlanilha()){
			getProjetoSelect().setListDiasCorridosProjeto(new ArrayList<DiaCorridoProjeto>());
			projetoService.gerarNovoInvestimento(getProjetoSelect(), false);
			redirecionarTelaEdit();
		}else if(validaPlanilhaImportacao()){
			getProjetoSelect().setListDiasCorridosProjeto(new ArrayList<DiaCorridoProjeto>());
			redirecionarTelaEdit();
		}else{
			if(UtilUser.isMobile()){
				RequestContext.getCurrentInstance().update("messages");
			}else{
				RequestContext.getCurrentInstance().update("messagesMdlProjeto");
			}
		}
		
	}
	
	
	private void inserirListProjetoDiaCorridoAno(Projeto projeto) {
		ProjetoDiaCorridoAnoAux projetoDiaCorridoAno = null;
		Integer anoAtual = null;
		
		for(DiaCorridoProjeto diaCorrido : projeto.getListDiasCorridosProjeto()){
			if(anoAtual == null || (anoAtual != null && anoAtual!= diaCorrido.getData().get(Calendar.YEAR))){
				anoAtual = diaCorrido.getData().get(Calendar.YEAR);
				projetoDiaCorridoAno = new ProjetoDiaCorridoAnoAux();
				projetoDiaCorridoAno.setAno(anoAtual);
				projetoDiaCorridoAno.setListDiasCorridosProjeto(new ArrayList<DiaCorridoProjeto>());
				projetoDiaCorridoAno.getListDiasCorridosProjeto().add(diaCorrido);
				getListProjetoDiaCorridoAno().add(projetoDiaCorridoAno);
			}else{
				projetoDiaCorridoAno.getListDiasCorridosProjeto().add(diaCorrido);
			}
		}
		
	}

	private boolean validaDadosPlanilha() {
		if(getProjetoSelect().getDataInicial()== null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O campo Data Inicial é obrigatório!"));
			return false;
		}else if(getProjetoSelect().getDataFinalPrevistaNova()== null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O campo Data Final é obrigatório!"));
			return false;
		}else if(getProjetoSelect().getDataInicial().after(getProjetoSelect().getDataFinalPrevistaNova())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Data inicial maior que data final!"));
			return false;
		}else if(getProjetoSelect().getJuroMes()<=0 && getProjetoSelect().getIndexador() == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "É necessário inserir juros por mês ou um indexador!"));
			return false;
		}else if(getProjetoSelect().getListOcorrenciasProjeto().isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "É necessário inserir pelo menos um sócio envolvido!"));
			return false;
		}
		return true;
	}
	
	private boolean validaPlanilhaImportacao() {
		if(StringUtils.isBlank(getProjetoSelect().getNome())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O campo nome do projeto é obrigatório!"));
			return false;
		}
		return true;
	}
	
	public List<SelectItem> getTipoItem(){
		return UtilSelectItem.getListEnum(EnumCreditoDebito.values());
	}
	
	public void adicionarOcorrenciaDiaCorrido(){
		
		getOcorrenciaSelect().setDiaCorridoProjeto(getDiaCorridoSelect());
		getOcorrenciaSelect().setData(getDiaCorridoSelect().getData());
		getOcorrenciaSelect().setDataInclusao(Calendar.getInstance());
		getOcorrenciaSelect().setLoginUser(UtilUser.getUserLogado());
		getOcorrenciaSelect().setMostrarOcorrencia(true);
		
		projetoService.adicionaDebitoCredito(getOcorrenciaSelect(), getDiaCorridoSelect());
		
		projetoService.recalcularProjeto(getProjetoSelect(), getDiaCorridoSelect());
		
		getDiaCorridoSelect().getListOcorrenciasProjeto().add(getOcorrenciaSelect());
	}
	
	public void adicionarOcorrenciaProjeto(){
		
		if(validarOcorrenciaProjeto()){
			getOcorrenciaSelect().setProjeto(getProjetoSelect());
			getOcorrenciaSelect().setMostrarOcorrencia(false);
			getOcorrenciaSelect().setDataInclusao(Calendar.getInstance());
			getOcorrenciaSelect().setLoginUser(UtilUser.getUserLogado());
			getProjetoSelect().getListOcorrenciasProjeto().add(getOcorrenciaSelect());
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Valores adicionado!"));
			RequestContext.getCurrentInstance().execute("PF('ocorrenciaProjetoDialog').hide();");
			limparOcorrencia();
		}else{
			RequestContext.getCurrentInstance().update("messagesMdlOcorrenciaProjeto");
		}
	}
	
	private boolean validarOcorrenciaProjeto() {
		if(getOcorrenciaSelect().getSocioEmpresa()== null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O campo Sócio/Empresa é obrigatório!"));
			return false;
		}else if(getOcorrenciaSelect().getCreditoDebito() == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O campo Tipo é obrigatório!"));
			return false;
		}else if(getOcorrenciaSelect().getValor()<=0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O campo Valor é obrigatório!"));
			return false;
		}
		return true;
	}

	public void setRemoverOcorrenciaProjeto(Integer index){
		getProjetoSelect().getListOcorrenciasProjeto().remove(index.intValue());
	}
	
	
	public void atualizarListIndexadores(){
		listIndexadorItens = new ArrayList<SelectItem>();
		for(Indexador indexador : indexadorDao.getListIndexadorAtivos()){
			listIndexadorItens.add(new SelectItem(indexador, indexador.getDescricao()));
		}
	}
	
	public void uploadFileOcorrencia(FileUploadEvent event) {
		
		Anexo anexoOcorrencia = new Anexo();
		anexoOcorrencia.setNomeArquivo(event.getFile().getFileName());
		anexoOcorrencia.setAnexo(event.getFile().getContents());
		anexoOcorrencia.setExtensao(event.getFile().getContentType());

		getOcorrenciaSelect().setAnexo(anexoOcorrencia);
	}
	
	public void uploadExcel(FileUploadEvent event) {
		try {
			if (event.getFile() != null) {

				InputStream input = event.getFile().getInputstream();
				XSSFWorkbook workbook = new XSSFWorkbook(input);
				
				getProjetoSelect().setListDiasCorridosProjeto(new ArrayList<DiaCorridoProjeto>());
				Row linhaRow;
				
				XSSFSheet planilha = workbook.getSheet("PROJETO");
				
				if(planilha!= null){
					for(int linha  = 0; linha < planilha.getLastRowNum(); linha++){
						linhaRow = planilha.getRow(linha);
						if(linhaRow!= null && linhaRow.getCell(0) != null){
							projetoService.inserirDiasCorridoProjeto(getProjetoSelect(), linhaRow);
						}
					}
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", event.getFile().getFileName() + " foi importado com sucesso.");
					FacesContext.getCurrentInstance().addMessage(null, message);
				}else{
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Renomeie o nome da planilha para 'PROJETO'!");
					FacesContext.getCurrentInstance().addMessage(null, message);
				}
				
				workbook.close();
				input.close();

				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void excluirProjeto(){
		getProjetoSelect().setAtivo(SimNao.NAO);
		projetoDao.salvarProjeto(getProjetoSelect());
		try {
			redirecionarTela();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Projeto excluído!"));
		RequestContext.getCurrentInstance().update("messages");
		
	}
	
	public boolean isDiaPrimeiroDiaCorrido(DiaCorridoProjeto diaCorrido){
		if(diaCorrido.getData().get(Calendar.DAY_OF_MONTH) == 1){
			return true;
		}else{
			return false;
		}
	}
	
	public void mudancaData(){
		setMudouDataFinal(Boolean.TRUE);
	}
	
	public void atualizarProjeto(){
		if(validaMudancaData()){
			projetoService.recalcularMudancaData(getProjetoSelect());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Projeto atualizado!"));
		}
		RequestContext.getCurrentInstance().update("messages");
	}

	private boolean validaMudancaData() {
		if(getProjetoSelect().getDataFinalPrevistaAtual()!= null &&
				getProjetoSelect().getDataFinalPrevistaAtual().after(getProjetoSelect().getDataFinalPrevistaNova())){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Nova data deve ser maior que data prevista!"));
			return false;
		}
		return true;
	}
	
	public void recalcularMudancaIndexadorMes(DiaCorridoProjeto diaCorridoProjeto){
		projetoService.recalcularMudancaIndexadorMes(diaCorridoProjeto, getProjetoSelect());
	}
	
	public void recalcularMudancaJurosMes(DiaCorridoProjeto diaCorridoProjeto){
		projetoService.recalcularMudancaJuroMes(diaCorridoProjeto, getProjetoSelect());
	}
	

	public void limparProjeto(){
		setProjetoSelect(new Projeto());
	}
	
	public void limparOcorrencia(){
		setOcorrenciaSelect(new OcorrenciaProjeto());
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

	public List<SelectItem> getListIndexadorItens() {
		return listIndexadorItens;
	}

	public List<Projeto> getListProjetoMobile() {
		return listProjetoMobile;
	}

	public void setListProjetoMobile(List<Projeto> listProjetoMobile) {
		this.listProjetoMobile = listProjetoMobile;
	}

	public Boolean getMudouDataFinal() {
		if(mudouDataFinal == null){
			mudouDataFinal = Boolean.FALSE;
		}
		return mudouDataFinal;
	}

	public void setMudouDataFinal(Boolean mudouDataFinal) {
		this.mudouDataFinal = mudouDataFinal;
	}

	public List<ProjetoDiaCorridoAnoAux> getListProjetoDiaCorridoAno() {
		if(listProjetoDiaCorridoAno == null){
			listProjetoDiaCorridoAno = new ArrayList<ProjetoDiaCorridoAnoAux>();
		}
		return listProjetoDiaCorridoAno;
	}

	public void setListProjetoDiaCorridoAno(List<ProjetoDiaCorridoAnoAux> listProjetoDiaCorridoAno) {
		this.listProjetoDiaCorridoAno = listProjetoDiaCorridoAno;
	}
}
