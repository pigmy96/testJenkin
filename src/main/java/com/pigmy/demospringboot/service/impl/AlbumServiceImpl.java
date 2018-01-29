package com.pigmy.demospringboot.service.impl;

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

import com.pigmy.demospringboot.entity.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pigmy.demospringboot.dao.AlbumDAO;
import com.pigmy.demospringboot.dao.ArtistDAO;
import com.pigmy.demospringboot.dao.GenreDAO;
import com.pigmy.demospringboot.entity.Artist;
import com.pigmy.demospringboot.entity.Genre;
import com.pigmy.demospringboot.model.AlbumInfo;
import com.pigmy.demospringboot.service.AlbumService;

import javax.servlet.http.HttpServletResponse;

@Service
public class AlbumServiceImpl implements AlbumService{

	@Autowired
	GenreDAO genreDAO;
	@Autowired
	AlbumDAO albumDAO;
	@Autowired
	ArtistDAO artistDAO;
	@Override
	public List<List<AlbumInfo>> getListAlbumByGenre(String id){
		int genreID = Integer.parseInt(id);
		List<AlbumInfo> album1 = genreDAO.getListAlbumByGenre(genreID);
		List<AlbumInfo> kq1 = new ArrayList<AlbumInfo>();
		List<List<AlbumInfo>> kq2 = new ArrayList<List<AlbumInfo>>();
  
		for (int i =0;i<album1.size();i++)
		{
			if (i%3==0 && i > 0){
				kq1.clear();
			}
			kq1.add(album1.get(i));
			if ((i == (album1.size()-1)) || (i%3==2)){
				List<AlbumInfo> kq3 = new ArrayList<AlbumInfo>();
				kq3.addAll(kq1);
				kq2.add(kq3); 
			}
		}
		return kq2;
	}
	@Override
	public List<List<AlbumInfo>> getListAlbumForHome(){
		List<AlbumInfo> album1 = albumDAO.getListAlbum();
		List<AlbumInfo> kq1 = new ArrayList<AlbumInfo>();
		List<List<AlbumInfo>> kq2 = new ArrayList<List<AlbumInfo>>();
	      
		for (int i =0;i<album1.size();i++)
		{
			if (i%3==0 && i > 0){
				kq1.clear();
			}
			kq1.add(album1.get(i));
			if ((i == (album1.size()-1)) || (i%3==2)){
			   List<AlbumInfo> kq3 = new ArrayList<AlbumInfo>();
			   kq3.addAll(kq1);
			   kq2.add(kq3);
			}
		}
		return kq2;
	}
	
	@Override
	public AlbumInfo getAlbumDetail(String id) {
		AlbumInfo albumInfo = null;
		int albumID = Integer.parseInt(id);
		if (id != null && id.length() > 0) {
			albumInfo = albumDAO.findAlbumDetail(albumID);
		}
		return albumInfo;
	}
	
	@Override
	public List<AlbumInfo> getListAlbum(){
		return albumDAO.getListAlbum();
	}
	
	@Override
	public AlbumInfo addAlbum(AlbumInfo albumInfo) {
		int genreID = Integer.parseInt(albumInfo.getGenreID());
	 	int artistID = Integer.parseInt(albumInfo.getArtistID());
	 	
	 	Genre genre = genreDAO.findGenre(genreID);
	 	albumInfo.setGenre(genre);
	 	
	 	Artist artist = artistDAO.findArtist(artistID);
	 	albumInfo.setArtist(artist);
	 	try{
			this.uploadImage(albumInfo);
		} catch (Exception e){
			return null;
		}
 	   albumInfo.setPrice(Double.parseDouble(albumInfo.getPriceString()));
		try {
			albumDAO.save(albumInfo);
		} catch (Exception e) {
			return null;
		}

       return albumInfo;
	}
	
	@Override
	public void uploadImage(AlbumInfo albumInfo) {
		//String uploadRootPath = ".." + File.separator + "Angular" + File.separator + "demoangular" + File.separator + "src" + File.separator + "assets"  + File.separator + "images";
		String uploadRootPath = "images";

		File uploadRootDir = new File(uploadRootPath);
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		System.out.println("path file upload: " + uploadRootDir.getAbsolutePath());
		CommonsMultipartFile fileData = albumInfo.getFileData();
		String name = fileData.getOriginalFilename();
		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();

		if (name != null && name.length() > 0) {
			try {
				StringBuilder sb = new StringBuilder( 10 );
				for( int i = 0; i < 10; i++ )
					sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
				String albumName = sb.toString();
				System.out.println("file name upload" + albumName);
				int temp = name.lastIndexOf(".");
				albumName += "." + name.substring(temp, name.length());

				File serverFile = new File(uploadRootDir.getAbsolutePath()
								+ File.separator + albumName);
				while (serverFile.exists()) {
					sb = new StringBuilder( 10 );
					for( int i = 0; i < 10; i++ )
						sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
					albumName = sb.toString();
					albumName += "." + name.substring(0, temp);
					serverFile = new File(uploadRootDir.getAbsolutePath()
							   + File.separator + albumName);
				}
				System.out.println("file name upload" + albumName);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(fileData.getBytes());
				stream.close();
				albumInfo.setImageUrl(albumName);
			} catch (Exception e) {
				System.out.println("Error Write file: " + name);
			}
		}
	}

	@Override
	public Album getAlbumLast(){
		return albumDAO.getAlbumLast();
	}

	@Override
	public boolean deleteAlbum(String id){
		AlbumInfo albumInfo = null;
		int albumID = Integer.parseInt(id);
		albumInfo = albumDAO.findAlbumDetail(albumID);
		//albumDAO.delete(albumInfo);
		try {
			albumDAO.delete(albumInfo);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public AlbumInfo editAlbum(AlbumInfo albumInfo, String id){
		int albumID = Integer.parseInt(id);
		int genreID = Integer.parseInt(albumInfo.getGenreID());
		int artistID = Integer.parseInt(albumInfo.getArtistID());

		albumInfo.setID(albumID);
		Genre genre = genreDAO.findGenre(genreID);
		albumInfo.setGenre(genre);
		Artist artist = artistDAO.findArtist(artistID);
		albumInfo.setArtist(artist);
		albumInfo.setPrice(Double.parseDouble(albumInfo.getPriceString()));

		AlbumInfo checkAlbumInfo = this.getAlbumDetail(id);
		if (albumInfo.getTitle().equals(checkAlbumInfo.getTitle())
				&& albumInfo.getPrice() == checkAlbumInfo.getPrice()
				&& genreID == checkAlbumInfo.getGenre().getID()
				&& artistID == checkAlbumInfo.getArtist().getID()
				&& albumInfo.getFileData().getSize() == 0)
		{
			return null;
		}
		this.uploadImage(albumInfo);
		albumDAO.save(albumInfo);
		return albumInfo;
	}

	@Override
	public void displayImage(HttpServletResponse response, String id){
		Album album = null;
		int albumID = Integer.parseInt(id);
		album = albumDAO.findAlbum(albumID);
		String uploadRootPath = "images" + File.separator + album.getImageUrl();
		File uploadRootDir = new File(uploadRootPath);
		System.out.println("path file upload - display: " + uploadRootDir.getAbsolutePath());
		Path path = Paths.get(uploadRootDir.getAbsolutePath());
		byte[] data = new byte[0];
		try {
			data = Files.readAllBytes(path);
			response.getOutputStream().write(data);
			System.out.println("find data Image:" + id);
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String getGenreNameOfAlbum(AlbumInfo albumInfo){
		return albumInfo.getGenre().getName();
	}

	@Override
	public String getArtistNameOfAlbum(AlbumInfo albumInfo){
		return albumInfo.getArtist().getName();
	}
}
