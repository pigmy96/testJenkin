package com.pigmy.demospringboot.controller;
import com.pigmy.demospringboot.model.AccountInfo;
import com.pigmy.demospringboot.model.AlbumInfo;
import com.pigmy.demospringboot.model.GenreInfo;
import com.pigmy.demospringboot.service.AccountService;
import com.pigmy.demospringboot.service.AlbumService;
import com.pigmy.demospringboot.service.GenreService;
import com.pigmy.demospringboot.session.CartSession;
import com.pigmy.demospringboot.validator.RegisterValidator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
@Controller
@Transactional
@EnableWebMvc
public class AccountController {

  @Autowired
    AlbumService albumService;
  @Autowired
    AccountService accountService;
  @Autowired
    GenreService genreService;
  @Autowired
  CartSession cartSession;
  @Autowired
    RegisterValidator registerValidator;
	
	@InitBinder
	   public void myInitBinder(WebDataBinder dataBinder) {
	       Object target = dataBinder.getTarget();
	       if (target == null) {
	           return;
	       }
	       System.out.println("Target=" + target);

	       if (target.getClass() == AccountInfo.class) {
	          dataBinder.setValidator(registerValidator);
	         
	       }
	      
	   }
    @RequestMapping(value = { "/403" }, method = RequestMethod.GET)
  public String accessDenied(HttpServletRequest request,
			Model model) {
	  model.addAttribute("totalQuantity", cartSession.getTotalQuantity(request));
      return "accessDenied";
  }

  @RequestMapping(value = { "/" }, method = RequestMethod.GET)
  public String home(HttpServletRequest request, Model model) {
	  List<GenreInfo> result = genreService.getListGenre();
	  List<List<AlbumInfo>> album = albumService.getListAlbumForHome();
      cartSession.setCheckOut(request, false);
      model.addAttribute("albumList",album);	  //
	  model.addAttribute("genreList", result);
	  model.addAttribute("totalQuantity", cartSession.getTotalQuantity(request));
      return "index";
  }

  @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
  public String login(Model model, HttpServletRequest request) {
      if (accountService.isLogin() == true){
          return "redirect:/";
      }
      List<GenreInfo> result = genreService.getListGenre();
      model.addAttribute("genreList", result);
	  model.addAttribute("totalQuantity", cartSession.getTotalQuantity(request));
      return "login";
  }
  @RequestMapping(value = { "/accountInfo" }, method = RequestMethod.GET)
  public String accountInfo(Model model, HttpServletRequest request) {
	  if (cartSession.getCheckOut(request)){
		  cartSession.setCheckOut(request, false);
		  return "redirect:/cart/checkout";
	  }
      AccountInfo accountInfo = accountService.getInfoUserLoggedIn();
      List<GenreInfo> result = genreService.getListGenre();
      System.out.println("user role in account Info: " + accountInfo.getRole());
      model.addAttribute("genreList", result);
	  model.addAttribute("totalQuantity", cartSession.getTotalQuantity(request));
      model.addAttribute("accountInfo", accountInfo);
      return "accountInfo";
  }

  @RequestMapping(value = { "/register" }, method = RequestMethod.GET)
  public String register(Model model, HttpServletRequest request) {
	  
    if (accountService.isLogin() == true){
      return "redirect:/";
    }
    AccountInfo accountInfo = new AccountInfo();
    List<GenreInfo> genreList = genreService.getListGenre();
    model.addAttribute("genreList", genreList);
    model.addAttribute("accountForm", accountInfo);
    model.addAttribute("totalQuantity", cartSession.getTotalQuantity(request));
    return "register";
  }

  @RequestMapping(value = { "/register" }, method = RequestMethod.POST)
  @Transactional(propagation = Propagation.NEVER)
  public String register(Model model, //
          @ModelAttribute("accountForm") @Validated AccountInfo accountInfo, //
          BindingResult result) {
    List<GenreInfo> genreList = genreService.getListGenre();

    if (result.hasErrors()) {
        model.addAttribute("genreList", genreList);
        return "register";
    }
    else{
        if (accountService.register(accountInfo) == null){
            model.addAttribute("genreList", genreList);
            return "register";
        }
    return "redirect:/login";
    }
    }
}