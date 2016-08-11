package org.com.proFinance.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.com.proFinance.entity.Empresa;
import org.com.proFinance.entity.SocioEmpresa;
import org.com.proFinance.enuns.SimNao;
import org.com.proFinance.infra.GenericHibernateDao;
import org.com.proFinance.util.Uteis;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class SocioEmpresaDao {

	@Inject
	GenericHibernateDao<SocioEmpresa> dao;
	
	@SuppressWarnings("unchecked")
	public List<SocioEmpresa> listar(Map<String, Object> filter, String sortField, String sortOrder, int firstResult, int maxResult) {

		Criteria criteria = criarCriteriaListagem(filter, sortField, sortOrder);
		criteria.setMaxResults(maxResult);
		criteria.setFirstResult(firstResult);

		List<SocioEmpresa> listSocioEmpresa = (List<SocioEmpresa>) criteria.list();

		return listSocioEmpresa;
	}
	
	public int count(Map<String, Object> filter) {
		Criteria criteriaCount = criarCriteriaListagem(filter, null, null);
		return ((Number) criteriaCount.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}
	
	
	private Criteria criarCriteriaListagem(Map<String, Object> filter, String sortField, String sortOrder) {

		Session s = (Session) dao.getEntityManager().getDelegate();
		Criteria criteria = s.createCriteria(SocioEmpresa.class);
		
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

	public SocioEmpresa loadSocioEmpresaById(Long id) {
		
		Session s = (Session) dao.getEntityManager().getDelegate();
		SocioEmpresa socioEmpresa = (SocioEmpresa)s.load(SocioEmpresa.class, id);
		Hibernate.initialize(socioEmpresa);
		s.evict(socioEmpresa);
		return socioEmpresa;
		
	}

	public void salvarSocioEmpresa(SocioEmpresa socioEmpresa) {
		Session s = (Session) dao.getEntityManager().getDelegate();
		if(socioEmpresa.getId()== null){
			s.save(socioEmpresa);
		}else{
			s.update(socioEmpresa);
		}
		s.flush();
	}
	
	public List<SocioEmpresa> getListSocioEmpresaMobile(){
		return listar(new HashMap<String, Object>(), null, "ASCENDING", 0, 1000);
	}
	
	@SuppressWarnings("unchecked")
	public List<SocioEmpresa> getListSocioEmpresaAtivo(){
		
		Session s = (Session) dao.getEntityManager().getDelegate();
		Criteria c = s.createCriteria(SocioEmpresa.class);
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		c.add(Restrictions.eq("ativo",SimNao.SIM));
		return c.list();
		
	}
	
	public Empresa findEmpresaByCnpjAndNome(String cnpj, String nome){
		Session s = (Session) dao.getEntityManager().getDelegate();
		Criteria c = s.createCriteria(Empresa.class, "empresa");
		c.createAlias("empresa.socioEmpresa", "socioEmpresa");
		c.add(Restrictions.eq("empresa.cnpj",cnpj));
		c.add(Restrictions.eq("socioEmpresa.nome",nome));
		Object result = c.uniqueResult();
		if(result!= null){
			return (Empresa)result;
		}
		return null;
	}
}
