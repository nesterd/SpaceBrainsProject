package com.spacebrains.widgets;

import com.spacebrains.core.AppController;
import com.spacebrains.model.Role;

import javax.swing.*;
import java.awt.event.*;

/**
 * @author Tatyana Vorobeva
 */
public class AppMenu extends JMenuBar {

    private JMenu mFile;
    private JMenuItem miFileChangePswd;
    private JMenuItem miFileLogout;
    private JMenuItem miFileCrawlerStats;
    private JMenuItem miFileExit;

    private JMenu mDicts;
    private JMenuItem miDictsUsers;
    private JMenuItem miDictsSites;
    private JMenuItem miDictsPersons;
    private JMenuItem miDictsKeywords;

    public static final String M_FILE = "Файл";
    public static final String MI_CRAWLER_STATS = "Статистика";
    public static final String MI_CHANGE_PSWD = "Сменить пароль";
    public static final String MI_LOGOUT = "Выход";
    public static final String MI_EXIT = "Закрыть";

    public static final String M_DICTS = "Справочники";
    public static final String MI_USERS = "Пользователи";
    public static final String MI_ADMINS = "Администраторы";
    public static final String MI_SITES = "Сайты";
    public static final String MI_PERSONS = "Личности";
    public static final String MI_KEYWORDS = "Ключевые слова";

    public AppMenu() {
        super();
        initMainMenu();
        initDictsMenu();
    }

    private void initMainMenu() {
        mFile = new JMenu(M_FILE);
        miFileCrawlerStats = new JMenuItem(MI_CRAWLER_STATS);
        miFileChangePswd = new JMenuItem(MI_CHANGE_PSWD);
        miFileLogout = new JMenuItem(MI_LOGOUT);
        miFileExit = new JMenuItem(MI_EXIT);

        add(mFile);
        mFile.add(miFileCrawlerStats);
        mFile.add(miFileChangePswd);
        mFile.add(miFileLogout);
        mFile.addSeparator(); // разделительная линия
        mFile.add(miFileExit);

        miFileExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void initDictsMenu() {
        mDicts = new JMenu(M_DICTS);
        miDictsUsers = new JMenuItem(MI_USERS);
        miDictsSites = new JMenuItem(MI_SITES);
        miDictsPersons = new JMenuItem(MI_PERSONS);
        miDictsKeywords = new JMenuItem(MI_KEYWORDS);

        add(mDicts);
        mDicts.add(miDictsUsers);
        mFile.addSeparator(); // разделительная линия
        mDicts.add(miDictsSites);
        mDicts.add(miDictsPersons);
        mDicts.add(miDictsKeywords);
    }

    public JMenuItem getMiFileCrawlerStats() {
        return miFileCrawlerStats;
    }

    public JMenuItem getMiFileChangePswd() {
        return miFileChangePswd;
    }

    public JMenuItem getMiFileLogout() {
        return miFileLogout;
    }

    public JMenuItem getMiDictsUsers() {
        return miDictsUsers;
    }

    public JMenuItem getMiDictsSites() {
        return miDictsSites;
    }

    public JMenuItem getMiDictsPersons() {
        return miDictsPersons;
    }

    public JMenuItem getMiDictsKeywords() {
        return miDictsKeywords;
    }

    public void switchForUserRights() {
        Role userRole = AppController.getInstance().getCurrentUserRole();

        if (userRole.equals(Role.SUPER_ADMIN)) {
            miDictsUsers.setText(MI_ADMINS);
            miDictsKeywords.setEnabled(false);
            miDictsPersons.setEnabled(false);
            miDictsSites.setEnabled(false);
            miFileCrawlerStats.setEnabled(false);
        }
        if (userRole.equals(Role.ADMIN)) {
            miDictsUsers.setText(MI_USERS);
            miDictsKeywords.setEnabled(true);
            miDictsPersons.setEnabled(true);
            miDictsSites.setEnabled(true);
            miFileCrawlerStats.setEnabled(true);
        }

        miDictsKeywords.updateUI();
        miDictsPersons.updateUI();
        miDictsSites.updateUI();
        miDictsUsers.updateUI();
        mDicts.updateUI();

        miFileCrawlerStats.updateUI();
        mFile.updateUI();

        this.updateUI();
    }
}
