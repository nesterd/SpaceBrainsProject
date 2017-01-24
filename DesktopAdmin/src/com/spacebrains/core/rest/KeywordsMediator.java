package com.spacebrains.core.rest;

import com.spacebrains.interfaces.IKeywordRepository;
import com.spacebrains.interfaces.IRest;
import com.spacebrains.interfaces.IRestRelay;
import com.spacebrains.model.Keyword;
import com.spacebrains.model.Person;

import java.util.ArrayList;

public class KeywordsMediator implements IKeywordRepository, IRest<Keyword> {
    private IKeywordRepository keywordRepository;
    private IRestRelay<Keyword> iRestRelay;

    public KeywordsMediator(IKeywordRepository keywordRepository, IRestRelay<Keyword> iRestRelay) {
        this.keywordRepository = keywordRepository;
        this.iRestRelay = iRestRelay;
    }

    // Функционал репозитория
    @Override
    public ArrayList<Keyword> getKeywords(Person person) {
        return keywordRepository.getKeywords(person);
    }

    // Функционал REST
    @Override
    public String save(Keyword obj) {
        return iRestRelay.save(obj);
    }

    @Override
    public Keyword get(Keyword obj) {
        return iRestRelay.get(obj);
    }

    @Override
    public boolean delete(Keyword obj) {
        return iRestRelay.delete(obj);
    }
}
