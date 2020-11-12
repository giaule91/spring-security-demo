package com.tina.demo.security.vo;

/**
 * Created by nhs3108 on 29/03/2017.
 */
public class AccountCredentials {
    private String username;
    private String password;
    private String token;

    public AccountCredentials() {
    }

    public AccountCredentials(String username, String password) {
        this.username = username;
        this.password = password;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
