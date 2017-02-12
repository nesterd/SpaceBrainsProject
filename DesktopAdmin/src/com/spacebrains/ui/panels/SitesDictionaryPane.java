package com.spacebrains.ui.panels;

import com.spacebrains.core.AppController;
import com.spacebrains.core.util.BaseParams;
import com.spacebrains.model.Site;
import com.spacebrains.widgets.SiteEditForm;
import com.spacebrains.widgets.base.BaseTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.spacebrains.core.util.BaseParams.APP_NAME;
import static com.spacebrains.core.util.BaseParams.SITES_DICT;

/**
 * @author Tatyana Vorobeva
 */
public class SitesDictionaryPane extends BasePane {
    BaseTable table = null;

    public SitesDictionaryPane() {
        super(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        windowTitle = APP_NAME + ": " + SITES_DICT;
        JPanel currentPanel = this;

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
                    int userChoice = getDeleteConfirmation(currentPanel, table.getSelectedItem().getName());

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
        setVisible(false);
    }

    @Override
    public void refreshData() {
        System.out.println("[SitesDictionaryPane] Active");
        if (table != null && wasAlreadyOpenedBefore) table.updateValues(AppController.getInstance().getSites());
    }
}
