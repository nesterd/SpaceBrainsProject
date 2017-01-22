package com.spacebrains.widgets;

import com.spacebrains.interfaces.INamed;
import com.spacebrains.ui.FormsManager;
import com.spacebrains.util.BaseParams;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.spacebrains.util.BaseParams.ALERT_MSG;
import static com.spacebrains.util.BaseParams.setDefaultFont;

public abstract class BaseWindow extends JFrame {

    protected static final int DEFAULT_WIDTH = 550;
    protected static final int DEFAULT_HEIGHT = 435;

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
        initMainMenu();
    }

    private void initMainSettings(int width, int height) {
        this.width = width;
        this.height = height;
        setTitle(BaseParams.APP_NAME);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        content = Box.createVerticalBox();
        content.setAlignmentY(Component.CENTER_ALIGNMENT);

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

    private void initMainMenu() {
        AppMenu menu = new AppMenu();

        setJMenuBar(menu);

        JFrame currentWindow = this;

        menu.getMiDictsPersons().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentWindow.dispose();
                FormsManager.showPersonsDictionaryForm();
            }
        });

        menu.getMiDictsKeywords().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentWindow.dispose();
                FormsManager.showKeywordsDictionaryForm();
            }
        });

        menu.getMiDictsSites().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentWindow.dispose();
                FormsManager.showSitesDictionaryForm();
            }
        });

        menu.getMiFileCrawlerStats().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentWindow.dispose();
                FormsManager.showCrawlerStatsForm();
            }
        });

    }

    protected int getDeleteConfirmation(JFrame currentFrame, String objectName) {
        Object[] options = {"Да", "Нет"};
        return JOptionPane.showOptionDialog(currentFrame,
                "Вы хотите удалить элемент '" + objectName + "'?",
                ALERT_MSG,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     // без специальной иконки
                options,  // заголовки кнопок
                options[0]); // выбор по умолчанию
    }
}
