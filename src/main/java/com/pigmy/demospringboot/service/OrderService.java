package com.pigmy.demospringboot.service;

import com.pigmy.demospringboot.entity.Account;
import com.pigmy.demospringboot.entity.Order;
import com.pigmy.demospringboot.model.OrderDetailInfo;
import com.pigmy.demospringboot.model.OrderInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {
    public OrderInfo addOrder(OrderInfo orderInfo, HttpServletRequest request);
    public Order getOrderLast();
    public List<OrderInfo> getOrderListByAccount();
    public OrderInfo getOrderInfo(String id);
}
