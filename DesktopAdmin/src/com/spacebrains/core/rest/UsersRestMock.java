package com.spacebrains.core.rest;

import com.spacebrains.core.AppController;
import com.spacebrains.core.AuthConstants;
import com.spacebrains.interfaces.IUsers;
import com.spacebrains.model.Role;
import com.spacebrains.model.User;

import java.util.ArrayList;

public class UsersRestMock implements IUsers {

    private static UsersRestMock instance;

    ArrayList<User> allUsers = new ArrayList();
    private static int nextId = 12;

    public UsersRestMock() {
        allUsers = new ArrayList<>();
        allUsers.add(new User(1, "TestUser1", "TestUser1", "testUser1@mail.ru","123", Role.USER));
        allUsers.add(new User(2, "TestUser2", "TestUser2", "testUser2@mail.ru","123", Role.USER));
        allUsers.add(new User(3, "Admin", "Admin", "admin@mail.ru","123", Role.ADMIN));
        allUsers.add(new User(4, "TestUser3","TestUser3", "testUser3@mail.ru","123", Role.USER));
        allUsers.add(new User(5, "SuperAdmin2","SuperAdmin2", "superAdmin2@mail.ru","123", Role.SUPER_ADMIN));
        allUsers.add(new User(6, "Admin2", "Admin2", "admin2@mail.ru","123", Role.ADMIN));
        allUsers.add(new User(7, "TestUser4", "TestUser4", "testUser4@mail.ru","123", Role.USER));
        allUsers.add(new User(8, "TestUser5", "TestUser5", "testUser5@mail.ru","123", Role.USER));
        allUsers.add(new User(9, "Admin3", "Admin3", "admin3@mail.ru","123", Role.ADMIN));
        allUsers.add(new User(10, "TestUser6", "TestUser6", "testUser6@mail.ru","123", Role.USER));
        allUsers.add(new User(11, "AdminS","AdminS", "AdminS@mail.ru","123", Role.SUPER_ADMIN));
    }

    public boolean login(String login, String pswd) {
        for (User user : allUsers) {
            if (user.getLogin().equalsIgnoreCase(login)) {
                if (pswd.equals("123")) {
                    if (user.getRole().equals(Role.USER)) {
                        AppController.setLastRequestMsg(AuthConstants.ERR_IS_USER);
                        return false;
                    }
                    AppController.setCurrentUser(user);
                    AppController.setLastRequestMsg(AuthConstants.SUCCESS);
                    return true;
                } else {
                    AppController.setLastRequestMsg(AuthConstants.ERR_WRONG_PWSD);
                    return false;
                }
            }
        }

        AppController.setLastRequestMsg(AuthConstants.ERR_WRONG_LOGIN);
        return false;
    }

    @Override
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        if (AppController.getInstance().getCurrentUserRole().equals(Role.SUPER_ADMIN)) {
            for (User user : allUsers) {
                if (!user.getLogin().equals(AppController.getInstance().getCurrentUserLogin()) && user.getRole().equals(Role.ADMIN)) {
                    users.add(user);
                }
            }
        }
        if (AppController.getInstance().getCurrentUserRole().equals(Role.ADMIN)) {
            for (User user : allUsers) {
                if (!user.getLogin().equals(AppController.getInstance().getCurrentUserLogin()) && user.getRole().equals(Role.USER)) {
                    users.add(user);
                }
            }
        }
        return users;
    }

    @Override
    public String save(User user) {
        if (allUsers.contains(user)) {
            allUsers.get(allUsers.indexOf(user)).setLogin(user.getLogin());
            allUsers.get(allUsers.indexOf(user)).setEmail(user.getEmail());
        } else {
            for (User userToCheck : allUsers) {
                if (user.getLogin().equals(userToCheck.getLogin())) return AuthConstants.USER_LOGIN_EXISTS;
                if (user.getEmail().equals(userToCheck.getEmail())) return AuthConstants.USER_EMAIL_EXISTS;
             }
            user.setID(nextId++);
            allUsers.add(user);
        }
        return AuthConstants.SUCCESS;
    }

    @Override
    public User get(User userToSearch) {
        if (allUsers.contains(userToSearch)) {
            return allUsers.get(allUsers.indexOf(userToSearch));
        } else return null;
    }

    @Override
    public boolean delete(User user) {
        allUsers.remove(user);
        return true;
    }

    public static UsersRestMock getInstance() {
        if (instance == null) instance = new UsersRestMock();
        return instance;
    }
}
