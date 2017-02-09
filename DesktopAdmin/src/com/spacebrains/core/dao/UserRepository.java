package com.spacebrains.core.dao;

import com.spacebrains.core.AuthConstants;
import com.spacebrains.core.RepoConstants;
import com.spacebrains.core.rest.RESTApiProvider;
import com.spacebrains.model.Role;
import com.spacebrains.model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Iterator;


public class UserRepository {
    private ArrayList<User> users;
    private RESTApiProvider rest;
    private String access_token;

    public UserRepository() {
        users = new ArrayList<>();
        rest = new RESTApiProvider();
    }

    public User login(String login, String password) {
        User currentUser = null;
        String jsonString = null;
        Role role = null;
        access_token = rest.login(new UserCredentials(login, password));
        if(access_token != null) {
            try {
                jsonString = rest.getAdmins();
                role = Role.SUPER_ADMIN;
            } catch (RuntimeException e) {
                if(e.getMessage().contains(AuthConstants.ACCESS_FORBIDDEN)) {
                    // наша роль не суперадмин - проверяем не админы ли мы
                    try {
                        jsonString = rest.getUsers();
                        role = Role.ADMIN;
                    } catch (RuntimeException e1) {
                        if(e1.getMessage().contains(AuthConstants.ACCESS_FORBIDDEN)) {
                            role = Role.USER;
                        }
                    }
                }
            }
            currentUser = new User(0,null, login, null, access_token, role);
        }
        return currentUser;
    }

    /**
     * Use for debug only!
     * @return String - token received from web-service
     */
    @Deprecated
    public String getToken() { return access_token; }

    public boolean changePswd(String newPassword) {
        return rest.changePass(new UserCredentials("", newPassword));
    }

    public ArrayList<User> get (Role role) {
        users = new ArrayList<>();
        String jsonString, key;
        switch (role) {
            case SUPER_ADMIN: {
                jsonString = rest.getAdmins();
                key = "admins";
                break;
            }
            case ADMIN: {
                jsonString = rest.getUsers();
                key = "users";
                break;
            }
            default:
                throw new RuntimeException(AuthConstants.ACCESS_FORBIDDEN);
        }
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
            JSONArray jsonArray = (JSONArray) jsonObject.get(key);
            Iterator<JSONObject> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                jsonObject = (JSONObject) iterator.next();
                UserDao userDao = new UserDao(jsonObject);
                users.add(userDao.getUser());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return users;
    }

    public String put(User user) {
        return (rest.updateObject(new UserRegistration(user))) ? RepoConstants.SUCCESS : RepoConstants.NOT_ANSWERED;
    }

    public boolean delete(User user) {
        return rest.deleteObject(new UserDao(user));
    }

    public boolean register(User user) {
        return rest.register(new UserRegistration(user));
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

        UserRegistration(User user) {
            super();
            addProperty("name", user.getName());
            addProperty("email", user.getEmail());
            addProperty("username", user.getLogin());
            addProperty("password", "");
        }

        @Override
        public String getEntityName() {
            return "register";
        }
    }

    private static class UserDao extends DbObject {

        UserDao(String id, String name, String email, String adminId, String role) {
            super();
            setUp();
            setProperty("id", id);
            setProperty("name", name);
            setProperty("email", email);
            setProperty("role", role);
        }

        UserDao(JSONObject jsonObject) {
            super();
            setUp();
            buildFromJSON(jsonObject);
        }

        UserDao(User user) {
            super();
            setUp();
            setProperty("id", user.getID());
            setProperty("name", user.getName());
            setProperty("email", user.getEmail());
            setProperty("role", user.getRole());
        }

        private void setUp() {
            addProperty("id", null);
            addProperty("name", null);
            addProperty("email", null);
            addProperty("role", null);
        }

        public User getUser() {
            int id = Integer.decode(this.getProperty("id").toString());
            String name = this.getProperty("name").toString();
            String email = this.getProperty("email").toString();
            int role = Integer.decode(this.getProperty("role").toString());
            return new User(id, name, "", email,null, Role.values()[role-1]);
        }

        @Override
        public String getEntityName() {
            return "user";
        }
    }
}
