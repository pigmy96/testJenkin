package com.pigmy.demospringboot.controllerTest;

import com.pigmy.demospringboot.controller.CartController;
import com.pigmy.demospringboot.entity.Album;
import com.pigmy.demospringboot.entity.Order;
import com.pigmy.demospringboot.model.*;
import com.pigmy.demospringboot.service.GenreService;
import com.pigmy.demospringboot.service.OrderDetailService;
import com.pigmy.demospringboot.service.OrderService;
import com.pigmy.demospringboot.session.CartSession;
import com.pigmy.demospringboot.validator.AlbumInfoValidator;
import com.pigmy.demospringboot.validator.OrderInfoValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartControllerTest {
    @InjectMocks
    CartController cartController;

    @Mock
    CartSession cartSessionMock;
    @Mock
    GenreService genreServiceMock;
    @Mock
    OrderService orderServiceMock;
    @Mock
    OrderDetailService orderDetailServiceMock;
    @Mock
    OrderInfoValidator orderInfoValidatorMock;

    MockMvc mockMvc;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(cartController)
                .setViewResolvers(new StandaloneMvcTestViewResolver())
                .build();
        when(orderInfoValidatorMock.supports(OrderInfo.class)).thenReturn(true);
    }

    @Test
    public void testAddCart() throws Exception {

        doNothing().when(cartSessionMock).addCart(eq("1"), isA(HttpServletRequest.class));
        mockMvc.perform(get("/cart/addCart?id=1"))
                .andExpect(redirectedUrl("/cart/cartList"));
        verify(cartSessionMock, times(1)).addCart(eq("1"), isA(HttpServletRequest.class));
        verifyNoMoreInteractions(cartSessionMock);
    }

    @Test
    public void testCartList() throws Exception {

        List<GenreInfo> expectedGenre = asList(new GenreInfo());
        Mockito.doReturn(expectedGenre).when(genreServiceMock).getListGenre();

        List<CartInfo> expectedCart = asList(new CartInfo("1", new Album(), 2, 1.2),
                                            new CartInfo("2", new Album(), 1, 2.1));
        Mockito.doReturn(expectedCart).when(cartSessionMock).getListCartInSession(isA(HttpServletRequest.class));

        int totalQuantity = 3;
        Mockito.doReturn(totalQuantity).when(cartSessionMock).getTotalQuantity(isA(HttpServletRequest.class));

        double totalPrice = 4.5;
        Mockito.doReturn(totalPrice).when(cartSessionMock).getTotalPrice(isA(HttpServletRequest.class));

        mockMvc.perform(get("/cart/cartList"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("cartList", hasSize(2)))
                .andExpect(model().attribute("cartList", hasItem(
                        allOf(
                                hasProperty("album", is(expectedCart.get(0).getAlbum())),
                                hasProperty("quantity", is(expectedCart.get(0).getQuantity())),
                                hasProperty("unitPrice", is(expectedCart.get(0).getUnitPrice()))
                        )
                )))
                .andExpect(model().attribute("cartList", hasItem(
                        allOf(
                                hasProperty("album", is(expectedCart.get(1).getAlbum())),
                                hasProperty("quantity", is(expectedCart.get(1).getQuantity())),
                                hasProperty("unitPrice", is(expectedCart.get(1).getUnitPrice()))
                        )
                )))
                .andExpect(model().attribute("genreList", expectedGenre))
                .andExpect(model().attribute("totalQuantity", totalQuantity))
                .andExpect(model().attribute("total", totalPrice))
                .andExpect(view().name("listCart"));
    }

    @Test
    public void testRemoveCart() throws Exception {

        doNothing().when(cartSessionMock).removeCartInSession(isA(HttpServletRequest.class), eq("1"));
        mockMvc.perform(get("/cart/remove?id=1"))
                .andExpect(redirectedUrl("/cart/cartList"));
        verify(cartSessionMock, times(1)).removeCartInSession(isA(HttpServletRequest.class), eq("1"));
        verifyNoMoreInteractions(cartSessionMock);
    }

    @Test
    public void testConfirmCheckOut_whenTotalQuantityPositive() throws Exception {

        when(cartSessionMock.getTotalQuantity(isA(HttpServletRequest.class))).thenReturn(1);
        doNothing().when(cartSessionMock).setCheckOut(isA(HttpServletRequest.class), eq(true));
        mockMvc.perform(get("/cart/confirmCheckout"))
                .andExpect(redirectedUrl("/cart/checkout"));
        verify(cartSessionMock, times(1)).setCheckOut(isA(HttpServletRequest.class), eq(true));
        //verifyNoMoreInteractions(cartSessionMock);
    }
    @Test
    public void testConfirmCheckOut_whenTotalQuantityEqual0() throws Exception {

        when(cartSessionMock.getTotalQuantity(isA(HttpServletRequest.class))).thenReturn(0);
        mockMvc.perform(get("/cart/confirmCheckout"))
                .andExpect(view().name("confirmCheckOut"));
        verify(cartSessionMock, times(1)).getTotalQuantity(isA(HttpServletRequest.class));
        verifyNoMoreInteractions(cartSessionMock);
    }

    @Test
    public void testCheckOutGet() throws Exception {

        OrderInfo expectedOrder = new OrderInfo();

        List<GenreInfo> expectedGenre = asList(new GenreInfo());
        Mockito.doReturn(expectedGenre).when(genreServiceMock).getListGenre();

        int totalQuantity = 3;
        Mockito.doReturn(totalQuantity).when(cartSessionMock).getTotalQuantity(isA(HttpServletRequest.class));

        mockMvc.perform(get("/cart/checkout"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("orderForm", hasProperty("firstName", is(expectedOrder.getFirstName()))))
                .andExpect(model().attribute("orderForm", hasProperty("lastName", is(expectedOrder.getLastName()))))
                .andExpect(model().attribute("orderForm", hasProperty("address", is(expectedOrder.getAddress()))))
                .andExpect(model().attribute("orderForm", hasProperty("city", is(expectedOrder.getCity()))))
                .andExpect(model().attribute("orderForm", hasProperty("state", is(expectedOrder.getState()))))
                .andExpect(model().attribute("orderForm", hasProperty("postalCode", is(expectedOrder.getPostalCode()))))
                .andExpect(model().attribute("orderForm", hasProperty("country", is(expectedOrder.getCountry()))))
                .andExpect(model().attribute("orderForm", hasProperty("phone", is(expectedOrder.getPhone()))))
                .andExpect(model().attribute("orderForm", hasProperty("email", is(expectedOrder.getEmail()))))
                .andExpect(model().attribute("genreList", expectedGenre))
                .andExpect(model().attribute("totalQuantity", totalQuantity))
                .andExpect(view().name("checkOut"));
    }

    @Test
    public void testCheckOutPost() throws Exception {
        OrderInfo expectedOrder = new OrderInfo();
        expectedOrder.setFirstName("abc");
        expectedOrder.setLastName("def");
        expectedOrder.setAddress(("123 abc"));
        expectedOrder.setCity("TPHCM");
        expectedOrder.setState("None");
        expectedOrder.setPostalCode("12345");
        expectedOrder.setCountry("Vietnam");
        expectedOrder.setPhone("1234567890");
        expectedOrder.setEmail("123@abc.com");
        doReturn(expectedOrder).when(orderServiceMock).addOrder(isA(OrderInfo.class), isA(HttpServletRequest.class));
        Order order = new Order();
        order.setFirstName(expectedOrder.getFirstName());
        order.setLastName(expectedOrder.getLastName());
        order.setAddress(expectedOrder.getAddress());
        order.setCity(expectedOrder.getCity());
        order.setState(expectedOrder.getState());
        order.setPostalCode(expectedOrder.getPostalCode());
        order.setCountry(expectedOrder.getCountry());
        order.setPhone(expectedOrder.getPhone());
        order.setEmail(expectedOrder.getEmail());

        doReturn(order).when(orderServiceMock).getOrderLast();

        List<OrderDetailInfo> orderDetailInfos = asList(new OrderDetailInfo());
        doReturn(orderDetailInfos).when(orderDetailServiceMock).addOrderDetail(eq(order), isA(HttpServletRequest.class));

        mockMvc.perform(post("/cart/checkout")
                .param("firstName", expectedOrder.getFirstName())
                .param("lastName", expectedOrder.getLastName())
                .param("address", expectedOrder.getAddress())
                .param("city", expectedOrder.getCity())
                .param("state", expectedOrder.getState())
                .param("postalCode", expectedOrder.getPostalCode())
                .param("country", expectedOrder.getCountry())
                .param("phone", expectedOrder.getPhone())
                .param("email", expectedOrder.getEmail())
                .flashAttr("orderForm", new OrderInfo()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(redirectedUrl("/cart/success"));

        verify(orderServiceMock, times(1)).addOrder(isA(OrderInfo.class), isA(HttpServletRequest.class));
        verify(orderServiceMock, times(1)).getOrderLast();
        verify(orderDetailServiceMock, times(1)).addOrderDetail(eq(order), isA(HttpServletRequest.class));
        verifyNoMoreInteractions(orderDetailServiceMock);
    }

    @Test
    public void testCheckOutPost_whenSaveOrderError() throws Exception {
        when(orderServiceMock.addOrder(Matchers.isA(OrderInfo.class), Matchers.isA(HttpServletRequest.class))).thenReturn(null);

        List<GenreInfo> expectedGenreList = asList(new GenreInfo());
        Mockito.doReturn(expectedGenreList).when(genreServiceMock).getListGenre();

        mockMvc.perform(post("/cart/checkout"))
                .andExpect(model().attribute("genreList", expectedGenreList))
                .andExpect(view().name("checkOut"));
    }

    @Test
    public void testCheckOutPost_whenSaveOrderDetailError() throws Exception {
        when(orderDetailServiceMock.addOrderDetail(Matchers.isA(Order.class), Matchers.isA(HttpServletRequest.class))).thenReturn(null);

        List<GenreInfo> expectedGenreList = asList(new GenreInfo());
        Mockito.doReturn(expectedGenreList).when(genreServiceMock).getListGenre();

        mockMvc.perform(post("/cart/checkout"))
                .andExpect(model().attribute("genreList", expectedGenreList))
                .andExpect(view().name("checkOut"));
    }

    @Test
    public void testCheckOutPost_whenHasErrorForm() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Errors errors = (Errors) invocationOnMock.getArguments()[1];
                errors.reject("forcing some error");
                return null;
            }
        }).when(orderInfoValidatorMock).validate(anyObject(), any(Errors.class));

        mockMvc.perform(post("/cart/checkout"))
                .andExpect(view().name("checkOut"));
    }

    @Test
    public void testCheckOutSuccess() throws Exception {

        List<GenreInfo> expectedGenre = asList(new GenreInfo());
        Mockito.doReturn(expectedGenre).when(genreServiceMock).getListGenre();

        int totalQuantity = 3;
        Mockito.doReturn(totalQuantity).when(cartSessionMock).getTotalQuantity(isA(HttpServletRequest.class));

        mockMvc.perform(get("/cart/success"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("genreList", expectedGenre))
                .andExpect(model().attribute("totalQuantity", totalQuantity))
                .andExpect(view().name("success"));
    }

}
