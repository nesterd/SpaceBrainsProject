package com.spacebrains.ui;

import com.spacebrains.interfaces.IStats;
import com.spacebrains.core.rest.StatsRestMock;
import com.spacebrains.core.util.BaseParams;
import com.spacebrains.widgets.base.BaseWindow;
import com.spacebrains.widgets.StatsTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

import static com.spacebrains.core.util.BaseParams.APP_NAME;
import static com.spacebrains.core.util.BaseParams.CRAWLER_STATS_WINDOW;

/**
 * @author Tatyana Vorobeva
 */
public class CrawlerStatsForm extends BaseWindow {

    IStats rest = StatsRestMock.getInstance();
    StatsTable table = null;

    public CrawlerStatsForm() {
        super(DEFAULT_WIDTH + 200, DEFAULT_HEIGHT);
        windowTitle = APP_NAME + ": " + CRAWLER_STATS_WINDOW;

        JLabel label = new JLabel(CRAWLER_STATS_WINDOW);
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

        wasAlreadyOpenedBefore = true;
    }

}