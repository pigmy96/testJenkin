package com.pigmy.demospringboot.model;

import com.pigmy.demospringboot.entity.Genre;

public class GenreInfo {
	private int id;
    private String name;
    private String description;
    private String genreID;
 
    //private boolean newProduct=false;
	  public GenreInfo() {
	    }
	 
	    public GenreInfo(Genre genre) {
	        this.id = genre.getID();
	        this.name = genre.getName();
	        this.description = genre.getDescription();
	        this.genreID = String.valueOf(genre.getID());
	    }

	    public GenreInfo(int id, String name, String description) {
	        this.id = id;
	        this.name = name;
	        this.description = description;
	        this.genreID = String.valueOf(id);
	    }
	 
	    public int getID() {
	        return id;
	    }
	 
	    public void setID(int id) {
	        this.id = id;
	    }
	    public String getGenreID() {
	        return genreID;
	    }
	 
	    public void setGenreID(String genreID) {
	        this.genreID = genreID;
	    }
	    public String getName() {
	        return name;
	    }
	 
	    public void setName(String name) {
	        this.name = name;
	    }
	    public String getDescription() {
	        return description;
	    }
	 
	    public void setDescription(String description) {
	        this.description = description;
	    }
}
