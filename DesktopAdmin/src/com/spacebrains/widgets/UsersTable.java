package com.spacebrains.widgets;

import com.spacebrains.core.util.BaseParams;
import com.spacebrains.model.User;
import com.spacebrains.widgets.base.UsersTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static com.spacebrains.core.util.BaseParams.*;

public class UsersTable extends JPanel {

    protected JTable table;
    private UsersTableModel tableModel;
    private TableRowSorter<UsersTableModel> sorter;

    protected JLabel filterLabel;
    protected JTextField filterField;

    protected JScrollPane jScroll;
    protected ArrayList<User> values;
    protected final com.spacebrains.widgets.base.Button addBtn = new com.spacebrains.widgets.base.Button("Добавить");
    protected final com.spacebrains.widgets.base.Button editBtn = new com.spacebrains.widgets.base.Button("Изменить");
    protected final com.spacebrains.widgets.base.Button deleteBtn = new com.spacebrains.widgets.base.Button("Удалить");
    protected GridBagConstraints gbc = new GridBagConstraints();

    public UsersTable(ArrayList<User> values) {
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

        addBtn.setFont(BaseParams.BASE_BTN_FONT);
        editBtn.setFont(BaseParams.BASE_BTN_FONT);
        deleteBtn.setFont(BaseParams.BASE_BTN_FONT);

        prepareTable();
        drawTable();

        int row = 0;
        gbc.gridx = 0; // столбец
        gbc.gridy = row;
        gbc.gridwidth = 4; // сколько столбцов занимает элемент

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

        gbc.gridy = ++row;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, addBtn.getMinimumSize().width - 10, 10, 0);
        add(addBtn, gbc);
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 5, 10, 0);
        add(editBtn, gbc);
        gbc.insets = new Insets(10, 5, 10, addBtn.getMinimumSize().width - 10);
        add(deleteBtn, gbc);

        setMinimumSize(new Dimension(TABLE_WIDTH + 90, TABLE_HEIGHT));
        setAlignmentX(CENTER_ALIGNMENT);
    }

    protected void prepareTable() {
        tableModel = new UsersTableModel(values);
        table = new JTable(tableModel);
    }

    public com.spacebrains.widgets.base.Button getAddBtn() {
        return addBtn;
    }

    public com.spacebrains.widgets.base.Button getEditBtn() {
        return editBtn;
    }

    public com.spacebrains.widgets.base.Button getDeleteBtn() {
        return deleteBtn;
    }

    public User getSelectedItem() {
        try {
            if (values.size() == 0) return null;
            else return values.get(table.convertRowIndexToModel(table.getSelectedRow()));
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void updateValues(ArrayList<User> values) {
        this.values = values;
        tableModel = new UsersTableModel(values);
        table.setModel(tableModel);
        sorter = new TableRowSorter<> (tableModel);
        table.setRowSorter(sorter);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

            if (i != 2) {
                table.getColumnModel().getColumn(i).setPreferredWidth(160);
                table.getColumnModel().getColumn(i).setMaxWidth(200);
            } else table.getColumnModel().getColumn(i).setMinWidth(190);
        }

        jScroll.updateUI();
        table.updateUI();
        table.getSelectionModel().setSelectionInterval(0, 0);
    }

    protected void drawTable() {
        table.setFont(BaseParams.BASE_TABLE_FONT);
        table.setRowHeight(22);

        table.getTableHeader().setFont(BASE_TABLE_HEADER_FONT);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setColumnSelectionAllowed(false);
        table.getSelectionModel().setSelectionInterval(0, 0);

        refreshSorter();

        // edit record on double click
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    editBtn.doClick();
                }
            }
        });

        jScroll = new JScrollPane(table);
        jScroll.createVerticalScrollBar();
        jScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        table.setPreferredScrollableViewportSize(new Dimension(TABLE_WIDTH + 200, TABLE_HEIGHT ));
    }

    protected void refreshSorter() {
        sorter = new TableRowSorter<> (tableModel);
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
    }
}
