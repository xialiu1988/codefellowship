package com.shuai88qi.xl2019.codefellowship;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.security.PublicKey;
import java.sql.Timestamp;


@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Size(min=2)
    String body;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    Timestamp createAt;
    @ManyToOne
    AppUser appUser;

    //constructor
    public Post(){}

    public Post(String body,AppUser appUser) {
        this.body = body;
        this.createAt = new Timestamp(System.currentTimeMillis());
        this.appUser = appUser;
    }

    public long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public AppUser getAppUser() {
        return appUser;
    }


    public void setBody(String body) {
        this.body = body;
    }

    public void setCreateAt() {
        this.createAt = new Timestamp(System.currentTimeMillis());
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
