package com.spacebrains.core;

import com.spacebrains.model.*;

import java.util.ArrayList;

import static com.spacebrains.core.AuthConstants.*;

public class AppController {

    private static User currentUser = null;

    private KeywordRepository keyRepo = null;
    private PersonRepository personRepo = null;
    private SiteRepository siteRepo = null;

    public AppController() {
        keyRepo = new KeywordRepository();
        personRepo = new PersonRepository();
        siteRepo = new SiteRepository();
    }

    /**
     * @author Tatyana Vorobeva
     * For GUI to request authorization by recieved login & pswd.
     * Uses AuthConstants as answers;
     */
    public String login(String login, String pswd) {
        // authorization via userRepo.
        // currentUser should be saved in case of successful authorizaion

        // simple mock start
        if (login.equals("Admin")) {
            if (pswd.equals("123")) {
                currentUser = new User(1, login, "someFakeAccessCode", true);
                return SUCCESS;
            } else return ERR_WRONG_PWSD;
        }

        if (login.equals("User")) {
            if (pswd.equals("123")) {
                currentUser = null;
                return ERR_IS_USER;
            } else return ERR_WRONG_PWSD;
        }

        return ERR_WRONG_LOGIN;
        // simple mock end
    }

    /**
     * @author Tatyana Vorobeva
     * For GUI to request logout.
     * Should send to REST a special request to invalidate accessCode;
     * Should be invoked ins case of authorizationFailed answer for any repo request
     */
    public void logout() {
        currentUser = null;
    }

    /**
     * @author Tatyana Vorobeva
     * For GUI to request logout.
     * Should send to REST a special request to invalidate accessCode;
     */
    public boolean isAdmin() {
        if (currentUser == null) return false;

        return true;
    }

    /**
     * @author Oleg Chizhov
     * for GUI to request Persons
     */
    public ArrayList<Person> getPersons() {
        return personRepo.get();
    }

    public ArrayList<Keyword> getKeywordsByPerson(Person person) {
        return keyRepo.getByObject(person);
    }

    public ArrayList<Site> getSites() {
        return siteRepo.get();
    }

    public boolean setPerson(Person person) {
        return personRepo.put(person);
    }

    public boolean setKeyword(Keyword keyword) {
        return keyRepo.put(keyword);
    }

    public boolean setSite(Site site) {
        return siteRepo.put(site);
    }

    public boolean deletePerson(Person person) {
        return personRepo.delete(person);
    }

    public boolean deleteKeyword(Keyword keyword) {
        return keyRepo.delete(keyword);
    }

    public boolean deleteSite(Site site) {
        return siteRepo.delete(site);
    }
}
