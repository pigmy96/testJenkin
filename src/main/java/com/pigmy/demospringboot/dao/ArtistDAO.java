package com.pigmy.demospringboot.dao;

import java.util.List;

import com.pigmy.demospringboot.entity.Artist;
import com.pigmy.demospringboot.model.ArtistInfo;

public interface ArtistDAO {
	public Artist findArtist(int id);
	public List<ArtistInfo> getListArtist();
	public ArtistInfo findArtistDetail(int artistID);
	public void save(ArtistInfo artistInfo);
}
