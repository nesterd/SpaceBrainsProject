package com.spacebrains.interfaces;

import com.spacebrains.model.Keyword;
import com.spacebrains.model.Person;

import java.util.ArrayList;

public interface IKeywords extends IRest<Keyword> {
    ArrayList<Keyword> getKeywords(Person person);
}
