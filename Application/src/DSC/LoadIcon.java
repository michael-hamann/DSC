/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC;

import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Amina
 */
public class LoadIcon {

    public void iconLoader(JLabel label2) {
        URL url = this.getClass().getResource("load_2.gif");
        Icon icon2 = new ImageIcon(url);
        label2.setIcon(icon2);
        label2.setOpaque(false);
    }
}
