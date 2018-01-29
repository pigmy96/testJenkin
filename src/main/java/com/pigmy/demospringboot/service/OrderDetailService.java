package com.pigmy.demospringboot.service;

import com.pigmy.demospringboot.entity.Order;
import com.pigmy.demospringboot.model.OrderDetailInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderDetailService {
    public List<OrderDetailInfo> addOrderDetail(Order order, HttpServletRequest request);
    public List<OrderDetailInfo> getListOrderDetailByOrder(String orderID);
}
