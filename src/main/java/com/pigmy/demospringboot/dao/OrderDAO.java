package com.pigmy.demospringboot.dao;

import java.util.List;

import com.pigmy.demospringboot.entity.Account;
import com.pigmy.demospringboot.entity.Order;
import com.pigmy.demospringboot.model.OrderInfo;

public interface OrderDAO {
	public Order findOrder(int orderID);
	public void save(OrderInfo orderInfo);
	public Order getOrderLast();
	public List<OrderInfo> getOrderListByAccount(Account account);
	public OrderInfo getOrderInfo(int orderID);
}
