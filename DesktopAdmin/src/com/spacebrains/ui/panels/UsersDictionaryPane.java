package com.spacebrains.ui.panels;

import com.spacebrains.core.AppController;
import com.spacebrains.core.util.BaseParams;
import com.spacebrains.model.Role;
import com.spacebrains.model.User;
import com.spacebrains.widgets.UserEditForm;
import com.spacebrains.widgets.UsersTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.spacebrains.core.util.BaseParams.*;

/**
 * @author Tatyana Vorobeva
 */
public class UsersDictionaryPane extends BasePane {

    UsersTable table = null;
    private UserEditForm editDialog = null;
    private JLabel dictLabel;

    public UsersDictionaryPane() {
        super(DEFAULT_WIDTH + 200, DEFAULT_HEIGHT);
        windowTitle = APP_NAME + ": " + KEYWORDS_DICT;
        JPanel currentPanel = this;

        dictLabel = new JLabel();
        setDictLabelText();
        dictLabel.setFont(BaseParams.BASE_LABEL_FONT);
        dictLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        table = new UsersTable(AppController.getInstance().getUsers());
        table.getAddBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editDialog = new UserEditForm(new User(0, "", "", "", "", getAvailableUserRole()));
                editDialog.setVisible(true);
                table.updateValues(AppController.getInstance().getUsers());
            }
        });
        table.getEditBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = (User) table.getSelectedItem();
                if (user == null) user = new User(0, "", "", "", "", getAvailableUserRole());
                editDialog = new UserEditForm(user);
                editDialog.setVisible(true);
                table.updateValues(AppController.getInstance().getUsers());
            }
        });
        table.getDeleteBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedItem() != null) {
                    int userChoice = getDeleteConfirmation(currentPanel, table.getSelectedItem().getName());

                    if (userChoice == JOptionPane.YES_OPTION) {
                        System.out.println("Delete: " + table.getSelectedItem());
                        AppController.getInstance().deleteUser(table.getSelectedItem());
                        table.updateValues(AppController.getInstance().getUsers());
                    }
                }
            }
        });

        content.add(new JLabel(" "));
        content.add(dictLabel);
        content.add(table);

        setVisible(true);
    }

    private void setDictLabelText() {
        dictLabel.setText("Справочник \""
                + (AppController.getInstance().getCurrentUserRole().equals(Role.SUPER_ADMIN) ? ADMINS_DICT : USERS_DICT) + "\"");
    }

    private Role getAvailableUserRole() {
        Role role = null;
        if (AppController.getInstance().getCurrentUserRole().equals(Role.SUPER_ADMIN)) role = Role.ADMIN;
        if (AppController.getInstance().getCurrentUserRole().equals(Role.ADMIN)) role = Role.USER;
        return role;
    }

    @Override
    public void refreshData() {
        System.out.println("[UsersDictionaryPane] Active");
        if (wasAlreadyOpenedBefore) {
            setDictLabelText();
            if (table != null) table.updateValues(AppController.getInstance().getUsers());
        }
        wasAlreadyOpenedBefore = true;
    }
}
