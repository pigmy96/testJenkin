package com.pigmy.demospringboot.model;

import com.pigmy.demospringboot.entity.Artist;

public class ArtistInfo {
	private int id;
    private String name;
    private String artistID;
    //private boolean newProduct=false;
	  public ArtistInfo() {
	    }
	 
	    public ArtistInfo(Artist artist) {
	        this.id = artist.getID();
	        this.name = artist.getName();
	        this.artistID = String.valueOf(artist.getID());
	    }

	    public ArtistInfo(int id, String name) {
	        this.id = id;
	        this.name = name;
	        this.artistID = String.valueOf(id);
	    }
	 
	    public int getID() {
	        return id;
	    }
	 
	    public void setID(int id) {
	        this.id = id;
	    }
	    public String getArtistID() {
	        return artistID;
	    }
	 
	    public void setArtistID(String artistID) {
	        this.artistID = artistID;
	    }
	    public String getName() {
	        return name;
	    }
	 
	    public void setName(String name) {
	        this.name = name;
	    }
}
