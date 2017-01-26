package com.spacebrains.core.util;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

public abstract class BaseParams {
    public static final String BaseURL = "echo.jsontest.com";

    public static final String APP_NAME = "SpaceBrains";
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
