package com.spacebrains.widgets;

import com.spacebrains.interfaces.INamed;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class NamedTableModel extends AbstractTableModel {

    private ArrayList<? extends INamed> values;

    public NamedTableModel(ArrayList<? extends INamed> values) {
        this.values = values;
    }

    public int getColumnCount() {
        return 1;
    }

    public String getColumnName(int columnIndex) {
        return "Наименование";
    }

    public int getRowCount() {
        return values.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return values.get(rowIndex).getName();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }
}
