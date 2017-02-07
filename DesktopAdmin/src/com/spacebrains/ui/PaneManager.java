package com.spacebrains.ui;

import com.spacebrains.ui.panels.*;

public class PaneManager {

    private static PaneManager instance;
    private static AuthPane authPane;
    private static MainWindowPane mainWindowPane;
    private static ChangePswdPane changePswdPane;

    private static CrawlerStatsPane crawlerStatsPane;
    private static UsersDictionaryPane usersDictionaryPane;

    private static PersonsDictionaryPane personsDictionaryPane;
    private static SitesDictionaryPane sitesDictionaryPane;
    private static KeywordsDictionaryPane keywordsDictionaryPane;

    private static BaseAppWindow appWindow;
    private static BasePane currentPane;

    private static LoadingPane loadingPane;

    private PaneManager() {}

    public static PaneManager getInstance() {
        if (instance == null) instance = new PaneManager();
        return instance;
    }

    public static BaseAppWindow appWindow() {
        if (appWindow == null) appWindow = new BaseAppWindow();
        return appWindow;
    }

    private static void switchToPane(BasePane newPane) {
        switchToPane(newPane, BasePane.DEFAULT_WIDTH, BasePane.DEFAULT_HEIGHT);
    }

    private static void switchToPane(BasePane newPane, int width, int height) {
        appWindow().switchContentPane(newPane, width, height);
        appWindow().hideMenu(newPane instanceof AuthPane);
    }

    public static void startMainForm() {
        appWindow();
        currentPane = new AuthPane();
        switchToPane(currentPane);
    }

    public static void switchToMainPane() {
        if (mainWindowPane == null) mainWindowPane = new MainWindowPane();
        switchToPane(mainWindowPane);
    }

    public static void switchToAuthPane() {
        if (authPane == null) authPane = new AuthPane();
        switchToPane(authPane);
    }

    public static void switchToChangePswdPane() {
        if (changePswdPane == null) changePswdPane = new ChangePswdPane();
        switchToPane(changePswdPane);
    }

    public static void switchToCrawlerStatsForm() {
        if (crawlerStatsPane == null) crawlerStatsPane = new CrawlerStatsPane();
        switchToPane(crawlerStatsPane, BasePane.DEFAULT_WIDTH + 200, BasePane.DEFAULT_HEIGHT);
    }

    public static void switchToUsersDictionaryPane() {
        if (usersDictionaryPane == null) usersDictionaryPane = new UsersDictionaryPane();
        switchToPane(usersDictionaryPane, BasePane.DEFAULT_WIDTH + 200, BasePane.DEFAULT_HEIGHT);
    }

    public static void switchToPersonsDictionaryPane() {
        if (personsDictionaryPane == null) personsDictionaryPane = new PersonsDictionaryPane();
        switchToPane(personsDictionaryPane);
    }

    public static void switchToSitesDictionaryPane() {
        if (sitesDictionaryPane == null) sitesDictionaryPane = new SitesDictionaryPane();
        switchToPane(sitesDictionaryPane);
    }

    public static void switchToKeywordsDictionaryPane() {
        if (keywordsDictionaryPane == null) keywordsDictionaryPane = new KeywordsDictionaryPane();
        switchToPane(keywordsDictionaryPane);
    }

    public static void switchToLoadingPane() {
        if (loadingPane == null) loadingPane = new LoadingPane();
        switchToPane(loadingPane);
    }
}
