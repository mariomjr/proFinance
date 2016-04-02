package org.com.proFinance.dao;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.com.proFinance.entity.DiaCorridoProjeto;
import org.com.proFinance.entity.OcorrenciaProjeto;
import org.com.proFinance.entity.Projeto;
import org.com.proFinance.infra.GenericHibernateDao;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class OcorrenciaProjetoDao {

	@Inject
	GenericHibernateDao<OcorrenciaProjeto> dao;
	
	@SuppressWarnings("unchecked")
	public List<OcorrenciaProjeto> listar(Map<String, Object> filter, String sortField, String sortOrder, int firstResult, int maxResult) {

		Criteria criteria = criarCriteriaListagem(filter, sortField, sortOrder);
		criteria.setMaxResults(maxResult);
		criteria.setFirstResult(firstResult);

		List<OcorrenciaProjeto> listOcorrenciaProjeto = (List<OcorrenciaProjeto>) criteria.list();

		return listOcorrenciaProjeto;
	}
	
	public int count(Map<String, Object> filter) {
		Criteria criteriaCount = criarCriteriaListagem(filter, null, null);
		return ((Number) criteriaCount.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}
	
	
	private Criteria criarCriteriaListagem(Map<String, Object> filter, String sortField, String sortOrder) {

		Session s = (Session) dao.getEntityManager().getDelegate();
		Criteria criteria = s.createCriteria(OcorrenciaProjeto.class);
		
		for (String atributo : filter.keySet()) {
			Object filtro = filter.get(atributo);
			if (atributo.equals("id")) {
				criteria.add(Restrictions.eq(atributo, Long.valueOf((String) filtro)));
			}else{ 
				criteria.add(Restrictions.ilike(atributo, (String) filtro, MatchMode.ANYWHERE));
			}
		}
		if (sortField != null) {
			if (sortOrder.contains("DES")) {
				criteria.addOrder(Order.desc(sortField));
			} else {
				criteria.addOrder(Order.asc(sortField));
			}
		}
		
//		criteria.add(Restrictions.eq("ativo", SimNao.SIM));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteria;
	}
	
	public DiaCorridoProjeto buscarDiaCorridoByProjetoAndData(Projeto projeto, Calendar data){
		
		Session s = (Session) dao.getEntityManager().getDelegate();
		Criteria c = s.createCriteria(DiaCorridoProjeto.class);
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		c.add(Restrictions.eq("projeto", projeto));
		c.add(Restrictions.eq("data", data));
		Object result = c.uniqueResult();
		
		if(result != null){
			return(DiaCorridoProjeto)result;
		}
		return null;
		
	}

	public OcorrenciaProjeto loadOcorrenciaProjetoById(Long id) {
		
		Session s = (Session) dao.getEntityManager().getDelegate();
		OcorrenciaProjeto ocorrenciaProjeto = (OcorrenciaProjeto)s.load(OcorrenciaProjeto.class, id);
		Hibernate.initialize(ocorrenciaProjeto);
		s.evict(ocorrenciaProjeto);
		return ocorrenciaProjeto;
		
	}

	public void salvarOcorrenciaEmpresa(OcorrenciaProjeto ocorrenciaEmpresa) {
		Session s = (Session) dao.getEntityManager().getDelegate();
		if(ocorrenciaEmpresa.getId()== null){
			s.save(ocorrenciaEmpresa);
		}else{
			s.update(ocorrenciaEmpresa);
		}
		s.flush();
	}
}
