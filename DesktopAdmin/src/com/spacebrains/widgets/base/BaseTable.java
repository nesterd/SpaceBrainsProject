package com.spacebrains.widgets.base;

import com.spacebrains.core.util.BaseParams;
import com.spacebrains.interfaces.INamed;

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

public class BaseTable extends JPanel {

    private JTable table;
    private NamedTableModel tableModel;
    private TableRowSorter<NamedTableModel> sorter;

    private JLabel filterLabel;
    private JTextField filterField;

    private JScrollPane jScroll;
    private ArrayList<? extends INamed> values;
    private final FormattedButton addBtn = new FormattedButton("Добавить");
    private final FormattedButton editBtn = new FormattedButton("Изменить");
    private final FormattedButton deleteBtn = new FormattedButton("Удалить");
    private GridBagConstraints gbc = new GridBagConstraints();

    public BaseTable(ArrayList<? extends INamed> values) {
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
        gbc.gridwidth = 3; // сколько столбцов занимает элемент

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
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 5, 10, 0);
        add(addBtn, gbc);
        gbc.insets = new Insets(10, 5, 10, 0);
        add(editBtn, gbc);
        gbc.insets = new Insets(10, 5, 10, 5);
        add(deleteBtn, gbc);

        setMinimumSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
        setAlignmentX(CENTER_ALIGNMENT);
    }

    protected void prepareTable() {
        tableModel = new NamedTableModel(values);
        table = new JTable(tableModel);
    }

    public FormattedButton getAddBtn() {
        return addBtn;
    }

    public FormattedButton getEditBtn() {
        return editBtn;
    }

    public FormattedButton getDeleteBtn() {
        return deleteBtn;
    }

    public INamed getSelectedItem() {
        try {
            if (values.size() == 0) return null;
            else return values.get(table.convertRowIndexToModel(table.getSelectedRow()));
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void updateValues(ArrayList<? extends INamed> values) {
        this.values = values;
        tableModel = new NamedTableModel(values);
        table.setModel(tableModel);
        sorter = new TableRowSorter<> (tableModel);
        table.setRowSorter(sorter);

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
        table.setPreferredScrollableViewportSize(new Dimension(TABLE_WIDTH - 20, TABLE_HEIGHT ));
    }

    private void refreshSorter() {
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
