package com.spacebrains.ui;

import com.spacebrains.interfaces.IKeywords;
import com.spacebrains.model.Keyword;
import com.spacebrains.rest.KeywordsRestMock;
import com.spacebrains.widgets.BaseEditForm;
import com.spacebrains.widgets.BaseTable;
import com.spacebrains.widgets.BaseWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KeywordsDictionaryForm extends BaseWindow {

    IKeywords rest = new KeywordsRestMock();

    public KeywordsDictionaryForm() {
        super();
        JFrame currentFrame = this;

        editDialog = new BaseEditForm<>(rest, new Keyword(""));

        BaseTable table = new BaseTable(rest.getKeywords(null));
        table.getAddBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editDialog = new BaseEditForm<>(rest, new Keyword(""));
                editDialog.setVisible(true);
                table.updateValues(rest.getKeywords(null));
            }
        });
        table.getEditBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editDialog = new BaseEditForm<>(rest, table.getSelectedItem());
                table.updateValues(rest.getKeywords(null));
            }
        });
        table.getDeleteBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userChoice = getDeleteConfirmation(currentFrame, table.getSelectedItem().getName());

                if (userChoice == JOptionPane.YES_OPTION) {
                    System.out.println("Delete: " + table.getSelectedItem());
                    rest.delete((Keyword) table.getSelectedItem());
                    table.updateValues(rest.getKeywords(null));
                }
            }
        });

        content.add(table);

        setVisible(true);
    }
}
