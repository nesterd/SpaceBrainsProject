package com.spacebrains.model;

import com.spacebrains.interfaces.IRepository;

public class RepositoryController {
    private IRepository keywordRepo;
    private IRepository personRepo;
    private IRepository siteRepo;

    public RepositoryController() {
        keywordRepo = new Repository();
        personRepo = new Repository();
        siteRepo = new Repository();
    }

    // что-то типа того, плюс методы для обновления данных
}
