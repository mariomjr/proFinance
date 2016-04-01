package org.com.proFinance.dataModel;


import java.util.List;
import java.util.Map;

import org.com.proFinance.dao.SocioEmpresaDao;
import org.com.proFinance.entity.SocioEmpresa;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * @author MarioJr
 *
 */
public class LazySocioEmpresaDataModel extends LazyDataModel<SocioEmpresa> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7209318926945276511L;

	private SocioEmpresaDao socioEmpresaDao;

	private List<SocioEmpresa> listSocioEmpresa;

	private int first;

	private int pageSize;

	private String sortField;

	private SortOrder sortOrder;

	private Map<String, Object> filters;

	public LazySocioEmpresaDataModel(SocioEmpresaDao socioEmpresaDao) {
		this.socioEmpresaDao = socioEmpresaDao;
	}

	@Override
	public SocioEmpresa getRowData(String rowKey) {

		for (SocioEmpresa socrioEmpresa : listSocioEmpresa) {
			if (socrioEmpresa.getId().equals(new Long(rowKey)))
				return socrioEmpresa;
		}
		return null;
	}

	@Override
	public Object getRowKey(SocioEmpresa socrioEmpresa) {
		return socrioEmpresa.getId();
	}

	public List<SocioEmpresa> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		this.first = first;
		this.pageSize = pageSize;
		this.sortField = sortField;
		this.sortOrder = sortOrder;
		this.filters = filters;

		List<SocioEmpresa> listSocioEmpresa = socioEmpresaDao.listar(filters, sortField, sortOrder.name(), first, pageSize);
		this.setRowCount(socioEmpresaDao.count(filters));
		this.listSocioEmpresa = listSocioEmpresa;

		return listSocioEmpresa;
		
	}

	public void atualizarLista() {
		load(first, pageSize, sortField, sortOrder, filters);
	}

}
