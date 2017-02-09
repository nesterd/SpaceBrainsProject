package com.spacebrains.widgets.base;

import com.spacebrains.model.User;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class UsersTableModel extends AbstractTableModel {

    public static final int NAME = 0;
    public static final int LOGIN = 1;
    public static final int EMAIL = 2;
    private ArrayList<User> values;

    public UsersTableModel(ArrayList<User> values) {
        this.values = values;
    }

    public int getColumnCount() {
        return 3;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case NAME: return "Имя";
            case LOGIN: return "Логин";
            case EMAIL: return "E-mail";
            default : return "Имя";
        }
    }

    public int getRowCount() {
        return values.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case NAME  : return values.get(rowIndex).getName();
            case LOGIN  : return values.get(rowIndex).getLogin();
            case EMAIL  : return values.get(rowIndex).getEmail();
            default : return "";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }
}
