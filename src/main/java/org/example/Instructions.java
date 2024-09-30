package org.example;

import javax.swing.*;
import java.awt.*;

public class Instructions extends JPanel {
//    private static final JButton startButton = new JButton("start"); // To start/continue the action
    private final JTextArea instructionsArea; // Helps the user around


    public Instructions(int windowWidth, int windowHeight,JButton startButton){
        //Panel sets
        this.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        this.setVisible(false);
        this.setBackground(Color.gray);
        this.setPreferredSize(new Dimension(windowWidth-16,(windowHeight/2)-(windowHeight/10)-20));
        //Text and button sets
        int clearSpace = (windowWidth-16)-(((windowWidth-16)/2)+(windowWidth-16)/7)-(windowWidth-16)/8;
        instructionsArea = new JTextArea();
        instructionsArea.setEnabled(false);
        instructionsArea.setAutoscrolls(true);
        instructionsArea.setPreferredSize(new Dimension((((windowWidth-16)/2)+(windowWidth-16)/7)+clearSpace,(windowHeight/2)-(windowHeight/10)-25));
        instructionsArea.setText("For a start you will need to enter your timing and demands.\n " +
                "Click on the set time afterwards you are done you will be asked to set each of the actions you wanted. When all is done you may click on the start button.\n" +
                "To see when you set the action you can see it in the list or calendar");
        //Adds to panel
        this.add(instructionsArea);
        this.add(startButton);

    }
}
