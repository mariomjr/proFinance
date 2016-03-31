package org.com.proFinance.infra;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class GenericHibernateDao<T> {

    @PersistenceContext(unitName="proFinance")
	EntityManager entityManager;
 
//    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
//    public T getById(Long pk) {
//        return (T)entityManager.find(getTypeClass(), pk);
//    }
 
//    public void save(T entity) {
//    	Session s = (Session)entityManager.getDelegate();
//        s.save(entity);
//        s.flush();
//    }
 
//    public void update(T entity) {
//    	Session s = (Session)entityManager.getDelegate();
//        s.update(entity);
//        s.flush();
//    }
 
//    public void delete(T entity) {
//    	Session s = (Session)entityManager.getDelegate();
//        s.delete(entity);
//        s.flush();
//    }
 
//    public List<T> findAll() {
//    	Session s = (Session)entityManager.getDelegate();
//    	Query q = s.createQuery(("FROM " + getTypeClass().getName()));
//    	
//        return q.list();
//    }
    
//    public List<T> findAllAtivos() {
//    	Session s = (Session)entityManager.getDelegate();
//    	Query q = s.createQuery("FROM " + getTypeClass().getName()+" where ativoInativo = 0");
//    	
//        return q.list();
//    }
    
//    public Query createQuery(String query, Object... parameters) {
//        Query q = entityManager.createQuery(query);
// 
//        for (int i = 1; i <= parameters.length; i++) {
//            q.setParameter(i, parameters[i]);
//        }
         
//        return q;
//    }
 
//    private Class<?> getTypeClass() {
//        Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//        return clazz;
//    }

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


    
}
