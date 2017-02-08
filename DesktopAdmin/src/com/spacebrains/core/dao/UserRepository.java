package com.spacebrains.core.dao;

import com.spacebrains.core.rest.RESTApiProvider;
import com.spacebrains.model.User;

import java.util.ArrayList;


public class UserRepository {
    private ArrayList<User> users;
    private RESTApiProvider rest;
    private String access_token;

    public UserRepository() {
        users = new ArrayList<>();
        rest = new RESTApiProvider();
    }

    public boolean login(String login, String password) {
        access_token = rest.login(new UserCredentials(login, password));
        return (access_token != null) ? true : false;
    }

    /**
     * Use for debug only!
     * @return
     */
    @Deprecated
    public String getToken() { return access_token; }

    public String changePswd(String oldPassword, String newPassword) {
        return null;
    }

    public ArrayList<User> get () {
        users = new ArrayList<>();
        rest.getUsers();
        return users;
    }

    private static class UserCredentials extends DbObject {

        UserCredentials(String username, String password) {
            super();
            addProperty("username", username);
            addProperty("password", password);
        }

        @Override
        public String getEntityName() {
            return "auth";
        }
    }

    private static class UserRegistration extends DbObject {

        UserRegistration(String name, String email, String login, String password) {
            super();
            addProperty("name", name);
            addProperty("email", email);
            addProperty("username", login);
            addProperty("password", password);
        }

        @Override
        public String getEntityName() {
            return "register";
        }
    }
}
