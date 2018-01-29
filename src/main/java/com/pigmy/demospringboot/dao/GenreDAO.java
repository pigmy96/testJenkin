package com.pigmy.demospringboot.dao;

import java.util.List;

import com.pigmy.demospringboot.entity.Genre;
import com.pigmy.demospringboot.model.AlbumInfo;
import com.pigmy.demospringboot.model.GenreInfo;

public interface GenreDAO {
	public List<GenreInfo> getListGenre();
	public List<AlbumInfo> getListAlbumByGenre(int genreID);
	public Genre findGenre(int genreID);
	public GenreInfo findGenreDetail(int genreID);
	public void save(GenreInfo genreInfo);
}
