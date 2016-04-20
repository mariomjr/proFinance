package org.com.proFinance.dataModel;


import java.util.List;
import java.util.Map;

import org.com.proFinance.dao.IndexadorDao;
import org.com.proFinance.entity.Indexador;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * @author MarioJr
 *
 */
public class LazyIndexadorDataModel extends LazyDataModel<Indexador> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2944255519145793120L;

	private IndexadorDao indexadorDao;

	private List<Indexador> listIndexador;

	private int first;

	private int pageSize;

	private String sortField;

	private SortOrder sortOrder;

	private Map<String, Object> filters;

	public LazyIndexadorDataModel(IndexadorDao indexadorDao) {
		this.indexadorDao = indexadorDao;
	}

	@Override
	public Indexador getRowData(String rowKey) {

		for (Indexador indexador : listIndexador) {
			if (indexador.getId().equals(new Long(rowKey)))
				return indexador;
		}
		return null;
	}

	@Override
	public Object getRowKey(Indexador indexador) {
		return indexador.getId();
	}

	public List<Indexador> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		this.first = first;
		this.pageSize = pageSize;
		this.sortField = sortField;
		this.sortOrder = sortOrder;
		this.filters = filters;

		List<Indexador> listIndexador = indexadorDao.listar(filters, sortField, sortOrder.name(), first, pageSize);
		this.setRowCount(indexadorDao.count(filters));
		this.listIndexador = listIndexador;

		return listIndexador;
		
	}

	public void atualizarLista() {
		load(first, pageSize, sortField, sortOrder, filters);
	}

}
