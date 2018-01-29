package com.pigmy.demospringboot.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.pigmy.demospringboot.entity.Album;
import com.pigmy.demospringboot.model.AlbumInfo;
import com.pigmy.demospringboot.model.ArtistInfo;
import com.pigmy.demospringboot.model.GenreInfo;
import com.pigmy.demospringboot.service.AlbumService;
import com.pigmy.demospringboot.session.CartSession;
import com.pigmy.demospringboot.validator.AlbumInfoValidator;

@Controller
@Transactional
@EnableWebMvc
@RequestMapping(value = { "/album" })
public class AlbumController {
	@Autowired
	AlbumService albumService;
	@Autowired
	GenreService genreService;
	@Autowired
	ArtistService artistService;
	@Autowired
	CartSession cartSession;
	@Autowired
	AlbumInfoValidator albumInfoValidator;
	
	@InitBinder
	   public void myInitBinder(WebDataBinder dataBinder) {
	       Object target = dataBinder.getTarget();
	       if (target == null) {
	           return;
	       }
	       System.out.println("Target=" + target);

	       if (target.getClass() == AlbumInfo.class) {
	          dataBinder.setValidator(albumInfoValidator);
	          dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	       }     
	   }

	@RequestMapping("/genre")
	  public String ListAlbumByGenre(HttpServletRequest request,
			  Model model, 
			  @RequestParam(value = "id", defaultValue = "") String id) {
		List<GenreInfo> result = genreService.getListGenre();
		List<List<AlbumInfo>> album = albumService.getListAlbumByGenre(id);
		if(album.size() > 0)
			model.addAttribute("genreName", albumService.getGenreNameOfAlbum(album.get(0).get(0)));
		else
			model.addAttribute("genreName", "No Album");
		model.addAttribute("albumList",album);
		model.addAttribute("totalQuantity", cartSession.getTotalQuantity(request));
		model.addAttribute("genreList", result);
		return "index";
	}

	 @RequestMapping(value = { "/detail" }, method = RequestMethod.GET)
	   public String AlbumDetail(Model model,
			   HttpServletRequest request,
			   @RequestParam(value = "id", defaultValue = "") String id) {
		AlbumInfo albumInfo = albumService.getAlbumDetail(id);
		List<GenreInfo> result = genreService.getListGenre();
		model.addAttribute("genreAlbum", albumService.getGenreNameOfAlbum(albumInfo));
		model.addAttribute("artistAlbum", albumService.getArtistNameOfAlbum(albumInfo));
		model.addAttribute("albumDetail", albumInfo);
		model.addAttribute("genreList", result);
		model.addAttribute("totalQuantity", cartSession.getTotalQuantity(request));
		return "albumDetail";
	 }
	 
	 @RequestMapping("/manage")
	  public String home(Model model,
			  HttpServletRequest request) {
		  List<GenreInfo> result = genreService.getListGenre();
	      List<AlbumInfo> album = albumService.getListAlbum();
	      model.addAttribute("albumList",album);	  //
		  model.addAttribute("genreList", result);
		  model.addAttribute("totalQuantity", cartSession.getTotalQuantity(request));
	      return "manageAlbum";
	  }
	 
	 @RequestMapping(value = { "/manage/add" }, method = RequestMethod.GET)
	   public String addAlbum(Model model,
			   HttpServletRequest request) {
	   	 AlbumInfo albumInfo = new AlbumInfo();
	   	 System.out.println("albumid : " + albumInfo.getID());
	   	 List<GenreInfo> genreList = genreService.getListGenre();
	   	 List<ArtistInfo> artistList = artistService.getListArtist();
	   	 model.addAttribute("artistList", artistList);
	   	 model.addAttribute("genreList", genreList);
	   	 model.addAttribute("albumForm", albumInfo);
	   	model.addAttribute("totalQuantity", cartSession.getTotalQuantity(request));
	   	 return "addAlbum";
	   } 
	
	 @RequestMapping(value = { "/manage/add" }, method = RequestMethod.POST)
	   @Transactional(propagation = Propagation.NEVER)
	   public String addAlbum(Model model,
			   @Valid @ModelAttribute("albumForm") AlbumInfo albumInfo,
	           BindingResult result) {
		 List<GenreInfo> genreList = genreService.getListGenre();
		 List<ArtistInfo> artistList = artistService.getListArtist();
		 	
		 if (result.hasErrors()) {
			 model.addAttribute("artistList", artistList);
			 model.addAttribute("genreList", genreList);
			 return "addAlbum";
		 }
		 else{
		 	if(albumService.addAlbum(albumInfo) == null){
				model.addAttribute("artistList", artistList);
				model.addAttribute("genreList", genreList);
				return "addAlbum";
			}

			 Album album = albumService.getAlbumLast();
			 return "redirect:/album/detail?id=" + album.getID();
		 }
	 }
	 
	 @RequestMapping(value = { "/manage/delete" }, method = RequestMethod.GET)
	   public String deleteAlbum(Model model, @RequestParam(value = "id", defaultValue = "") String id) {
		if(albumService.deleteAlbum(id) == false){
			return "manageAlbum";
		}
		return "redirect:/album/manage";
	}
	 
	 @RequestMapping(value = { "/manage/edit" }, method = RequestMethod.GET)
	   public String editAlbum(Model model, 
			   HttpServletRequest request,
			   @RequestParam(value = "id", defaultValue = "") String id) {
		AlbumInfo albumInfo = albumService.getAlbumDetail(id);
		List<GenreInfo> genreList = genreService.getListGenre();
		List<ArtistInfo> artistList = artistService.getListArtist();
		model.addAttribute("artistList", artistList);
		model.addAttribute("genreList", genreList);
		model.addAttribute("albumForm", albumInfo);
		model.addAttribute("albumID", id);
		model.addAttribute("totalQuantity", cartSession.getTotalQuantity(request));
		return "editAlbum";
	}
	 
	 @RequestMapping(value = { "/manage/edit" }, method = RequestMethod.POST)
	   @Transactional(propagation = Propagation.NEVER)
	 public String editAlbum(Model model, //
	           @ModelAttribute("albumForm") @Validated AlbumInfo albumInfo,
	           BindingResult result,
	           @RequestParam(value = "id", defaultValue = "") String id) {
		 List<GenreInfo> genreList = genreService.getListGenre();
		 List<ArtistInfo> artistList = artistService.getListArtist();

		if (result.hasErrors()) {
			model.addAttribute("artistList", artistList);
			model.addAttribute("genreList", genreList);
			model.addAttribute("albumID", id);
		   return "editAlbum";
		}
		else{
			try {

				//boolean checkEdit = albumService.editAlbum(albumInfo, id);
				if (albumService.editAlbum(albumInfo, id) == null)
				{
					model.addAttribute("artistList", artistList);
					model.addAttribute("genreList", genreList);
					model.addAttribute("errorMessage", "The album information has not been changed");
					model.addAttribute("albumID", id);
					return "editAlbum";
				}
			} catch (Exception e) {
				String message = e.getMessage();
				model.addAttribute("artistList", artistList);
				model.addAttribute("genreList", genreList);
				model.addAttribute("errorMessage", message);
				model.addAttribute("albumID", id);
				return "editAlbum";
			}
			return "redirect:/album/detail?id=" + id;
		}
	}
	 
	 @RequestMapping(value = { "/albumImage" }, method = RequestMethod.GET)
	   public void productImage(HttpServletResponse response,
	           @RequestParam("id") String id) throws IOException {
	       albumService.displayImage(response, id);
	   }
	 
}
