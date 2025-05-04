package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Instructions extends JPanel {
//    private static final JButton startButton = new JButton("start"); // To start/continue the action
    private final JTextPane instructionsArea; // Helps the user around
    Image backgroundImage;
    private JLayeredPane layeredPane;

    {
        try {
            backgroundImage = ImageIO.read(new File("src/Resources/cosmetics/ezgif.com-cropfgt.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    ImageIcon icon = new ImageIcon("src/Resources/cosmetics/info.png");
    private JButton infoButton = new JButton();
    private double givenScaleX=1.0;
    private double givenScaley=1.0;

    public Instructions(int windowWidth, int windowHeight,JButton startButton,double scaleX,double scaleY){
        //Panel sets
        this.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        this.setVisible(false);
//        this.setBackground(Color.gray);
        this.setPreferredSize(new Dimension(windowWidth-16,(windowHeight/2)-(windowHeight/10)-20));
        //Text and button sets
        int clearSpace = (windowWidth-16)-(((windowWidth-16)/2)+(windowWidth-16)/7)-(windowWidth-16)/8;
        Image image = icon.getImage().getScaledInstance((int) (icon.getIconWidth()*scaleX), (int) (icon.getIconHeight()*scaleY), Image.SCALE_SMOOTH);
        infoButton.setIcon(new ImageIcon(image));
        infoButton.setPreferredSize(new Dimension((int) (50*scaleX), (int) (50*scaleY)));
        infoButton.setOpaque(false);
        infoButton.setBorderPainted(false);
        givenScaleX=scaleX;
        givenScaley=scaleY;
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension((windowWidth-16)/8,(windowHeight/2)-(windowHeight/10)-25));
        infoButton.setBounds(layeredPane.getWidth()/2,0,(int) (50*scaleX), (int) (50*scaleY));
        layeredPane.add(startButton,JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(infoButton,JLayeredPane.PALETTE_LAYER);
        instructionsArea = new JTextPane();
        instructionsArea.setEnabled(false);
        instructionsArea.setDisabledTextColor(Color.black);
        instructionsArea.setFont(new Font("Arial", Font.PLAIN, (int) (20*scaleY)));
        instructionsArea.setAutoscrolls(true);
        instructionsArea.setOpaque(false);
        instructionsArea.setPreferredSize(new Dimension((((windowWidth-16)/2)+(windowWidth-16)/7)+clearSpace,(windowHeight/2)-(windowHeight/10)-25));
        instructionsArea.setText("\nFor a start you will need to enter your timing and demands.\n " +
                "Click on the set time afterwards you are done you will be asked to set each\n"+" of the actions you wanted. When all is done you may click on the start button.\n" +
                "To see when you set the action you can see it in the list or calendar (only when they are set!)\n");
        StyledDocument doc = instructionsArea.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(1, doc.getLength(), center, false);

        //Adds to panel
        this.add(instructionsArea);
        this.add(layeredPane);
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, (int)(-40*givenScaleX),(int) (-25*givenScaley), (int)(getWidth()-105*givenScaleX), (int)(getHeight()*givenScaley), this);
    }
}
