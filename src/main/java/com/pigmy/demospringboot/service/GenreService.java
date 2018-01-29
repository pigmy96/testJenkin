package com.pigmy.demospringboot.service;

import com.pigmy.demospringboot.model.GenreInfo;

import java.util.List;

public interface GenreService {
    public List<GenreInfo> getListGenre();
    public GenreInfo addGenre(GenreInfo genreInfo);
}
