package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public class Window extends JFrame implements ActionListener,Utils {
    private static Container panel; // Contains the frame of the window mainly used of the calendar at the moment

    private InfoPanel infoPanel; // Shows the list of actions that user have made
    private Instructions instructions; // Shows information of how to use the app
    private List<String> listOfAction = new ArrayList<>();
    private String startHour = "0";
    private String startMinute = "0";
    private String startSecond = "0";
    private String endHour = "0";
    private String endMinute = "0";
    private String endSecond = "0";
    private static final JButton infoPoint = new JButton("Calendar"); // Button that transitions to the calendar of actions/explanation
    private CalendarForProject calendar; // The calendar of years/months/days that may or may not add actions to it
    private static final JButton timingPoint = new JButton("Set Time"); // Button that transitions to the timing of action (10 actions counts as 1)
    private static TimeSet timer; // Sets the time and actions of user's input
    private static final JButton actionPoint = new JButton("Make A Move"); // Button that transitions to the MAM (Make A Move)
    private MAM action; // Sets the time of each action that the user gave and the potions of each action that user gave
    private static final JButton exit = new JButton(new ImageIcon("src/Resources/x.png")); // Exits/closes the window
    private static final JButton goDown = new JButton(new ImageIcon("src/Resources/hide.png")); // Hides/minimises the window

    private static final JButton confirmOption = new JButton("CONFIRM");
    private static final JButton confirmSelection = new JButton("Confirm Your Selection");
    private static final JButton startButton = new JButton("start"); // To start/continue the action
    private static final Dimension size = Toolkit.getDefaultToolkit().getScreenSize(); // Gets the size of user's window
    //User's width/height window
    private final int windowWidth = size.width;
    private final int windowHeight = size.height;
    private final short YEAR=0,MOUNTH=1,DAY=2;
    private final short HS=3,MS=4,SS=5,HE=6,ME=7,SE=8;


    private static JPanel boxOfNavigation = new JPanel(); // Box of the 3 buttons mentioned before
    private static JPanel boxOfWindowOp = new JPanel(); //Box of the 2 window buttons
    //    private static JPanel panel3 = new JPanel();
    private List<Integer> dateATime = new ArrayList<>();
    private DataContainer data;
    private DataHandler handler = new DataHandler();
    private String nameOf;
    private ActionWindow actionWindow;
    private List<DataContainer> dataContainer;
    public Window(){
        //Window setting
        this.setTitle("Robotic Calendar");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        //Sets "info point" button
        infoPoint.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        infoPoint.setFont(new Font("Arial",Font.BOLD, 26));
        infoPoint.setForeground(Color.white);
        infoPoint.setBackground(Color.blue);
        infoPoint.setFocusPainted(false);

        //Sets "timing point" button
        timingPoint.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        timingPoint.setFont(new Font("Arial",Font.BOLD, 26));
        timingPoint.setForeground(Color.white);
        timingPoint.setBackground(Color.blue);
        timingPoint.setFocusPainted(false);

        //Sets "action point" button
        actionPoint.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        actionPoint.setFont(new Font("Arial",Font.BOLD, 26));
        actionPoint.setForeground(Color.white);
        actionPoint.setBackground(Color.blue);
        actionPoint.setEnabled(false);
        actionPoint.setFocusPainted(false);

        //Adds actions to the 3 buttons mentioned before
        for (ActionListener al : infoPoint.getActionListeners()) {
            infoPoint.removeActionListener(al);
        }
        infoPoint.addActionListener(this);
        for (ActionListener al : timingPoint.getActionListeners()) {
            timingPoint.removeActionListener(al);
        }
        timingPoint.addActionListener(this);
        for (ActionListener al : actionPoint.getActionListeners()) {
            actionPoint.removeActionListener(al);
        }
        actionPoint.addActionListener(this);
        //Sets "box of navigation" panel
        boxOfNavigation.setBackground(Color.gray);
        boxOfNavigation.setPreferredSize(new Dimension((windowWidth/2)-8,windowHeight/10));
        boxOfNavigation.setLayout(new FlowLayout(FlowLayout.CENTER,1,1));
        //Adds to the "box of navigation" panel the 3 buttons mentioned before
        boxOfNavigation.add(infoPoint);
        boxOfNavigation.add(timingPoint);
        boxOfNavigation.add(actionPoint);

        //Sets the button of "exit" that exits/closes the window and adds action to it
        exit.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        exit.addActionListener(this);
        //Sets the button of "go down" that hides/minimises the window and adds action to it
        goDown.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        goDown.addActionListener(this);

        //Sets the "box of window operations" and adds the 2 buttons mentioned before
        boxOfWindowOp.setBackground(Color.gray);
        boxOfWindowOp.setPreferredSize(new Dimension((windowWidth/2)-8,windowHeight/10));
        boxOfWindowOp.setLayout(new FlowLayout(FlowLayout.RIGHT,5,1));
        boxOfWindowOp.add(goDown);
        boxOfWindowOp.add(exit);

        //Sets the container
        panel = this.getContentPane(); //Get content pane
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        confirmOption.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        for (ActionListener al : confirmOption.getActionListeners()) {
            confirmOption.removeActionListener(al);
        }
        confirmOption.addActionListener(this);

        for (ActionListener al : confirmSelection.getActionListeners()) {
            confirmSelection.removeActionListener(al);
        }
        confirmSelection.addActionListener(this);
        startButton.setPreferredSize(new Dimension((windowWidth-16)/8,(windowHeight/2)-(windowHeight/10)-25));
        startButton.setBackground(Color.LIGHT_GRAY);
        for (ActionListener al : startButton.getActionListeners()) {
            startButton.removeActionListener(al);
        }
        startButton.addActionListener(this);
        //Adds/makes the operations
        this.add(boxOfNavigation);
        this.add(boxOfWindowOp);
        timer = new TimeSet(windowWidth,windowHeight,confirmOption);
        infoPanel = new InfoPanel(windowWidth,windowHeight,"",0,0,0,"0","0","0");
        this.add(timer);
        this.calendar = new CalendarForProject(windowWidth,windowHeight,timer.getNameAction(), timer.getChosenYear(), timer.getChosenMonth() , timer.getChosenDay());
        this.action =new MAM(windowWidth,windowHeight,listOfAction, Integer.parseInt(startHour),Integer.parseInt(startMinute),
                Integer.parseInt(startSecond),Integer.parseInt(endHour),Integer.parseInt(endMinute),Integer.parseInt(endSecond)
                ,this,confirmSelection);
        this.add(action);
        instructions = new Instructions(windowWidth,windowHeight,startButton);
        panel.add(calendar);
        panel.add(infoPanel);
        panel.add(instructions);
        this.setVisible(true);
    }
    public Window(List<DataContainer> dataContainers){ // if data exits then loud it
        //Window setting
        this.setTitle("Robotic Calendar");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.dataContainer = dataContainers;

        //Sets "info point" button
        infoPoint.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        infoPoint.setFont(new Font("Arial",Font.BOLD, 26));
        infoPoint.setForeground(Color.white);
        infoPoint.setBackground(Color.blue);
        infoPoint.setFocusPainted(false);

        //Sets "timing point" button
        timingPoint.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        timingPoint.setFont(new Font("Arial",Font.BOLD, 26));
        timingPoint.setForeground(Color.white);
        timingPoint.setBackground(Color.blue);
        timingPoint.setFocusPainted(false);

        //Sets "action point" button
        actionPoint.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        actionPoint.setFont(new Font("Arial",Font.BOLD, 26));
        actionPoint.setForeground(Color.white);
        actionPoint.setBackground(Color.blue);
        actionPoint.setEnabled(false);
        actionPoint.setFocusPainted(false);

        //Adds actions to the 3 buttons mentioned before
        for (ActionListener al : infoPoint.getActionListeners()) {
            infoPoint.removeActionListener(al);
        }
        infoPoint.addActionListener(this);
        for (ActionListener al : timingPoint.getActionListeners()) {
            timingPoint.removeActionListener(al);
        }
        timingPoint.addActionListener(this);
        for (ActionListener al : actionPoint.getActionListeners()) {
            actionPoint.removeActionListener(al);
        }
        actionPoint.addActionListener(this);


        //Sets "box of navigation" panel
        boxOfNavigation.setBackground(Color.gray);
        boxOfNavigation.setPreferredSize(new Dimension((windowWidth/2)-8,windowHeight/10));
        boxOfNavigation.setLayout(new FlowLayout(FlowLayout.CENTER,1,1));
        //Adds to the "box of navigation" panel the 3 buttons mentioned before
        boxOfNavigation.add(infoPoint);
        boxOfNavigation.add(timingPoint);
        boxOfNavigation.add(actionPoint);

        //Sets the button of "exit" that exits/closes the window and adds action to it
        exit.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        exit.addActionListener(this);
        //Sets the button of "go down" that hides/minimises the window and adds action to it
        goDown.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        goDown.addActionListener(this);

        //Sets the "box of window operations" and adds the 2 buttons mentioned before
        boxOfWindowOp.setBackground(Color.gray);
        boxOfWindowOp.setPreferredSize(new Dimension((windowWidth/2)-8,windowHeight/10));
        boxOfWindowOp.setLayout(new FlowLayout(FlowLayout.RIGHT,5,1));
        boxOfWindowOp.add(goDown);
        boxOfWindowOp.add(exit);

        //Sets the container
        panel = this.getContentPane(); //Get content pane
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        confirmOption.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        if (confirmOption.getActionListeners().length<1){
            confirmOption.addActionListener(this);
        }
        if (confirmSelection.getActionListeners().length<1) {
            confirmSelection.addActionListener(this);
        }
        startButton.setPreferredSize(new Dimension((windowWidth-16)/8,(windowHeight/2)-(windowHeight/10)-25));
        startButton.setBackground(Color.LIGHT_GRAY);
        if (startButton.getActionListeners().length<1) {
            startButton.addActionListener(this);
        }
        timer = new TimeSet(windowWidth,windowHeight,confirmOption);

        for (int i = 0; i < dataContainers.size(); i++) {
            String thePlan = timer.getPlans(dataContainers.get(i).getDateATime().get(YEAR),dataContainers.get(i).getDateATime().get(MOUNTH),dataContainers.get(i).getDateATime().get(DAY),
                    dataContainers.get(i).getDateATime().get(HS),dataContainers.get(i).getDateATime().get(MS),dataContainers.get(i).getDateATime().get(SS),
                    dataContainers.get(i).getDateATime().get(HE),dataContainers.get(i).getDateATime().get(ME),dataContainers.get(i).getDateATime().get(SE));
            infoPanel = new InfoPanel(windowWidth,windowHeight,thePlan,dataContainers.get(i).getDateATime().get(DAY),dataContainers.get(i).getDateATime().get(MOUNTH),dataContainers.get(i).getDateATime().get(YEAR),
                    String.valueOf(dataContainers.get(i).getDateATime().get(SS)),String.valueOf(dataContainers.get(i).getDateATime().get(MS)),String.valueOf(dataContainers.get(i).getDateATime().get(HS)));
            //Calendar needs to get a list of dates then changing it to a new o
            this.calendar = new CalendarForProject(windowWidth,windowHeight,dataContainers.get(i).getNameOf(), dataContainers.get(i).getDateATime().get(YEAR),
                    dataContainers.get(i).getDateATime().get(MOUNTH) , dataContainers.get(i).getDateATime().get(DAY));
        }
        //Adds/makes the operations
        this.add(boxOfNavigation);
        this.add(boxOfWindowOp);
//        infoPanel = new InfoPanel(windowWidth,windowHeight,"",0,0,0,"0","0","0");
        this.add(timer);
//        this.calendar = new CalendarForProject(windowWidth,windowHeight,timer.getNameAction(), timer.getChosenYear(), timer.getChosenMonth() , timer.getChosenDay());
        this.action =new MAM(windowWidth,windowHeight,listOfAction, Integer.parseInt(startHour),Integer.parseInt(startMinute),
                Integer.parseInt(startSecond),Integer.parseInt(endHour),Integer.parseInt(endMinute),Integer.parseInt(endSecond)
                ,this,confirmSelection);
        this.add(action);
        instructions = new Instructions(windowWidth,windowHeight,startButton);
        panel.add(calendar);
        panel.add(infoPanel);
        panel.add(instructions);
        this.setVisible(true);
    }
    //Sets the visibility
    public void showWindow(){
        this.setVisible(true);

    }

    //Sets the actions
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==exit){
            System.exit(0); // Faster exit than dispose
//            this.dispose();
        }
        if (e.getSource()==goDown){
            this.setState(JFrame.ICONIFIED); // Makes the window hide
        }
        timer.setVisible(e.getSource() == timingPoint);
        confirmOption.setVisible(e.getSource() == timingPoint);
        confirmSelection.setVisible(e.getSource()==actionPoint);
        calendar.setVisible(e.getSource() == infoPoint);
        infoPanel.setVisible(e.getSource() == infoPoint);
        instructions.setVisible(e.getSource() == infoPoint);
        action.setVisible(e.getSource() == actionPoint);
        if (e.getSource() == confirmOption && isFull() &&
                timer.isTimeValid() && timer.isDateValid() && timer.isTodayValid()
                && timer.getActionToList().size()>1){
            dateATime.add(timer.getChosenYear());
            dateATime.add(timer.getChosenMonth()+1);
            dateATime.add(timer.getChosenDay());
            nameOf = timer.getNameAction();
            this.remove(calendar);
            this.calendar = new CalendarForProject(windowWidth,windowHeight,timer.getNameAction(), timer.getChosenYear(), timer.getChosenMonth() , timer.getChosenDay());
            this.add(calendar);
            this.remove(infoPanel);
            infoPanel = new InfoPanel(windowWidth,windowHeight,timer.getPlans(),
                    timer.getChosenDay(),timer.getChosenMonth()+1,timer.getChosenYear(),
                    timer.getSecondStart(),timer.getMinutesStart(),timer.getHoursStart());
            this.add(infoPanel);
            this.remove(instructions);
            instructions = new Instructions(windowWidth,windowHeight,startButton);
            this.add(instructions);
            listOfAction = timer.getActionToList();
            startHour =timer.getHoursStart();
            startMinute =timer.getMinutesStart();
            startSecond =timer.getSecondStart();
            endHour = timer.getHoursEnd();
            endMinute=timer.getMinutesEnd();
            endSecond=timer.getSecondEnd();
            dateATime.add(Integer.valueOf(startHour));
            dateATime.add(Integer.valueOf(startMinute));
            dateATime.add(Integer.valueOf(startSecond));
            dateATime.add(Integer.valueOf(endHour));
            dateATime.add(Integer.valueOf(endMinute));
            dateATime.add(Integer.valueOf(endSecond));
            actionPoint.setEnabled(e.getSource() == confirmOption);
            this.remove(action);
            this.action =new MAM(windowWidth,windowHeight, listOfAction, Integer.parseInt(startHour),
                    Integer.parseInt(startMinute), Integer.parseInt(startSecond), Integer.parseInt(endHour),
                    Integer.parseInt(endMinute), Integer.parseInt(endSecond),this,confirmSelection);
            this.add(action);
            this.remove(timer);
            timer=new TimeSet(windowWidth,windowHeight,confirmOption);
            this.add(timer);
        }
        else if (e.getSource() == confirmOption && !isFull()) { //!timer.isTimeValid() &&
            JOptionPane.showMessageDialog(null,"ERROR IN TIME INPUT","ERROR",JOptionPane.ERROR_MESSAGE);
        } else if (e.getSource() == confirmOption && timer.getActionToList().size()<=1) {
            JOptionPane.showMessageDialog(null,"ERROR AMOUNT OF ACTIONS CAN'T BE ONE OR LESS","ERROR",JOptionPane.ERROR_MESSAGE);
        }
        if (e.getSource()==confirmSelection){
            int amountOfGiven =0,givenEnd=0; // need to fix transitions effect between this class and windows class
            if (action.isMapsFull()){
                for (int i = 0; i < action.getSavingsMap().size(); i++) {
                    if (action.getSavingsMap().get(i).get(0)==1){
                        amountOfGiven += (action.getSavingsMap().get(i).get(5)*60*60)+(action.getSavingsMap().get(i).get(6)*60)+action.getSavingsMap().get(i).get(7);
                    }else{
                        amountOfGiven +=action.getSavingsMap().get(i).get(1)*((action.getSavingsMap().get(i).get(2)*60*60)+(action.getSavingsMap().get(i).get(3)*60)+action.getSavingsMap().get(i).get(4));
                    }
                }
                givenEnd=(MAM.getEndHour()*60*60)+(MAM.getEndMinute()*60)+ MAM.getEndSecond();
                if (amountOfGiven<=givenEnd){
                    JOptionPane.showMessageDialog(null,"YOUR TIME WAS SUCCESSFULLY RECEIVED","ACCEPTED",JOptionPane.INFORMATION_MESSAGE);
                    //<--
//                    File file = new File("test.txt");
                    data = new DataContainer(dateATime,action.getSavingsMap(),nameOf);
                    handler.addDataContainer(data);
                    action.setCurrentIndex(0);
                    action.setSavingsMap();
                    dateATime = new ArrayList<>();
                    actionPoint.setEnabled(false);
                    startHour="0";startMinute="0";startSecond="0";endHour="0";endMinute="0";endSecond="0";
                    this.remove(action);
//                    this.action =new MAM(windowWidth,windowHeight, null, Integer.parseInt(startHour),
//                            Integer.parseInt(startMinute), Integer.parseInt(startSecond), Integer.parseInt(endHour),
//                            Integer.parseInt(endMinute), Integer.parseInt(endSecond),this,confirmSelection);
//                    this.add(action);
                }else {
                    JOptionPane.showMessageDialog(null,
                            "YOUR TIME INPUT WASN'T RIGHT PLEASE CHANGE INPUT\n"+"Given time:"+amountOfGiven+" and not lower or same to:"+ givenEnd,
                            "ERROR",JOptionPane.WARNING_MESSAGE);
                }
            }else {
                JOptionPane.showMessageDialog(null,"YOU DIDN'T FULL YOUR INPUT","ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource()==startButton){
                if (handler.readDataFromFile()==null || handler.readDataFromFile().isEmpty()){
                    JOptionPane.showMessageDialog(null,
                            "THERE IS NOTHING TO START",
                            "ERROR",JOptionPane.WARNING_MESSAGE);
                }else {
                    List<LocalDateTime> localDateTimes = new ArrayList<>();
                    List<DataContainer> containers = handler.readDataFromFile();
                    int theEarliest=0;
                    GregorianCalendar cal = new GregorianCalendar();
                    for (int i = 0; i < containers.size(); i++) {
                        localDateTimes.add(LocalDateTime.of(containers.get(i).getDateATime().get(0),containers.get(i).getDateATime().get(1),containers.get(i).getDateATime().get(2),
                                containers.get(i).getDateATime().get(3),containers.get(i).getDateATime().get(4),containers.get(i).getDateATime().get(5)));
                    }
                    LocalDateTime earliestDate = localDateTimes.get(0);
                    for (int i = 0; i < containers.size(); i++) {
                        if(localDateTimes.get(i).isBefore(earliestDate)){
                            earliestDate =localDateTimes.get(i);
                            theEarliest=i;
                        }
                    }
                    for (int i = 0; i < containers.size(); i++) {
                        if (containers.get(i).getDateATime().get(0)==cal.get(GregorianCalendar.YEAR) &&
                                containers.get(i).getDateATime().get(1)==cal.get(GregorianCalendar.MONTH)&&
                        containers.get(i).getDateATime().get(2)==cal.get(GregorianCalendar.DAY_OF_MONTH)){
                            System.out.println("the nearest is: ");
                            this.setVisible(false);
                            actionWindow = new ActionWindow(containers.get(theEarliest).getActions(),windowWidth,windowHeight,Window.this,theEarliest);
                            break;

                        }else {
                            String[] myChoices = {"Yes", "No"};
                            int choiceMassage =JOptionPane.showOptionDialog(
                                    null,
                                    "Today there are no events.\n " +
                                            "Would you like to skip to the nearest event?",
                                    "Choice massage",
                                    JOptionPane.OK_OPTION,
                                    JOptionPane.INFORMATION_MESSAGE,
                                    null,
                                    myChoices,
                                    myChoices[0]);
                            if (choiceMassage!=-1 && choiceMassage!=1 && containers.size()==1){
                                System.out.println("He clicked yes");
                                this.setVisible(false);
                                actionWindow = new ActionWindow(containers.get(theEarliest).getActions(),windowWidth,windowHeight,Window.this,theEarliest);
                                break;
                            } else if (choiceMassage!=-1 && choiceMassage!=1 && containers.size()>1) {
                                int selected = choices();
                                if (selected!=-1){
                                this.setVisible(false);
                                actionWindow = new ActionWindow(containers.get(selected).getActions(),windowWidth,windowHeight,Window.this,theEarliest);
                                break;
                                }
                            }
                        }
                    }
                    //<--
                    this.remove(infoPanel);
                    this.remove(calendar);
                    this.remove(instructions);
                    if (!dataContainer.isEmpty() && dataContainer!=null) {
                        for (int i = 0; i < dataContainer.size(); i++) {
                            for (int j = 0; j < dataContainer.get(i).getDateATime().size(); j++) {
                                String thePlan = timer.getPlans(dataContainer.get(i).getDateATime().get(0), dataContainer.get(i).getDateATime().get(1), dataContainer.get(i).getDateATime().get(2),
                                        dataContainer.get(i).getDateATime().get(3), dataContainer.get(i).getDateATime().get(4), dataContainer.get(i).getDateATime().get(5),
                                        dataContainer.get(i).getDateATime().get(6), dataContainer.get(i).getDateATime().get(7), dataContainer.get(i).getDateATime().get(8));
                                infoPanel = new InfoPanel(windowWidth, windowHeight, thePlan, dataContainer.get(i).getDateATime().get(2), dataContainer.get(i).getDateATime().get(1), dataContainer.get(i).getDateATime().get(0),
                                        String.valueOf(dataContainer.get(i).getDateATime().get(5)), String.valueOf(dataContainer.get(i).getDateATime().get(4)), String.valueOf(dataContainer.get(i).getDateATime().get(3)));

                                this.calendar = new CalendarForProject(windowWidth, windowHeight, dataContainer.get(i).getNameOf(), dataContainer.get(i).getDateATime().get(0),
                                        dataContainer.get(i).getDateATime().get(1), dataContainer.get(i).getDateATime().get(2));
                            }
                        }
                    }
                    this.add(calendar);
                    this.add(infoPanel);
                    instructions = new Instructions(windowWidth,windowHeight,startButton);
                    this.add(instructions);
                }
        }
    }

    private boolean isFull() {
        return (timer.getChosenYear() != 0 && timer.getChosenMonth()+1 != 0 && !timer.getHoursEnd().isEmpty()
                && !timer.getHoursStart().isEmpty() && !timer.getMinutesEnd().isEmpty() &&!timer.getMinutesStart().isEmpty()
                && !timer.getSecondEnd().isEmpty() && !timer.getSecondStart().isEmpty() && !timer.getActionToList().isEmpty());
        //need to add other parts like
    }

    @Override
    public void setWindowVisibility(boolean visible) {
        this.setVisible(visible);
    }

    private int choices(){
        List<DataContainer> containers = handler.readDataFromFile();
        String[] hisTasks = new String[containers.size()];
        for (int i = 0; i < containers.size(); i++) {
            hisTasks[i] = containers.get(i).getNameOf();
        }
        int choiceMassage =JOptionPane.showOptionDialog(
                null,
                "Witch task would you like to start?",
                "Choose Your Task",
                JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                hisTasks,
                hisTasks[0]);
        return choiceMassage;
    }
}
