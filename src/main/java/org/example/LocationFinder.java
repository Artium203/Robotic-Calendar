package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.MultiResolutionImage;

public class LocationFinder extends JFrame {
    private Circle circle;
    private static final Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    private MultiResolutionImage image;
    private Image picture;
    private JButton location = new JButton("Confirm Location");
    public LocationFinder(){
        try {
            Rectangle rectangle = new Rectangle(size.width,size.height);
            Robot robot = new Robot();
            robot.createMultiResolutionScreenCapture(rectangle);
            this.image = robot.createMultiResolutionScreenCapture(rectangle);
            this.picture = this.image.getResolutionVariant(50,50);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        setTitle("Circle Moving GUI");
        setExtendedState(MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        location.setPreferredSize(new Dimension(50,50));
        location.setLocation(size.width/2,0);
        location.addActionListener(e -> {
            System.out.println(circle.getX() +":"+ circle.getY());
            location.setIcon(new ImageIcon(picture));
        });

        // Create a panel to hold the circle
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(picture, 0, 0, null);
                if (circle != null) {
                    circle.draw(g);
                }
            }
        };
        panel.setBackground(Color.WHITE);

        // Add mouse listener to the panel
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Create a new circle when mouse is pressed and no circle exists yet
                if (circle == null) {
                    circle = new Circle(e.getX(), e.getY(), 20);
                    panel.repaint();
                }
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Move the circle to the mouse position when dragged
                if (circle != null) {
                    circle.setX(e.getX());
                    circle.setY(e.getY());
                    panel.repaint();
                }
            }
        });
        panel.add(location);
        add(panel);
    }
}
