package com.spacebrains.view;

import com.spacebrains.interfaces.IDbEntity;
import com.spacebrains.model.KeywordDto;
import com.spacebrains.model.Person;
import com.spacebrains.model.Repository;

import java.util.ArrayList;
import java.util.Iterator;

public class RepositoryView {
    private Repository repository;

    public RepositoryView(Repository repository) {
        this.repository = repository;
    }

    public ArrayList<KeywordDto> getKeywords(Person person) {
        ArrayList<KeywordDto> keywords = new ArrayList<>();
        Iterator<IDbEntity> dbIterator = repository.iterator();
        while (dbIterator.hasNext()) {
            KeywordDto keyword = (KeywordDto) dbIterator;
            if(keyword.getPersonId() == person.getID()) {
               keywords.add(keyword);
            }
            dbIterator.next();
        }
        return keywords;
    }
}
