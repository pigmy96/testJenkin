package com.pigmy.demospringboot.dao;

import java.util.List;

import com.pigmy.demospringboot.entity.Album;
import com.pigmy.demospringboot.model.AlbumInfo;

public interface AlbumDAO {
	public List<AlbumInfo> getListAlbum();
	public Album findAlbum(int albumID);
	public AlbumInfo findAlbumDetail(int albumID);
	public void save(AlbumInfo albumInfo);
	public void delete(AlbumInfo albumInfo);
	public Album getAlbumLast();
}
