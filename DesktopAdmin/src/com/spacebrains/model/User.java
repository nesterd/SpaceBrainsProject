package com.spacebrains.model;

import com.spacebrains.interfaces.INamed;

/**
 * @author Tatyana Vorobeva
 * Should be created by Controller in case of successfull autorization. Pswd isn't necessary to be stored.
 */
public class User implements INamed {
    private int id;
    private String name;
    private String login;
    private String pswd;
    private String accessCode; // For authorization confirmation in REST requests.
    private String email;
    private Role role;

    public User(int userId, String name, String login, String email, String accessCode, Role role) {
        this.id = userId;
        this.name = name;
        this.login = login;
        this.email = email;
        this.accessCode = accessCode;
        this.role = role;
        this.pswd = "";
    }

    public int getID() {
        return id;
    }

    public User setID(int id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object setName(String name) {
        return this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPswd() {
        return pswd;
    }

    public User setPswd(String pswd) {
        this.pswd = pswd;
        return this;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public User setAccessCode(String accessCode) {
        this.accessCode = accessCode;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && ((User) obj).getID() == this.getID();
    }

    @Override
    public String toString() {
        return "User {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", pswd='" + pswd + '\'' +
                ", accessCode='" + accessCode + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
