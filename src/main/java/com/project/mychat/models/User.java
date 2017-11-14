package com.project.mychat.models;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Entity
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String username;

    private String password;

    private Boolean online;

    @PrePersist
    public void before()
    {
        if(online == null)
        {
            online = true;
        }
    }

    public User(int id, String username, String password)
    {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public User()
    {
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public boolean isOnline()
    {
        return online;
    }

    public void setOnline(boolean online)
    {
        this.online = online;
    }
}
