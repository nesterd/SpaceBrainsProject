package com.spacebrains.ui;

import com.spacebrains.interfaces.IPersons;
import com.spacebrains.model.Person;
import com.spacebrains.rest.PersonsRestMock;
import com.spacebrains.util.BaseParams;
import com.spacebrains.widgets.BaseEditForm;
import com.spacebrains.widgets.BaseTable;
import com.spacebrains.widgets.BaseWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrawlerStatsForm extends BaseWindow {

    IPersons rest = PersonsRestMock.getInstance();

    public CrawlerStatsForm() {
        super(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        JFrame currentFrame = this;

        JLabel label = new JLabel("Статистика Краулера");
        label.setFont(BaseParams.BASE_LABEL_FONT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        BaseTable table = new BaseTable(rest.getPersons());
        table.getAddBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editDialog = new BaseEditForm<>(rest, new Person(""));
                editDialog.setVisible(true);
                table.updateValues(rest.getPersons());
            }
        });
        table.getEditBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editDialog = new BaseEditForm<>(rest, table.getSelectedItem());
                editDialog.setVisible(true);
                table.updateValues(rest.getPersons());
            }
        });
        table.getDeleteBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedItem() != null) {
                    int userChoice = getDeleteConfirmation(currentFrame, table.getSelectedItem().getName());

                    if (userChoice == JOptionPane.YES_OPTION) {
                        System.out.println("Delete: " + table.getSelectedItem());
                        rest.delete((Person) table.getSelectedItem());
                        table.updateValues(rest.getPersons());
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
