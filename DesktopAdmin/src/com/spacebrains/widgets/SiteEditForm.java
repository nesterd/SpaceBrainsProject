package com.spacebrains.widgets;

import com.spacebrains.core.AppController;
import com.spacebrains.core.RepoConstants;
import com.spacebrains.model.Site;

public class SiteEditForm extends BaseEditForm<Site> {

    public SiteEditForm(Site site) {
        this(site, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public SiteEditForm(Site site, int width, int height) {
        super(site, width, height);
    }

    @Override
    protected String save(Site site) {
        try {
            return AppController.getInstance().setSite(site)
                    ? RepoConstants.SUCCESS
                    : RepoConstants.NOT_ANSWERED;
        } catch (RuntimeException e) {
            return RepoConstants.NOT_ANSWERED;
        }
    }
}
