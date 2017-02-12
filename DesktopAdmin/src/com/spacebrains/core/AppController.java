package com.spacebrains.core;

import com.spacebrains.core.dao.KeywordRepository;
import com.spacebrains.core.dao.PersonRepository;
import com.spacebrains.core.dao.SiteRepository;
import com.spacebrains.core.dao.UserRepository;
import com.spacebrains.core.rest.StatsRestMock;
import com.spacebrains.model.*;

import java.util.ArrayList;

import static com.spacebrains.core.AuthConstants.SUCCESS;

public class AppController {

    private static AppController instance;
    private static Person lastChosenPerson;
    private static User currentUser = null;
    private static String lastLogin = "";

    private KeywordRepository keyRepo = null;
    private PersonRepository personRepo = null;
    private SiteRepository siteRepo = null;
    private UserRepository userRepo = null;

    private static String lastRequestMsg = SUCCESS;

    private AppController() {
        keyRepo = new KeywordRepository();
        personRepo = new PersonRepository();
        siteRepo = new SiteRepository();
        userRepo = new UserRepository();
    }

    public static AppController getInstance() {
        if (instance == null) instance = new AppController();
        return instance;
    }

    /**
     * @author Tatyana Vorobeva
     * For GUI to request authorization by recieved login & pswd.
     * Uses AuthConstants as answers, if possible;
     */
    public String login(String login, String pswd) {
        try {
            currentUser = userRepo.login(login, pswd);
            lastLogin = currentUser.getLogin();
            if (currentUser.getRole().equals(Role.USER)) {
                setLastRequestMsg(AuthConstants.ERR_IS_USER);
            } else setLastRequestMsg(AuthConstants.SUCCESS);
        } catch (RuntimeException e) {
            lastLogin = login;
            setLastRequestMsg(e.getLocalizedMessage());
        }
        return lastRequestMsg;
    }

    /**
     * @author Tatyana Vorobeva
     * For GUI to request logout.
     * Should send to REST a special request to invalidate accessCode;
     * Should be invoked ins case of authorizationFailed answer for any repo request
     */
    public void logout() {
        currentUser = null;
        lastRequestMsg = "";
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
        boolean pswdChanged = false;
        if (currentUser != null) {
            try {
                if (userRepo.changePswd(oldPswd, newPswd)) setLastRequestMsg(AuthConstants.PSWD_CHANGED);
            } catch (RuntimeException e) {
                setLastRequestMsg(e.getLocalizedMessage());
            }
        } else
            setLastRequestMsg(AuthConstants.INVALID_SESSION);
        return lastRequestMsg();
    }

    /**
     * @author Tatyana Vorobeva
     * For GUI to request password restore
     */
    public String restorePswd(String email) {
        try {
            if (userRepo.restorePswd(email)) setLastRequestMsg(AuthConstants.PSWD_RESTORED);
        } catch (RuntimeException e) {
            setLastRequestMsg(e.getLocalizedMessage());
        }
        return lastRequestMsg();
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
     * Попробуй в GUI примерно такую обработку исключений
     */
    public ArrayList<Person> getPersons() {
        ArrayList<Person> persons = new ArrayList<>();
        String answer = "";
        try {
            persons = personRepo.get();
            answer = RepoConstants.SUCCESS;
        } catch (RuntimeException e) {
            answer = e.getMessage();
        }
        setLastRequestMsg(answer);
        System.out.println(lastRequestMsg());
        return persons;
    }

    public ArrayList<Keyword> getKeywordsByPerson(Person person) {
        ArrayList<Keyword> keywords =new ArrayList<>();
        String answer = "";
        try {
            keywords = keyRepo.getByObject(person);
            answer = RepoConstants.SUCCESS;
        } catch (RuntimeException e) {
            answer = e.getMessage();
        }
        setLastRequestMsg(answer);
        System.out.println(lastRequestMsg());
        return keywords;
    }

    public ArrayList<Site> getSites() {
        ArrayList<Site> sites = new ArrayList<>();
        String answer = "";
        try {
            sites = siteRepo.get();
            answer = RepoConstants.SUCCESS;
        } catch (RuntimeException e) {
            answer = e.getMessage();
        }
        setLastRequestMsg(answer);
        System.out.println(lastRequestMsg());
        return sites;
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

    /** Return Users for ADMIN and Admins for SUPER_ADMIN */
    public ArrayList<User> getUsers() {
        return userRepo.get(currentUser.getRole());
    }

    public String setUser(User user) {
        if (user.getID() == 0)
            return userRepo.register(user) ? AuthConstants.SUCCESS : AuthConstants.NOT_ANSWERED;
        else return userRepo.put(user);
    }

    public boolean deleteUser(User user) {
        return userRepo.delete(user);
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

    public ArrayList<CrawlerStats> getCrawlerStats() {
        // simple mock
        return StatsRestMock.getInstance().getCrawlerStats();
        // simple mosk end
    }

    /**
     * @author Tatyana Vorobeva
     * For auto-filling AuthPane.login after logout
     * */
    public static String getLastLogin() {
        return lastLogin.length() < 1 ? "" : lastLogin;
    }
}
