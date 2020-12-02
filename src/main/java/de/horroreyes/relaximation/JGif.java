package de.horroreyes.relaximation;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * Created on 15.03.2005
 */

/**
 * @author Steffen Rumpf
 */
public class JGif extends JPanel {
    private static final long serialVersionUID = -4492480149124880432L;
    private JLabel label;
    private final JPanel panel;

    public JGif(URL url) throws MalformedURLException {
        label = new JLabel(new ImageIcon(url));
        panel = new JPanel();
        panel.add(label);
        add(panel);

        setVisible(true);
    }

    public void setUrl(String url) throws MalformedURLException {
        panel.remove(label);
        label = new JLabel(new ImageIcon(new URL(url)));
        panel.add(label);
        this.repaint();
    }


}

