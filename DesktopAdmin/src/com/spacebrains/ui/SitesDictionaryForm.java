package com.spacebrains.ui;

import com.spacebrains.model.Site;
import com.spacebrains.widgets.BaseEditForm;
import com.spacebrains.widgets.BaseTable;
import com.spacebrains.widgets.BaseWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SitesDictionaryForm extends BaseWindow {

    public SitesDictionaryForm() {
        super();

        editDialog = new BaseEditForm<>(new Site(""));

        ArrayList<Site> sites = new ArrayList<>();
        sites.add(new Site(1, "lenta.ru"));
        sites.add(new Site(2, "yandex.ru"));
        sites.add(new Site(3, "goodle.ru"));

        BaseTable table = new BaseTable("Сайты", sites);
        table.getAddBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Add new site");
                editDialog = new BaseEditForm<>(new Site(""));
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
