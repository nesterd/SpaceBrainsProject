package com.spacebrains.view;

import com.spacebrains.interfaces.IDbEntity;
import com.spacebrains.model.KeywordDto;
import com.spacebrains.model.Person;
import com.spacebrains.model.Storage;

import java.util.ArrayList;
import java.util.Iterator;

public class StorageView {
    private Storage storage;

    public StorageView(Storage storage) {
        this.storage = storage;
    }

    public ArrayList<KeywordDto> getKeywords(Person person) {
        ArrayList<KeywordDto> keywords = new ArrayList<>();
        Iterator<IDbEntity> dbIterator = storage.iterator();
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
