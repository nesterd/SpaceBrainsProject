package com.spacebrains.core;

/**
 * @author Tatyana Vorobeva
 * Static constants for Authorization answers to keep all Strings in one place.
 */
public abstract class AuthConstants {
    public static final String USER_WEB_INTERFACE_LINK = "http://www.spacebrains.com";

    public static final String SUCCESS = "";
    public static final String WRONG_LOGIN = "Указанный логин не найден";
    public static final String WRONG_PWSD = "Неверный пароль";
    public static final String IS_USER = "Воспользуйтесь <a href='" + USER_WEB_INTERFACE_LINK + "'>Web-версией</a>, пожалуйста";
}
