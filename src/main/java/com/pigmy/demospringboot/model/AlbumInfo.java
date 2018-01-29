package com.pigmy.demospringboot.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pigmy.demospringboot.entity.Album;
import com.pigmy.demospringboot.entity.Artist;
import com.pigmy.demospringboot.entity.Genre;

public class AlbumInfo {
	private int id;
	private Genre genre;
	private Artist artist;
	private String title;
	private double price;
	private String imageUrl;
	private String albumID;
	private String genreName;
	private String artistName;
	private String genreID;
	private String artistID;
	private String priceString;
	private CommonsMultipartFile fileData;
	public AlbumInfo(){
		
	}
	public AlbumInfo(Album album) {
        this.id = album.getID();
        this.genre = album.getGenre();
        this.artist = album.getArtist();
        this.title = album.getTitle();
        this.price = album.getPrice();
        this.imageUrl = album.getImageUrl();
        this.albumID = String.valueOf(album.getID());
        this.genreName = album.getGenre().getName();
        this.artistName = album.getArtist().getName();
        this.genreID = String.valueOf(album.getGenre().getID());
        this.artistID = String.valueOf(album.getArtist().getID());
        this.priceString = String.valueOf(album.getPrice());
    }

    public AlbumInfo(int id, Genre genre, Artist artist, String title, double price, String imageUrl) {
        this.id = id;
        this.genre = genre;
        this.artist = artist;
        this.title = title;
        this.price = price;
        this.imageUrl = imageUrl;
        this.albumID = String.valueOf(id);
        this.genreName = genre.getName();
        this.artistName = artist.getName();
        this.genreID = String.valueOf(genre.getID());
        this.artistID = String.valueOf(artist.getID());
        this.priceString = String.valueOf(price);
    }
    
    public int getID() {
        return id;
    }
 
    public void setID(int id) {
        this.id = id;
    }
    public String getAlbumID() {
        return albumID;
    }
 
    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }
    public Genre getGenre() {
        return genre;
    }
 
    public void setGenre(Genre genre) {
        this.genre = genre;
    }
 
    public Artist getArtist() {
        return artist;
    }
 
    public void setArtist(Artist artist) {
        this.artist = artist;
    }
    
    public String getTitle() {
        return title;
    }
 
    public void setTitle(String title) {
        this.title = title;
    }
  
    public double getPrice() {
        return price;
    }
 
    public void setPrice(double price) {
        this.price = price;
    }
  
    public String getImageUrl() {
        return imageUrl;
    }
 
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getGenreName() {
        return genreName;
    }
 
    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
    public String getArtistName() {
        return artistName;
    }
 
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    public String getGenreID() {
        return genreID;
    }
 
    public void setGenreID(String genreID) {
        this.genreID = genreID;
    }
    public String getArtistID() {
        return artistID;
    }
 
    public void setArtistID(String artistID) {
        this.artistID = artistID;
    }
    
    public CommonsMultipartFile getFileData() {
        return fileData;
    }
 
    public void setFileData(CommonsMultipartFile fileData) {
        this.fileData = fileData;
    }
    
    public void setPriceString(String priceString) {
        this.priceString = priceString;
    }
    public String getPriceString() {
        return priceString;
    }
}
