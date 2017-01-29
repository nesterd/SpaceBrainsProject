package com.spacebrains.core;

import com.spacebrains.model.Keyword;
import com.spacebrains.model.Person;
import com.spacebrains.model.Site;
import com.spacebrains.model.User;

import java.util.ArrayList;

import static com.spacebrains.core.AuthConstants.*;

public class AppController {

    private static User currentUser = null;

    public AppController() {
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
        return null;
    }

    public ArrayList<Keyword> getKeywordsByPerson(Person person) {
        return null;
    }

    public ArrayList<Site> getSites() {
        return null;
    }

    public void setPerson(Person person) {

    }

    public void setKeyword(Keyword keyword) {

    }

    public void setSite(Site site) {

    }

    public boolean deletePerson(Person person) {
        return false;
    }

    public boolean deleteKeyword(Keyword keyword) {
        return false;
    }

    public boolean deleteSite(Site site) {
        return false;
    }
}
