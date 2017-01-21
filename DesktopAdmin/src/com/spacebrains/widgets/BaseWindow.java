package com.spacebrains.widgets;

import com.spacebrains.interfaces.INamed;
import com.spacebrains.util.BaseParams;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

public class BaseWindow extends JFrame {

    protected static final int DEFAULT_WIDTH = 550;
    protected static final int DEFAULT_HEIGHT = 400;

    protected int width = DEFAULT_WIDTH;
    protected int height = DEFAULT_HEIGHT;

    protected Box content;
    protected BaseEditForm<INamed> editDialog;

    public BaseWindow() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public BaseWindow(int width, int height) {
        initMainSettings(width, height);
        setDefaultFont();
    }

    private void initMainSettings(int width, int height) {
        this.width = width;
        this.height = height;
        setTitle(BaseParams.APP_NAME);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        content = Box.createVerticalBox();

        // смотрим размер экрана и размещаем окно чата в центре
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - width) / 2, (screenSize.height - height) / 2, width, height);

        // Приводим внешний вид элементов к виду как в системе пользователя (например соответствующий теме windows)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {}

        add(content);
        setResizable(false);
    }

    private void setDefaultFont() {
        FontUIResource f = new FontUIResource(new Font("Monotype", 0, 18));
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
    }

}
