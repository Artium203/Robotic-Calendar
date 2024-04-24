package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MAM extends JPanel {
    //In future wait
    private JPanel boxList;
    private List<JCheckBox> performanceList;
    public MAM(int windowWidth, int windowHeight, List<String> actionList){
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setPreferredSize(new Dimension(windowWidth-8,windowHeight-(windowHeight/10)-11));
        this.setVisible(false);
        this.setBackground(Color.green);



        boxList = new JPanel();
        boxList.setPreferredSize(new Dimension(windowWidth/5,31+(windowHeight/6)*5));
        boxList.setBackground(Color.DARK_GRAY);

        performanceList = new ArrayList<>();
        if (!actionList.isEmpty()){
            System.out.println(actionList.size());
            for (int i = 0; i < 10; i++) {
                performanceList.add(new JCheckBox());
                performanceList.get(i).setPreferredSize(new Dimension((windowWidth/6),(windowHeight/10)-15));
                performanceList.get(i).setText(actionList.get(i));
                boxList.add(performanceList.get(i));
            }
        }
        this.add(boxList);
    }
}
