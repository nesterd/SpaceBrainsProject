package com.spacebrains.widgets;

import com.spacebrains.model.CrawlerStats;
import com.spacebrains.core.util.BaseParams;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import static com.spacebrains.core.util.BaseParams.*;

public class StatsTable extends JPanel {

    private JTable table;
    private StatsTableModel tableModel;

    private JLabel filterLabel;
    private JTextField filterField;

    private JScrollPane jScroll;
    ArrayList<CrawlerStats> values;
    private GridBagConstraints gbc = new GridBagConstraints();

    public StatsTable(ArrayList<CrawlerStats> values) {
        super();
        this.values = values;
        setLayout(new GridBagLayout());

        Box filterBox = Box.createHorizontalBox();

        filterLabel = new JLabel("Фильтр: ");
        filterLabel.setFont(BaseParams.BASE_TABLE_FONT);
        filterLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        filterField = new JTextField();
        filterField.setPreferredSize(new Dimension(100, 19));

        filterBox.add(filterLabel);
        filterBox.add(filterField);

        tableModel = new StatsTableModel(values);
        table = new JTable(tableModel);
        drawTable();

        int row = 0;
        gbc.gridx = 0;
        gbc.gridy = row; // столбец
//        gbc.gridwidth = 3; // сколько столбцов занимает элемент

        // "лишнее" пространство оставлять пустым
        gbc.weightx = 0;
        gbc.weighty = 0;

        gbc.fill = GridBagConstraints.HORIZONTAL; // заполнять по горизонтали
        gbc.anchor = GridBagConstraints.BELOW_BASELINE; // привязка к центру

        gbc.insets = new Insets(5, 5, 0, 5);// отступы
        gbc.ipadx = 5;
        gbc.ipady = 5;

        add(filterBox, gbc);
        gbc.gridy = ++row;
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

        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setColumnSelectionAllowed(false);
        table.getSelectionModel().setSelectionInterval(0, 0);

        TableRowSorter<StatsTableModel> sorter = new TableRowSorter<> (tableModel);
        table.setRowSorter(sorter);

        filterField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                String expr = filterField.getText().replaceAll(FILTER_EXCLUDE, "....");
                sorter.setRowFilter(RowFilter.regexFilter(expr));
                sorter.setSortKeys(null);
            }
        });

        jScroll = new JScrollPane(table);
        jScroll.createVerticalScrollBar();
        jScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        table.setPreferredScrollableViewportSize(new Dimension(TABLE_WIDTH + 180, TABLE_HEIGHT ));
    }
}
