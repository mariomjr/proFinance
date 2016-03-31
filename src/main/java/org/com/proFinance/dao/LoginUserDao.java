package org.com.proFinance.dao;

import javax.inject.Inject;

import org.com.proFinance.entity.LoginUser;
import org.com.proFinance.infra.GenericHibernateDao;
import org.hibernate.Session;

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
	
	
}
