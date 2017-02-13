package com.spacebrains.core.dao;

import com.spacebrains.core.AuthConstants;
import com.spacebrains.core.RepoConstants;
import com.spacebrains.core.rest.RESTApiProvider;
import com.spacebrains.model.Role;
import com.spacebrains.model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Обеспечивает хранение и обработку данных о пользователях,
 * полученных от RESTful web-сервиса
 * а также обеспечивает формирование запросов в REST
 * для управления пользователями
 */
public class UserRepository {
    private ArrayList<User> users;
    private RESTApiProvider rest;
    private String access_token;

    public UserRepository() {
        users = new ArrayList<>();
        rest = new RESTApiProvider();
    }

    /**
     * Регистрирует вход пользователя в систему
     * При входе сервис выдаёт временный токен, который следует использовать
     * для дальнейших запросов, требующих авторизации
     * @param login аккаунт пользователя
     * @param password пароль в открытом виде
     * @return объект User с заполненными полями из запроса "/status"
     */
    public User login(String login, String password) {
        User currentUser = null;
        String jsonString = null;
        Role role = null;
        access_token = rest.login(new UserCredentials(login, password));
        if(access_token != null) {
//            try {
//                jsonString = rest.getAdmins();
//                role = Role.SUPER_ADMIN;
//            } catch (RuntimeException e) {
//                if(e.getMessage().contains(AuthConstants.ACCESS_FORBIDDEN)) {
//                    // наша роль не суперадмин - проверяем не админы ли мы
//                    try {
//                        jsonString = rest.getUsers();
//                        role = Role.ADMIN;
//                    } catch (RuntimeException e1) {
//                        if(e1.getMessage().contains(AuthConstants.ACCESS_FORBIDDEN)) {
//                            role = Role.USER;
//                        }
//                    }
//                }
//            }
            jsonString = rest.getStatus();
            JSONParser parser = new JSONParser();
            UserDao userDao = null;
            try {
                JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
                userDao = new UserDao(jsonObject);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            role = Role.values()[Integer.decode(userDao.getProperty("role").toString()) - 1];
            currentUser = new User(0, login, login, null, access_token, role);
            currentUser.setID(Integer.decode(userDao.getProperty("id").toString()));
            currentUser.setName(userDao.getProperty("name").toString());
            currentUser.setEmail(userDao.getProperty("email").toString());
            currentUser.setPswd(password);
        }
        return currentUser;
    }

    /**
     * Use for debug only!
     * @return String - token received from web-service
     */
    @Deprecated
    public String getToken() { return access_token; }

    /**
     * Запрашивает смену пароля
     * @param oldPassword старый пароль
     * @param newPassword новый пароль
     * @return возвращает true в случае успешной смены пароля
     */
    public boolean changePswd(String oldPassword, String newPassword) {
        DbObject user = new DbObject() {
            @Override
            public String getEntityName() {
                return "/user/changepass";
            }
        };
        user.addProperty("password", oldPassword);
        user.addProperty("new_password", newPassword);
        return rest.changePass(user);
    }

    /**
     * Запрашивает новый пароль на электронный почтовый ящик пользователя
     * @param email e-mail должен совпадать с хранящимся в базе данных веб-сервиса
     * @return возвращает true в случае успешной отправки уведомления на электронную почту
     */
    public boolean restorePswd(String email) {
        UserCredentials user = new UserCredentials("","");
        user.addProperty("email", email);
        return rest.restorePswd(user);
    }

    /**
     * Запрашивает список пользователей
     * @param role роль текущего пользователя
     *             пользователь с ролью SUPER_ADMIN имеет доступ к управлению списком администраторов
     *             пользователь с ролью ADMIN имеет доступ к управлению списком пользователей
     *             пользователь с ролью USER не имеет доступа ни к одному из списков
     * @return список пользователей в виде ArrayList
     */
    public ArrayList<User> get (Role role) {
        users = new ArrayList<>();
        String jsonString, key;
        switch (role) {
            case SUPER_ADMIN: {
            }
            case ADMIN: {
                jsonString = rest.getUsers();
                key = "users";
                break;
            }
            default:
                throw new RuntimeException(AuthConstants.ACCESS_FORBIDDEN);
        }
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
            JSONArray jsonArray = (JSONArray) jsonObject.get(key);
            Iterator<JSONObject> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                jsonObject = (JSONObject) iterator.next();
                UserDao userDao = new UserDao(jsonObject);
                users.add(userDao.getUser());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Запрашивает сохранение новых значений атрибутов пользователя
     * @param user объект из модели данных, содержащий новые значения атрибутов
     * @return возвращает строку статуса операции сохранения
     */
    public String put(User user) {
        UserRegistration regObj = new UserRegistration(user);
        regObj.addProperty("id", user.getID());
        return (rest.updateObject(regObj)) ? RepoConstants.SUCCESS : RepoConstants.NOT_ANSWERED;
    }

    /**
     * Запрашивает удаление пользователя из базы
     * @param user удаляемый пользователь
     * @return возвращает true в случае успешного удаления пользователя
     */
    public boolean delete(User user) {
        return rest.deleteObject(new UserDao(user));
    }

    /**
     * Запрашивает регистрацию нового пользователя
     * @param user добавляемый пользователь
     * @return возвращает true в случае успешной регистрации нового пользователя
     */
    public boolean register(User user) {
        return rest.register(new UserRegistration(user));
    }

    /**
     * Внутренний класс-обёртка для объектов DbObject
     * позволяет использовать унифицированные вызовы при обращении к REST
     * во время авторизации пользователя
     */
    private static class UserCredentials extends DbObject {

        UserCredentials(String username, String password) {
            super();
            addProperty("username", username);
            addProperty("password", password);
        }

        @Override
        public String getEntityName() {
            return "auth";
        }
    }

    /**
     * Внутренний класс-обёртка для объектов DbObject
     * позволяет использовать унифицированные вызовы при обращении к REST
     * во время регистрации нового пользователя
     */
    private static class UserRegistration extends DbObject {

        UserRegistration(String name, String email, String login, String password) {
            super();
            addProperty("name", name);
            addProperty("email", email);
            addProperty("username", login);
            addProperty("password", password);
        }

        UserRegistration(User user) {
            super();
            addProperty("name", user.getName());
            addProperty("email", user.getEmail());
            addProperty("username", user.getLogin());
            addProperty("password", user.getPswd());
        }

        @Override
        public String getEntityName() {
            return "user";
        }
    }

    /**
     * Внутренний класс-обёртка для объектов DbObject
     * позволяет использовать унифицированные вызовы при обращении к REST
     * применяется для кодирования в JSON расширенной информации о пользователе
     */
    private static class UserDao extends DbObject {

        UserDao(String id, String name, String email, String adminId, String role) {
            super();
            setUp();
            setProperty("id", id);
            setProperty("name", name);
            setProperty("email", email);
            setProperty("role", role);
        }

        UserDao(JSONObject jsonObject) {
            super();
            setUp();
            buildFromJSON(jsonObject);
        }

        UserDao(User user) {
            super();
            setUp();
            setProperty("id", user.getID());
            setProperty("name", user.getName());
            setProperty("email", user.getEmail());
            setProperty("role", user.getRole());
            setProperty("username", user.getLogin());
        }

        private void setUp() {
            addProperty("id", null);
            addProperty("name", null);
            addProperty("email", null);
            addProperty("role", null);
            addProperty("username", null);
        }

        public User getUser() {
            int id = Integer.decode(this.getProperty("id").toString());
            String name = this.getProperty("name").toString();
            String email = this.getProperty("email").toString();
            int role = Integer.decode(this.getProperty("role").toString());
            String login = this.getProperty("username").toString();
            return new User(id, name, login, email,null, Role.values()[role-1]);
        }

        @Override
        public String getEntityName() {
            return "user";
        }
    }
}
