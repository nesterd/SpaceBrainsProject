package com.spacebrains.widgets;

import com.spacebrains.model.CrawlerStats;
import com.spacebrains.core.util.BaseParams;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

import static com.spacebrains.core.util.BaseParams.*;

public class StatsTable extends JPanel {

    private JTable table;
    private JScrollPane jScroll;
    ArrayList<CrawlerStats> values;
    private GridBagConstraints gbc = new GridBagConstraints();

    public StatsTable(ArrayList<CrawlerStats> values) {
        super();
        this.values = values;
        setLayout(new GridBagLayout());

        table = new JTable(new StatsTableModel(values));
        drawTable();

        int row = 0;
        gbc.gridx = row;
        gbc.gridy = 0; // столбец
        gbc.gridwidth = 3; // сколько столбцов занимает элемент

        // "лишнее" пространство оставлять пустым
        gbc.weightx = 0;
        gbc.weighty = 0;

        gbc.fill = GridBagConstraints.HORIZONTAL; // заполнять по горизонтали
        gbc.anchor = GridBagConstraints.BELOW_BASELINE; // привязка к центру

        gbc.insets = new Insets(5, 5, 10, 5);// отступы
        gbc.ipadx = 5;
        gbc.ipady = 5;

        add(jScroll, gbc);

        setMinimumSize(new Dimension(TABLE_WIDTH + 200, TABLE_HEIGHT));
        setAlignmentX(CENTER_ALIGNMENT);
    }

    public void updateValues(ArrayList<CrawlerStats> values) {
        this.values = values;
        table.setModel(new StatsTableModel(values));
        jScroll.updateUI();
        table.updateUI();
        table.getSelectionModel().setSelectionInterval(0, 0);
    }

    private void drawTable() {
        table.setFont(BaseParams.BASE_TABLE_FONT);
        table.setRowHeight(22);

        table.getTableHeader().setFont(BASE_TABLE_HEADER_FONT);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setAutoCreateRowSorter(true);

        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setColumnSelectionAllowed(false);
        table.getSelectionModel().setSelectionInterval(0, 0);

        jScroll = new JScrollPane(table);
        jScroll.createVerticalScrollBar();
        jScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        table.setPreferredScrollableViewportSize(new Dimension(TABLE_WIDTH + 180, TABLE_HEIGHT ));
    }
}
