package com.spacebrains.ui;

import com.spacebrains.widgets.base.BaseWindow;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Tatyana Vorobeva
 * New forms should be requested from FormsManager.
 * If form already exists - FormsManager makes it visible.
 * If form is not exists - FormsManager will create it.
 */
public class FormsManager {

    private static ArrayList<BaseWindow> menuWindows = new ArrayList<>();

    private static MainWindowForm mainWindowForm;
    private static AuthForm authorizationForm;
    private static ChangePswdForm changePswdForm;
    private static PersonsDictionaryForm personsDictionaryForm;
    private static KeywordsDictionaryForm keywordsDictionaryForm;
    private static SitesDictionaryForm sitesDictionaryForm;
    private static CrawlerStatsForm crawlerStatsForm;
    private static UsersDictionaryForm usersForm;

    private FormsManager() {}

    private static void activateForm(JFrame frame) {
        frame.setVisible(true);
        frame.setState(Frame.NORMAL);

        for (BaseWindow window : menuWindows) {
            if (window != null) window.menu.switchForUserRights();
        }
    }

    public static MainWindowForm showMainWindowForm() {
        if (mainWindowForm == null) {
            mainWindowForm = new MainWindowForm();
            menuWindows.add(mainWindowForm);
            mainWindowForm.menu.switchForUserRights();
            mainWindowForm.wasAlreadyOpenedBefore = true;
        } else {
            mainWindowForm.refreshGreetings();
            activateForm(mainWindowForm);
        }
        return mainWindowForm;
    }

    public static AuthForm showAuthorizationForm() {
        if (authorizationForm == null) {
            authorizationForm = new AuthForm();
            authorizationForm.wasAlreadyOpenedBefore = true;
        } else {
            activateForm(authorizationForm);
        }
        return authorizationForm;
    }

    public static ChangePswdForm showChangePswdForm() {
        if (changePswdForm == null) {
            changePswdForm = new ChangePswdForm();
            menuWindows.add(changePswdForm);
            changePswdForm.menu.switchForUserRights();
            changePswdForm.wasAlreadyOpenedBefore = true;
        } else {
            activateForm(changePswdForm);
        }
        return changePswdForm;
    }

    public static PersonsDictionaryForm showPersonsDictionaryForm() {
        if (personsDictionaryForm == null) {
            personsDictionaryForm = new PersonsDictionaryForm();
            menuWindows.add(personsDictionaryForm);
            personsDictionaryForm.menu.switchForUserRights();
            personsDictionaryForm.wasAlreadyOpenedBefore = true;
        } else {
            activateForm(personsDictionaryForm);
        }
        return personsDictionaryForm;
    }

    public static KeywordsDictionaryForm showKeywordsDictionaryForm() {
        if (keywordsDictionaryForm == null) {
            keywordsDictionaryForm = new KeywordsDictionaryForm();
            menuWindows.add(keywordsDictionaryForm);
            keywordsDictionaryForm.menu.switchForUserRights();
            keywordsDictionaryForm.wasAlreadyOpenedBefore = true;
        } else {
            activateForm(keywordsDictionaryForm);
        }
        return keywordsDictionaryForm;
    }

    public static SitesDictionaryForm showSitesDictionaryForm() {
        if (sitesDictionaryForm == null) {
            sitesDictionaryForm = new SitesDictionaryForm();
            menuWindows.add(sitesDictionaryForm);
            sitesDictionaryForm.menu.switchForUserRights();
            sitesDictionaryForm.wasAlreadyOpenedBefore = true;
        } else {
            activateForm(sitesDictionaryForm);
        }
        return sitesDictionaryForm;
    }

    public static CrawlerStatsForm showCrawlerStatsForm() {
        if (crawlerStatsForm == null) {
            crawlerStatsForm = new CrawlerStatsForm();
            menuWindows.add(crawlerStatsForm);
            crawlerStatsForm.menu.switchForUserRights();
            crawlerStatsForm.wasAlreadyOpenedBefore = true;
        } else {
            activateForm(crawlerStatsForm);
        }
        return crawlerStatsForm;
    }

    public static UsersDictionaryForm showUsersForm() {
        if (usersForm == null) {
            usersForm = new UsersDictionaryForm();
            menuWindows.add(usersForm);
            usersForm.menu.switchForUserRights();
            usersForm.wasAlreadyOpenedBefore = true;
        } else {
            activateForm(usersForm);
        }
        return usersForm;
    }
}
