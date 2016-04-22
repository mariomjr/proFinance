package org.com.proFinance.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.com.proFinance.dao.IndexadorDao;
import org.com.proFinance.entity.Indexador;
import org.com.proFinance.entity.Projeto;
import org.com.proFinance.services.ProjetoService;

@ViewScoped
@ManagedBean
public class SimuladorBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3861028531209531091L;

	@Inject
	IndexadorDao indexadorDao;
	
	@Inject
	ProjetoService projetoService;
	
	private List<Projeto> listProjetos;
	
	private Projeto projetoPadrao;
	
	private List<SelectItem> listIndexadorItens;
	
	@PostConstruct
	public void init(){
		atualizarListIndexadores();
	}

	public void simularProjeto(){
		
		Projeto projetoAux = new Projeto();
		projetoAux.setJuroMes(getProjetoPadrao().getJuroMes());
		projetoAux.setIndexador(getProjetoPadrao().getIndexador());
		projetoAux.setValorSimulador(getProjetoPadrao().getValorSimulador());
		projetoAux.setDataInicial(getProjetoPadrao().getDataInicial());
		projetoAux.setDataFinalPrevista(getProjetoPadrao().getDataFinalPrevista());
		
		projetoAux.setTabTitle(trataTitleTab(projetoAux));
		
		projetoService.gerarNovoInvestimento(projetoAux, true);
		
		getListProjetos().add(projetoAux);
		
		getProjetoPadrao().setJuroMes(null);
		getProjetoPadrao().setIndexador(null);
	}
	
	public void limparSimulacoes(){
		setListProjetos(new ArrayList<Projeto>());
		setProjetoPadrao(new Projeto());
	}
	
	private String trataTitleTab(Projeto projetoAux) {
		
		StringBuilder str = new StringBuilder();
		if(projetoAux.getJuroMes()>0){
			str.append("Juro/Mês - ").append(projetoAux.getJuroMes());
		}
		if(projetoAux.getIndexador()!= null){
			str.append(" - ").append(projetoAux.getIndexador().getDescricao());
		}
		
		return str.toString();
	}

	public void atualizarListIndexadores(){
		listIndexadorItens = new ArrayList<SelectItem>();
		for(Indexador indexador : indexadorDao.getListIndexadorAtivos()){
			listIndexadorItens.add(new SelectItem(indexador, indexador.getDescricao()));
		}
	}
	
	public List<Projeto> getListProjetos() {
		if(listProjetos == null){
			listProjetos = new ArrayList<Projeto>();
		}
		return listProjetos;
	}

	public void setListProjetos(List<Projeto> listProjetos) {
		this.listProjetos = listProjetos;
	}

	public Projeto getProjetoPadrao() {
		if(projetoPadrao == null){
			projetoPadrao = new Projeto();
		}
		return projetoPadrao;
	}

	public void setProjetoPadrao(Projeto projetoPadrao) {
		this.projetoPadrao = projetoPadrao;
	}

	public List<SelectItem> getListIndexadorItens() {
		return listIndexadorItens;
	}
	
	
}
