package com.spacebrains.interfaces;

import com.spacebrains.model.User;

import java.util.ArrayList;

public interface IUsers extends IRest<User>{
    ArrayList<User> getUsers();
}
