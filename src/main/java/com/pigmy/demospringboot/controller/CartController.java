package com.pigmy.demospringboot.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pigmy.demospringboot.service.AccountService;
import com.pigmy.demospringboot.service.GenreService;
import com.pigmy.demospringboot.service.OrderDetailService;
import com.pigmy.demospringboot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.pigmy.demospringboot.entity.Order;
import com.pigmy.demospringboot.model.CartInfo;
import com.pigmy.demospringboot.model.GenreInfo;
import com.pigmy.demospringboot.model.OrderInfo;
import com.pigmy.demospringboot.session.CartSession;
import com.pigmy.demospringboot.validator.OrderInfoValidator;


@Controller
@Transactional
@EnableWebMvc
@RequestMapping(value = { "/cart" })
public class CartController {
	@Autowired
	GenreService genreService;
	@Autowired
	AccountService accountService;
	@Autowired
	OrderService orderService;
	@Autowired
	CartSession cartSession;
	@Autowired
	OrderDetailService orderDetailService;
	@Autowired
	OrderInfoValidator orderInfoValidator;
	
	@InitBinder
	   public void myInitBinder(WebDataBinder dataBinder) {
	       Object target = dataBinder.getTarget();
	       if (target == null) {
	           return;
	       }
	       System.out.println("Target=" + target);

	       if (target.getClass() == OrderInfo.class) {
	          dataBinder.setValidator(orderInfoValidator);
	         
	       }
	      
	   }
	
	@RequestMapping(value = { "/addCart" },method=RequestMethod.GET)
	public String addCart(HttpServletRequest request,
			Model model,
			@RequestParam(value = "id", defaultValue = "") String id){
		cartSession.addCart(id, request);
		return "redirect:/cart/cartList";
	}
	
	@RequestMapping(value = { "/cartList" },method=RequestMethod.GET)
	public String cartList(HttpServletRequest request,
			Model model){
		List<GenreInfo> result = genreService.getListGenre();
		List<CartInfo> listCart = cartSession.getListCartInSession(request);
		model.addAttribute("genreList", result);
		model.addAttribute("totalQuantity", cartSession.getTotalQuantity(request));
		model.addAttribute("cartList", listCart);
		model.addAttribute("total", cartSession.getTotalPrice(request));
		return "listCart";
	}
	
	@RequestMapping(value = { "/remove" },method=RequestMethod.GET)
	public String removeCart(HttpServletRequest request,
			Model model,
			@RequestParam(value = "id", defaultValue = "") String id){
		
		cartSession.removeCartInSession(request, id);
		return "redirect:/cart/cartList";
	}
	
	@RequestMapping(value = { "/confirmCheckout" },method=RequestMethod.GET)
	public String confirmCheckOut(HttpServletRequest request,
			Model model){
		
		if (cartSession.getTotalQuantity(request) > 0){
			cartSession.setCheckOut(request, true);
		    return "redirect:/cart/checkout";
		}
		else{
			return "confirmCheckOut";
		}
	}
	
	@RequestMapping(value = { "/checkout" },method=RequestMethod.GET)
	public String checkOut(HttpServletRequest request,
			Model model){
		OrderInfo orderInfo = new OrderInfo();
		List<GenreInfo> genreList = genreService.getListGenre();
		model.addAttribute("genreList", genreList);
		model.addAttribute("orderForm", orderInfo);
		model.addAttribute("genreList", genreList);
		model.addAttribute("totalQuantity", cartSession.getTotalQuantity(request));
		return "checkOut";
	}
	
	 @RequestMapping(value = { "/checkout" }, method = RequestMethod.POST)
	   @Transactional(propagation = Propagation.NEVER)
	   public String checkOut(Model model, //
	           @ModelAttribute("orderForm") @Validated OrderInfo orderInfo, //
	           BindingResult result, //
	           HttpServletRequest request) {
		List<GenreInfo> genreList = genreService.getListGenre();

		if (result.hasErrors()) {
			model.addAttribute("genreList", genreList);
			return "checkOut";
		}
		else{
			if(orderService.addOrder(orderInfo, request) == null){
				model.addAttribute("genreList", genreList);
				return "checkOut";
			}
			Order order = orderService.getOrderLast();
			if(orderDetailService.addOrderDetail(order, request) == null){
				model.addAttribute("genreList", genreList);
				return "checkOut";
			}
			return "redirect:/cart/success";
		}
	}
	 
	 @RequestMapping(value = { "/success" },method=RequestMethod.GET)
		public String checkOutSuccess(HttpServletRequest request,
				Model model){
			List<GenreInfo> genreList = genreService.getListGenre();
			model.addAttribute("genreList", genreList);
			model.addAttribute("totalQuantity", cartSession.getTotalQuantity(request));
			return "success";
		}
		
}
