package org.example;

import javax.swing.*;
import java.awt.*;

public class Instructions extends JPanel {
    private static JButton startButton = new JButton("start");
    private JTextArea textArea;

    public Instructions(int windowWidth, int windowHeight){
        this.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        this.setVisible(false);
        this.setBackground(Color.gray);
        this.setPreferredSize(new Dimension(windowWidth-16,(windowHeight/2)-(windowHeight/10)-20));
        int clearSpace = (windowWidth-16)-(((windowWidth-16)/2)+(windowWidth-16)/7)-(windowWidth-16)/8;
        textArea = new JTextArea();
        textArea.setEnabled(false);
        textArea.setAutoscrolls(true);
        textArea.setPreferredSize(new Dimension((((windowWidth-16)/2)+(windowWidth-16)/7)+clearSpace,(windowHeight/2)-(windowHeight/10)-25));
        textArea.setText("Instructions here");
        startButton.setPreferredSize(new Dimension((windowWidth-16)/8,(windowHeight/2)-(windowHeight/10)-25));
        startButton.setBackground(Color.DARK_GRAY);
        this.add(textArea);
        this.add(startButton);

    }
}
