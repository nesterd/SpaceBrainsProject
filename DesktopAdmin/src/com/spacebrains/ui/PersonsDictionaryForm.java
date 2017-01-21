package com.spacebrains.ui;

import com.spacebrains.interfaces.IPersons;
import com.spacebrains.model.Person;
import com.spacebrains.rest.PersonsRestMock;
import com.spacebrains.widgets.BaseEditForm;
import com.spacebrains.widgets.BaseTable;
import com.spacebrains.widgets.BaseWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonsDictionaryForm extends BaseWindow {

    IPersons rest = new PersonsRestMock();

    public PersonsDictionaryForm() {
        super();
        JFrame currentFrame = this;

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
                int userChoice = getDeleteConfirmation(currentFrame, table.getSelectedItem().getName());

                if (userChoice == JOptionPane.YES_OPTION) {
                    System.out.println("Delete: " + table.getSelectedItem());
                    rest.delete((Person) table.getSelectedItem());
                    table.updateValues(rest.getPersons());
                }
            }
        });

        content.add(table);

        setVisible(true);
    }
}
