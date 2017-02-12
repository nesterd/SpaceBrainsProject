package com.spacebrains.ui.panels;

import com.spacebrains.core.AppController;

import javax.swing.*;
import java.awt.*;

import static com.spacebrains.core.util.BaseParams.getBaseFont;

/**
 * @author Tatyana Vorobeva
 */
public class MainWindowPane extends BasePane {

    private JLabel greetings = new JLabel();

    public MainWindowPane() {
        super();
        setVisible(true);
        setLayout(new GridBagLayout());

        refreshGreetings();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;
        add(greetings, gbc);
    }

    public void refreshGreetings() {
        greetings.setText("Здравствуйте, " + AppController.getInstance().getCurrentUserLogin());
        greetings.setFont(getBaseFont(Font.BOLD, 20));
        greetings.updateUI();
    }

    @Override
    public void refreshData() {
        System.out.println("[MainWindowPane] Active");
        refreshGreetings();
    }
}
