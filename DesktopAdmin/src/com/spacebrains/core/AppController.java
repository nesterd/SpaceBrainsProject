package com.spacebrains.core;

import com.spacebrains.core.dao.KeywordRepository;
import com.spacebrains.core.dao.PersonRepository;
import com.spacebrains.core.dao.SiteRepository;
import com.spacebrains.model.*;
import java.util.ArrayList;

import static com.spacebrains.core.AuthConstants.*;

public class AppController {

    private static AppController instance;
    private static User currentUser = null;

    private KeywordRepository keyRepo = null;
    private PersonRepository personRepo = null;
    private SiteRepository siteRepo = null;

    private static String lastRequestMsg = SUCCESS;

    private AppController() {
        keyRepo = new KeywordRepository();
        personRepo = new PersonRepository();
        siteRepo = new SiteRepository();
    }

    public static AppController getInstance() {
        if (instance == null) instance = new AppController();
        return instance;
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
                lastRequestMsg = SUCCESS;
            } else {
                lastRequestMsg = ERR_WRONG_PWSD;
            }
        } else if (login.equals("User")) {
            if (pswd.equals("123")) {
                currentUser = null;
                lastRequestMsg = ERR_IS_USER;
            } else lastRequestMsg = ERR_WRONG_PWSD;
        } else lastRequestMsg = ERR_WRONG_LOGIN;

        return lastRequestMsg;
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
     * @author Tatyana Vorobeva
     * For GUI to check if can show MainWindowForm
     * If false - AuthForm will be shown.
     */
    public boolean isAuthorized() {
        if (currentUser == null) lastRequestMsg = AuthConstants.INVALID_SESSION;
        return currentUser != null;
    }

    /**
     * @author Tatyana Vorobeva
     * For GUI to show user.login in MainWindowForm
     */
    public String userLogin() {
        return currentUser != null ? currentUser.getLogin() : "";
    }

    /**
     * @author Tatyana Vorobeva
     * For GUI to request password changing
     */
    public String changePswd(String oldPswd, String newPswd) {
        if (currentUser != null) {
            if (oldPswd.equals("123")) { // for real request - currentPswd check should be on web-service side.
                return AuthConstants.PSWD_CHANGED;
            } else return AuthConstants.ERR_WRONG_PWSD;
        } else
            return AuthConstants.INVALID_SESSION;
    }

    public static String lastRequestMsg() {
        return lastRequestMsg;
    }

    public static void setLastRequestMsg(String msg) {
        lastRequestMsg = msg;
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
