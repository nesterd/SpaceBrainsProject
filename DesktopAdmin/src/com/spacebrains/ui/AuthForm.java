package com.spacebrains.ui;

import com.spacebrains.core.AuthConstants;
import com.spacebrains.core.util.BaseParams;
import com.spacebrains.widgets.BaseWindow;
import com.spacebrains.widgets.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;

import static com.spacebrains.core.AppController.lastRequestMsg;
import static com.spacebrains.core.util.BaseParams.DARK_RED;
import static com.spacebrains.core.util.BaseParams.getBaseFont;

/**
 * @author Tatyana Vorobeva
 */
public class AuthForm extends BaseWindow {

    public static final String LOGIN_LBL = "Логин: ";
    public static final String PSWD_LBL = "Пароль: ";
    public static final String LOGIN_BTN_LBL = "Войти";
    public static final String FORGOT_LBL = "Забыл пароль";

    private GridBagConstraints gbc = new GridBagConstraints();
    private JFrame currentFrame;
    private JLabel errorMsgLabel;
    private JTextField loginField;
    private JPasswordField pswdField;
    private Button loginBtn;
    private Button forgotBtn;

    private final int FIELD_WIDTH = 170;
    private final int FIELD_HEIGHT = 18;

    private final KeyListener ENTER_KEY_LISTENER = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) tryToLogin();
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    };
    private final ActionListener ACTION_LISTENER = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            tryToLogin();
        }
    };

    public AuthForm() {
        super();
        windowTitle = BaseParams.APP_NAME + ": " + BaseParams.AUTHORIZATION;
        setLayout(new GridBagLayout());
        currentFrame = this;

        // создание элементов
        JLabel mainLabel = new JLabel(BaseParams.AUTHORIZATION);
        mainLabel.setFont(BaseParams.BASE_LABEL_FONT);
        mainLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        errorMsgLabel = new JLabel(lastRequestMsg());
        errorMsgLabel.setFont(getBaseFont(Font.BOLD, 16));
        errorMsgLabel.setForeground(DARK_RED);
        setElementSize(errorMsgLabel, new Dimension(FIELD_WIDTH + 110, FIELD_HEIGHT * 2));
        errorMsgLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel loginLabel = new JLabel(LOGIN_LBL);
        loginLabel.setFont(getBaseFont(Font.BOLD, FIELD_HEIGHT - 2));
        loginLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        loginField = new JTextField();
        loginField.setFont(getBaseFont(FIELD_HEIGHT - 1));
        loginField.setAlignmentX(Component.CENTER_ALIGNMENT);
        setElementSize(loginField, new Dimension(FIELD_WIDTH, FIELD_HEIGHT));

        JLabel pswdLabel = new JLabel(PSWD_LBL);
        pswdLabel.setFont(getBaseFont(Font.BOLD, FIELD_HEIGHT - 2));
        pswdLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        pswdField = new JPasswordField();
        pswdField.setFont(getBaseFont(FIELD_HEIGHT - 1));
        setElementSize(pswdField, new Dimension(FIELD_WIDTH, FIELD_HEIGHT));

        loginBtn = new Button(LOGIN_BTN_LBL);
        loginBtn.grabFocus();
        forgotBtn = new Button(FORGOT_LBL);
        forgotBtn.setFont(getBaseFont(FIELD_HEIGHT - 4));
        setElementSize(forgotBtn, new Dimension(FIELD_WIDTH - 15, FIELD_HEIGHT));

        // размещение элементов
        int row = 0;
        gbc.gridx = 0; // столбец
        gbc.gridy = row;
        gbc.gridwidth = 2; // сколько столбцов занимает элемент

        // "лишнее" пространство оставлять пустым
        gbc.weightx = 0;
        gbc.weighty = 0;

        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        int mainSideInset = 60;

        gbc.insets = new Insets(50, mainSideInset, 10, 50); // отступы
        gbc.ipadx = 5;
        gbc.ipady = 5;

        add(mainLabel, gbc);

        gbc.gridy = ++row;
        gbc.insets = new Insets(0, mainSideInset, 5, 50); // отступы
        add(errorMsgLabel, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy = ++row;
        gbc.gridwidth = 1;
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, mainSideInset, 10, 0);
        add(loginLabel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 10, 50);
        add(loginField, gbc);

        gbc.gridy = ++row;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, mainSideInset, 10, 0);
        add(pswdLabel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 10, 50);
        add(pswdField, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = ++row;
        gbc.gridwidth = 2; // сколько столбцов занимает элемент
        gbc.insets = new Insets(10, mainSideInset, 70, 50); // отступы
        add(loginBtn, gbc);

        gbc.gridy = ++row;
        gbc.insets = new Insets(40, mainSideInset, 5, 50); // отступы
        add(forgotBtn, gbc);

        initListeners();

        menu.setVisible(false);
        setVisible(true);
    }

    private String getPswdText() {
        String pswd = "";

        for (char pswdChar : pswdField.getPassword()) {
            pswd += pswdChar;
        };
        return pswd;
    }

    private void tryToLogin() {
        String answer = appController.login(loginField.getText(), getPswdText());
        if (answer.equals(AuthConstants.SUCCESS)) {
            this.setVisible(false);
            FormsManager.showMainWindowForm();
        } else {
            setErrorMsg(lastRequestMsg());
            pswdField.setText("");
        }
    }

    private void showLinkInPopUp(String link) {
        JTextField msg = new JTextField(link);
        msg.setEditable(false);
        JOptionPane.showMessageDialog(currentFrame, msg, BaseParams.APP_NAME, JOptionPane.PLAIN_MESSAGE);
    }

    private void setErrorMsg(String errorMsg) {
        errorMsgLabel.setText(errorMsg);

        if (errorMsg.contains(AuthConstants.ERR_IS_USER)) {
            errorMsgLabel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (Desktop.isDesktopSupported()) {
                        try {
                            Desktop.getDesktop().browse(new URI(AuthConstants.USER_WEB_INTERFACE_LINK));
                        } catch (Exception e2) {
                            showLinkInPopUp(AuthConstants.USER_WEB_INTERFACE_LINK);
                        }
                    } else {
                        showLinkInPopUp(AuthConstants.USER_WEB_INTERFACE_LINK);
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {}

                @Override
                public void mouseEntered(MouseEvent e) {}

                @Override
                public void mouseExited(MouseEvent e) {}
            });
        }

        errorMsgLabel.updateUI();
    }

    private void initListeners() {
        loginField.addKeyListener(ENTER_KEY_LISTENER);
        pswdField.addKeyListener(ENTER_KEY_LISTENER);
        loginBtn.addActionListener(ACTION_LISTENER);

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                setErrorMsg(lastRequestMsg());
                pswdField.setText("");
            }

            @Override
            public void windowClosing(WindowEvent e) {}

            @Override
            public void windowClosed(WindowEvent e) {}

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {
                pswdField.setText("");
            }

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });

        forgotBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLinkInPopUp("Обратитесь к администратору, пожалуйста: " + AuthConstants.USER_WEB_INTERFACE_LINK);
            }
        });
    }

}
