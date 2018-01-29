package com.pigmy.demospringboot.service.impl;

import com.pigmy.demospringboot.dao.GenreDAO;
import com.pigmy.demospringboot.model.GenreInfo;
import com.pigmy.demospringboot.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService{
    @Autowired
    GenreDAO genreDAO;

    @Override
    public List<GenreInfo> getListGenre(){
        return genreDAO.getListGenre();
    }

    @Override
    public GenreInfo addGenre(GenreInfo genreInfo){

        try {
            genreDAO.save(genreInfo);
        } catch (Exception e) {
            return null;
        }
        return genreInfo;
    }
}
