package com.spacebrains.ui;

import com.spacebrains.core.AppController;
import com.spacebrains.core.util.BaseParams;
import com.spacebrains.model.Site;
import com.spacebrains.widgets.base.BaseTable;
import com.spacebrains.widgets.base.BaseWindow;
import com.spacebrains.widgets.SiteEditForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import static com.spacebrains.core.util.BaseParams.APP_NAME;
import static com.spacebrains.core.util.BaseParams.SITES_DICT;

/**
 * @author Tatyana Vorobeva
 */
public class SitesDictionaryForm extends BaseWindow {
    BaseTable table = null;

    public SitesDictionaryForm() {
        super(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        windowTitle = APP_NAME + ": " + SITES_DICT;
        JFrame currentFrame = this;

        JLabel label = new JLabel("Справочник \"" + SITES_DICT + "\"");
        label.setFont(BaseParams.BASE_LABEL_FONT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        table = new BaseTable(AppController.getInstance().getSites());
        table.getAddBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Add new site");
                editDialog = new SiteEditForm(new Site(""));
                editDialog.setVisible(true);
                table.updateValues(AppController.getInstance().getSites());
            }
        });
        table.getEditBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editDialog = new SiteEditForm((Site) table.getSelectedItem());
                editDialog.setVisible(true);
                table.updateValues(AppController.getInstance().getSites());
            }
        });
        table.getDeleteBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedItem() != null) {
                    int userChoice = getDeleteConfirmation(currentFrame, table.getSelectedItem().getName());

                    if (userChoice == JOptionPane.YES_OPTION) {
                        System.out.println("Delete: " + table.getSelectedItem());
                        AppController.getInstance().deleteSite((Site) table.getSelectedItem());
                        table.updateValues(AppController.getInstance().getSites());
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
        if (table != null && wasAlreadyOpenedBefore) table.updateValues(AppController.getInstance().getSites());
        wasAlreadyOpenedBefore = true;
    }
}
