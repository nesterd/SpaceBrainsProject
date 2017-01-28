package com.spacebrains.core;

import com.spacebrains.interfaces.IRepository;
import com.spacebrains.model.Repository;
import com.spacebrains.model.User;

import static com.spacebrains.core.AuthConstants.*;

public class AppController {

    private static User currentUser = null;

    private IRepository keywordRepo;
    private IRepository personRepo;
    private IRepository siteRepo;

    public AppController() {
        keywordRepo = new Repository();
        personRepo = new Repository();
        siteRepo = new Repository();
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
        if (login.equals("Admin") && pswd.equals("123")) {
            currentUser = new User(1, login, "someFakeAccessCode", true);
            return SUCCESS;
        }

        if (login.equals("User") && pswd.equals("123")) {
            currentUser = null;
            return IS_USER;
        }

        return WRONG_LOGIN;
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
}
