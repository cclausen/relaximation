package de.horroreyes.relaximation;

import com.intellij.openapi.wm.ToolWindow;
import com.intellij.uiDesigner.core.GridConstraints;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;

public class RelaximationToolWindow {
    private JPanel relaximationToolWindowContent;

    public RelaximationToolWindow(ToolWindow toolWindow) {

        try {
            JGif gif = new JGif();
            gif.setBounds(100, 100, 100, 100);
            this.relaximationToolWindowContent.add(gif, new GridConstraints());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public JPanel getContent() {
        return relaximationToolWindowContent;
    }
}
