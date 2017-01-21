package com.spacebrains.ui;

import com.spacebrains.interfaces.ISites;
import com.spacebrains.model.Site;
import com.spacebrains.rest.SitesRestMock;
import com.spacebrains.util.BaseParams;
import com.spacebrains.widgets.BaseEditForm;
import com.spacebrains.widgets.BaseTable;
import com.spacebrains.widgets.BaseWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SitesDictionaryForm extends BaseWindow {

    ISites rest = SitesRestMock.getInstance();

    public SitesDictionaryForm() {
        super(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        JFrame currentFrame = this;

        JLabel label = new JLabel("Справочник \"Сайты\"");
        label.setFont(BaseParams.BASE_LABEL_FONT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        BaseTable table = new BaseTable(rest.getSites());
        table.getAddBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Add new site");
                editDialog = new BaseEditForm<>(rest, new Site(""));
                editDialog.setVisible(true);
                table.updateValues(rest.getSites());
            }
        });
        table.getEditBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editDialog = new BaseEditForm<>(rest, table.getSelectedItem());
                editDialog.setVisible(true);
                table.updateValues(rest.getSites());
            }
        });
        table.getDeleteBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedItem() != null) {
                    int userChoice = getDeleteConfirmation(currentFrame, table.getSelectedItem().getName());

                    if (userChoice == JOptionPane.YES_OPTION) {
                        System.out.println("Delete: " + table.getSelectedItem());
                        rest.delete((Site) table.getSelectedItem());
                        table.updateValues(rest.getSites());
                    }
                }
            }
        });

        content.add(new JLabel(" "));
        content.add(label);
        content.add(table);

        setVisible(true);
    }
}
