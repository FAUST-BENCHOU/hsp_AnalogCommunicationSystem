package com.hsp.config;

import java.io.Serializable;

/**
 * @author FAUST
 * @version 1.0
 * @date 2023/6/2 21:20
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String kind;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
