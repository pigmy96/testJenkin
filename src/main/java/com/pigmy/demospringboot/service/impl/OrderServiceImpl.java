package com.pigmy.demospringboot.service.impl;

import com.pigmy.demospringboot.dao.AccountDAO;
import com.pigmy.demospringboot.dao.OrderDAO;
import com.pigmy.demospringboot.entity.Account;
import com.pigmy.demospringboot.entity.Order;
import com.pigmy.demospringboot.entity.OrderDetail;
import com.pigmy.demospringboot.model.CartInfo;
import com.pigmy.demospringboot.model.OrderInfo;
import com.pigmy.demospringboot.service.OrderService;
import com.pigmy.demospringboot.session.CartSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    AccountDAO accountDAO;
    @Autowired
    CartSession cartSession;

    @Override
    public OrderInfo addOrder(OrderInfo orderInfo, HttpServletRequest request){
        orderInfo.setOrderDate(new Date());

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountDAO.findAccount(userDetails.getUsername());
        orderInfo.setUser(account);

        orderInfo.setTotal(cartSession.getTotalPrice(request));
       // List<CartInfo> listCart = CartSession.getListCartInSession(request);

        try {
            orderDAO.save(orderInfo);
        } catch (Exception e) {
           return null;
        }
        return orderInfo;
    }

    @Override
    public Order getOrderLast(){
        return orderDAO.getOrderLast();
    }

    @Override
    public List<OrderInfo> getOrderListByAccount(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountDAO.findAccount(userDetails.getUsername());
        List<OrderInfo> orderList = orderDAO.getOrderListByAccount(account);
        return  orderList;
    }

    @Override
    public OrderInfo getOrderInfo(String id){
        int orderID = Integer.parseInt(id);
        OrderInfo orderInfo = orderDAO.getOrderInfo(orderID);
        return orderInfo;
    }
}
