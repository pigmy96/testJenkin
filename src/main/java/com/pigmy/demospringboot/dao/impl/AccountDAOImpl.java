package com.pigmy.demospringboot.dao.impl;

import com.pigmy.demospringboot.dao.RoleDAO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import com.pigmy.demospringboot.dao.AccountDAO;
import com.pigmy.demospringboot.entity.Account;
import com.pigmy.demospringboot.model.AccountInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class AccountDAOImpl implements AccountDAO {
   
   @Autowired
   private SessionFactory sessionFactory;
    @Autowired
    private RoleDAO roleDAO;
   @SuppressWarnings("deprecation")
@Override
   public Account findAccount(String userName ) {
       Session session = sessionFactory.getCurrentSession();
       Criteria crit = session.createCriteria(Account.class);
       crit.add(Restrictions.eq("userName", userName));
       return (Account) crit.uniqueResult();
   }
   
   @Override
   public void save(AccountInfo accountInfo){
	   String username = accountInfo.getUsername();
       Account account = null;

       if (username.length() > 0) {
    	   account = this.findAccount(username);
       }
       if (account == null) {
    	   account = new Account();
       }
       String password = accountInfo.getPassword();
 	 	//password = passwordEncoder.encode(password);
       account.setUserName(accountInfo.getUsername());
       account.setActive(true);
       account.setEmail(accountInfo.getEmail());
       account.setPassword(password);
       account.setUserRole(roleDAO.findRole(1));
       this.sessionFactory.getCurrentSession().persist(account);
       // Ném ngoại lệ nếu có lỗi ở Db
       this.sessionFactory.getCurrentSession().flush();
   }

}