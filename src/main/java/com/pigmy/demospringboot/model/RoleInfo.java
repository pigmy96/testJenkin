package com.pigmy.demospringboot.model;

import com.pigmy.demospringboot.entity.Role;

public class RoleInfo {
    private int id;
    private String name;
    private String roleID;
    //private boolean newProduct=false;
    public RoleInfo() {
    }

    public RoleInfo(Role role) {
        this.id = role.getID();
        this.name = role.getName();
        this.roleID = String.valueOf(role.getID());
    }

    public RoleInfo(int id, String name) {
        this.id = id;
        this.name = name;
        this.roleID = String.valueOf(id);
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }
    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
