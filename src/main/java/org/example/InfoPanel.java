package org.example;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    private JPanel listPanel;
    private JLabel listActionTime;

    public InfoPanel(int windowWidth, int windowHeight){
        this.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.setVisible(false);
        this.setBackground(Color.red);
        this.setPreferredSize(new Dimension((windowWidth/2)-17,windowHeight/2));

        listPanel = new JPanel();
        listPanel.setBackground(Color.gray);
        listPanel.setPreferredSize(new Dimension((windowWidth/2)-27,(windowHeight/2)-(windowHeight/60)));
        listActionTime = new JLabel("wpw");
        listPanel.add(listActionTime);
        this.add(listPanel);
    }
}
