package com.spacebrains.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Generator {

    public static SimpleDateFormat S_DT_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private static Random rand = new Random(); // генератор случайных чисел

    public static SimpleDateFormat getDateFormat() {
        return S_DT_FORMAT;
    }

    public static String dateToString(Date dt) {
        return getDateFormat().format(dt);
    }

    public static Random getRnd() {
        return rand;
    }
}
