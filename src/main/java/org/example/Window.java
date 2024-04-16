package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.security.Security;

public class Window extends JFrame implements ActionListener {
    private static  JButton openingPoint = new JButton("Calendar");
    Calendar calendar;
    private static  JButton timingPoint = new JButton("Set Time");
    TimeSet timer;
    private static  JButton actionPoint = new JButton("Make A Move");
    MAM action;
    private static  JButton startPoint = new JButton();
    private static  JButton exit = new JButton(new ImageIcon("src/Resources/x.png"));
    private static  JButton goDown = new JButton(new ImageIcon("src/Resources/hide.png"));
    private static Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    private final int windowWidth = size.width;
    private final int windowHeight = size.height;

    private static JPanel panel1 = new JPanel();
    private static JPanel panel2 = new JPanel();
//    private static JPanel panel3 = new JPanel();
    public Window(){
        this.setTitle("Robotic Calendar");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        openingPoint.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        openingPoint.setFont(new Font("Arial",Font.BOLD, 26));
        openingPoint.setForeground(Color.white);
        openingPoint.setBackground(Color.blue);
        openingPoint.setFocusPainted(false);

        timingPoint.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        timingPoint.setFont(new Font("Arial",Font.BOLD, 26));
        timingPoint.setForeground(Color.white);
        timingPoint.setBackground(Color.blue);
        timingPoint.setFocusPainted(false);

        actionPoint.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        actionPoint.setFont(new Font("Arial",Font.BOLD, 26));
        actionPoint.setForeground(Color.white);
        actionPoint.setBackground(Color.blue);
        actionPoint.setFocusPainted(false);

        openingPoint.addActionListener(this);
        timingPoint.addActionListener(this);
        actionPoint.addActionListener(this);

        panel1.setBackground(Color.gray);
        panel1.setPreferredSize(new Dimension((windowWidth/2)-8,windowHeight/10));
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER,1,1));
        panel1.add(openingPoint);
        panel1.add(timingPoint);
        panel1.add(actionPoint);

        exit.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        exit.addActionListener(this);
        goDown.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        goDown.addActionListener(this);

        panel2.setBackground(Color.gray);
        panel2.setPreferredSize(new Dimension((windowWidth/2)-8,windowHeight/10));
        panel2.setLayout(new FlowLayout(FlowLayout.RIGHT,5,1));
        panel2.add(goDown);
        panel2.add(exit);

        this.add(panel1);
        this.add(panel2);

        this.timer = new TimeSet(windowWidth,windowHeight);
        this.add(timer);
        this.calendar = new Calendar(windowWidth,windowHeight);
        this.add(calendar);
        this.action =new MAM(windowWidth,windowHeight);
        this.add(action);



    }
    public void showWindow(){
        this.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==exit){
            System.exit(0);
//            this.dispose();
        }
        if (e.getSource()==goDown){
            this.setState(JFrame.ICONIFIED);
        }
        timer.setVisible(e.getSource() == timingPoint);
        calendar.setVisible(e.getSource() == openingPoint);
        action.setVisible(e.getSource() == actionPoint);
    }
}
