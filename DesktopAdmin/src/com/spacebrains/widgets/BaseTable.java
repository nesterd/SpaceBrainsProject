package com.spacebrains.widgets;

import com.spacebrains.interfaces.INamed;
import com.spacebrains.util.BaseParams;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

import static com.spacebrains.util.BaseParams.BASE_DICT_LABEL_FONT;
import static com.spacebrains.util.BaseParams.BASE_TABLE_HEADER_FONT;

public class BaseTable extends JPanel {

    private final int TABLE_WIDTH = 300;
    private final int TABLE_HEIGHT = 197;

    private JTable table;
    ArrayList<? extends INamed> values;
    private final Button addBtn = new Button("Добавить");
    private final Button editBtn = new Button("Изменить");
    private final Button deleteBtn = new Button("Удалить");

    public BaseTable(String name, ArrayList<? extends INamed> values) {
        super();
        this.values = values;
        Box paneContent = Box.createVerticalBox();
        paneContent.setAlignmentY(CENTER_ALIGNMENT);

        JLabel jLabel = new JLabel("Справочник \"" + name + "\"");
        jLabel.setFont(BASE_DICT_LABEL_FONT);
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        paneContent.add(jLabel);
        paneContent.add(new JLabel(" "));

        table = new JTable(new NamedTableModel(values));
        table.setFont(BaseParams.BASE_TABLE_FONT);
        table.setRowHeight(22);

        table.getTableHeader().setFont(BASE_TABLE_HEADER_FONT);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setAutoCreateRowSorter(true);

        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        table.getSelectionModel().setSelectionInterval(0, 0);

        JScrollPane jScroll = new JScrollPane(table);

        jScroll.createVerticalScrollBar();
        jScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        table.setPreferredScrollableViewportSize(new Dimension(TABLE_WIDTH - 20, TABLE_HEIGHT ));

        paneContent.add(jScroll);
        paneContent.add(new JLabel(" "));

        Box buttons = Box.createHorizontalBox();
        buttons.add(addBtn);
        buttons.add(editBtn);
        buttons.add(deleteBtn);
        paneContent.add(buttons);

        add(paneContent);
        setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
        setAlignmentX(CENTER_ALIGNMENT);
    }

    public Button getAddBtn() {
        return addBtn;
    }

    public Button getEditBtn() {
        return editBtn;
    }

    public Button getDeleteBtn() {
        return deleteBtn;
    }

    public INamed getSelectedItem() {
        if (values.size() == 0) return null;
        else return values.get(table.convertRowIndexToModel(table.getSelectedRow()));
    }
}
