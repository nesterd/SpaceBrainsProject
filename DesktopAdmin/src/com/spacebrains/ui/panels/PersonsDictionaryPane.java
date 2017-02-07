package com.spacebrains.ui.panels;

import com.spacebrains.core.AppController;
import com.spacebrains.core.util.BaseParams;
import com.spacebrains.model.Person;
import com.spacebrains.widgets.PersonEditForm;
import com.spacebrains.widgets.base.BaseTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.spacebrains.core.util.BaseParams.APP_NAME;
import static com.spacebrains.core.util.BaseParams.PERSONS_DICT;

/**
 * @author Tatyana Vorobeva
 */
public class PersonsDictionaryPane extends BasePane {

    BaseTable table = null;

    public PersonsDictionaryPane() {
        super(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        windowTitle = APP_NAME + ": " + PERSONS_DICT;
        JPanel currentPanel = this;

        JLabel label = new JLabel("Справочник \"" + PERSONS_DICT + "\"");
        label.setFont(BaseParams.BASE_LABEL_FONT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        table = new BaseTable(AppController.getInstance().getPersons());
        table.getAddBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editDialog = new PersonEditForm(new Person(""));
                editDialog.setVisible(true);
                table.updateValues(AppController.getInstance().getPersons());
            }
        });
        table.getEditBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editDialog = new PersonEditForm((Person) table.getSelectedItem());
                editDialog.setVisible(true);
                table.updateValues(AppController.getInstance().getPersons());
            }
        });
        table.getDeleteBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedItem() != null) {
                    int userChoice = getDeleteConfirmation(currentPanel, table.getSelectedItem().getName());

                    if (userChoice == JOptionPane.YES_OPTION) {
                        System.out.println("Delete: " + table.getSelectedItem());
                        AppController.getInstance().deletePerson((Person) table.getSelectedItem());
                        table.updateValues(AppController.getInstance().getPersons());
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
    public void refreshData() {
        System.out.println("[PersonsDictionaryPane] Active");
        if (table != null && wasAlreadyOpenedBefore) table.updateValues(AppController.getInstance().getPersons());
        wasAlreadyOpenedBefore = true;
    }
}
