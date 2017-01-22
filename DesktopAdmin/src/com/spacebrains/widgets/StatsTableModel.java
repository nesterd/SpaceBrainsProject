package com.spacebrains.widgets;

import com.spacebrains.model.CrawlerStats;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class StatsTableModel extends AbstractTableModel {

    public static final int SITE = 0;
    public static final int LINKS_COUNT = 1;
    public static final int NEW_LINKS = 2;
    public static final int PROCESSED = 3;
    private ArrayList<CrawlerStats> values;

    public StatsTableModel(ArrayList<CrawlerStats> values) {
        this.values = values;
    }

    public int getColumnCount() {
        return 4;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case SITE: return "Сайт";
            case LINKS_COUNT: return "Всего ссылок";
            case NEW_LINKS: return "Новых";
            case PROCESSED: return "Обработанных";
            default : return "Сайт";
        }
    }

    public int getRowCount() {
        return values.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case SITE  : return values.get(rowIndex).getSite().getName();
            case LINKS_COUNT  : return values.get(rowIndex).getLinksCount();
            case NEW_LINKS  : return values.get(rowIndex).getNewLinks();
            case PROCESSED  : return values.get(rowIndex).getProcessedLinks();
            default : return "";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }
}
