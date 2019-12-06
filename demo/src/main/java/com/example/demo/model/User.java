package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    private Integer id;
    private String username;
    private String password;

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    protected User() {}

    public User(String username, String password) {

        this.username = validate(username);
        this.password = validate(password);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String validate(String text) {
        if (text == null || text.isEmpty() || !text.matches("^[a-zA-Z0-9_]*$")) {
            throw new IllegalArgumentException(
                "illegal string value: [" + text + "]");
        }
        return text;
    }
    
}