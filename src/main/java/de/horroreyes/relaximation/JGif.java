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

    public JGif() throws MalformedURLException {

        JLabel label = new JLabel(new ImageIcon(new URL("https://images.squarespace-cdn.com/content/v1/5c7c1bf2c46f6d18969b37ae/1554126352255-LDIMHVTFY1X7WKJEQ2CE/ke17ZwdGBToddI8pDm48kBbdSUIHrnfszC0Uv-s6NXNZw-zPPgdn4jUwVcJE1ZvWEtT5uBSRWt4vQZAgTJucoTqqXjS3CfNDSuuf31e0tVHVFCHbO600DSvoILJ4oa2QnThAdi_sonYsmMjm7Z6bbO87Nsj43NRAr6WuWZv5DKs/Animated+GIF-downsized_large.gif")));
        JPanel panel = new JPanel();
        panel.add(label);
        add(panel);

        setVisible(true);
    }


//        private URL url = null;
//        private boolean viewImg = true;
//
//        public JGif(URL url, boolean viewImg) {
//            super(true);
//            // the url to the image
//            this.url = url;
//            // a flag if true the image will be viewed
//            this.viewImg = viewImg;
//        }
//
//        /** (non-Javadoc)
//         * @see java.awt.Component#paint(java.awt.Graphics)
//         * Paint the background image
//         */
//        @Override
//        public void paint(Graphics g) {
//            if(viewImg == true) {
//                BufferedImage pic = null;
//                // create the url for the background image
//                try {
//                    pic = ImageIO.read(url);
//                } catch (MalformedURLException e1) {
//                    e1.printStackTrace();
//                } catch (IOException e2) {
//                    e2.printStackTrace();
//                }
//                g.drawImage(pic, 0, 0,this);
//                super.paint(g);
//            } else {
//                super.paint(g);
//            }
//        }
//
//        /**
//         * @param viewImg The viewImg to set.
//         */
//        public void setViewImg(boolean viewImg) {
//            this.viewImg = viewImg;
//            repaint();
//        }
}

