package com.pigmy.demospringboot.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigmy.demospringboot.dao.GenericDAO;

@SuppressWarnings("unchecked")
@Repository
public abstract class GenericDAOImpl<E, K, M extends Serializable> 
        implements GenericDAO<E, K, M> {
    @Autowired
    private SessionFactory sessionFactory;
     
    protected Class<? extends E> daoType;
    protected Class<? extends M> modelType;
     
    /**
    * By defining this class as abstract, we prevent Spring from creating 
    * instance of this class If not defined as abstract, 
    * getClass().getGenericSuperClass() would return Object. There would be 
    * exception because Object class does not hava constructor with parameters.
    */
    public GenericDAOImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        daoType = (Class) pt.getActualTypeArguments()[0];
    }
     
    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }
     
     
    @Override
    public void save(M model) {
        currentSession().saveOrUpdate(model);
    }
  
    @Override
    public void delete(K id) {
        
	       E entity = null;
	       entity = this.find(id);
	       
	       Session session = sessionFactory.getCurrentSession();
	       session.delete(entity);
	       session.flush();
    }
     
    @Override
    public E find(K key) {
    	Session session = sessionFactory.getCurrentSession();
	    Criteria crit = session.createCriteria(daoType);
	    crit.add(Restrictions.eq("id", key));
	    return (E) crit.uniqueResult();
        //return (E) currentSession().get(daoType, key);
    }
   
    
    @Override
    public List<E> getAll() {
        return currentSession().createCriteria(daoType).list();
    }
}