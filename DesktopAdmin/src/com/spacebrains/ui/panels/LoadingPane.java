package com.spacebrains.ui.panels;

import com.spacebrains.core.RepoConstants;
import com.spacebrains.ui.BaseAppWindow;

import javax.swing.*;
import java.awt.*;

import static com.spacebrains.core.util.BaseParams.DARK_BLUE;
import static com.spacebrains.core.util.BaseParams.DARK_RED;
import static com.spacebrains.core.util.BaseParams.getBaseFont;

/**
 * @author Tatyana Vorobeva
 */
public class LoadingPane extends BasePane {

    private JLabel messageLbl = new JLabel();

    public LoadingPane() {
        super();
        setLayout(new GridBagLayout());

        refreshMessage(RepoConstants.WAITING);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;
        add(messageLbl, gbc);
        setVisible(true);
    }

    public void refreshMessage(String msg) {
        messageLbl.setText(msg);
        messageLbl.setFont(getBaseFont(Font.BOLD, 20));
        messageLbl.setMaximumSize(BaseAppWindow.getCurrentDimension());
        messageLbl.setPreferredSize(BaseAppWindow.getCurrentDimension());

        if (msg.equals(RepoConstants.WAITING)) messageLbl.setForeground(DARK_BLUE);
        else messageLbl.setForeground(DARK_RED);

        messageLbl.updateUI();
    }

    @Override
    public void refreshData() {
        System.out.println("[LoadingPane] Active");
        refreshMessage(RepoConstants.WAITING);
    }
}
