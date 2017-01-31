package com.spacebrains.core.util;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

public abstract class BaseParams {
    public static final String BaseURL = "93.174.131.56:5000";

    public static final String APP_NAME = "SpaceBrains";
    public static final String AUTHORIZATION = "Авторизация";
    public static final String SITES_DICT = "Сайты";
    public static final String PERSONS_DICT = "Личности";
    public static final String KEYWORDS_DICT = "Ключевые слова";
    public static final String CHANGE_PSWD = "Смена пароля";
    public static final String CRAWLER_STATS_WINDOW = "Статистика Краулера";

    public static final String ALERT_MSG = "Предупреждение";
    public static final int MAX_BTN_HEIGHT = 25;

    private static final String TIMES_NEW_ROMAN = "Times New Roman";
    private static final String MONOTYPE = "Monotype";
    private static final String COURIER_NEW = "Courier New";

    public static final Font BASE_LABEL_FONT = getBaseFont(Font.BOLD, 19);
    public static final Font BASE_BTN_FONT = getBaseFont(17);
    public static final Font BASE_OPTION_FONT = new Font(TIMES_NEW_ROMAN, Font.PLAIN, 17);

    public static final Font BASE_TABLE_HEADER_FONT = new Font(TIMES_NEW_ROMAN, Font.BOLD, 17);
    public static final Font BASE_TABLE_FONT = new Font(TIMES_NEW_ROMAN, Font.PLAIN, 17);

    public static final int TABLE_WIDTH = 350;
    public static final int TABLE_HEIGHT = 192;

    public static final Color DARK_GREEN = new Color(9, 156, 19);
    public static final Color DARK_RED = new Color(191, 19, 19);

    public static final String FILTER_EXCLUDE = "[*?+)(^:,'\"{}\\]\\[]|\\\\";

    public static Font getBaseFont(int size) {
        return getBaseFont(Font.PLAIN, size);
    }

    public static Font getBaseFont(int type, int size) {
        return new Font(COURIER_NEW, type, size);
    }

    public static void setDefaultFont() {
        FontUIResource f = new FontUIResource(new Font(MONOTYPE, Font.PLAIN, 15));
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                FontUIResource orig = (FontUIResource) value;
                Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
                UIManager.put(key, new FontUIResource(font));
            }
        }

        UIManager.put("OptionPane.messageFont", BASE_OPTION_FONT);
        UIManager.put("OptionPane.buttonFont", getBaseFont(17));
    }
}
