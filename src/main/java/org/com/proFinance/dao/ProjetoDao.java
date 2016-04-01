package org.com.proFinance.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.com.proFinance.entity.Projeto;
import org.com.proFinance.infra.GenericHibernateDao;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class ProjetoDao {

	@Inject
	GenericHibernateDao<Projeto> dao;
	
	@SuppressWarnings("unchecked")
	public List<Projeto> listar(Map<String, Object> filter, String sortField, String sortOrder, int firstResult, int maxResult) {

		Criteria criteria = criarCriteriaListagem(filter, sortField, sortOrder);
		criteria.setMaxResults(maxResult);
		criteria.setFirstResult(firstResult);

		List<Projeto> listProjeto = (List<Projeto>) criteria.list();

		return listProjeto;
	}
	
	public int count(Map<String, Object> filter) {
		Criteria criteriaCount = criarCriteriaListagem(filter, null, null);
		return ((Number) criteriaCount.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}
	
	
	private Criteria criarCriteriaListagem(Map<String, Object> filter, String sortField, String sortOrder) {

		Session s = (Session) dao.getEntityManager().getDelegate();
		Criteria criteria = s.createCriteria(Projeto.class);
		
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
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteria;
	}

	public Projeto loadProjetoById(Long id) {
		
		Session s = (Session) dao.getEntityManager().getDelegate();
		Projeto projeto = (Projeto)s.load(Projeto.class, id);
		Hibernate.initialize(projeto);
		s.evict(projeto);
		return projeto;
		
	}

	public void salvarProjeto(Projeto projeto) {
		Session s = (Session) dao.getEntityManager().getDelegate();
		if(projeto.getId()== null){
			s.save(projeto);
		}else{
			s.update(projeto);
		}
		s.flush();
	}
}
