package de.horroreyes.relaximation;

import com.intellij.openapi.wm.ToolWindow;
import com.intellij.uiDesigner.core.GridConstraints;
import org.json.JSONObject;

import javax.swing.*;
import java.net.MalformedURLException;

public class RelaximationToolWindow {
    private JPanel relaximationToolWindowContent;

    public RelaximationToolWindow(ToolWindow toolWindow) {

        try {
            GifLoader gifLoader = new GifLoader();
            JSONObject gifs = gifLoader.getGifs();
            String url = "https://i.pinimg.com/originals/01/fb/2c/01fb2cb2cf0855514cf1df69f46acda8.gif";
            JGif gif = new JGif(url);
            url = gifs.getJSONArray("results").getJSONObject(0).getJSONArray("media").getJSONObject(0).getJSONObject("gif").getString("url");
            gif.setUrl(url);
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
