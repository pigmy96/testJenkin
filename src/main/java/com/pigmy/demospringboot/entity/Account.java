package com.pigmy.demospringboot.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "Accounts")
public class Account implements Serializable {

   private static final long serialVersionUID = -2054386655979281969L;

     
   public static final String ROLE_ADMIN = "ADMIN";
   public static final String ROLE_EMPLOYEE = "EMPLOYEE";

   private String userName;
   private String password;
   private boolean active;
   private Role roleID;
   private String email;

   @Id
   @Column(name = "User_Name", length = 20, nullable = false)
   public String getUserName() {
       return userName;
   }

   public void setUserName(String userName) {
       this.userName = userName;
   }

   @Column(name = "Password", length = 60, nullable = false)
   public String getPassword() {
       return password;
   }

   public void setPassword(String password) {
       this.password = password;
   }

   @Column(name = "Active", length = 1, nullable = false)
   public boolean isActive() {
       return active;
   }

   public void setActive(boolean active) {
       this.active = active;
   }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Role_ID", nullable = false, //
            foreignKey = @ForeignKey(name = "ACCOUNT_ROLE_FK") )
    public Role getUserRole() {
        return roleID;
    }

    public void setUserRole(Role userRole) {
        this.roleID = userRole;
    }

   @Column(name = "Email", length = 255, nullable = true)
   public String getEmail() {
       return email;
   }

   public void setEmail(String email) {
       this.email = email;
   }
   @Override
   public String toString()  {
       return "["+ this.userName+","+ this.password+","+ this.roleID.getName()+","+ this.email+"]";
   }
   
}