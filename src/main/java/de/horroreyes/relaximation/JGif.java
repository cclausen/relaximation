package de.horroreyes.relaximation;

import javax.swing.*;
import java.net.URL;

public class JGif extends JPanel {
    private static final long serialVersionUID = -4492480149124880432L;

    public JGif(URL url) {
        JLabel label = new JLabel(new ImageIcon(url));
        JPanel panel = new JPanel();
        panel.add(label);
        add(panel);

        setVisible(true);
    }

}

