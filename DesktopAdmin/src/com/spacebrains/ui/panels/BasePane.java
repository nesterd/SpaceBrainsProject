package com.spacebrains.ui.panels;

import com.spacebrains.core.util.BaseParams;
import com.spacebrains.interfaces.INamed;
import com.spacebrains.widgets.base.BaseEditForm;

import javax.swing.*;
import java.awt.*;

import static com.spacebrains.core.util.BaseParams.ALERT_MSG;
import static com.spacebrains.core.util.BaseParams.setDefaultFont;

public abstract class BasePane extends JPanel {

    public boolean wasAlreadyOpenedBefore = false;
    protected String windowTitle = BaseParams.APP_NAME;

    public static final int DEFAULT_WIDTH = 550;
    public static final int DEFAULT_HEIGHT = 485;

    protected int width = DEFAULT_WIDTH;
    protected int height = DEFAULT_HEIGHT;

    protected Box content;
    protected BaseEditForm<? extends INamed> editDialog;

    public BasePane() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public BasePane(int width, int height) {
        initMainSettings(width, height);
        setDefaultFont();
        windowTitle = BaseParams.APP_NAME;
    }

    private void initMainSettings(int width, int height) {
        this.width = width;
        this.height = height;
        content = Box.createVerticalBox();
        content.setAlignmentY(Component.CENTER_ALIGNMENT);

        // смотрим размер экрана и размещаем окно чата в центре
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - width) / 2, (screenSize.height - height) / 2, width, height);

        // Приводим внешний вид элементов к виду как в системе пользователя (например соответствующий теме windows)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {}

        add(content);
    }

    protected int getDeleteConfirmation(JPanel currentPanel, String objectName) {
        Object[] options = {"Да", "Нет"};
        return JOptionPane.showOptionDialog(currentPanel,
                "Вы хотите удалить элемент '" + objectName + "'?",
                ALERT_MSG,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     // без специальной иконки
                options,  // заголовки кнопок
                options[0]); // выбор по умолчанию
    }

    protected Component setElementSize(Component component, Dimension dimension) {
        component.setMinimumSize(dimension);
        component.setMaximumSize(dimension);
        component.setPreferredSize(dimension);
        return component;
    }

    public abstract void refreshData();

    public String getWindowTitle() {
        return windowTitle;
    }
}
