package com.spacebrains.widgets;

import com.spacebrains.interfaces.INamed;
import com.spacebrains.util.BaseParams;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BaseEditForm<T extends INamed> extends JDialog {

    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 190;

    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;

    private T object;
    private Box content;
    private Button saveBtn = new Button("Сохранить");
    private Button cancelBtn = new Button("Отменить");

    public BaseEditForm(T obj) {
        this(obj, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public BaseEditForm(T obj, int width, int height) {
        this.object = obj;
        this.width = width;
        this.height = height;
        setModal(true);
        content = Box.createVerticalBox();
        content.setAlignmentX(Component.CENTER_ALIGNMENT);

        initMainSettings();

        JLabel label = new JLabel("Наименование:");
        label.setAlignmentX(RIGHT_ALIGNMENT);
        label.setFont(BaseParams.BASE_LABEL_FONT);

        JTextField editField = new JTextField();
        editField.setMaximumSize(new Dimension(300, 30));
        editField.setText((object == null || object.getID() == 0) ? "" : object.getName());

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        Box buttons = Box.createHorizontalBox();
        buttons.add(saveBtn);
        buttons.add(cancelBtn);

        content.add(new JLabel(" "));
        content.add(label);
        content.add(new JLabel(" "));
        content.add(editField);
        content.add(new JLabel(" "));
        content.add(buttons);

        setAlwaysOnTop(true);
    }

    private void initMainSettings() {
        setTitle(object.getID() == 0 ? "Новый элемент" : "Редактировать");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // смотрим размер экрана и размещаем окно чата в центре
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - width) / 2, (screenSize.height - height) / 2, width, height);

        // Приводим внешний вид элементов к виду как в системе пользователя (например соответствующий теме windows)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {}

        add(content);
        setResizable(false);
    }

    public Button getSaveBtn() {
        return saveBtn;
    }

    public Button getCancelBtn() {
        return cancelBtn;
    }
}
