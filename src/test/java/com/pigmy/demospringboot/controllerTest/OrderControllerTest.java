package com.pigmy.demospringboot.controllerTest;

import com.pigmy.demospringboot.controller.OrderController;
import com.pigmy.demospringboot.entity.Account;
import com.pigmy.demospringboot.entity.Album;
import com.pigmy.demospringboot.entity.Order;
import com.pigmy.demospringboot.model.CartInfo;
import com.pigmy.demospringboot.model.GenreInfo;
import com.pigmy.demospringboot.model.OrderDetailInfo;
import com.pigmy.demospringboot.model.OrderInfo;
import com.pigmy.demospringboot.service.GenreService;
import com.pigmy.demospringboot.service.OrderDetailService;
import com.pigmy.demospringboot.service.OrderService;
import com.pigmy.demospringboot.session.CartSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderControllerTest {
    @InjectMocks
    OrderController orderController;

    @Mock
    CartSession cartSessionMock;
    @Mock
    GenreService genreServiceMock;
    @Mock
    OrderService orderServiceMock;
    @Mock
    OrderDetailService orderDetailServiceMock;

    MockMvc mockMvc;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(orderController)
                .setViewResolvers(new StandaloneMvcTestViewResolver())
                .build();
    }

    @Test
    public void testOrderHistory() throws Exception {

        List<GenreInfo> expectedGenre = asList(new GenreInfo());
        Mockito.doReturn(expectedGenre).when(genreServiceMock).getListGenre();

        List<OrderInfo> expectedOrder = asList(new OrderInfo(1, new Date(), new Account(),
                "abc", "def", "123 abc", "TP.HCM", "state",
                "12345", "Vietnam","1234567890", "123@abc.com", 3),
                new OrderInfo(2, new Date(), new Account(),
                "Dinh", "My", "456 abc", "TP.HCM", "None",
                "12345", "Vietnam","2345678901", "456@abc.com", 2));
        Mockito.doReturn(expectedOrder).when(orderServiceMock).getOrderListByAccount();

        int totalQuantity = 0;
        Mockito.doReturn(totalQuantity).when(cartSessionMock).getTotalQuantity(isA(HttpServletRequest.class));

        mockMvc.perform(get("/order/orderHistory"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("orderList", hasSize(2)))
                .andExpect(model().attribute("orderList", hasItem(
                        allOf(
                                hasProperty("orderDate", is(expectedOrder.get(0).getOrderDate())),
                                hasProperty("user", is(expectedOrder.get(0).getUser())),
                                hasProperty("firstName", is(expectedOrder.get(0).getFirstName())),
                                hasProperty("lastName", is(expectedOrder.get(0).getLastName())),
                                hasProperty("address", is(expectedOrder.get(0).getAddress())),
                                hasProperty("city", is(expectedOrder.get(0).getCity())),
                                hasProperty("state", is(expectedOrder.get(0).getState())),
                                hasProperty("postalCode", is(expectedOrder.get(0).getPostalCode())),
                                hasProperty("country", is(expectedOrder.get(0).getCountry())),
                                hasProperty("phone", is(expectedOrder.get(0).getPhone())),
                                hasProperty("email", is(expectedOrder.get(0).getEmail())),
                                hasProperty("total", is(expectedOrder.get(0).getTotal()))
                        )
                )))
                .andExpect(model().attribute("orderList", hasItem(
                        allOf(
                                hasProperty("orderDate", is(expectedOrder.get(1).getOrderDate())),
                                hasProperty("user", is(expectedOrder.get(1).getUser())),
                                hasProperty("firstName", is(expectedOrder.get(1).getFirstName())),
                                hasProperty("lastName", is(expectedOrder.get(1).getLastName())),
                                hasProperty("address", is(expectedOrder.get(1).getAddress())),
                                hasProperty("city", is(expectedOrder.get(1).getCity())),
                                hasProperty("state", is(expectedOrder.get(1).getState())),
                                hasProperty("postalCode", is(expectedOrder.get(1).getPostalCode())),
                                hasProperty("country", is(expectedOrder.get(1).getCountry())),
                                hasProperty("phone", is(expectedOrder.get(1).getPhone())),
                                hasProperty("email", is(expectedOrder.get(1).getEmail())),
                                hasProperty("total", is(expectedOrder.get(1).getTotal()))
                        )
                )))
                .andExpect(model().attribute("genreList", expectedGenre))
                .andExpect(model().attribute("totalQuantity", totalQuantity))
                .andExpect(view().name("orderList"));
    }

    @Test
    public void testOrderDetail() throws Exception {

        List<GenreInfo> expectedGenre = asList(new GenreInfo());
        Mockito.doReturn(expectedGenre).when(genreServiceMock).getListGenre();

        OrderInfo orderInfo = new OrderInfo(1, new Date(), new Account(),
                "abc", "def", "123 abc", "TP.HCM", "state",
                "12345", "Vietnam","1234567890", "123@abc.com", 3);
        Mockito.doReturn(orderInfo).when(orderServiceMock).getOrderInfo("1");

        Order order = new Order();
        order.setID(orderInfo.getID());
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

        List<OrderDetailInfo> expectedOrderDetail = asList(new OrderDetailInfo(1, order, new Album(), 1, 1),
                new OrderDetailInfo(2, order, new Album(), 2, 1));
        Mockito.doReturn(expectedOrderDetail).when(orderDetailServiceMock).getListOrderDetailByOrder("1");

        int totalQuantity = 0;
        Mockito.doReturn(totalQuantity).when(cartSessionMock).getTotalQuantity(isA(HttpServletRequest.class));

        mockMvc.perform(get("/order/orderDetail?id=1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("order", orderInfo))
                .andExpect(model().attribute("orderDetail", hasSize(2)))
                .andExpect(model().attribute("orderDetail", hasItem(
                        allOf(
                                hasProperty("order", is(expectedOrderDetail.get(0).getOrder())),
                                hasProperty("album", is(expectedOrderDetail.get(0).getAlbum())),
                                hasProperty("quantity", is(expectedOrderDetail.get(0).getQuantity())),
                                hasProperty("unitPrice", is(expectedOrderDetail.get(0).getUnitPrice()))
                        )
                )))
                .andExpect(model().attribute("orderDetail", hasItem(
                        allOf(
                                hasProperty("order", is(expectedOrderDetail.get(1).getOrder())),
                                hasProperty("album", is(expectedOrderDetail.get(1).getAlbum())),
                                hasProperty("quantity", is(expectedOrderDetail.get(1).getQuantity())),
                                hasProperty("unitPrice", is(expectedOrderDetail.get(1).getUnitPrice()))
                        )
                )))
                .andExpect(model().attribute("genreList", expectedGenre))
                .andExpect(model().attribute("totalQuantity", totalQuantity))
                .andExpect(view().name("orderDetailList"));
    }
}
