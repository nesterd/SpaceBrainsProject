package com.spacebrains.interfaces;

import com.spacebrains.model.Person;

import java.util.ArrayList;

public interface IPersons extends IRest<Person> {
    ArrayList<Person> getPersons();
}
