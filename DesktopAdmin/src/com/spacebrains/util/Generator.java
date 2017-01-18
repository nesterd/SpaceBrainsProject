package com.spacebrains.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Generator {

    public static SimpleDateFormat S_DT_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public static SimpleDateFormat getDateFormat() {
        return S_DT_FORMAT;
    }

    public static String dateToString(Date dt) {
        return getDateFormat().format(dt);
    }
}
