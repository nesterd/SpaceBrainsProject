package com.spacebrains.widgets;

import com.spacebrains.core.AppController;
import com.spacebrains.core.AuthConstants;
import com.spacebrains.core.util.BaseParams;
import com.spacebrains.model.User;
import com.spacebrains.widgets.base.FormattedButton;
import com.spacebrains.widgets.base.FieldLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.spacebrains.core.util.BaseParams.*;

public class UserEditForm extends JDialog {

    protected static final int DEFAULT_WIDTH = 450;
    protected static final int DEFAULT_HEIGHT = 350;
    private static final int FIELD_WIDTH = 100;
    public static final int FIELD_HEIGHT = 20;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;
    private JLabel errorMsgLabel;

    private User user;
    private GridBagConstraints gbc = new GridBagConstraints();
    private FormattedButton saveBtn = new FormattedButton("Сохранить");
    private FormattedButton cancelBtn = new FormattedButton("Отменить");

    private JTextField nameField;
    private JTextField loginField;
    private JTextField emailField;
    private JTextField pswdField;
    private String answer;

    public UserEditForm(User user) {
        this(user, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public UserEditForm(User user, int width, int height) {
        this.user = user;
        this.width = width;
        this.height = height;
        setModal(true);
        setLayout(new GridBagLayout());

        initMainSettings();
        String nameToShow = user.getLogin().length() > 1 ? user.getLogin() : user.getName();
        JLabel label = new JLabel((user == null || user.getID() == 0) ? "Добавление" : "Редактирование \"" + nameToShow + "\"");
        label.setFont(BaseParams.BASE_LABEL_FONT);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        errorMsgLabel = new JLabel(AuthConstants.SUCCESS);
        errorMsgLabel.setMaximumSize(new Dimension(270, 30));
        errorMsgLabel.setMinimumSize(new Dimension(270, 30));
        errorMsgLabel.setPreferredSize(new Dimension(270, 30));
        errorMsgLabel.setFont(getBaseFont(Font.BOLD, 16));
        errorMsgLabel.setHorizontalAlignment(SwingConstants.CENTER);

        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        nameField.setText((user == null || user.getID() == 0) ? "" : user.getName());

        loginField = new JTextField();
        loginField.setMaximumSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        loginField.setText((user == null || user.getID() == 0) ? "" : user.getLogin());

        emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        emailField.setText((user == null || user.getID() == 0) ? "" : user.getEmail());

        pswdField = new JTextField();
        pswdField.setMaximumSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        pswdField.setText("");

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                answer = AuthConstants.SUCCESS;

                checkNameField();
                checkLoginField();
                checkEmailField();
                checkPswdField();

                if (checkNameField() && checkLoginField() && checkEmailField() && checkPswdField()) {
                    setErrorMsg(DARK_BLUE, "Сохраняем...");
                    user.setName(nameField.getText());
                    user.setLogin(loginField.getText());
                    user.setEmail(emailField.getText());
                    user.setPswd(pswdField.getText());
                    answer = save(user);
                }

                if (!answer.equals(AuthConstants.SUCCESS)) {
                    setErrorMsg(DARK_RED, answer);
                } else dispose();

                System.out.println(((user == null || user.getID() == 0) ? "Add new record: " : "Edit record: ") + user);
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        int row = 0;
        gbc.gridx = row;
        gbc.gridy = 0; // столбец
        gbc.gridwidth = 3; // сколько столбцов занимает элемент

        // "лишнее" пространство оставлять пустым
        gbc.weightx = 0;
        gbc.weighty = 0;

        gbc.fill = GridBagConstraints.HORIZONTAL; // заполнять по горизонтали
        gbc.anchor = GridBagConstraints.CENTER; // привязка к центру

        gbc.insets = new Insets(0, 50, 0, 50); // отступы
        gbc.ipadx = 5;
        gbc.ipady = 5;

        add(errorMsgLabel, gbc);

        gbc.insets = new Insets(0, 50, 10, 50); // отступы
        gbc.gridy = ++row;
        add(label, gbc);

        gbc.insets = new Insets(0, 50, 0, 5); // отступы
        gbc.gridy = ++row;
        gbc.gridwidth = 1; // сколько столбцов занимает элемент
        add(new FieldLabel("Имя:"), gbc);

        gbc.gridwidth = 2; // сколько столбцов занимает элемент
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 0, 50); // отступы
        add(nameField, gbc);

        gbc.insets = new Insets(0, 50, 0, 5); // отступы
        gbc.gridy = ++row;
        gbc.gridwidth = 1; // сколько столбцов занимает элемент
        add(new FieldLabel("Логин:"), gbc);

        gbc.gridwidth = 2; // сколько столбцов занимает элемент
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 0, 50); // отступы
        add(loginField, gbc);

        gbc.insets = new Insets(0, 20, 0, 5); // отступы
        gbc.gridy = ++row;
        gbc.gridwidth = 1; // сколько столбцов занимает элемент
        add(new FieldLabel("E-Mail:"), gbc);

        gbc.gridwidth = 2; // сколько столбцов занимает элемент
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 0, 50); // отступы
        add(emailField, gbc);

        gbc.insets = new Insets(0, 20, 0, 5); // отступы
        gbc.gridy = ++row;
        gbc.gridwidth = 1; // сколько столбцов занимает элемент
        add(new FieldLabel("Пароль" + ((user != null && user.getID() != 0) ? "*:" : ":")), gbc);

        gbc.gridwidth = 2; // сколько столбцов занимает элемент
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 0, 50); // отступы
        add(pswdField, gbc);

        if (user != null && user.getID() != 0) {
            gbc.insets = new Insets(0, 50, 10, 50); // отступы
            gbc.gridx = 0;
            gbc.gridy = ++row;
            gbc.gridwidth = 3;
            JLabel hint = new JLabel("* Оставьте пустым, если не хотите изменять");
            hint.setFont(getBaseFont(Font.ITALIC, 14));
            add(hint, gbc);
        }

        gbc.gridy = ++row;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.insets = new Insets(10, WIDTH * 2, 25, 5);
        add(saveBtn, gbc);
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 5, 25, 50);
        add(cancelBtn, gbc);

        initKeysListeners();
    }

    private boolean checkField(JTextField field, int minLength, int maxLength, String minAnswer, String maxAnswer) {
        if (field.getText().length() < minLength) answer = minAnswer;
        else if (field.getText().length() > maxLength) answer = maxAnswer;
        else answer = AuthConstants.SUCCESS;

        return answer.equals(AuthConstants.SUCCESS);
    }

    private boolean checkNameField() {
        return checkField(nameField, 1, 75, AuthConstants.USER_NAME_EMPTY, AuthConstants.USER_NAME_TOO_LONG);
    }

    private boolean checkLoginField() {
        return checkField(loginField, 1, 15, AuthConstants.USER_LOGIN_EMPTY, AuthConstants.USER_LOGIN_TOO_LONG);
    }

    private boolean checkEmailField() {
        boolean checkResult = checkField(emailField, 1, 150, AuthConstants.USER_EMAIL_EMPTY, AuthConstants.USER_EMAIL_TOO_LONG);
        if (checkResult && !validate(emailField.getText())) {
            answer = AuthConstants.USER_EMAIL_FORMAT;
            checkResult = false;
        }
        return checkResult;
    }

    private boolean checkPswdField() {
        int minLenght = (user == null || user.getID() == 0) ? 1 : 0;
        System.out.println("minLenght: " + minLenght);
        return checkField(pswdField, minLenght, 1024, AuthConstants.USER_PSWD_EMPTY, AuthConstants.USER_PSWD_TOO_LONG);
    }

    private void initMainSettings() {
        setTitle((user == null || user.getID() == 0)? "Новый элемент" : "Редактировать");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // смотрим размер экрана и размещаем окно чата в центре
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - width) / 2, (screenSize.height - height) / 2, width, height);

        // Приводим внешний вид элементов к виду как в системе пользователя (например соответствующий теме windows)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {}

        setResizable(false);
    }

    public void initKeysListeners() {
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        // Escape
        Action escapeListener = new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                cancelBtn.doClick();
            }
        };
        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), KeyEvent.VK_ESCAPE);
        rootPane.getActionMap().put(KeyEvent.VK_ESCAPE, escapeListener);

        // Enter
        Action enterListener = new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                saveBtn.doClick();
            }
        };
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), KeyEvent.VK_ENTER);
        rootPane.getActionMap().put(KeyEvent.VK_ENTER, enterListener);
    }

    protected String save(User user) {
        try {
            return AppController.getInstance().setUser(user);
        } catch (RuntimeException e) {
            return AuthConstants.NOT_ANSWERED;
        }
    }

    protected void setErrorMsg(Color color, String errorMsg) {
        errorMsgLabel.setText(errorMsg);
        errorMsgLabel.setForeground(color);
        errorMsgLabel.updateUI();
    }

    private static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

}
