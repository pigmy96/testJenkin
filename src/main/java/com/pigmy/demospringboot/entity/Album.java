package com.pigmy.demospringboot.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.ForeignKey;
@Entity
@Table(name="Albums")
public class Album implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private Genre genre;
	private Artist artist;
	private String title;
	private double price;
	private String imageUrl;
	public Album(){
		
	}

    @Id
    @Column(name = "Album_ID", nullable = false)
    public int getID() {
        return id;
    }
 
    public void setID(int id) {
        this.id = id;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Genre_ID", nullable = false, //
    foreignKey = @ForeignKey(name = "ALBUM_GENRE_FK") )
    public Genre getGenre() {
        return genre;
    }
 
    public void setGenre(Genre genre) {
        this.genre = genre;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Artist_ID", nullable = false, //
    foreignKey = @ForeignKey(name = "ALBUM_ARTIST_FK") )
    public Artist getArtist() {
        return artist;
    }
 
    public void setArtist(Artist artist) {
        this.artist = artist;
    }
    
    @Column(name = "Title", length = 160, nullable = false)
    public String getTitle() {
        return title;
    }
 
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Column(name = "Price", nullable = false)
    public double getPrice() {
        return price;
    }
 
    public void setPrice(double price) {
        this.price = price;
    }
    @Column(name = "Image_Url", length = 1024, nullable = true)
    public String getImageUrl() {
        return imageUrl;
    }
 
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
 
}
