package org.example.cosmetics;

import javax.swing.*;
import java.awt.*;

public class MyCheckBoxUI extends JCheckBox {
    private final Image backgroundImage;
    private double givenScaleX, givenScaleY;
    public MyCheckBoxUI(String text, String imagePath,double scaleX, double scaleY) {
        super(text);
        this.backgroundImage = new ImageIcon(imagePath).getImage();
        this.givenScaleX = scaleX;
        this.givenScaleY = scaleY;
        setOpaque(false);
    }
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0,getWidth(),getHeight(), this);
        super.paintComponent(g);
    }
}
