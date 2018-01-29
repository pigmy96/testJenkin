package com.pigmy.demospringboot.dao;

import java.util.List;

import com.pigmy.demospringboot.entity.Order;
import com.pigmy.demospringboot.entity.OrderDetail;
import com.pigmy.demospringboot.model.OrderDetailInfo;

public interface OrderDetailDAO {
	public OrderDetail findOrderDetail(int orderDetailID);
	public void save(OrderDetailInfo orderDetailInfo);
	public List<OrderDetailInfo> getListOrderDetailByOrder(Order order);
}
