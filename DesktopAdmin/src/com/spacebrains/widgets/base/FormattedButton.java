package com.spacebrains.widgets.base;

import com.spacebrains.core.util.BaseParams;

import javax.swing.*;
import java.awt.*;

public class FormattedButton extends JButton {

    public FormattedButton(String text) {
        super(text);
        setFont(BaseParams.BASE_BTN_FONT);
        setMaximumSize(new Dimension(getMinimumSize().width, BaseParams.MAX_BTN_HEIGHT));
        setPreferredSize(new Dimension(getMinimumSize().width, BaseParams.MAX_BTN_HEIGHT));
    }

}
