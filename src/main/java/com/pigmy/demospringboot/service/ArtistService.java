package com.pigmy.demospringboot.service;

import com.pigmy.demospringboot.dao.ArtistDAO;
import com.pigmy.demospringboot.model.ArtistInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ArtistService {
    public List<ArtistInfo> getListArtist();
    public ArtistInfo addArtist(ArtistInfo artistInfo);
}
