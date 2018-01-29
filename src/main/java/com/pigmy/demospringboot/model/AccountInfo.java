package com.pigmy.demospringboot.model;

import com.pigmy.demospringboot.entity.Account;
 
public class AccountInfo {
    private String username;
    private String password;
    private boolean active;
    private String userrole;
    private String email;
    private String confirmPassword;
 
    private boolean newAccount=false;
  public AccountInfo() {
    }
 
    public AccountInfo(Account account) {
        this.username = account.getUserName();
        this.password = account.getPassword();
        this.active = account.isActive();
        this.userrole = account.getUserRole().getName();
        this.email = account.getEmail();
        this.confirmPassword = account.getPassword();
    }
 
    public AccountInfo(String username, String password, boolean active, String userrole, String email) {
    	this.username = username;
        this.password = password;
        this.active = active;
        this.userrole = userrole;
        this.email = email;
        this.confirmPassword = password;
    }
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPass() {
        return password;
    }
 
    public void setPass(String password) {
        this.password = password;
    }
    
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getConfirmPassword() {
        return confirmPassword;
    }
 
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
 
    public boolean getActive() {
        return active;
    }
 
    public void setActive(boolean active) {
        this.active = active;
    }
    public String getRole() {
        return userrole;
    }
 
    public void setRole(String role) {
        this.userrole = role;
    }
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
  
    public boolean isNewAccount() {
        return newAccount;
    }
 
    public void setNewAccount(boolean newAccount) {
        this.newAccount = newAccount;
    }
 
}