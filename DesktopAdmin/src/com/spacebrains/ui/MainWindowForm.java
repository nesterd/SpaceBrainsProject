package com.spacebrains.ui;

import com.spacebrains.core.AppController;
import com.spacebrains.widgets.base.BaseWindow;

import javax.swing.*;

import java.awt.*;

import static com.spacebrains.core.util.BaseParams.getBaseFont;

/**
 * @author Tatyana Vorobeva
 */
public class MainWindowForm extends BaseWindow {

    private JLabel greetings = new JLabel();

    public MainWindowForm() {
        super();
        setVisible(true);

        refreshGreetings();
        add(greetings);
    }

    public void refreshGreetings() {
        greetings.setText("Здравствуйте, " + AppController.getInstance().getCurrentUserLogin());
        greetings.setFont(getBaseFont(Font.BOLD, 20));
        greetings.setHorizontalAlignment(SwingConstants.CENTER);
        greetings.updateUI();
    }
}
