package com.pigmy.demospringboot.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pigmy.demospringboot.service.GenreService;
import com.pigmy.demospringboot.service.OrderDetailService;
import com.pigmy.demospringboot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.pigmy.demospringboot.model.GenreInfo;
import com.pigmy.demospringboot.model.OrderDetailInfo;
import com.pigmy.demospringboot.model.OrderInfo;
import com.pigmy.demospringboot.session.CartSession;

@Controller
@Transactional
@EnableWebMvc
@RequestMapping(value = { "/order" })
public class OrderController {
	@Autowired
	CartSession cartSession;
	@Autowired
	GenreService genreService;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderDetailService orderDetailService;
	
	@RequestMapping(value = { "/orderHistory" },method=RequestMethod.GET)
	public String orderHistory(HttpServletRequest request,
			Model model){
		List<OrderInfo> orderList = orderService.getOrderListByAccount();
		List<GenreInfo> genreList = genreService.getListGenre();
		model.addAttribute("orderList", orderList);//
		model.addAttribute("genreList", genreList);
		model.addAttribute("totalQuantity", cartSession.getTotalQuantity(request));
		return "orderList";
	}
	
	@RequestMapping(value = { "/orderDetail" },method=RequestMethod.GET)
	public String orderDetail(HttpServletRequest request,
			Model model,
			@RequestParam(value = "id", defaultValue = "") String id){

		List<OrderDetailInfo> orderDetail = orderDetailService.getListOrderDetailByOrder(id);
		OrderInfo orderInfo = orderService.getOrderInfo(id);
		List<GenreInfo> genreList = genreService.getListGenre();
		System.out.println("order first name: " + orderInfo.getFirstName());
		model.addAttribute("order", orderInfo);
		model.addAttribute("orderDetail", orderDetail);
		model.addAttribute("genreList", genreList);
		model.addAttribute("totalQuantity", cartSession.getTotalQuantity(request));
		return "orderDetailList";
	}
}
