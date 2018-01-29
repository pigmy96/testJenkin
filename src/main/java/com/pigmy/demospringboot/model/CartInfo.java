package com.pigmy.demospringboot.model;

import com.pigmy.demospringboot.entity.Account;
import com.pigmy.demospringboot.entity.Album;

public class CartInfo {
	private String id;
	private Album album;
	private int quantity;
	private double unitPrice;
	private String albumTitle;
	private String cartID;
	private Account account;
	public CartInfo(){
		
	}
	public CartInfo(CartInfo cart) {
		this.id = cart.getID();
        this.album = cart.getAlbum();
        this.quantity = cart.getQuantity();
        this.unitPrice = cart.getUnitPrice();
        this.albumTitle = cart.getAlbum().getTitle();
        this.cartID = cart.getID();
        this.account = cart.getAccount();
    }

    public CartInfo(String id, Album album, int quantity, double unitPrice) {
    	this.id = id;
        this.album = album;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.albumTitle = album.getTitle();
        this.cartID = id;
    }

    public CartInfo(String id, Album album, int quantity, double unitPrice, Account account) {
        this.id = id;
        this.album = album;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.albumTitle = album.getTitle();
        this.cartID = id;
        this.account = account;
    }
    
    public String getID() {
        return id;
    }
    public void setID(String id) {
        this.id = id;
    }
    
    public String getCartID() {
        return cartID;
    }
    public void setCartID(String cartID) {
        this.cartID = cartID;
    }
    
    public Album getAlbum() {
        return album;
    }
    public void setAlbum(Album album) {
        this.album = album;
    }

    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public String getAlbumTitle() {
        return albumTitle;
    }
    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }
   
}
