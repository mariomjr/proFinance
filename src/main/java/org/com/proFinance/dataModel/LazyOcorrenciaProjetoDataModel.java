package org.com.proFinance.dataModel;


import java.util.List;
import java.util.Map;

import org.com.proFinance.dao.OcorrenciaProjetoDao;
import org.com.proFinance.entity.OcorrenciaProjeto;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * @author MarioJr
 *
 */
public class LazyOcorrenciaProjetoDataModel extends LazyDataModel<OcorrenciaProjeto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7209318926945276511L;

	private OcorrenciaProjetoDao ocorrenciaProjetoDao;

	private List<OcorrenciaProjeto> listOcorrenciaProjeto;

	private int first;

	private int pageSize;

	private String sortField;

	private SortOrder sortOrder;

	private Map<String, Object> filters;

	public LazyOcorrenciaProjetoDataModel(OcorrenciaProjetoDao ocorrenciaProjetoDao) {
		this.ocorrenciaProjetoDao = ocorrenciaProjetoDao;
	}

	@Override
	public OcorrenciaProjeto getRowData(String rowKey) {

		for (OcorrenciaProjeto ocorrenciaProjeto : listOcorrenciaProjeto) {
			if (ocorrenciaProjeto.getId().equals(new Long(rowKey)))
				return ocorrenciaProjeto;
		}
		return null;
	}

	@Override
	public Object getRowKey(OcorrenciaProjeto ocorrenciaProjeto) {
		return ocorrenciaProjeto.getId();
	}

	public List<OcorrenciaProjeto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		this.first = first;
		this.pageSize = pageSize;
		this.sortField = sortField;
		this.sortOrder = sortOrder;
		this.filters = filters;

		List<OcorrenciaProjeto> listOcorrenciaProjeto = ocorrenciaProjetoDao.listar(filters, sortField, sortOrder.name(), first, pageSize);
		this.setRowCount(ocorrenciaProjetoDao.count(filters));
		this.listOcorrenciaProjeto = listOcorrenciaProjeto;

		return listOcorrenciaProjeto;
		
	}

	public void atualizarLista() {
		load(first, pageSize, sortField, sortOrder, filters);
	}

}
