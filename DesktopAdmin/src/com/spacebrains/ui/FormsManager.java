package com.spacebrains.ui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Tatyana Vorobeva
 * New forms should be requested from FormsManager.
 * If form already exists - FormsManager makes it visible.
 * If form is not exists - FormsManager will create it.
 */
public class FormsManager {

    private static MainWindowForm mainWindowForm;
    private static AuthForm authorizationForm;
    private static ChangePswdForm changePswdForm;
    private static PersonsDictionaryForm personsDictionaryForm;
    private static KeywordsDictionaryForm keywordsDictionaryForm;
    private static SitesDictionaryForm sitesDictionaryForm;
    private static CrawlerStatsForm сrawlerStatsForm;

    private FormsManager() {}

    private static void activateForm(JFrame frame) {
        frame.setVisible(true);
        frame.setState(Frame.NORMAL);
    }

    public static MainWindowForm showMainWindowForm() {
        if (mainWindowForm == null) {
            mainWindowForm = new MainWindowForm();
        } else {
            activateForm(mainWindowForm);
        }
        return mainWindowForm;
    }

    public static AuthForm showAuthorizationForm() {
        if (authorizationForm == null) {
            authorizationForm = new AuthForm();
        } else {
            activateForm(authorizationForm);
        }
        return authorizationForm;
    }

    public static ChangePswdForm showChangePswdForm() {
        if (changePswdForm == null) {
            changePswdForm = new ChangePswdForm();
        } else {
            activateForm(changePswdForm);
        }
        return changePswdForm;
    }

    public static PersonsDictionaryForm showPersonsDictionaryForm() {
        if (personsDictionaryForm == null) {
            personsDictionaryForm = new PersonsDictionaryForm();
        } else {
            activateForm(personsDictionaryForm);
        }
        return personsDictionaryForm;
    }

    public static KeywordsDictionaryForm showKeywordsDictionaryForm() {
        if (keywordsDictionaryForm == null) {
            keywordsDictionaryForm = new KeywordsDictionaryForm();
        } else {
            activateForm(keywordsDictionaryForm);
        }
        return keywordsDictionaryForm;
    }

    public static SitesDictionaryForm showSitesDictionaryForm() {
        if (sitesDictionaryForm == null) {
            sitesDictionaryForm = new SitesDictionaryForm();
        } else {
            activateForm(sitesDictionaryForm);
        }
        return sitesDictionaryForm;
    }

    public static CrawlerStatsForm showCrawlerStatsForm() {
        if (сrawlerStatsForm == null) {
            сrawlerStatsForm = new CrawlerStatsForm();
        } else {
            activateForm(сrawlerStatsForm);
        }
        return сrawlerStatsForm;
    }
}
