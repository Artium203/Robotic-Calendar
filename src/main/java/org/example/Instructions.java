package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Instructions extends JPanel {
//    private static final JButton startButton = new JButton("start"); // To start/continue the action
    private final JTextArea instructionsArea; // Helps the user around
    Image backgroundImage;

    {
        try {
            backgroundImage = ImageIO.read(new File("src/Resources/cosmetics/ezgif.com-cropfgt.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private final int buttonWidth;

    public Instructions(int windowWidth, int windowHeight,JButton startButton){
        //Panel sets
        this.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        this.setVisible(false);
//        this.setBackground(Color.gray);
        this.setPreferredSize(new Dimension(windowWidth-16,(windowHeight/2)-(windowHeight/10)-20));
        //Text and button sets
        int clearSpace = (windowWidth-16)-(((windowWidth-16)/2)+(windowWidth-16)/7)-(windowWidth-16)/8;
        buttonWidth = startButton.getWidth();
        instructionsArea = new JTextArea();
        instructionsArea.setEnabled(false);
        instructionsArea.setDisabledTextColor(Color.black);
        instructionsArea.setFont(new Font("Arial", Font.PLAIN, 20));
        instructionsArea.setAutoscrolls(true);
        instructionsArea.setOpaque(false);
        instructionsArea.setPreferredSize(new Dimension((((windowWidth-16)/2)+(windowWidth-16)/7)+clearSpace,(windowHeight/2)-(windowHeight/10)-25));
        instructionsArea.setText("\n           For a start you will need to enter your timing and demands.\n " +
                "          Click on the set time afterwards you are done you will be asked to set each\n"+"           of the actions you wanted. When all is done you may click on the start button.\n" +
                "           To see when you set the action you can see it in the list or calendar");

        //Adds to panel
        this.add(instructionsArea);
        this.add(startButton);
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, -40, -25, getWidth()-buttonWidth-105, getHeight(), this);
    }
}
