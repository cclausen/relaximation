package de.horroreyes.relaximation;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.uiDesigner.core.GridConstraints;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

public class RelaximationToolWindow {
    private JPanel relaximationToolWindowContent;
    private final GifLoader gifLoader = new GifLoader();
    private JGif gif;
    private final RelaximationSettingsState settings = ServiceManager.getService(RelaximationSettingsState.class);
    private final Timer timer;
    private static final Logger log = Logger.getInstance(RelaximationToolWindow.class);

    public RelaximationToolWindow() {
        ActionListener changer = evt -> changeGif();
        timer = new Timer(settings.duration * 1000, changer);
        timer.setRepeats(true);
        timer.start();
        changeGif();
    }

    private void changeGif() {
        timer.setDelay(settings.duration * 1000);
        try {
            URL url = gifLoader.getNextGif();
            if (gif != null) {
                RelaximationToolWindow.this.relaximationToolWindowContent.remove(gif);
            }
            gif = new JGif(url);

        } catch (MalformedURLException | InterruptedException e) {
            log.error("Uups, end of relaxing...", e);
            gif = new JGif();
        }
        gif.setBounds(100, 100, 100, 100);
        RelaximationToolWindow.this.relaximationToolWindowContent.add(gif,
                new GridConstraints());
        RelaximationToolWindow.this.relaximationToolWindowContent.revalidate();
        RelaximationToolWindow.this.relaximationToolWindowContent.repaint();
    }


    public JPanel getContent() {
        return relaximationToolWindowContent;
    }
}
