package com.spacebrains.ui;

import com.spacebrains.core.AppController;
import com.spacebrains.core.AuthConstants;
import com.spacebrains.core.RepoConstants;
import com.spacebrains.ui.panels.*;

public class PaneManager {

    private static PaneManager instance;
    private static AuthPane authPane;
    private static MainWindowPane mainWindowPane;
    private static ChangePswdPane changePswdPane;
    private static RestorePswdPane restorePswdPane;

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
        String msg = "";
        if (!newPane.equals(loadingPane) && !newPane.equals(authPane) && !newPane.equals(restorePswdPane)) {
            try {
                appWindow().switchContentPane(newPane, width, height);
                msg = AppController.lastRequestMsg();
            } catch (Exception e) {
                msg = e.getLocalizedMessage();
                System.out.println(e.fillInStackTrace());
            }
            if (!msg.equals(RepoConstants.SUCCESS)) {
                msg = msg.contains("HttpHostConnectException")
                        ? RepoConstants.NOT_ANSWERED
                        : msg; // temporary mock
                if (msg.equals(AuthConstants.ERR_WRONG_CREDENTIALS) || msg.equals(AuthConstants.ERR_WRONG_PWSD)) {
                    AppController.getInstance().logout();
                    newPane = authPane;
                    switchToAuthPane();
                    authPane.setErrorMsg(AppController.lastRequestMsg());
                } else {
                    switchToLoadingPane();
                    loadingPane.refreshMessage(msg);
                }
            }
        } else {
            appWindow().switchContentPane(newPane, width, height);
        }
        appWindow().hideMenu(newPane instanceof AuthPane);
    }

    public static void startMainForm() {
        appWindow();
        currentPane = new AuthPane();
        authPane = (AuthPane) currentPane;
        switchToPane(currentPane);
    }

    public static void switchToMainPane() {
        if (mainWindowPane == null) mainWindowPane = new MainWindowPane();
        switchToPane(mainWindowPane);
    }

    public static void switchToAuthPane() {
        if (authPane == null) authPane = new AuthPane();
        switchToPane(authPane);
        authPane.setErrorMsg(AppController.lastRequestMsg());
    }

    public static void switchToChangePswdPane() {
        if (changePswdPane == null) changePswdPane = new ChangePswdPane();
        switchToPane(changePswdPane);
    }

    public static void switchToRestorePswdPane() {
        if (restorePswdPane == null) restorePswdPane = new RestorePswdPane();
        switchToPane(restorePswdPane);
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
