package com.pigmy.demospringboot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Order_Details")
public class OrderDetail implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private Order order;
	private Album album;
	private int quantity;
	private double unitPrice;
	public OrderDetail(){
		
	}

    @Id
    @Column(name = "OrderDetail_ID", nullable = false)
    public int getID() {
        return id;
    }
 
    public void setID(int id) {
        this.id = id;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Order_ID", nullable = false, //
    foreignKey = @ForeignKey(name = "ORDERDETAIL_ORDER_FK") )
    public Order getOrder() {
        return order;
    }
 
    public void setOrder(Order order) {
        this.order = order;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Album_ID", nullable = false, //
    foreignKey = @ForeignKey(name = "ORDERDETAIL_ALBUM_FK") )
    public Album getAlbum() {
        return album;
    }
 
    public void setAlbum(Album album) {
        this.album = album;
    }
    
    @Column(name = "Quantity", nullable = false)
    public int getQuantity() {
        return quantity;
    }
 
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    @Column(name = "UnitPrice", nullable = false)
    public double getUnitPrice() {
        return unitPrice;
    }
 
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
