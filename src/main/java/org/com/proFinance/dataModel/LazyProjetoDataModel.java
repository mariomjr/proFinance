package org.com.proFinance.dataModel;

import java.util.List;
import java.util.Map;

import org.com.proFinance.dao.ProjetoDao;
import org.com.proFinance.entity.Projeto;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyProjetoDataModel extends LazyDataModel<Projeto>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -848527400632339558L;

	private ProjetoDao projetoDao;

	private List<Projeto> listProjeto;

	private int first;

	private int pageSize;

	private String sortField;

	private SortOrder sortOrder;

	private Map<String, Object> filters;

	public LazyProjetoDataModel(ProjetoDao projetoDao) {
		this.projetoDao = projetoDao;
	}

	@Override
	public Projeto getRowData(String rowKey) {

		for (Projeto projeto : listProjeto) {
			if (projeto.getId().equals(new Long(rowKey)))
				return projeto;
		}
		return null;
	}

	@Override
	public Object getRowKey(Projeto projeto) {
		return projeto.getId();
	}

	public List<Projeto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		this.first = first;
		this.pageSize = pageSize;
		this.sortField = sortField;
		this.sortOrder = sortOrder;
		this.filters = filters;

		List<Projeto> listProjeto = projetoDao.listar(filters, sortField, sortOrder.name(), first, pageSize);
		this.setRowCount(projetoDao.count(filters));
		this.listProjeto = listProjeto;

		return listProjeto;
		
	}

	public void atualizarLista() {
		load(first, pageSize, sortField, sortOrder, filters);
	}

}
