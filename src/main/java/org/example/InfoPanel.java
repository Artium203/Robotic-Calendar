package org.example;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    private JPanel listPanel; // Contains the action that user gave and still didn't finish

    public InfoPanel(int windowWidth, int windowHeight){
        //Sets of the panel
        this.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.setVisible(false);
        this.setBackground(Color.red);
        this.setPreferredSize(new Dimension((windowWidth/2)-17,windowHeight/2));

        //Sets the list and adds it ot panel
        listPanel = new JPanel();
        listPanel.setBackground(Color.gray);
        listPanel.setPreferredSize(new Dimension((windowWidth/2)-27,(windowHeight/2)-(windowHeight/60)));
        this.add(listPanel);


    }
}
