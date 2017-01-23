package com.spacebrains.rest;

import com.spacebrains.interfaces.IRestRelay;
import com.spacebrains.model.Keyword;

public class FakeKeywordRelay implements IRestRelay<Keyword> {
    @Override
    public String save(Keyword obj) {
        return null;
    }

    @Override
    public Keyword get(Keyword obj) {
        return null;
    }

    @Override
    public boolean delete(Keyword obj) {
        return false;
    }
}
