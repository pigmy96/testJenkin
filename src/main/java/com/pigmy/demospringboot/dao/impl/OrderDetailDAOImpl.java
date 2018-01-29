package com.pigmy.demospringboot.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigmy.demospringboot.dao.OrderDetailDAO;
import com.pigmy.demospringboot.entity.Order;
import com.pigmy.demospringboot.entity.OrderDetail;
import com.pigmy.demospringboot.model.OrderDetailInfo;
@Repository
@Transactional
public class OrderDetailDAOImpl implements OrderDetailDAO{
	@Autowired
	   private SessionFactory sessionFactory;
	
	@Override
	public OrderDetail findOrderDetail(int orderDetailID) {
	       Session session = sessionFactory.getCurrentSession();
	       Criteria crit = session.createCriteria(OrderDetail.class);
	       crit.add(Restrictions.eq("id", orderDetailID));
	       return (OrderDetail) crit.uniqueResult();
	   }
	
	@Override
	public void save(OrderDetailInfo orderDetailInfo) {
		   int orderDetailID = orderDetailInfo.getID();
	       OrderDetail orderDetail = null;

	       if (orderDetailID > 0) {
	    	   orderDetail = this.findOrderDetail(orderDetailID);
	       }
	       if (orderDetail == null) {
	    	   orderDetail = new OrderDetail();
	       }
	       orderDetail.setAlbum(orderDetailInfo.getAlbum());
	       orderDetail.setOrder(orderDetailInfo.getOrder());
	       orderDetail.setQuantity(orderDetailInfo.getQuantity());
	       orderDetail.setUnitPrice(orderDetailInfo.getUnitPrice());
	      
     this.sessionFactory.getCurrentSession().persist(orderDetail);
	       // Ném ngoại lệ nếu có lỗi ở Db
	       this.sessionFactory.getCurrentSession().flush();
	   }
	
	@Override
	public List<OrderDetailInfo> getListOrderDetailByOrder(Order order){
		Session session = sessionFactory.getCurrentSession();
	    Criteria crit = session.createCriteria(OrderDetail.class);
        crit.add(Restrictions.eq("order.id", order.getID()));
        @SuppressWarnings("unchecked")
		List<OrderDetail> orderDetail = (List<OrderDetail>) crit.list();
        List<OrderDetailInfo> result = new ArrayList<OrderDetailInfo>();
        for(int i = 0; i < orderDetail.size(); i++){
        	result.add(new OrderDetailInfo(orderDetail.get(i).getID(), orderDetail.get(i).getOrder(),
        			orderDetail.get(i).getAlbum(),
        			orderDetail.get(i).getQuantity(), orderDetail.get(i).getUnitPrice()));
        }
        return result;
	}

}

