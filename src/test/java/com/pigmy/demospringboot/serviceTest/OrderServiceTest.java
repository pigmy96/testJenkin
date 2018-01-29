package com.pigmy.demospringboot.serviceTest;

import com.pigmy.demospringboot.dao.AccountDAO;
import com.pigmy.demospringboot.dao.OrderDAO;
import com.pigmy.demospringboot.entity.Account;
import com.pigmy.demospringboot.entity.Order;
import com.pigmy.demospringboot.model.OrderInfo;
import com.pigmy.demospringboot.service.impl.OrderServiceImpl;
import com.pigmy.demospringboot.session.CartSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {
    @InjectMocks
    OrderServiceImpl orderServiceImpl;
    @Mock
    OrderDAO orderDAOMock;
    @Mock
    AccountDAO accountDAOMock;
    @Mock
    CartSession cartSessionMock;
    MockHttpServletRequest request;

    @Before
    public void setUp()throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetOrderLast(){
        Order expectedOrder = new Order();
        Mockito.when(orderDAOMock.getOrderLast()).thenReturn(expectedOrder);
        Assert.assertEquals(expectedOrder, orderServiceImpl.getOrderLast());
    }

    @Test
    public void testGetOrderInfo(){
        OrderInfo orderInfo = new OrderInfo();
        Mockito.when(orderDAOMock.getOrderInfo(1)).thenReturn(orderInfo);
        Assert.assertEquals(orderInfo, orderServiceImpl.getOrderInfo("1"));
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void testGetOrderListByAccount(){
        Account account = new Account();
        Mockito.when(accountDAOMock.findAccount(anyString())).thenReturn(account);
        List<OrderInfo> orderList = asList(new OrderInfo());
        Mockito.when(orderDAOMock.getOrderListByAccount(account)).thenReturn(orderList);
        Assert.assertEquals(orderList, orderServiceImpl.getOrderListByAccount());
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void testAddOrder_whenSuccessThenReturnOrderInfo(){
        Account account = new Account();
        Mockito.when(accountDAOMock.findAccount(anyString())).thenReturn(account);

        double total = 2;
        Mockito.when(cartSessionMock.getTotalPrice(isA(HttpServletRequest.class))).thenReturn(total);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderDate(new Date());
        orderInfo.setUser(account);
        orderInfo.setTotal(total);
        doNothing().when(orderDAOMock).save(orderInfo);

        Assert.assertEquals(orderInfo, orderServiceImpl.addOrder(orderInfo, request));
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void testAddOrder_whenSaveErrorThenReturnNull(){

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderDate(new Date());

        doThrow(Exception.class).when(orderDAOMock).save(orderInfo);

        Assert.assertEquals(null, orderServiceImpl.addOrder(orderInfo, request));
    }
}
