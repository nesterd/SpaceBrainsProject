package com.spacebrains.model;

/**
 * @author Tatyana Vorobeva
 * Should be created by Controller in case of successfull autorization. Pswd isn't necessary to be stored.
 */
public class User {
    private int userId;
    private String login;
    private String accessCode; // For authorization confirmation in REST requests.
    private boolean admin;

    public User(int userId, String login, String accessCode, boolean admin) {
        this.userId = userId;
        this.login = login;
        this.accessCode = accessCode;
        this.admin = admin;
    }

    public int getUserId() {
        return userId;
    }

    public User setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public User setAccessCode(String accessCode) {
        this.accessCode = accessCode;
        return this;
    }

    public boolean isAdmin() {
        return admin;
    }

    public User setAdmin(boolean admin) {
        this.admin = admin;
        return this;
    }
}
