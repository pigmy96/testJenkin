package com.pigmy.demospringboot.service.impl;

import com.pigmy.demospringboot.dao.OrderDAO;
import com.pigmy.demospringboot.dao.OrderDetailDAO;
import com.pigmy.demospringboot.entity.Order;
import com.pigmy.demospringboot.model.CartInfo;
import com.pigmy.demospringboot.model.GenreInfo;
import com.pigmy.demospringboot.model.OrderDetailInfo;
import com.pigmy.demospringboot.service.OrderDetailService;
import com.pigmy.demospringboot.session.CartSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{
    @Autowired
    OrderDetailDAO orderDetailDAO;
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    CartSession cartSession;

    @Override
    public List<OrderDetailInfo> addOrderDetail(Order order, HttpServletRequest request){
        List<CartInfo> listCart = cartSession.getListCartInSession(request);
        List<OrderDetailInfo> orderDetailInfo = new ArrayList<OrderDetailInfo>();
        for (int i = 0; i < listCart.size(); i++){
            OrderDetailInfo orderDetail = new OrderDetailInfo();
            orderDetail.setAlbum(listCart.get(i).getAlbum());
            orderDetail.setQuantity(listCart.get(i).getQuantity());
            orderDetail.setUnitPrice(listCart.get(i).getUnitPrice());
            orderDetail.setOrder(order);
            orderDetailInfo.add(orderDetail);
            try {
                orderDetailDAO.save(orderDetailInfo.get(i));
            } catch (Exception e) {
                return null;
            }
        }
        cartSession.removeListCartInSession(request);
        return orderDetailInfo;
    }

    @Override
    public List<OrderDetailInfo> getListOrderDetailByOrder(String id){
        int orderID = Integer.parseInt(id);
        Order order = orderDAO.findOrder(orderID);
        List<OrderDetailInfo> orderDetail = orderDetailDAO.getListOrderDetailByOrder(order);
        return orderDetail;
    }
}
