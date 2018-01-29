package com.pigmy.demospringboot.dao.impl;

import com.pigmy.demospringboot.dao.RoleDAO;
import com.pigmy.demospringboot.entity.Role;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RoleDAOImpl implements RoleDAO{
    @Autowired
    private SessionFactory sessionFactory;
    public Role findRole(int id){
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Role.class);
        crit.add(Restrictions.eq("id", id));
        return (Role) crit.uniqueResult();
    }
}
