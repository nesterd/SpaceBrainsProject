package com.spacebrains.widgets;

import com.spacebrains.interfaces.INamed;
import com.spacebrains.interfaces.IRest;
import com.spacebrains.core.util.BaseParams;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class BaseEditForm<T extends INamed> extends JDialog {

    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 190;

    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;

    private IRest restService;
    private T object;
    private GridBagConstraints gbc = new GridBagConstraints();
    private Button saveBtn = new Button("Сохранить");
    private Button cancelBtn = new Button("Отменить");

    public BaseEditForm(IRest restService, T obj) {
        this(restService, obj, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public BaseEditForm(IRest restService, T obj, int width, int height) {
        this.restService = restService;
        this.object = obj;
        this.width = width;
        this.height = height;
        setModal(true);
        setLayout(new GridBagLayout());

        initMainSettings();

        JLabel label = new JLabel("Наименование:");
        label.setFont(BaseParams.BASE_LABEL_FONT);

        JTextField editField = new JTextField();
        editField.setMaximumSize(new Dimension(280, 30));
        editField.setText((object == null || object.getID() == 0) ? "" : object.getName());

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obj.setName(editField.getText());
                String answer = restService.save(obj);
                if (answer.equals("")) dispose();
                else System.out.println("Can't save: " + obj + "\n\t" + answer);

                System.out.println(((obj == null || obj.getID() == 0) ? "Add new record: " : "Edit record: ") + obj);
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
        gbc.gridwidth = 2; // сколько столбцов занимает элемент

        // "лишнее" пространство оставлять пустым
        gbc.weightx = 0;
        gbc.weighty = 0;

        gbc.fill = GridBagConstraints.HORIZONTAL; // заполнять по горизонтали
        gbc.anchor = GridBagConstraints.CENTER; // привязка к центру

        gbc.insets = new Insets(5, 50, 5, 50); // отступы
        gbc.ipadx = 5;
        gbc.ipady = 5;

        add(label, gbc);

        gbc.gridy = ++row;
        add(editField, gbc);

        gbc.gridy = ++row;
        gbc.gridwidth = 1;
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 50, 25, 5);
        add(saveBtn, gbc);
        gbc.insets = new Insets(10, 5, 25, 50);
        add(cancelBtn, gbc);

        initKeysListeners();
    }

    private void initMainSettings() {
        setTitle((object == null || object.getID() == 0)? "Новый элемент" : "Редактировать");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // смотрим размер экрана и размещаем окно чата в центре
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - width) / 2, (screenSize.height - height) / 2, width, height);

        // Приводим внешний вид элементов к виду как в системе пользователя (например соответствующий теме windows)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {}

//        add(content);
        setResizable(false);
    }

    public Button getSaveBtn() {
        return saveBtn;
    }

    public Button getCancelBtn() {
        return cancelBtn;
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
}
