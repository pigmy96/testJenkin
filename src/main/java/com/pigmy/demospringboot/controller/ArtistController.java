package com.pigmy.demospringboot.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pigmy.demospringboot.service.ArtistService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pigmy.demospringboot.model.ArtistInfo;
import com.pigmy.demospringboot.model.GenreInfo;
import com.pigmy.demospringboot.session.CartSession;
import com.pigmy.demospringboot.validator.ArtistInfoValidator;

@Controller
@Transactional
@EnableWebMvc
@RequestMapping(value = { "/artist" })
public class ArtistController {
	@Autowired
	GenreService genreService;
	@Autowired
	ArtistService artistService;
	@Autowired
	CartSession cartSession;
	@Autowired
	ArtistInfoValidator artistInfoValidator;

	@InitBinder
	   public void myInitBinder(WebDataBinder dataBinder) {
	       Object target = dataBinder.getTarget();
	       if (target == null) {
	           return;
	       }
	       System.out.println("Target=" + target);

	       if (target.getClass() == ArtistInfo.class) {
	          dataBinder.setValidator(artistInfoValidator);
	       }
	   }
	@RequestMapping("/add")
	  public String addArtist(Model model,
			  HttpServletRequest request) {
		
		ArtistInfo artistInfo = new ArtistInfo();
		List<GenreInfo> genreList = genreService.getListGenre();
		model.addAttribute("genreList", genreList);
		model.addAttribute("artistForm", artistInfo);
		model.addAttribute("totalQuantity", cartSession.getTotalQuantity(request));
		return "addArtist";
	  }
	
	 @RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	   @Transactional(propagation = Propagation.NEVER)
	   public String addArtist(Model model, //
	           @ModelAttribute("artistForm") @Validated ArtistInfo artistInfo, //
	           BindingResult result, //
	           HttpServletRequest request,
	           final RedirectAttributes redirectAttributes) {
		List<GenreInfo> genreList = genreService.getListGenre();
		if (result.hasErrors()) {
			System.out.println("has error" + result.getErrorCount());

			model.addAttribute("genreList", genreList);
			return "addArtist";
		}
		else{
			if(artistService.addArtist(artistInfo) == null){
				model.addAttribute("genreList", genreList);
				return "addArtist";
			}
			return "redirect:/";
		}
	}
}
