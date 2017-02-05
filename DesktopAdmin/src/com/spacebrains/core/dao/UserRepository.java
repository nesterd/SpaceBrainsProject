package com.spacebrains.core.dao;

import com.spacebrains.core.rest.RESTApiProvider;
import com.spacebrains.model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class UserRepository {
    private ArrayList<User> users;
    private RESTApiProvider rest;

    public UserRepository() {
        users = new ArrayList<>();
        rest = new RESTApiProvider();
    }

    public String register(String login, String password) {
        return rest.register(new UserCredentials(login, password));
    }

    public String login(String login, String password) {
        return null;
    }

    public String changePswd(String oldPassword, String newPassword) {
        return null;
    }

    public ArrayList<User> get () {
        users = new ArrayList<>();
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
            return "user";
        }
    }
}
