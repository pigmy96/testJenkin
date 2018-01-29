package com.pigmy.demospringboot.model;

import java.util.Date;

import com.pigmy.demospringboot.entity.Account;
import com.pigmy.demospringboot.entity.Order;

public class OrderInfo {
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
    private String orderID;
    private String userName;
    public OrderInfo(){
		
	}
	public OrderInfo(Order order) {
        this.id = order.getID();
        this.orderDate = order.getOrderDate();
        this.user = order.getUser();
        this.firstName = order.getFirstName();
        this.lastName = order.getLastName();
        this.address = order.getAddress();
        this.city = order.getCity();
        this.state = order.getState();
        this.postalCode = order.getPostalCode();
        this.country = order.getCountry();
        this.phone = order.getPhone();
        this.email = order.getEmail();
        this.total = order.getTotal();
        this.orderID = String.valueOf(order.getID());
        this.userName = order.getUser().getUserName();
    }

    public OrderInfo(int id, Date orderDate, Account user, String firstName, String lastName,
    		String address, String city, String state, String postalCode, String country, String phone,
    		String email, double total) {
        this.id = id;
        this.orderDate = orderDate;
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.phone = phone;
        this.email = email;
        this.total = total;
        this.orderID = String.valueOf(id);
        this.userName = user.getUserName();
    }
    
    public int getID() {
        return id;
    }
 
    public void setID(int id) {
        this.id = id;
    }
    
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
 
    public Account getUser() {
        return user;
    }
    public void setUser(Account user) {
        this.user = user;
    }
 
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    
    public String getOrderID() {
        return orderID;
    }
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
    
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
