package com.spacebrains.widgets;

import javax.swing.*;

public class AppMenu extends JMenuBar {

    private JMenu mFile;
    private JMenuItem miFileLogout;
    private JMenuItem miFileExit;

    private JMenu mDicts;
    private JMenuItem miDictsSites;
    private JMenuItem miDictsPersons;
    private JMenuItem miDictsKeywords;

    private JMenu mStats;
    private JMenuItem miStatsFull;
    private JMenuItem miStatsDaily;

    public static final String M_FILE = "Файл";
    public static final String M_LOGOUT = "Выход";
    public static final String MI_EXIT = "Закрыть";

    public static final String M_DICTS = "Справочники";
    public static final String MI_SITES = "Сайты";
    public static final String MI_PERSONS = "Личности";
    public static final String MI_KEYWORDS = "Ключевые слова";

    public static final String M_STATS = "Статистика";
    public static final String MI_STATS_FULL = "Общая";
    public static final String MI_STATS_DAILY = "Ежедневная";

    public AppMenu() {
        super();
        initMainMenu();
        initDictsMenu();
        initStatsMenu();
    }

    private void initMainMenu() {
        mFile = new JMenu(M_FILE);
        miFileLogout = new JMenuItem(M_LOGOUT);
        miFileExit = new JMenuItem(MI_EXIT);

        add(mFile);
        mFile.add(miFileLogout);
        mFile.addSeparator(); // разделительная линия
        mFile.add(miFileExit);

        miFileExit.addActionListener(e -> System.exit(0));
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

    private void initStatsMenu() {
        mStats = new JMenu(M_STATS);
        miStatsFull = new JMenuItem(MI_STATS_FULL);
        miStatsDaily = new JMenuItem(MI_STATS_DAILY);

        add(mStats);
        mStats.add(miStatsFull);
        mStats.add(miStatsDaily);
    }
}
