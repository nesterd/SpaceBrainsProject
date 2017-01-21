package com.spacebrains.ui;

public class FormsManager {

    private static PersonsDictionaryForm personsDictionaryForm;
    private static KeywordsDictionaryForm keywordsDictionaryForm;
    private static SitesDictionaryForm sitesDictionaryForm;

    private FormsManager() {}

    public static PersonsDictionaryForm showPersonsDictionaryForm() {
        personsDictionaryForm = new PersonsDictionaryForm();
        return personsDictionaryForm;
    }

    public static KeywordsDictionaryForm showKeywordsDictionaryForm() {
        keywordsDictionaryForm = new KeywordsDictionaryForm();
        return keywordsDictionaryForm;
    }

    public static SitesDictionaryForm showSitesDictionaryForm() {
        sitesDictionaryForm = new SitesDictionaryForm();
        return sitesDictionaryForm;
    }
}
