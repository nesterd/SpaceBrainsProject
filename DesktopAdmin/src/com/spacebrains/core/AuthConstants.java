package com.spacebrains.core;

/**
 * @author Tatyana Vorobeva
 * Static constants for Authorization answers to keep all Strings in one place.
 */
public abstract class AuthConstants {
    public static final String USER_WEB_INTERFACE_LINK = "http://www.geekbrains.ru";

    public static final String SUCCESS = " ";
    public static final String NOT_ANSWERED = "Сервер не отвечает";
    public static final String NOT_FOUND = "Страница не найдена";

    public static final String ERR_WRONG_CREDENTIALS = "Неверный логин/пароль";
    public static final String ERR_WRONG_LOGIN = "Логин не найден";
    public static final String ERR_WRONG_PWSD = "Неверный пароль";
    public static final String INVALID_SESSION = "Сессия истекла";
    public static final String ERR_IS_USER = "<html>Воспользуйтесь <a href='" + USER_WEB_INTERFACE_LINK
            + "'>Web-версией</a>,<br>&#09;пожалуйста</html>"; //<br>&#09;

    public static final String NOT_MATCHING_PSWD = "Введенные пароли не совпадают";
    public static final String PSWD_CHANGED = "Пароль изменен";
    public static final String NEED_OLD_PSWD = "Не введен старый пароль";
    public static final String NEED_NEW_PSWD = "Не введен новый пароль";

    public static final String USER_NAME_EMPTY = "Не заполнено имя пользователя";
    public static final String USER_LOGIN_EMPTY = "Не заполнен логин пользователя";
    public static final String USER_PSWD_EMPTY = "Не заполнен пароль пользователя";
    public static final String USER_LOGIN_EXISTS = "Этот логин уже занят";
    public static final String USER_EMAIL_EXISTS = "Этот e-mail уже занят";
    public static final String USER_EMAIL_EMPTY = "Не заполнен e-mail пользователя";
    public static final String USER_EMAIL_FORMAT = "Неверный формат e-mail";
}
