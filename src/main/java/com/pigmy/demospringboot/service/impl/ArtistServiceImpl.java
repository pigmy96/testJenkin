package com.pigmy.demospringboot.service.impl;

import com.pigmy.demospringboot.dao.ArtistDAO;
import com.pigmy.demospringboot.model.ArtistInfo;
import com.pigmy.demospringboot.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService{
    @Autowired
    ArtistDAO artistDAO;

    @Override
    public List<ArtistInfo> getListArtist(){
        return artistDAO.getListArtist();
    }

    @Override
    public ArtistInfo addArtist(ArtistInfo artistInfo){
        try {
            artistDAO.save(artistInfo);
        } catch (Exception e) {
            return null;
        }
        return artistInfo;
    }
}
