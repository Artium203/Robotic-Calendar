package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class MAM extends JPanel{

    private Utils utils;
    private JPanel boxList; // Contains checkers of user's input
    private List<JCheckBox> performanceList; // The checkers of user input
    private JPanel boxOfCommand; // Box the contains the commands for the checked action, info of it's input and explanation

    private JPanel repeat; // The repeater  option
    private JCheckBox loop; // The looper (if user want it to be in loops)
    private JSpinner countOfRepeat; // The amount of the action to repeat itself

    //The time given for action to live if repeater is selected
    private JSpinner frequencyAmountHour;
    private JSpinner frequencyAmountMinute;
    private JSpinner frequencyAmountSecond;
    private final JButton confirmRepeat = new JButton("CONFIRM");

    // Time to live for the loop if he is selected
    private final JPanel timeToLive;
    private JSpinner lifeHour;
    private JSpinner lifeMinute;
    private JSpinner lifeSecond;
    private final JButton confirmLoop = new JButton("CONFIRM");

    private final JPanel nextOrPrevious; // The place for user to go on in the action list
    private static final JButton next = new JButton("Next"); // Going forward in checkers list
    private static final JButton previous = new JButton("Previous");// Going backwards in checkers list
    private final JPanel location; // The place where user will select the place of action
    private final JButton pointLocation = new JButton("Point The Location"); //Will transform to user's desktop/screen

    // Explanations for user's need
    private final JPanel instructions;
    private JLabel instructionsText;

    //Place to show the user his time input and the changes of inputting time for action
    private final JPanel timeMonitor;
    private JLabel timeChecker;
    private final JButton confirmSelection = new JButton("Confirm Your Selection");
    private int HOURS;
    private int MINUTES;
    private int SECONDS;

    //In future wait
    private List<Integer> saveDataOfIndex;
    private Timer actionLife;
    private int currentIndex = 0;
    List<Boolean> isSelectedOnThePast = new ArrayList<>();
    private Map<Integer, List<Integer>> savingsMap = new HashMap<>();






    public MAM(int windowWidth, int windowHeight, List<String> actionList,
               int startHour, int startMinute, int startSecond, int endHour,
               int endMinute, int endSecond, Utils util) {
        //Setting for class MAM
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setPreferredSize(new Dimension(windowWidth - 8, windowHeight - (windowHeight / 10) - 11));
        this.setVisible(false);
        this.setBackground(Color.green);
        this.utils = util;


        //Setting for List of Check boxes
        boxList = new JPanel();
        boxList.setPreferredSize(new Dimension(windowWidth / 4, 31 + (windowHeight / 6) * 5));
        boxList.setBackground(Color.DARK_GRAY);
        boxList.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("List Of Commands"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        ButtonGroup checkGroup = new ButtonGroup();
        performanceList = new ArrayList<>();



        if (!actionList.isEmpty()) {
            for (int i = 0; i < actionList.size(); i++) { // Creation of check boxes and his settings
                performanceList.add(new JCheckBox());
                performanceList.get(i).setPreferredSize(new Dimension((windowWidth / 6), (windowHeight / 10) - 15));
                performanceList.get(i).setText(actionList.get(i));
                boxList.add(performanceList.get(i));
                checkGroup.add(performanceList.get(i));
                performanceList.get(i).setEnabled(false);
                performanceList.get(0).setSelected(true);
                isSelectedOnThePast.add(0 , true);
//                if (i == currentIndex) {
//                }
                if (i > 0){ // In testing
                    isSelectedOnThePast.add(i , false);
                }
            }
            // In testing
            next.addActionListener(e -> {
                if (currentIndex < actionList.size() - 1) {
                    saveDataOfIndex.add((Integer) countOfRepeat.getValue());
                    saveDataOfIndex.add((Integer) frequencyAmountHour.getValue());
                    saveDataOfIndex.add((Integer) frequencyAmountMinute.getValue());
                    saveDataOfIndex.add((Integer) frequencyAmountSecond.getValue());
                    savingsMap.put(currentIndex,saveDataOfIndex);
                    saveDataOfIndex = new ArrayList<>();
                    currentIndex++;
                    performanceList.get(currentIndex).setSelected(true);
                    if (!savingsMap.containsKey(currentIndex)) {
                        countOfRepeat.setValue(1);
                        frequencyAmountHour.setValue(0);
                        frequencyAmountMinute.setValue(0);
                        frequencyAmountSecond.setValue(0);
                        isSelectedOnThePast.set(currentIndex , true);
                    }
                }
            });
            previous.addActionListener(e -> {
                if (currentIndex > 0) {
                    currentIndex--;
                    // <----map here
                    System.out.println(savingsMap);
                    for (int i = 0; i < savingsMap.get(currentIndex).size(); i++) {
                        switch (i){
                            case 0:countOfRepeat.setValue(savingsMap.get(currentIndex).get(i));
                            case 1:frequencyAmountHour.setValue(savingsMap.get(currentIndex).get(i));
                            case 2:frequencyAmountMinute.setValue(savingsMap.get(currentIndex).get(i));
                            case 3:frequencyAmountSecond.setValue(savingsMap.get(currentIndex).get(i));
                        }
                    }
                    isSelectedOnThePast.set(currentIndex , true);
                    performanceList.get(currentIndex).setSelected(true);
                }
            });
        }

        //Settings of the commands box
        boxOfCommand = new JPanel();
        boxOfCommand.setPreferredSize(new Dimension(windowWidth - (windowWidth / 4) - 25, 31 + (windowHeight / 6) * 5));
        boxOfCommand.setBackground(Color.orange);
        boxOfCommand.setLayout(new GridLayout(3, 3));

        //Settings of the repeater channel
        repeat = new JPanel();
        repeat.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Repeats"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        repeat.setBackground(Color.cyan);
        repeat.setLayout(new FlowLayout(FlowLayout.CENTER));
        //Loop declares if there shall be a loop
        loop = new JCheckBox("Loop?");
        loop.setPreferredSize(new Dimension(windowWidth / 15, (windowHeight / 10) - 15));
        loop.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("LOOP"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        loop.addActionListener(e -> {
            if (loop.isSelected()){
                countOfRepeat.setEnabled(false);
                confirmRepeat.setEnabled(false);
                lifeHour.setEnabled(true);
                lifeMinute.setEnabled(true);
                lifeSecond.setEnabled(true);
                confirmLoop.setEnabled(true);
            }else {
                countOfRepeat.setEnabled(true);
                confirmRepeat.setEnabled(true);
                lifeHour.setEnabled(false);
                lifeMinute.setEnabled(false);
                lifeSecond.setEnabled(false);
                confirmLoop.setEnabled(false);
            }
        });
        repeat.add(loop);

        //Says the amount of times the action shall repeat
        countOfRepeat = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        countOfRepeat.setPreferredSize(new Dimension(windowWidth / 17, (windowHeight / 10) - 15));
        countOfRepeat.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Counter"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        ((JSpinner.DefaultEditor) countOfRepeat.getEditor()).getTextField().setEditable(false);
        repeat.add(countOfRepeat);

        // Time input for the action to repeat
        frequencyAmountHour = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
        frequencyAmountHour.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Hours"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        frequencyAmountMinute = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        frequencyAmountMinute.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Minutes"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        frequencyAmountSecond = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        frequencyAmountSecond.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Seconds"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        ((JSpinner.DefaultEditor) frequencyAmountHour.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor) frequencyAmountMinute.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor) frequencyAmountSecond.getEditor()).getTextField().setEditable(false);

        frequencyAmountHour.setPreferredSize(new Dimension(windowWidth / 17, (windowHeight / 10) - 15));
        frequencyAmountMinute.setPreferredSize(new Dimension(windowWidth / 17, (windowHeight / 10) - 15));
        frequencyAmountSecond.setPreferredSize(new Dimension(windowWidth / 17, (windowHeight / 10) - 15));

        // Confirms user's input of the amount to repeat and puts it on the clock
        confirmRepeat.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        confirmRepeat.addActionListener(e -> {
            if (endHour == (Integer.parseInt(countOfRepeat.getValue().toString())*startHour) + Integer.parseInt(frequencyAmountHour.getValue().toString()) &&
                    endMinute == (Integer.parseInt(countOfRepeat.getValue().toString())*startMinute) + Integer.parseInt(frequencyAmountMinute.getValue().toString()) &&
                    endSecond == (Integer.parseInt(countOfRepeat.getValue().toString())*startSecond) + Integer.parseInt(frequencyAmountSecond.getValue().toString())){
                HOURS = (Integer.parseInt(countOfRepeat.getValue().toString())*startHour) + Integer.parseInt(frequencyAmountHour.getValue().toString());
                MINUTES = (Integer.parseInt(countOfRepeat.getValue().toString())*startMinute) + Integer.parseInt(frequencyAmountMinute.getValue().toString());
                SECONDS = (Integer.parseInt(countOfRepeat.getValue().toString())*startSecond) + Integer.parseInt(frequencyAmountSecond.getValue().toString());
                updateText();
            } else if (endHour == (Integer.parseInt(countOfRepeat.getValue().toString())*startHour) + Integer.parseInt(frequencyAmountHour.getValue().toString()) &&
                    endMinute >= (Integer.parseInt(countOfRepeat.getValue().toString())*startMinute) + Integer.parseInt(frequencyAmountMinute.getValue().toString()) &&
                    endSecond >= (Integer.parseInt(countOfRepeat.getValue().toString())*startSecond) + Integer.parseInt(frequencyAmountSecond.getValue().toString())) {
                HOURS = (Integer.parseInt(countOfRepeat.getValue().toString())*startHour) + Integer.parseInt(frequencyAmountHour.getValue().toString());
                MINUTES = (Integer.parseInt(countOfRepeat.getValue().toString())*startMinute) + Integer.parseInt(frequencyAmountMinute.getValue().toString());
                SECONDS = (Integer.parseInt(countOfRepeat.getValue().toString())*startSecond) + Integer.parseInt(frequencyAmountSecond.getValue().toString());
                updateText();
            } else if (endHour == (Integer.parseInt(countOfRepeat.getValue().toString())*startHour) + Integer.parseInt(frequencyAmountHour.getValue().toString()) &&
                    endMinute == (Integer.parseInt(countOfRepeat.getValue().toString())*startMinute) + Integer.parseInt(frequencyAmountMinute.getValue().toString()) &&
                    endSecond >= (Integer.parseInt(countOfRepeat.getValue().toString())*startSecond) + Integer.parseInt(frequencyAmountSecond.getValue().toString())) {
                HOURS = (Integer.parseInt(countOfRepeat.getValue().toString())*startHour) + Integer.parseInt(frequencyAmountHour.getValue().toString());
                MINUTES = (Integer.parseInt(countOfRepeat.getValue().toString())*startMinute) + Integer.parseInt(frequencyAmountMinute.getValue().toString());
                SECONDS = (Integer.parseInt(countOfRepeat.getValue().toString())*startSecond) + Integer.parseInt(frequencyAmountSecond.getValue().toString());
                updateText();
            } else if (endHour > (Integer.parseInt(countOfRepeat.getValue().toString())*startHour) + Integer.parseInt(frequencyAmountHour.getValue().toString())){
                HOURS = (Integer.parseInt(countOfRepeat.getValue().toString())*startHour) + Integer.parseInt(frequencyAmountHour.getValue().toString());
                MINUTES = (Integer.parseInt(countOfRepeat.getValue().toString())*startMinute) + Integer.parseInt(frequencyAmountMinute.getValue().toString());
                SECONDS = (Integer.parseInt(countOfRepeat.getValue().toString())*startSecond) + Integer.parseInt(frequencyAmountSecond.getValue().toString());
                updateText();
            }
        });
        repeat.add(frequencyAmountHour);
        repeat.add(frequencyAmountMinute);
        repeat.add(frequencyAmountSecond);
        repeat.add(confirmRepeat);

        // Same thing as the repeater but without amount only loop
        timeToLive = new JPanel();
        timeToLive.setBackground(Color.PINK);
        timeToLive.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Time For Loop To Live"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        lifeHour = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));

        ((JSpinner.DefaultEditor) lifeHour.getEditor()).getTextField().setEditable(false);
        lifeHour.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Hour"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        lifeHour.setPreferredSize(new Dimension(windowWidth / 17, (windowHeight / 10) - 15));

        lifeMinute = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        ((JSpinner.DefaultEditor) lifeMinute.getEditor()).getTextField().setEditable(false);
        lifeMinute.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Minute"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        lifeMinute.setPreferredSize(new Dimension(windowWidth / 17, (windowHeight / 10) - 15));

        lifeSecond = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        ((JSpinner.DefaultEditor) lifeSecond.getEditor()).getTextField().setEditable(false);
        lifeSecond.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Second"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        lifeSecond.setPreferredSize(new Dimension(windowWidth / 17, (windowHeight / 10) - 15));
        lifeHour.setEnabled(false);
        lifeMinute.setEnabled(false);
        lifeSecond.setEnabled(false);
        confirmLoop.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        confirmLoop.setEnabled(false);
        timeToLive.add(lifeHour);
        timeToLive.add(lifeMinute);
        timeToLive.add(lifeSecond);
        timeToLive.add(confirmLoop);

        //The buttons for going back or forward from the list
        nextOrPrevious = new JPanel();
        nextOrPrevious.setBackground(Color.red);
        nextOrPrevious.setLayout(new BorderLayout(0, 10));
        nextOrPrevious.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Go Back/Go Forward"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        next.setPreferredSize(new Dimension(windowWidth / 6, windowHeight / 8));
        saveDataOfIndex = new ArrayList<>();
        previous.setPreferredSize(new Dimension(windowWidth / 6, windowHeight / 8));
        nextOrPrevious.add(next, BorderLayout.NORTH);
        nextOrPrevious.add(previous, BorderLayout.SOUTH);

        //Shows user's input of the time and updates it until the end, also the confirm buttons of all user's input
        timeMonitor = new JPanel();
        timeMonitor.setBackground(Color.GREEN);
        timeMonitor.setLayout(new GridLayout(2,0));
        timeMonitor.setEnabled(false);
        confirmSelection.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));

        timeChecker = new JLabel((startHour + Integer.parseInt(frequencyAmountHour.getValue().toString())) + ":" +
                (startMinute + Integer.parseInt(frequencyAmountMinute.getValue().toString())) + ":" +
                (startSecond + Integer.parseInt(frequencyAmountSecond.getValue().toString())));
        timeChecker.setFont(new Font("Arial", Font.BOLD, 50));
        timeMonitor.add(timeChecker);
        timeMonitor.add(confirmSelection);

        //User's pointer for the location of current action
        location = new JPanel();
        location.setBackground(Color.MAGENTA);
        location.setLayout(new GridLayout());
        location.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Point Robot's Action Point"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        pointLocation.addActionListener(e -> {
            utils.setWindowVisibility(false);
            System.out.println("sos");
            SwingUtilities.invokeLater(() -> {
                LocationFinder gui = new LocationFinder();
                gui.setVisible(true);
            });
        });
        location.add(pointLocation);

        //Instruction of how to put and what things do
        instructions = new JPanel();
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
    private void updateText(){
        timeChecker.setText(HOURS + ":" +
                MINUTES + ":" +
                SECONDS);
    }
    public static boolean isTimeInputValid(){
        boolean result = false;

        return result;
    }

    public boolean hasChanged(int currentIndex){
        boolean result = false;
        for (int i = 0; i < savingsMap.size(); i++) {
         if (savingsMap.get(currentIndex).get(i) == (Integer) countOfRepeat.getValue() || savingsMap.get(currentIndex).get(i) == (Integer) frequencyAmountHour.getValue()
         || savingsMap.get(currentIndex).get(i) == (Integer) frequencyAmountMinute.getValue() || savingsMap.get(currentIndex).get(i) == (Integer) frequencyAmountSecond.getValue()){
             result=true;
         }
        }
        return result;
    }

}