package com.pigmy.demospringboot.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Carts")
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private Account account;
    private Album album;
    private int quantity;
    private double unitPrice;
    public Cart(){

    }

    @Id
    @Column(name = "Cart_ID", nullable = false)
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Name", nullable = false,
            foreignKey = @ForeignKey(name = "CART_ACCOUNT_FK") )
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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