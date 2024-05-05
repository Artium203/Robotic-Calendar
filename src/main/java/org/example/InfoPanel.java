package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class InfoPanel extends JPanel {
    private JPanel listPanel;
    private JLabel listActionTime;

    private static Queue<String> plansList = new LinkedList<>();


    public InfoPanel(int windowWidth, int windowHeight,String plans){
        this.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.setVisible(false);
        this.setBackground(Color.red);
        this.setPreferredSize(new Dimension((windowWidth/2)-17,windowHeight/2));

        listPanel = new JPanel();
        listPanel.setBackground(Color.gray);
        listPanel.setPreferredSize(new Dimension((windowWidth/2)-27,(windowHeight/2)-(windowHeight/60)));
        listActionTime = new JLabel();
        listActionTime.setPreferredSize(new Dimension((windowWidth/2)-27,(windowHeight/2)-(windowHeight/60)));
        addToLastTwoPlans(plans);
        listActionTime.setText("<html> "+ lastTwoPlansString()+ "</html>");
        listPanel.add(listActionTime);
        this.add(listPanel);
    }
    public String lastTwoPlansString(){
        StringBuilder get = new StringBuilder();
        for (String plan : plansList){
            get.append(plan);
            get.append("<br><br>");
        }
        return get.toString();
    }
    public void addToLastTwoPlans(String plan){
        if (this.plansList.size() > 9){
            plansList.remove();
            plansList.add(plan);
        }
        else {
            plansList.add(plan);
        }
    }
}
