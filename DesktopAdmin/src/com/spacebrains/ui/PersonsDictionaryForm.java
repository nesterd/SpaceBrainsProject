package com.spacebrains.ui;

import com.spacebrains.interfaces.IPersons;
import com.spacebrains.model.Person;
import com.spacebrains.core.rest.PersonsRestMock;
import com.spacebrains.core.util.BaseParams;
import com.spacebrains.widgets.BaseEditForm;
import com.spacebrains.widgets.BaseTable;
import com.spacebrains.widgets.BaseWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class PersonsDictionaryForm extends BaseWindow {

    IPersons rest = PersonsRestMock.getInstance();
    BaseTable table = null;

    public PersonsDictionaryForm() {
        super(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        JFrame currentFrame = this;

        JLabel label = new JLabel("Справочник \"Личности\"");
        label.setFont(BaseParams.BASE_LABEL_FONT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        table = new BaseTable(rest.getPersons());
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

    @Override
    public void windowActivated(WindowEvent e) {
        super.windowActivated(e);
        if (table != null) table.updateValues(rest.getPersons());
    }
}
