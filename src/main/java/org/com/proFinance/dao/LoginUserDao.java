package org.com.proFinance.dao;

import javax.inject.Inject;

import org.com.proFinance.entity.LoginUser;
import org.com.proFinance.infra.GenericHibernateDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class LoginUserDao {

	@Inject
	GenericHibernateDao<LoginUser> dao;
	
	public void salvaLoginUser(LoginUser loginUser){
		Session s = (Session)dao.getEntityManager().getDelegate();
		if(loginUser.getId()!= null){
			s.update(loginUser);
		}else{
			s.save(loginUser);
		}
		s.flush();
		
	}

	public LoginUser buscaUserByLogin(String login) {
		
		Session s = (Session)dao.getEntityManager().getDelegate();
		Criteria c = s.createCriteria(LoginUser.class);
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		c.add(Restrictions.eq("login", login));
		Object result = c.uniqueResult();
		
		if(result != null){
			return (LoginUser)result;
		}
		return null;
	}
	
	
}
