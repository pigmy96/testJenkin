package com.pigmy.demospringboot.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pigmy.demospringboot.service.GenreService;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.pigmy.demospringboot.model.GenreInfo;
import com.pigmy.demospringboot.session.CartSession;
import com.pigmy.demospringboot.validator.GenreInfoValidator;

@Controller
@Transactional
@EnableWebMvc
@RequestMapping(value = { "/genre" })
public class GenreController {


	@Autowired
	GenreService genreService;
	@Autowired
	CartSession cartSession;
	@Autowired
	GenreInfoValidator genreInfoValidator;
	
	@InitBinder
	   public void myInitBinder(WebDataBinder dataBinder) {
	       Object target = dataBinder.getTarget();
	       if (target == null) {
	           return;
	       }
	       System.out.println("Target=" + target);

	       if (target.getClass() == GenreInfo.class) {
	          dataBinder.setValidator(genreInfoValidator);
	       }
	   }
	@RequestMapping("/add")
	  public String addGenre(Model model,
			  HttpServletRequest request) {
		
		GenreInfo genreInfo = new GenreInfo();
		List<GenreInfo> genreList = genreService.getListGenre();
		model.addAttribute("genreList", genreList);
		model.addAttribute("genreForm", genreInfo);
		model.addAttribute("totalQuantity", cartSession.getTotalQuantity(request));
		return "addGenre";
	  }
	
	 @RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	   @Transactional(propagation = Propagation.NEVER)
	   public String addGenre(Model model, //
	           @ModelAttribute("genreForm") @Validated GenreInfo genreInfo, //
	           BindingResult result) {
		List<GenreInfo> genreList = genreService.getListGenre();
		if (result.hasErrors()) {
			model.addAttribute("genreList", genreList);
			return "addGenre";
		}
		else{
			if(genreService.addGenre(genreInfo) == null){
				model.addAttribute("genreList", genreList);
				return "addGenre";
			}
			return "redirect:/";
		}
	}
}
