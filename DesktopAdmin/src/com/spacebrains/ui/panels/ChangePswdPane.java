package com.spacebrains.ui.panels;

import com.spacebrains.core.AppController;
import com.spacebrains.core.AuthConstants;
import com.spacebrains.core.util.BaseParams;
import com.spacebrains.ui.PaneManager;
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
public class ChangePswdPane extends BasePane {

    private GridBagConstraints gbc = new GridBagConstraints();

    private JLabel mainLabel;
    private JLabel errorMsgLabel;
    private JPasswordField oldPswdField;
    private JPasswordField newPswdField;
    private JPasswordField repeatPswdField;
    private FormattedButton changeBtn;

    private final int FIELD_WIDTH = 140;
    private final int FIELD_HEIGHT = 18;

    private final KeyListener ENTER_KEY_LISTENER = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) tryToChangePswd();
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    };
    private final ActionListener ACTION_LISTENER = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            tryToChangePswd();
        }
    };

    public ChangePswdPane() {
        super();
        windowTitle = BaseParams.APP_NAME + ": " + BaseParams.CHANGE_PSWD;
        setLayout(new GridBagLayout());

        // создание элементов
        errorMsgLabel = new JLabel(" ");
        errorMsgLabel.setFont(getBaseFont(Font.BOLD, 16));
        errorMsgLabel.setForeground(Color.RED);

        mainLabel = new JLabel();
        mainLabel.setFont(BaseParams.BASE_LABEL_FONT);
        mainLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        setMainLabelText();

        JLabel oldPswdLabel = new JLabel("Старый пароль: ");
        oldPswdLabel.setFont(getBaseFont(Font.BOLD, FIELD_HEIGHT - 2));
        oldPswdField = new JPasswordField();
        oldPswdField.setFont(getBaseFont(FIELD_HEIGHT - 1));
        setElementSize(oldPswdField, new Dimension(FIELD_WIDTH, FIELD_HEIGHT));

        JLabel pswdLabel = new JLabel(" Новый пароль: ");
        pswdLabel.setFont(getBaseFont(Font.BOLD, FIELD_HEIGHT - 2));
        newPswdField = new JPasswordField();
        newPswdField.setFont(getBaseFont(FIELD_HEIGHT - 1));
        setElementSize(newPswdField, new Dimension(FIELD_WIDTH, FIELD_HEIGHT));

        JLabel repeatPswdLabel = new JLabel("       Повтор: ");
        repeatPswdLabel.setFont(getBaseFont(Font.BOLD, FIELD_HEIGHT - 2));
        repeatPswdField = new JPasswordField();
        repeatPswdField.setFont(getBaseFont(FIELD_HEIGHT - 1));
        setElementSize(repeatPswdField, new Dimension(FIELD_WIDTH, FIELD_HEIGHT));

        changeBtn = new FormattedButton("Сменить");

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
        oldPswdLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        add(oldPswdLabel, gbc);
        gbc.insets = new Insets(5, 5, 10, 50);
        add(oldPswdField, gbc);

        gbc.gridy = ++row;
        gbc.insets = new Insets(5, 50, 10, 0);
        add(pswdLabel, gbc);
        gbc.insets = new Insets(5, 5, 10, 50);
        add(newPswdField, gbc);

        gbc.gridy = ++row;
        gbc.insets = new Insets(5, 50, 10, 0);
        add(repeatPswdLabel, gbc);
        gbc.insets = new Insets(5, 5, 10, 50);
        add(repeatPswdField, gbc);

        gbc.gridy = ++row;
        gbc.gridwidth = 2; // сколько столбцов занимает элемент
        gbc.insets = new Insets(10, 50, 70, 50); // отступы
        add(changeBtn, gbc);

        initListeners();

        setVisible(true);
    }

    private String getPswdText(JPasswordField anyPswdField) {
        String pswd = "";

        for (char pswdChar : anyPswdField.getPassword()) {
            pswd += pswdChar;
        };
        return pswd;
    }

    /**
     * Tries to request password changing from AppController.
     * Shows simple popUp info-message for successful password changing.
     * Swithes app to authorization if user is not authorized at the moment.
     * Shows errorMsg recieved from AppController for all other cases.
     * */
    private void tryToChangePswd() {
        String answer = getChangePswdAnswer();

        if (answer.equals(AuthConstants.PSWD_CHANGED)) {
            setErrorMsg(DARK_GREEN, answer);
        } else {
            if (answer.equals(AuthConstants.INVALID_SESSION)) {
                PaneManager.switchToAuthPane();
            } else {
                setErrorMsg(DARK_RED, answer);
            }
        }
    }

    private String getChangePswdAnswer() {
        if (!AppController.getInstance().isAuthorized()) return AuthConstants.INVALID_SESSION;
        if (getPswdText(oldPswdField).length() < 1) return AuthConstants.NEED_OLD_PSWD;
        if (getPswdText(newPswdField).length() < 1) return AuthConstants.NEED_NEW_PSWD;
        if (!getPswdText(newPswdField).equals(getPswdText(repeatPswdField))) return AuthConstants.NOT_MATCHING_PSWD;

        return AppController.getInstance().changePswd(getPswdText(oldPswdField), getPswdText(newPswdField));
    }

    private void setErrorMsg(Color color, String errorMsg) {
        errorMsgLabel.setText(errorMsg);
        errorMsgLabel.setForeground(color);
        errorMsgLabel.updateUI();

        oldPswdField.setText("");
        newPswdField.setText("");
        repeatPswdField.setText("");
    }

    private void initListeners() {
        oldPswdField.addKeyListener(ENTER_KEY_LISTENER);
        newPswdField.addKeyListener(ENTER_KEY_LISTENER);
        repeatPswdField.addKeyListener(ENTER_KEY_LISTENER);
        changeBtn.addActionListener(ACTION_LISTENER);
    }

    private void setMainLabelText() {
        mainLabel.setText(BaseParams.CHANGE_PSWD
                + (AppController.getInstance().isAuthorized()
                    ? " для " + AppController.getInstance().getCurrentUserLogin()
                    : ""));
    }

    @Override
    public void refreshData() {
        System.out.println("[ChangePswdPane] Active");
        setErrorMsg(DARK_GREEN, AuthConstants.SUCCESS);
    }
}
