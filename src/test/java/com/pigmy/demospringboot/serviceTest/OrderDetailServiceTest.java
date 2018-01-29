package com.pigmy.demospringboot.serviceTest;

import com.pigmy.demospringboot.dao.OrderDAO;
import com.pigmy.demospringboot.dao.OrderDetailDAO;
import com.pigmy.demospringboot.entity.Album;
import com.pigmy.demospringboot.entity.Cart;
import com.pigmy.demospringboot.entity.Order;
import com.pigmy.demospringboot.model.CartInfo;
import com.pigmy.demospringboot.model.OrderDetailInfo;
import com.pigmy.demospringboot.service.OrderDetailService;
import com.pigmy.demospringboot.service.impl.OrderDetailServiceImpl;
import com.pigmy.demospringboot.session.CartSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailServiceTest {
    @InjectMocks
    OrderDetailServiceImpl orderDetailServiceImpl;
    @Mock
    OrderDetailDAO orderDetailDAOMock;
    @Mock
    OrderDAO orderDAOMock;
    @Mock
    CartSession cartSessionMock;
    MockHttpServletRequest request;

    @Before
    public void setUp()throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddOrderDetail_whenSuccessThenReturnListOrderDetail(){
        List<CartInfo> listCart = asList(new CartInfo("1", new Album(), 2, 1),
                                         new CartInfo("2", new Album(), 1, 1));
        doReturn(listCart).when(cartSessionMock).getListCartInSession(request);
        Order order = new Order();
        doNothing().when(orderDetailDAOMock).save(isA(OrderDetailInfo.class));
        List<OrderDetailInfo> orderDetailInfo = orderDetailServiceImpl.addOrderDetail(order, request);

        System.out.println("size:" + orderDetailInfo.size());
        for(int i = 0; i < listCart.size(); i++){
            Assert.assertEquals(listCart.get(i).getAlbum(), orderDetailInfo.get(i).getAlbum());
            Assert.assertEquals(listCart.get(i).getQuantity(), orderDetailInfo.get(i).getQuantity());
            Assert.assertEquals(listCart.get(i).getUnitPrice(), orderDetailInfo.get(i).getUnitPrice(), 0);

        }
        //Assert.assertNotEquals(null, orderDetailServiceImpl.addOrderDetail(order, request));
        Assert.assertEquals(2, orderDetailInfo.size());

    }

    @Test
    public void testAddOrderDetail_whenSaveErrorThenReturnNull(){
        List<CartInfo> listCart = asList(new CartInfo("1", new Album(), 2, 1),
                new CartInfo("2", new Album(), 1, 1));
        doReturn(listCart).when(cartSessionMock).getListCartInSession(request);
        Order order = new Order();
        doThrow(Exception.class).when(orderDetailDAOMock).save(isA(OrderDetailInfo.class));
        List<OrderDetailInfo> orderDetailInfo = orderDetailServiceImpl.addOrderDetail(order, request);

        //Assert.assertNotEquals(null, orderDetailServiceImpl.addOrderDetail(order, request));
        Assert.assertEquals(null, orderDetailInfo);

    }

    @Test
    public void testGetListOrderDetailByOrder(){
        Order order = new Order();
        doReturn(order).when(orderDAOMock).findOrder(1);
        List<OrderDetailInfo> orderDetailInfos = asList(new OrderDetailInfo());
        doReturn(orderDetailInfos).when(orderDetailDAOMock).getListOrderDetailByOrder(order);
        Assert.assertEquals(orderDetailInfos, orderDetailServiceImpl.getListOrderDetailByOrder("1"));
    }
}
