package com.pigmy.demospringboot.service;

import java.util.List;

import com.pigmy.demospringboot.entity.Album;
import com.pigmy.demospringboot.model.AlbumInfo;

import javax.servlet.http.HttpServletResponse;

public interface AlbumService {

	 public List<List<AlbumInfo>> getListAlbumByGenre(String genreID);
	 public List<List<AlbumInfo>> getListAlbumForHome();
	 public AlbumInfo getAlbumDetail(String id);
	 public List<AlbumInfo> getListAlbum();
	 public AlbumInfo addAlbum(AlbumInfo albumInfo);
	 public void uploadImage(AlbumInfo albumInfo);
	 public Album getAlbumLast();
	 public boolean deleteAlbum(String id);
	 public AlbumInfo editAlbum(AlbumInfo albumInfo, String id);
	 public void displayImage(HttpServletResponse response, String id);
	 public String getGenreNameOfAlbum(AlbumInfo albumInfo);
	public String getArtistNameOfAlbum(AlbumInfo albumInfo);
}
