package org.com.proFinance.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.com.proFinance.entity.Indexador;
import org.com.proFinance.enuns.SimNao;
import org.com.proFinance.infra.GenericHibernateDao;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class IndexadorDao {

	@Inject
	GenericHibernateDao<Indexador> dao;
	
	@SuppressWarnings("unchecked")
	public List<Indexador> listar(Map<String, Object> filter, String sortField, String sortOrder, int firstResult, int maxResult) {

		Criteria criteria = criarCriteriaListagem(filter, sortField, sortOrder);
		criteria.setMaxResults(maxResult);
		criteria.setFirstResult(firstResult);

		List<Indexador> listIndexador = (List<Indexador>) criteria.list();

		return listIndexador;
	}
	
	public int count(Map<String, Object> filter) {
		Criteria criteriaCount = criarCriteriaListagem(filter, null, null);
		return ((Number) criteriaCount.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}
	
	
	private Criteria criarCriteriaListagem(Map<String, Object> filter, String sortField, String sortOrder) {

		Session s = (Session) dao.getEntityManager().getDelegate();
		Criteria criteria = s.createCriteria(Indexador.class);
		
		for (String atributo : filter.keySet()) {
			Object filtro = filter.get(atributo);
			if (atributo.equals("id") || atributo.equals("codigo") ) {
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

	public Indexador loadIndexadorById(Long id) {
		Session s = (Session) dao.getEntityManager().getDelegate();
		Indexador indexador = (Indexador)s.load(Indexador.class, id);
		Hibernate.initialize(indexador);
		s.evict(indexador);
		return indexador;
	}

	public void salvarIndexador(Indexador indexador) {
		Session s = (Session) dao.getEntityManager().getDelegate();
		if(indexador.getId() == null){
			s.save(indexador);
		}else{
			s.update(indexador);
		}
		s.flush();
	}
	
	@SuppressWarnings("unchecked")
	public List<Indexador> getListIndexadorAtivos(){
		Session s = (Session) dao.getEntityManager().getDelegate();
		Criteria c = s.createCriteria(Indexador.class);
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		c.add(Restrictions.eq("ativo", SimNao.SIM));
		return c.list();
	}
}
