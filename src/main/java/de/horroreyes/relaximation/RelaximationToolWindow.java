package de.horroreyes.relaximation;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.uiDesigner.core.GridConstraints;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

public class RelaximationToolWindow {
    private JPanel relaximationToolWindowContent;
    ActionListener changer = evt -> changeGif();
    GifLoader gifLoader = new GifLoader();
    JGif gif;
    private static final Logger log = Logger.getInstance(RelaximationToolWindow.class);

    public RelaximationToolWindow(ToolWindow toolWindow) {
        changeGif();
        Timer timer = new Timer(10000, changer);
        timer.setRepeats(true);
        timer.start();
    }

    private void changeGif() {
        try {
            URL url = gifLoader.getNextGif();
            if (gif != null) {
                RelaximationToolWindow.this.relaximationToolWindowContent.remove(gif);
            }
            gif = new JGif(url);
            gif.setBounds(100, 100, 100, 100);
            RelaximationToolWindow.this.relaximationToolWindowContent.add(gif, new GridConstraints());
            RelaximationToolWindow.this.relaximationToolWindowContent.revalidate();
            RelaximationToolWindow.this.relaximationToolWindowContent.repaint();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public JPanel getContent() {
        return relaximationToolWindowContent;
    }
}
