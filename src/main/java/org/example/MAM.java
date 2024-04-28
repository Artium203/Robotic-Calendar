package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MAM extends JPanel {
    //In future wait
    private JPanel boxList;
    private List<JCheckBox> performanceList;
    private JPanel boxOfCommand;

    private JPanel repeat;
    private JPanel timeToLive;
    private JPanel nextOrPrevious;
    private JPanel location;
    private  JPanel instructions;
    private JPanel timeMonitor;




    public MAM (int windowWidth, int windowHeight, List<String> actionList){

        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setPreferredSize(new Dimension(windowWidth-8,windowHeight-(windowHeight/10)-11));
        this.setVisible(false);
        this.setBackground(Color.green);

        boxList = new JPanel();
        boxList.setPreferredSize(new Dimension(windowWidth/4,31+(windowHeight/6)*5));
        boxList.setBackground(Color.DARK_GRAY);

        ButtonGroup checkGroup = new ButtonGroup();
        performanceList = new ArrayList<>();
        if (!actionList.isEmpty()){
            for (int i = 0; i < actionList.size(); i++) {
                performanceList.add(new JCheckBox());
                performanceList.get(i).setPreferredSize(new Dimension((windowWidth/6),(windowHeight/10)-15));
                performanceList.get(i).setText(actionList.get(i));
                boxList.add(performanceList.get(i));
                checkGroup.add(performanceList.get(i));
            }
        }
        boxOfCommand = new JPanel();
        boxOfCommand.setPreferredSize(new Dimension(windowWidth-(windowWidth/4)-25,31+(windowHeight/6)*5));
        boxOfCommand.setBackground(Color.orange);
        boxOfCommand.setLayout(new GridLayout(3,3));

        repeat = new JPanel();
        repeat.setBackground(Color.cyan);
        timeToLive =new JPanel();
        timeToLive.setBackground(Color.BLACK);
        nextOrPrevious = new JPanel();
        nextOrPrevious.setBackground(Color.red);
        timeMonitor =new JPanel();
        timeMonitor.setBackground(Color.GREEN);
        location =new JPanel();
        location.setBackground(Color.MAGENTA);
        instructions =new JPanel();
        instructions.setBackground(Color.yellow);

        boxOfCommand.add(repeat);
        boxOfCommand.add(timeToLive);
        boxOfCommand.add(nextOrPrevious);
        boxOfCommand.add(timeMonitor);
        boxOfCommand.add(location);
        boxOfCommand.add(instructions);


        this.add(boxList);
        this.add(boxOfCommand);
    }
}
