package com.pigmy.demospringboot.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Orders")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

    private int id;
    private Date orderDate;
    private Account user;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String phone;
    private String email;
    private double total;
    public Order() {
    }
 
    @Id
    @Column(name = "Order_ID", nullable = false)
    public int getID() {
        return id;
    }
 
    public void setID(int id) {
        this.id = id;
    }
    
    @Column(name = "Order_Date", nullable = false)
    public Date getOrderDate() {
        return orderDate;
    }
 
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Name", nullable = false, //
    foreignKey = @ForeignKey(name = "ORDER_ACCOUNT_FK") )
    public Account getUser() {
        return user;
    }
 
    public void setUser(Account user) {
        this.user = user;
    }
 
    @Column(name = "First_Name", length = 160, nullable = false)
    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    @Column(name = "Last_Name", length = 160, nullable = false)
    public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @Column(name = "Address", length = 70, nullable = false)
    public String getAddress() {
        return address;
    }
 
    public void setAddress(String address) {
        this.address = address;
    }
    
    @Column(name = "City", length = 40, nullable = false)
    public String getCity() {
        return city;
    }
 
    public void setCity(String city) {
        this.city = city;
    }
    
    @Column(name = "State", length = 40, nullable = false)
    public String getState() {
        return state;
    }
 
    public void setState(String state) {
        this.state = state;
    }
    
    @Column(name = "Country", length = 40, nullable = false)
    public String getCountry() {
        return country;
    }
 
    public void setCountry(String country) {
        this.country = country;
    }
    
    @Column(name = "PostalCode", length = 10, nullable = false)
    public String getPostalCode() {
        return postalCode;
    }
 
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    @Column(name = "Phone", length = 24, nullable = false)
    public String getPhone() {
        return phone;
    }
 
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Column(name = "Email", length = 160, nullable = false)
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Column(name = "Total", nullable = false)
    public double getTotal() {
        return total;
    }
 
    public void setTotal(double total) {
        this.total = total;
    }
}

