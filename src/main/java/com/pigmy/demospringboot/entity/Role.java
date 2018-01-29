package com.pigmy.demospringboot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Roles")
public class Role implements Serializable {

    private static final long serialVersionUID = -2054386655979281969L;

    private int id;
    private String name;

    @Id
    @Column(name = "Role_ID", nullable = false)
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    @Column(name = "Name", length = 20, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}