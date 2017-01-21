package com.spacebrains.ui;

public class FormsManager {

    private static PersonsDictionaryForm personsDictionaryForm;
    private static KeywordsDictionaryForm keywordsDictionaryForm;
    private static SitesDictionaryForm sitesDictionaryForm;

    private FormsManager() {}

    public static PersonsDictionaryForm showPersonsDictionaryForm() {
        if (personsDictionaryForm == null) {
            personsDictionaryForm = new PersonsDictionaryForm();
        }
        else personsDictionaryForm.setVisible(true);
        return personsDictionaryForm;
    }

    public static KeywordsDictionaryForm showKeywordsDictionaryForm() {
        if (keywordsDictionaryForm == null) {
            keywordsDictionaryForm = new KeywordsDictionaryForm();
        }
        else keywordsDictionaryForm.setVisible(true);
        return keywordsDictionaryForm;
    }

    public static SitesDictionaryForm showSitesDictionaryForm() {
        if (sitesDictionaryForm == null) {
            sitesDictionaryForm = new SitesDictionaryForm();
        }
        else sitesDictionaryForm.setVisible(true);
        return sitesDictionaryForm;
    }
}
