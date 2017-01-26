package com.spacebrains.view;

import com.spacebrains.interfaces.IDbEntity;
import com.spacebrains.model.DbKeyword;
import com.spacebrains.model.Person;
import com.spacebrains.model.Repository;

import java.util.ArrayList;
import java.util.Iterator;

public class RepositoryView {
    private Repository repository;

    public RepositoryView(Repository repository) {
        this.repository = repository;
    }

    public ArrayList<DbKeyword> getKeywords(Person person) {
        ArrayList<DbKeyword> keywords = new ArrayList<>();
        Iterator<IDbEntity> dbIterator = repository.iterator();
        while (dbIterator.hasNext()) {
            DbKeyword keyword = (DbKeyword) dbIterator;
            if(keyword.getPersonId() == person.getID()) {
               keywords.add(keyword);
            }
            dbIterator.next();
        }
        return keywords;
    }
}
