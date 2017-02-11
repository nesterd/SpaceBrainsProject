package com.spacebrains.ui.panels;

import com.spacebrains.core.AppController;
import com.spacebrains.core.AuthConstants;
import com.spacebrains.core.util.BaseParams;
import com.spacebrains.ui.PaneManager;
import com.spacebrains.widgets.UserEditForm;
import com.spacebrains.widgets.base.FieldLabel;
import com.spacebrains.widgets.base.FormattedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.spacebrains.core.util.BaseParams.*;

/**
 * @author Tatyana Vorobeva
 */
public class RestorePswdPane extends BasePane {

    private GridBagConstraints gbc = new GridBagConstraints();

    private JLabel mainLabel;
    private JLabel errorMsgLabel;
    private JTextField emailField;
    private FormattedButton sendBtn;
    private FormattedButton loginBtn;

    private final int FIELD_WIDTH = 140;
    private final int FIELD_HEIGHT = 18;

    private final KeyListener ENTER_KEY_LISTENER = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) tryToRestorePswd();
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    };
    private final ActionListener RESTORE_ACTION_LISTENER = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            tryToRestorePswd();
        }
    };
    private final ActionListener LOGIN_ACTION_LISTENER = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            PaneManager.switchToAuthPane();
        }
    };

    public RestorePswdPane() {
        super();
        windowTitle = BaseParams.APP_NAME + ": " + BaseParams.FORGOT_PSWD;
        setLayout(new GridBagLayout());

        // создание элементов
        errorMsgLabel = new JLabel(" ");
        errorMsgLabel.setFont(getBaseFont(Font.BOLD, 16));
        errorMsgLabel.setForeground(Color.RED);

        mainLabel = new JLabel();
        mainLabel.setFont(BaseParams.BASE_LABEL_FONT);
        mainLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainLabel.setText(BaseParams.FORGOT_PSWD);

        JLabel emailLabel = new FieldLabel("E-mail: ");
        emailField = new JTextField();
        emailField.setFont(getBaseFont(FIELD_HEIGHT - 1));
        setElementSize(emailField, new Dimension(FIELD_WIDTH + 60, FIELD_HEIGHT));

        sendBtn = new FormattedButton("Отправить");
        loginBtn = new FormattedButton("Войти");

        // размещение элементов
        int row = 0;
        gbc.gridx = 0; // столбец
        gbc.gridy = row;
        gbc.gridwidth = 2; // сколько столбцов занимает элемент

        // "лишнее" пространство оставлять пустым
        gbc.weightx = 0;
        gbc.weighty = 0;

        gbc.fill = GridBagConstraints.NONE; // заполнять по горизонтали
        gbc.anchor = GridBagConstraints.CENTER; // привязка к центру

        gbc.insets = new Insets(50, 50, 10, 50); // отступы
        gbc.ipadx = 5;
        gbc.ipady = 5;

        add(mainLabel, gbc);

        gbc.gridy = ++row;
        gbc.insets = new Insets(10, 50, 5, 50); // отступы
        add(errorMsgLabel, gbc);

        gbc.gridy = ++row;
        gbc.gridwidth = 1;
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 50, 10, 0);
        emailLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        add(emailLabel, gbc);
        gbc.insets = new Insets(5, 5, 10, 50);
        add(emailField, gbc);

        gbc.gridy = ++row;
        gbc.gridwidth = 2; // сколько столбцов занимает элемент
        gbc.insets = new Insets(10, 50, 70, 50); // отступы
        add(sendBtn, gbc);

        gbc.gridy = ++row;
        gbc.gridwidth = 2; // сколько столбцов занимает элемент
        gbc.insets = new Insets(10, 50, 70, 50); // отступы
        add(loginBtn, gbc);

        initListeners();

        setVisible(true);
    }

    /**
     * Tries to request password restore from AppController.
     * */
    private void tryToRestorePswd() {
        String answer = getRestorePswdAnswer();

        if (answer.equals(AuthConstants.PSWD_RESTORED)) {
            setErrorMsg(DARK_GREEN, answer);
        } else {
            if (answer.equals(AuthConstants.INVALID_SESSION)) {
                PaneManager.switchToAuthPane();
            } else {
                setErrorMsg(DARK_RED, answer);
            }
        }
    }

    private String getRestorePswdAnswer() {
        if (AppController.getInstance().isAuthorized()) return AuthConstants.SUCCESS;
        if (emailField.getText().length() < 1) return AuthConstants.USER_EMAIL_EMPTY;
        if (!UserEditForm.validate(emailField.getText())) return AuthConstants.USER_EMAIL_FORMAT;

        return AppController.getInstance().restorePswd(emailField.getText());
    }

    private void setErrorMsg(Color color, String errorMsg) {
        errorMsgLabel.setText(errorMsg);
        errorMsgLabel.setForeground(color);
        errorMsgLabel.updateUI();
    }

    private void initListeners() {
        emailField.addKeyListener(ENTER_KEY_LISTENER);
        sendBtn.addActionListener(RESTORE_ACTION_LISTENER);
        loginBtn.addActionListener(LOGIN_ACTION_LISTENER);
    }

    @Override
    public void refreshData() {
        System.out.println("[RestorePswdPane] Active");
    }
}
