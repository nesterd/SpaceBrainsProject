package com.spacebrains.widgets;

import com.spacebrains.core.AppController;
import com.spacebrains.core.RepoConstants;
import com.spacebrains.model.Keyword;
import com.spacebrains.widgets.base.BaseEditForm;

public class KeywordEditForm extends BaseEditForm<Keyword> {

    public KeywordEditForm(Keyword keyword) {
        this(keyword, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public KeywordEditForm(Keyword keyword, int width, int height) {
        super(keyword, width, height);
    }

    @Override
    protected String save(Keyword keyword) {
        try {
            return AppController.getInstance().setKeyword(keyword)
                    ? RepoConstants.SUCCESS
                    : RepoConstants.NOT_ANSWERED;
        } catch (RuntimeException e) {
            return RepoConstants.NOT_ANSWERED;
        }
    }
}
