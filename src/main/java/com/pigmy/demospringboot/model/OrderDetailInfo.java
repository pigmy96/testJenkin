package com.pigmy.demospringboot.model;

import com.pigmy.demospringboot.entity.Album;
import com.pigmy.demospringboot.entity.Order;
import com.pigmy.demospringboot.entity.OrderDetail;

public class OrderDetailInfo {
	private int id;
	private Order order;
	private Album album;
	private int quantity;
	private double unitPrice;
	private String orderDetailID;
	private String albumTitle;
	
	public OrderDetailInfo(){
		
	}
	public OrderDetailInfo(OrderDetail orderDetail) {
        this.id = orderDetail.getID();
        this.order = orderDetail.getOrder();
        this.album = orderDetail.getAlbum();
        this.quantity = orderDetail.getQuantity();
        this.unitPrice = orderDetail.getUnitPrice();
        this.orderDetailID = String.valueOf(orderDetail.getID());
        this.albumTitle = orderDetail.getAlbum().getTitle();
    }

    public OrderDetailInfo(int id, Order order, Album album, int quantity, double unitPrice) {
        this.id = id;
        this.order = order;
        this.album = album;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.orderDetailID = String.valueOf(id);
        this.albumTitle = album.getTitle();
    }
    
    public int getID() {
        return id;
    }
    public void setID(int id) {
        this.id = id;
    }
 
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    
    public Album getAlbum() {
        return album;
    }
    public void setAlbum(Album album) {
        this.album = album;
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
    
    public String getOrderDetailID() {
        return orderDetailID;
    }
    public void setOrderDetailID(String orderDetailID) {
        this.orderDetailID = orderDetailID;
    }
    
    public String getAlbumTitle() {
        return albumTitle;
    }
    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }
}
