package org.example;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MAM extends JPanel implements ActionListener {
    //In future wait
    private JPanel boxList;
    private List<JCheckBox> performanceList;
    private JPanel boxOfCommand;

    private final JPanel repeat;
    private JCheckBox loop;
    private JSpinner countOfRepeat;
    private JSpinner frequencyAmountHour;
    private JSpinner frequencyAmountMinute;
    private JSpinner frequencyAmountSecond;

    private final JPanel timeToLive;
    private JSpinner lifeHour;
    private JSpinner lifeMinute;
    private JSpinner lifeSecond;

    private final JPanel nextOrPrevious;
    private static final JButton next = new JButton("Next");
    private static final JButton previous = new JButton("Previous");
    private final JPanel location;
    private static final JButton pointLocation = new JButton("Point The Location");
    private final  JPanel instructions;
    private JLabel instructionsText;
    private final JPanel timeMonitor;
    private JLabel timeChecker;




    public MAM (int windowWidth, int windowHeight, List<String> actionList,
                int startHour, int startMinute, int startSecond, int endHour,
                int endMinute, int endSecond){

        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setPreferredSize(new Dimension(windowWidth-8,windowHeight-(windowHeight/10)-11));
        this.setVisible(false);
        this.setBackground(Color.green);

        boxList = new JPanel();
        boxList.setPreferredSize(new Dimension(windowWidth/4,31+(windowHeight/6)*5));
        boxList.setBackground(Color.DARK_GRAY);
        boxList.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("List Of Commands"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

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
        repeat.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Repeats"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        repeat.setBackground(Color.cyan);
        repeat.setLayout(new FlowLayout(FlowLayout.CENTER));
        loop = new JCheckBox("Loop?");
        loop.setPreferredSize(new Dimension(windowWidth/15,(windowHeight/10)-15));
        loop.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("LOOP"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        repeat.add(loop);

        countOfRepeat = new JSpinner(new SpinnerNumberModel(1,1,99,1));
        countOfRepeat.setPreferredSize(new Dimension(windowWidth/17,(windowHeight/10)-15));
        countOfRepeat.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Counter"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        ((JSpinner.DefaultEditor) countOfRepeat.getEditor()).getTextField().setEditable(false);
        repeat.add(countOfRepeat);

        frequencyAmountHour = new JSpinner(new SpinnerNumberModel(0,0,23,1));
        frequencyAmountHour.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Hours"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        frequencyAmountMinute = new JSpinner(new SpinnerNumberModel(0,0,59,1));
        frequencyAmountMinute.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Minutes"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        frequencyAmountSecond = new JSpinner(new SpinnerNumberModel(0,0,59,1));
        frequencyAmountSecond.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Seconds"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        ((JSpinner.DefaultEditor) frequencyAmountHour.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor) frequencyAmountMinute.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor) frequencyAmountSecond.getEditor()).getTextField().setEditable(false);

        frequencyAmountHour.setPreferredSize(new Dimension(windowWidth/17,(windowHeight/10)-15));
        frequencyAmountMinute.setPreferredSize(new Dimension(windowWidth/17,(windowHeight/10)-15));
        frequencyAmountSecond.setPreferredSize(new Dimension(windowWidth/17,(windowHeight/10)-15));

        repeat.add(frequencyAmountHour);
        repeat.add(frequencyAmountMinute);
        repeat.add(frequencyAmountSecond);



        timeToLive =new JPanel();
        timeToLive.setBackground(Color.PINK);
        timeToLive.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Time For Loop To Live"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        lifeHour = new JSpinner(new SpinnerNumberModel(0,0,23,1));
        ((JSpinner.DefaultEditor) lifeHour.getEditor()).getTextField().setEditable(false);
        lifeHour.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Hour"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        lifeHour.setPreferredSize(new Dimension(windowWidth/17,(windowHeight/10)-15));

        lifeMinute =new JSpinner(new SpinnerNumberModel(0,0,59,1));
        ((JSpinner.DefaultEditor) lifeMinute.getEditor()).getTextField().setEditable(false);
        lifeMinute.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Minute"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        lifeMinute.setPreferredSize(new Dimension(windowWidth/17,(windowHeight/10)-15));

        lifeSecond =new JSpinner(new SpinnerNumberModel(0,0,59,1));
        ((JSpinner.DefaultEditor) lifeSecond.getEditor()).getTextField().setEditable(false);
        lifeSecond.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Second"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        lifeSecond.setPreferredSize(new Dimension(windowWidth/17,(windowHeight/10)-15));
        timeToLive.add(lifeHour);
        timeToLive.add(lifeMinute);
        timeToLive.add(lifeSecond);

        nextOrPrevious = new JPanel();
        nextOrPrevious.setBackground(Color.red);
        nextOrPrevious.setLayout(new BorderLayout(0,10));
        nextOrPrevious.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Go Back/Go Forward"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        next.setPreferredSize(new Dimension(windowWidth/6,windowHeight/8));
        next.addActionListener(this);
        previous.setPreferredSize(new Dimension(windowWidth/6,windowHeight/8));
        previous.addActionListener(this);
        nextOrPrevious.add(next,BorderLayout.NORTH);
        nextOrPrevious.add(previous,BorderLayout.SOUTH);

        timeMonitor =new JPanel();
        timeMonitor.setBackground(Color.GREEN);
        timeMonitor.setLayout(new GridLayout());
        frequencyAmountHour.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (endHour > Integer.parseInt(frequencyAmountHour.getValue().toString())){
                    timeChecker.setText((startHour+Integer.parseInt(frequencyAmountHour.getValue().toString()))+":"+
                            startMinute+":"+
                            startSecond);
                }
            }
        });
        frequencyAmountMinute.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (endMinute > Integer.parseInt(frequencyAmountMinute.getValue().toString()) && endHour == Integer.parseInt(frequencyAmountHour.getValue().toString())){
                    timeChecker.setText(startHour+":"+
                            (startMinute+Integer.parseInt(frequencyAmountMinute.getValue().toString()))+":"+
                            startSecond);
                }
            }
        });
        frequencyAmountSecond.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (endSecond > Integer.parseInt(frequencyAmountSecond.getValue().toString())){
                    timeChecker.setText(startHour+":"+
                            startMinute+":"+
                            (startSecond+Integer.parseInt(frequencyAmountSecond.getValue().toString())));
                }
            }
        });
        timeChecker = new JLabel((startHour+Integer.parseInt(frequencyAmountHour.getValue().toString()))+":"+
                (startMinute+Integer.parseInt(frequencyAmountMinute.getValue().toString()))+":"+
                (startSecond+Integer.parseInt(frequencyAmountSecond.getValue().toString())));
        timeChecker.setFont(new Font("Arial" , Font.BOLD , 50));
        timeMonitor.add(timeChecker);


        location =new JPanel();
        location.setBackground(Color.MAGENTA);
        location.setLayout(new GridLayout());
        location.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Point Robot's Action Point"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        location.add(pointLocation);

        instructions =new JPanel();
        instructions.setBackground(Color.yellow);
        instructions.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Help For Understatement"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        instructions.setLayout(new GridLayout());
        instructionsText = new JLabel("Questions For Settings");


        instructions.add(instructionsText);

        boxOfCommand.add(repeat);
        boxOfCommand.add(timeToLive);
        boxOfCommand.add(nextOrPrevious);
        boxOfCommand.add(timeMonitor);
        boxOfCommand.add(location);
        boxOfCommand.add(instructions);


        this.add(boxList);
        this.add(boxOfCommand);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
