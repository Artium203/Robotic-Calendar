package org.example;

import javax.swing.*;
import java.awt.*;

public class MAM extends JPanel {

    public MAM(int windowWidth,int windowHeight){

        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setPreferredSize(new Dimension(windowWidth-8,windowHeight-(windowHeight/10)-11));
        this.setVisible(false);
        this.setBackground(Color.green);

    }
}
