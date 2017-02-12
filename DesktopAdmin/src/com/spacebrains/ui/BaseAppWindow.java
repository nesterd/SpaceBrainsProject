package com.spacebrains.ui;

import com.spacebrains.core.AppController;
import com.spacebrains.core.util.BaseParams;
import com.spacebrains.ui.panels.BasePane;
import com.spacebrains.widgets.AppMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import static com.spacebrains.core.util.BaseParams.setDefaultFont;

public class BaseAppWindow extends JFrame implements WindowListener {

    protected String windowTitle = BaseParams.APP_NAME;

    private AppMenu menu;

    protected static final int DEFAULT_WIDTH = 550;
    protected static final int DEFAULT_HEIGHT = 485;

    protected static int currentWidth = DEFAULT_WIDTH;
    protected static int currentHeight = DEFAULT_HEIGHT;

    private Container contain;

    public BaseAppWindow() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public BaseAppWindow(int width, int height) {
        initMainSettings(width, height);
        setDefaultFont();
        initMainMenu();

        addWindowListener(this);
    }

    private void initMainSettings(int width, int height) {
        this.currentWidth = width;
        this.currentHeight = height;
        setTitle(windowTitle);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        resizeWindow(width, height);

        // Приводим внешний вид элементов к виду как в системе пользователя (например соответствующий теме windows)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {}

        setResizable(false);
        setVisible(true);
    }

    private void resizeWindow(int width, int height) {
        // смотрим размер экрана и размещаем окно чата в центре
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - width) / 2, (screenSize.height - height) / 2, width, height);
    }

    public void switchContentPane(BasePane newContentPane, int width, int height) {
        currentWidth = width;
        currentHeight = height;
        contain = getContentPane();
        for (Component component : contain.getComponents()) {
            component.setVisible(false);
        }
        contain.removeAll();

        contain.add(newContentPane);
        newContentPane.refreshData();
        newContentPane.setVisible(true);
        contain.validate();

        setTitle(newContentPane.getWindowTitle());

        resizeWindow(width, height);
        setVisible(true);
    }

    public void hideMenu(boolean needToHide) {
        if (!needToHide) {
            menu.switchForUserRights();
        }
        menu.setVisible(!needToHide);
    }

    private void initMainMenu() {
        menu = new AppMenu();
        setJMenuBar(menu);

        menu.getMiDictsPersons().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PaneManager.switchToPersonsDictionaryPane();
            }
        });

        menu.getMiDictsKeywords().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PaneManager.switchToKeywordsDictionaryPane();
            }
        });

        menu.getMiDictsSites().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PaneManager.switchToSitesDictionaryPane();
            }
        });

        menu.getMiFileCrawlerStats().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PaneManager.switchToCrawlerStatsForm();
            }
        });

        menu.getMiFileChangePswd().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PaneManager.switchToChangePswdPane();
            }
        });

        menu.getMiFileLogout().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppController.getInstance().logout();
                PaneManager.switchToAuthPane();
            }
        });

        menu.getMiDictsUsers().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PaneManager.switchToUsersDictionaryPane();
            }
        });
    }

    @Override
    public void windowClosing(WindowEvent e) {}

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {
        initMainSettings(currentWidth, currentHeight);
    }

    public static Dimension getCurrentDimension() {
        return new Dimension(currentWidth - 20, currentHeight - 20);
    }
}
