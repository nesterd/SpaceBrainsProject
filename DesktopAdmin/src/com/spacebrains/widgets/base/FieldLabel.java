package com.spacebrains.widgets.base;

import javax.swing.*;
import java.awt.*;

import static com.spacebrains.core.util.BaseParams.getBaseFont;

public class FieldLabel extends JLabel {

    public static final int WIDTH = 50;
    public static final int HEIGHT = 16;

    public FieldLabel(String text) {
        super(text);
        setFont(getBaseFont(Font.BOLD, HEIGHT));
        setHorizontalAlignment(SwingConstants.RIGHT);
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
    }
}
