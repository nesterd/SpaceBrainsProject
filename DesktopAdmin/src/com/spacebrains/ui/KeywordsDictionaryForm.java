package com.spacebrains.ui;

import com.spacebrains.model.Keyword;
import com.spacebrains.model.Person;
import com.spacebrains.widgets.BaseEditForm;
import com.spacebrains.widgets.BaseTable;
import com.spacebrains.widgets.BaseWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class KeywordsDictionaryForm extends BaseWindow {

    public KeywordsDictionaryForm() {
        super();

        editDialog = new BaseEditForm<>(new Keyword(""));

        ArrayList<Keyword> keywords = new ArrayList<>();
        keywords.add(new Keyword(1, "Путин"));
        keywords.add(new Keyword(2, "Путину"));
        keywords.add(new Keyword(3, "Путина"));
        keywords.add(new Keyword(4, "Захарова"));
        keywords.add(new Keyword(5, "Захаровой"));
        keywords.add(new Keyword(6, "Чуркин"));
        keywords.add(new Keyword(7, "Чуркину"));
        keywords.add(new Keyword(8, "Чуркина"));
        keywords.add(new Keyword(9, "Яровая"));
        keywords.add(new Keyword(10, "Миронову"));

        BaseTable table = new BaseTable(keywords);
        table.getAddBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Add new keyword");
                editDialog = new BaseEditForm<>(new Person(""));
                editDialog.setVisible(true);
            }
        });
        table.getEditBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Edit: " + table.getSelectedItem());
                editDialog = new BaseEditForm<>(table.getSelectedItem());
                editDialog.setVisible(true);
            }
        });
        table.getDeleteBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Delete: " + table.getSelectedItem());
            }
        });

        content.add(table);

        setVisible(true);
    }
}
