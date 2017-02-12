package com.spacebrains.ui.panels;

import com.spacebrains.core.AppController;
import com.spacebrains.core.util.BaseParams;
import com.spacebrains.widgets.StatsTable;

import javax.swing.*;
import java.awt.*;

import static com.spacebrains.core.util.BaseParams.APP_NAME;
import static com.spacebrains.core.util.BaseParams.CRAWLER_STATS_WINDOW;

/**
 * @author Tatyana Vorobeva
 */
public class CrawlerStatsPane extends BasePane {

    StatsTable table = null;

    public CrawlerStatsPane() {
        super(DEFAULT_WIDTH + 200, DEFAULT_HEIGHT);
        windowTitle = APP_NAME + ": " + CRAWLER_STATS_WINDOW;

        JLabel label = new JLabel(CRAWLER_STATS_WINDOW);
        label.setFont(BaseParams.BASE_LABEL_FONT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        table = new StatsTable(AppController.getInstance().getCrawlerStats());

        content.add(new JLabel(" "));
        content.add(label);
        content.add(table);

        setVisible(true);
    }

    @Override
    public void refreshData() {
        System.out.println("[CrawlerStatsPane] Active");
        if (table != null) table.updateValues(AppController.getInstance().getCrawlerStats());

        wasAlreadyOpenedBefore = true;
    }
}