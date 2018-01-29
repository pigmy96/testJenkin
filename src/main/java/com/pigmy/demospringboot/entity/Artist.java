package com.pigmy.demospringboot.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="Artists")
public class Artist implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
    private String name;
    public Artist() {
    }
 
    @Id
    @Column(name = "Artist_ID", nullable = false)
    public int getID() {
        return id;
    }
 
    public void setID(int id) {
        this.id = id;
    }
 
    @Column(name = "Name", length = 120, nullable = false)
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }

}
