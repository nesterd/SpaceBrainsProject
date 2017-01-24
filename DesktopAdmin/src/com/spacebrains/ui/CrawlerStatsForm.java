package com.spacebrains.ui;

import com.spacebrains.interfaces.IStats;
import com.spacebrains.core.rest.StatsRestMock;
import com.spacebrains.core.util.BaseParams;
import com.spacebrains.widgets.BaseWindow;
import com.spacebrains.widgets.StatsTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class CrawlerStatsForm extends BaseWindow {

    IStats rest = StatsRestMock.getInstance();
    StatsTable table = null;

    public CrawlerStatsForm() {
        super(DEFAULT_WIDTH + 200, DEFAULT_HEIGHT);

        JLabel label = new JLabel("Статистика Краулера");
        label.setFont(BaseParams.BASE_LABEL_FONT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        table = new StatsTable(rest.getCrawlerStats());

        content.add(new JLabel(" "));
        content.add(label);
        content.add(table);

        setVisible(true);


    }

    @Override
    public void windowActivated(WindowEvent e) {
        super.windowActivated(e);
        if (table != null) table.updateValues(rest.getCrawlerStats());
    }

}