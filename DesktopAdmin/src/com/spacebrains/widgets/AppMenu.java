package com.spacebrains.widgets;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppMenu extends JMenuBar {

    private JMenu mFile;
    private JMenuItem miFileLogout;
    private JMenuItem miFileCrawlerStats;
    private JMenuItem miFileExit;

    private JMenu mDicts;
    private JMenuItem miDictsSites;
    private JMenuItem miDictsPersons;
    private JMenuItem miDictsKeywords;

    public static final String M_FILE = "Файл";
    public static final String MI_CRAWLER_STATS = "Статистика";
    public static final String MI_LOGOUT = "Выход";
    public static final String MI_EXIT = "Закрыть";

    public static final String M_DICTS = "Справочники";
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
        miFileCrawlerStats.setVisible(false);
        miFileLogout = new JMenuItem(MI_LOGOUT);
        miFileLogout.setEnabled(false);
        miFileExit = new JMenuItem(MI_EXIT);

        add(mFile);
        mFile.add(miFileCrawlerStats);
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
        miDictsSites = new JMenuItem(MI_SITES);
        miDictsPersons = new JMenuItem(MI_PERSONS);
        miDictsKeywords = new JMenuItem(MI_KEYWORDS);

        add(mDicts);
        mDicts.add(miDictsSites);
        mDicts.add(miDictsPersons);
        mDicts.add(miDictsKeywords);
    }

    public JMenuItem getMiFileCrawlerStats() {
        return miFileCrawlerStats;
    }

    public JMenuItem getMiFileLogout() {
        return miFileLogout;
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
}
