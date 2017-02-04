package com.spacebrains.core;

import com.spacebrains.core.dao.KeywordRepository;
import com.spacebrains.core.dao.PersonRepository;
import com.spacebrains.core.dao.SiteRepository;
import com.spacebrains.core.rest.UsersRestMock;
import com.spacebrains.model.*;
import java.util.ArrayList;

import static com.spacebrains.core.AuthConstants.*;

public class AppController {

    private static AppController instance;
    private static Person lastChosenPerson;
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
        UsersRestMock.getInstance().login(login, pswd);
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
     * For GUI to check if can show MainWindowForm
     * If false - AuthForm will be shown.
     */
    public boolean isAuthorized() {
        if (currentUser == null) lastRequestMsg = AuthConstants.INVALID_SESSION;
        return currentUser != null;
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

    public static Person getLastChosenPerson() {
        return lastChosenPerson;
    }

    public static void setLastChosenPerson(Person lastChosenPerson) {
        AppController.lastChosenPerson = lastChosenPerson;
    }

    public static void setCurrentUser(User currentUser) {
        AppController.currentUser = currentUser;
    }

    public ArrayList<User> getUsers() {
//        return userRepo.get();

        // simple mock
        return UsersRestMock.getInstance().getUsers();
        // end simple mock
    }

    public boolean setUser(User user) {
//        return userRepo.put(user);

        // simple mock
        return UsersRestMock.getInstance().save(user).equals(RepoConstants.SUCCESS);
        // end simple mock
    }

    public boolean deleteUser(User user) {
        //        return userRepo.delete(user);

        // simple mock
        return UsersRestMock.getInstance().delete(user);
        // end simple mock
    }

    /**
     * @author Tatyana Vorobeva
     */
    public Role getCurrentUserRole() {
        return (currentUser != null) ? currentUser.getRole() : Role.USER;
    }

    public String getCurrentUserLogin() {
        return (currentUser != null) ? currentUser.getLogin() : "Guest";
    }

    public String getCurrentUserName() {
        return (currentUser != null) ? currentUser.getName() : "Guest";
    }
}
