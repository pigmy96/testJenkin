package com.pigmy.demospringboot.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigmy.demospringboot.dao.OrderDAO;
import com.pigmy.demospringboot.entity.Account;
import com.pigmy.demospringboot.entity.Order;
import com.pigmy.demospringboot.model.OrderInfo;
@Repository
@Transactional
public class OrderDAOImpl implements OrderDAO{
	@Autowired
	   private SessionFactory sessionFactory;
	
	@Override
	public Order findOrder(int orderID) {
	       Session session = sessionFactory.getCurrentSession();
	       Criteria crit = session.createCriteria(Order.class);
	       crit.add(Restrictions.eq("id", orderID));
	       return (Order) crit.uniqueResult();
	   }
	
	@Override
	public void save(OrderInfo orderInfo) {
		   int orderID = orderInfo.getID();
	       Order order = null;

	       if (orderID > 0) {// trường hợp edit => album đã tồn tại trong db
	    	   order = this.findOrder(orderID);
	       }
	       if (order == null) {//album chưa tồn tại trong db
	    	   order = new Order();
	       }
	       System.out.println("save order");
	       order.setOrderDate(orderInfo.getOrderDate());
	       order.setUser(orderInfo.getUser());
	       order.setFirstName(orderInfo.getFirstName());
	       order.setLastName(orderInfo.getLastName());
	       order.setAddress(orderInfo.getAddress());
	       order.setCity(orderInfo.getCity());
	       order.setState(orderInfo.getState());
	       order.setPostalCode(orderInfo.getPostalCode());
	       order.setCountry(orderInfo.getCountry());
	       order.setPhone(orderInfo.getPhone());
	       order.setEmail(orderInfo.getEmail());
	       order.setTotal(orderInfo.getTotal());
        this.sessionFactory.getCurrentSession().persist(order);
	       // Ném ngoại lệ nếu có lỗi ở Db
	       this.sessionFactory.getCurrentSession().flush();
	   }
	
	@Override
	   public Order getOrderLast() {
	   	List<Order> ds1 = new ArrayList<Order>();
	       Session session = sessionFactory.getCurrentSession();
	       Criteria crit = session.createCriteria(Order.class);
	       ds1 =crit.list();

	       return ds1.get(ds1.size() - 1);
			 
	   }
	
	@Override
	public List<OrderInfo> getOrderListByAccount(Account account){
		Session session = sessionFactory.getCurrentSession();
	    Criteria crit = session.createCriteria(Order.class);
        crit.add(Restrictions.eq("user.userName", account.getUserName()));
        @SuppressWarnings("unchecked")
		List<Order> order = (List<Order>) crit.list();
        List<OrderInfo> result = new ArrayList<OrderInfo>();
        for(int i = 0; i < order.size(); i++){
        	System.out.println("order ID: " + order.get(i).getID());
        	result.add(new OrderInfo(order.get(i).getID(), order.get(i).getOrderDate(), order.get(i).getUser(),
        			order.get(i).getFirstName(), order.get(i).getLastName(), order.get(i).getAddress(),
        			order.get(i).getCity(), order.get(i).getState(), order.get(i).getPostalCode(),
        			order.get(i).getCountry(), order.get(i).getPhone(), order.get(i).getEmail(), order.get(i).getTotal()));
        }
        return result;
	}
	
	@Override
	public OrderInfo getOrderInfo(int orderID){
		Order order = this.findOrder(orderID);
	       if (order == null) {
	           return null;
	       }
	       return new OrderInfo(order.getID(), order.getOrderDate(), order.getUser(),
       			order.getFirstName(), order.getLastName(), order.getAddress(),
       			order.getCity(), order.getState(), order.getPostalCode(),
       			order.getCountry(), order.getPhone(), order.getEmail(), order.getTotal());

	}
}
