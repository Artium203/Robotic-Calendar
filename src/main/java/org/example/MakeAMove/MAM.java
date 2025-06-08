package org.example.MakeAMove;

import org.example.ButtonPlace;
import org.example.Utils;
import org.example.VoiceHandler.VoiceManager;
import org.example.cosmetics.GradientTitled;
import org.example.cosmetics.MyCheckBoxUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class MAM extends JPanel implements ButtonPlace {

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
//    private final JButton confirmRepeat = new JButton("CONFIRM");

    // Time to live for the loop if he is selected
    private final JPanel timeToLive;
    private JSpinner lifeHour;
    private JSpinner lifeMinute;
    private JSpinner lifeSecond;
//    private final JButton confirmLoop = new JButton("CONFIRM");

    private final JPanel nextOrPrevious; // The place for user to go on in the action list
    private static final JButton next = new JButton("Next"); // Going forward in checkers list
    private static final JButton previous = new JButton("Previous");// Going backwards in checkers list
    private final JPanel location; // The place where user will select the place of action
    private final JButton pointLocation = new JButton("Point The Location"); //Will transform to user's desktop/screen
    private Point locationPoint = new Point();

    // Explanations for user's need
    private final JPanel instructions;
    private JLabel instructionsText;

    //Place to show the user his time input and the changes of inputting time for action
    private final JPanel timeMonitor;

    //    private JLabel timeChecker;
//    private final JButton confirmSelection = new JButton("Confirm Your Selection");
//    private int HOURS;
//    private int MINUTES;
//    private int SECONDS;

    //In future wait
    private List<Integer> saveDataOfIndex;
//    private Timer actionLife;
    private static int currentIndex = 0;
    List<Boolean> isSelectedOnThePast = new ArrayList<>();

    private Map<Integer, List<Integer>> savingsMap = new HashMap<>();

    private int sizeOfList;
    private static int endHour;
    private static int endMinute;

    private static int endSecond;
    private final Image backGImageList;
    private final Image backGImage;
    private final Image backGImageIn;
    private final Image backGImageRock;
    private final Image backIChecker;
    private double givenScaleX, givenScaleY;
    private ImageIcon unScaledIcon = new ImageIcon("src/Resources/cosmetics/checker_empty.png");
    private ImageIcon unScaledCheckedIcon = new ImageIcon("src/Resources/cosmetics/checker.png");
    ImageIcon icon = new ImageIcon("src/Resources/cosmetics/info.png");
    private JButton infoButtonToLive = new JButton();
    private JButton infoButtonRep = new JButton();
    private JButton infoButtonNorP = new JButton();
    private JButton infoButtonLoc = new JButton();
    private JButton infoButtonList = new JButton();
    private JButton infoButtonCon = new JButton();
    private JLayeredPane layeredToLive = new JLayeredPane();
    private JLayeredPane layeredRep = new JLayeredPane();
    private JLayeredPane layeredNorP = new JLayeredPane();
    private JLayeredPane layeredLoc = new JLayeredPane();
    private JLayeredPane layeredList = new JLayeredPane();
    private JLayeredPane layeredCon = new JLayeredPane();

    {
        try {
            backGImageList = ImageIO.read(new File("src/Resources/cosmetics/hd_restoration_result_image - Copy.png"));
            backGImage = ImageIO.read(new File("src/Resources/cosmetics/ezgif.com-cropfgt.png"));
            backIChecker= ImageIO.read(new File("src/Resources/cosmetics/checker_text.png"));
            backGImageIn = ImageIO.read(new File("src/Resources/cosmetics/52ukVp-ezgif.com-crop (1)ds.png"));
            backGImageRock= ImageIO.read(new File("src/Resources/cosmetics/woodenRock-buttons.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MAM(int windowWidth, int windowHeight, List<String> actionList,
               int startHour, int startMinute, int startSecond, int endHourGiven,
               int endMinuteGiven, int endSecondGiven, Utils util,JButton confirmSelection,
               double scaleX, double scaleY) {
        //Setting for class MAM
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setPreferredSize(new Dimension(windowWidth - 8, windowHeight - (windowHeight / 10) - 11));
        this.setVisible(false);
        Image scaledImage = unScaledIcon.getImage().getScaledInstance((int) (unScaledIcon.getIconWidth()*scaleX), (int) (unScaledIcon.getIconHeight()*scaleY), Image.SCALE_SMOOTH);
        Image scaledCheckerIcon = unScaledCheckedIcon.getImage().getScaledInstance((int) (unScaledCheckedIcon.getIconWidth()*scaleX), (int) (unScaledCheckedIcon.getIconHeight()*scaleY), Image.SCALE_SMOOTH);
        this.utils = util;
        layeredList.setPreferredSize(new Dimension(windowWidth / 4, 31 + (windowHeight / 6) * 5));
        sizeOfList = actionList.size();
        endHour=endHourGiven;
        endMinute=endMinuteGiven;
        endSecond=endSecondGiven;
        givenScaleX = scaleX;
        givenScaleY = scaleY;
        Image image = icon.getImage().getScaledInstance((int) (icon.getIconWidth()*scaleX), (int) (icon.getIconHeight()*scaleY), Image.SCALE_SMOOTH);
        setInfoButton(infoButtonToLive, image);
        for (ActionListener al : infoButtonToLive.getActionListeners()) {
            infoButtonToLive.removeActionListener(al);
        }
        infoButtonToLive.addActionListener(e -> {
            VoiceManager voiceManager=new VoiceManager();
            try {
                voiceManager.playSound(124000*1000,127000);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        infoButtonToLive.setBounds((int) (5*givenScaleX),0,(int) (30*givenScaleX), (int) (30*givenScaleY));
        setInfoButton(infoButtonRep, image);
        for (ActionListener al : infoButtonRep.getActionListeners()) {
            infoButtonRep.removeActionListener(al);
        }
        infoButtonRep.addActionListener(e -> {
            VoiceManager voiceManager=new VoiceManager();
            try {
                voiceManager.playSound(111000*1000,124000);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        infoButtonRep.setBounds((int) (465*givenScaleX),0,(int) (30*givenScaleX), (int) (30*givenScaleY));
        setInfoButton(infoButtonNorP, image);
        for (ActionListener al : infoButtonNorP.getActionListeners()) {
            infoButtonNorP.removeActionListener(al);
        }
        infoButtonNorP.addActionListener(e -> {
            VoiceManager voiceManager=new VoiceManager();
            try {
                voiceManager.playSound(127000*1000,132000);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        infoButtonNorP.setBounds((int) (465*givenScaleX),0,(int) (30*givenScaleX), (int) (30*givenScaleY));
        setInfoButton(infoButtonLoc, image);
        for (ActionListener al : infoButtonLoc.getActionListeners()) {
            infoButtonLoc.removeActionListener(al);
        }
        infoButtonLoc.addActionListener(e -> {
            VoiceManager voiceManager=new VoiceManager();
            try {
                voiceManager.playSound(132000*1000,139000);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        infoButtonLoc.setBounds((int) (465*givenScaleX),0,(int) (30*givenScaleX), (int) (30*givenScaleY));
        setInfoButton(infoButtonList, image);
        for (ActionListener al : infoButtonList.getActionListeners()) {
            infoButtonList.removeActionListener(al);
        }
        infoButtonList.addActionListener(e -> {
            VoiceManager voiceManager=new VoiceManager();
            try {
                voiceManager.playSound(106000*1000,111000);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        infoButtonList.setBounds((int) (315*givenScaleX),0,(int) (30*givenScaleX), (int) (30*givenScaleY));
        setInfoButton(infoButtonCon, image);
        for (ActionListener al : infoButtonCon.getActionListeners()) {
            infoButtonCon.removeActionListener(al);
        }
        infoButtonCon.addActionListener(e -> {
            VoiceManager voiceManager=new VoiceManager();
            try {
                voiceManager.playSound(139000*1000,167000);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        infoButtonCon.setBounds((int) (5*givenScaleX),0,(int) (30*givenScaleX), (int) (30*givenScaleY));

        //Setting for List of Check boxes
        boxList = new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backGImageList, (int) (-84*scaleX), (int) (-100*scaleY), (int) (getWidth()+170*scaleX), (int) (getHeight()+170*scaleY), this);
            }
        };
        boxList.setBounds((int) (-3*scaleX),0, (int) (355*scaleX), (int) (650*scaleY));
//        boxList.setPreferredSize(new Dimension(windowWidth / 4, 31 + (windowHeight / 6) * 5));
        boxList.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("List Of Commands"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        ButtonGroup checkGroup = new ButtonGroup();
        performanceList = new ArrayList<>();

        //Settings of the commands box
        boxOfCommand = new JPanel();
        boxOfCommand.setPreferredSize(new Dimension(windowWidth - (windowWidth / 4) - 25, 31 + (windowHeight / 6) * 5));
        boxOfCommand.setLayout(new GridLayout(3, 3));

        //Settings of the repeater channel
        repeat = new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backGImage, (int)(-30*scaleX), (int)(-15*scaleY) , (int)(getWidth()+56*scaleX), (int)(getHeight()+35*scaleY), this);
            }
        };
        repeat.setBounds(0,0, (int) (500*scaleX), (int) (225*scaleY));
        repeat.setBorder(setBorders("              Repeats"));
        repeat.setLayout(new FlowLayout(FlowLayout.CENTER));
        //Loop declares if there shall be a loop
        loop = new JCheckBox("Loop?"){
            protected void paintComponent(Graphics g) {
                if (backIChecker != null) {
                    g.drawImage(backIChecker, 0, 0, getWidth(), getHeight(), this);
                }
                super.paintComponent(g);
            }
        };
        loop.setOpaque(false);
        loop.setFont(new Font("SansSerif", Font.PLAIN, (int) (12*scaleY)));
        loop.setIcon(new ImageIcon("src/Resources/cosmetics/checker_empty.png"));
        loop.setSelectedIcon(new ImageIcon("src/Resources/cosmetics/checker.png"));
        loop.setPreferredSize(new Dimension(windowWidth / 15, (windowHeight / 10) - 15));
        loop.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("LOOP"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        for (ActionListener al : loop.getActionListeners()){
            loop.removeActionListener(al);
        }
            loop.addActionListener(e -> {
                if (loop.isSelected()) {
                    countOfRepeat.setEnabled(false);
                    lifeHour.setEnabled(true);
                    lifeMinute.setEnabled(true);
                    lifeSecond.setEnabled(true);
                } else {
                    countOfRepeat.setEnabled(true);
                    lifeHour.setEnabled(false);
                    lifeMinute.setEnabled(false);
                    lifeSecond.setEnabled(false);
                }
            });
        repeat.add(loop);

        //Says the amount of times the action shall repeat
        countOfRepeat = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        countOfRepeat.setPreferredSize(new Dimension(windowWidth / 17, (windowHeight / 10) - 15));
        countOfRepeat.setBorder(new GradientTitled("Counter",Color.BLACK,Color.black,"src/Resources/cosmetics/Iron_frame.png",
                null,(int)(1*scaleX),0,0,0,scaleY));
        ((JSpinner.DefaultEditor) countOfRepeat.getEditor()).getTextField().setEditable(false);
        repeat.add(countOfRepeat);

        // Time input for the action to repeat
        frequencyAmountHour = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
        frequencyAmountHour.setBorder(new GradientTitled("Hours",Color.BLACK,Color.black,"src/Resources/cosmetics/Iron_frame.png",
                null,(int)(1*scaleX),0,0,0,scaleY));
        frequencyAmountHour.setFont(new Font("SansSerif", Font.PLAIN, (int) (12*scaleY)));

        frequencyAmountMinute = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        frequencyAmountMinute.setBorder(new GradientTitled("Minutes",Color.BLACK,Color.black,"src/Resources/cosmetics/Iron_frame.png",
                null,(int)(1*scaleX),0,0,0,scaleY));
        frequencyAmountMinute.setFont(new Font("SansSerif", Font.PLAIN, (int) (12*scaleY)));

        frequencyAmountSecond = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        frequencyAmountSecond.setBorder(new GradientTitled("Seconds",Color.BLACK,Color.black,"src/Resources/cosmetics/Iron_frame.png",
                null,(int)(1*scaleX),0,0,0,scaleY));
        frequencyAmountSecond.setFont(new Font("SansSerif", Font.PLAIN, (int) (12*scaleY)));

        ((JSpinner.DefaultEditor) frequencyAmountHour.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor) frequencyAmountMinute.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor) frequencyAmountSecond.getEditor()).getTextField().setEditable(false);

        frequencyAmountHour.setPreferredSize(new Dimension(windowWidth / 17, (windowHeight / 10) - 15));
        frequencyAmountMinute.setPreferredSize(new Dimension(windowWidth / 17, (windowHeight / 10) - 15));
        frequencyAmountSecond.setPreferredSize(new Dimension(windowWidth / 17, (windowHeight / 10) - 15));

        repeat.add(frequencyAmountHour);
        repeat.add(frequencyAmountMinute);
        repeat.add(frequencyAmountSecond);

        // Same thing as the repeater but without amount only loop
        timeToLive = new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backGImage, (int)(-30*scaleX), (int)(-15*scaleY) ,(int) (getWidth()+56*scaleX), (int)(getHeight()+35*scaleY), this);
            }
        };
        timeToLive.setBounds(0,0, (int) (500*scaleX), (int) (225*scaleY));
        timeToLive.setBorder(setBorders("              Time For Loop To Live"));
        lifeHour = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
        lifeHour.setFont(new Font("SansSerif", Font.PLAIN, (int) (12*scaleY)));

        ((JSpinner.DefaultEditor) lifeHour.getEditor()).getTextField().setEditable(false);
        lifeHour.setBorder(new GradientTitled("Hour",Color.BLACK,Color.black,"src/Resources/cosmetics/Iron_frame.png",
                null,(int)(1*scaleX),0,0,0,scaleY));
        lifeHour.setPreferredSize(new Dimension(windowWidth / 17, (windowHeight / 10) - 15));


        lifeMinute = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        ((JSpinner.DefaultEditor) lifeMinute.getEditor()).getTextField().setEditable(false);
        lifeMinute.setBorder(new GradientTitled("Minute",Color.BLACK,Color.black,"src/Resources/cosmetics/Iron_frame.png",
                null,(int)(1*scaleX),0,0,0,scaleY));
        lifeMinute.setPreferredSize(new Dimension(windowWidth / 17, (windowHeight / 10) - 15));
        lifeMinute.setFont(new Font("SansSerif", Font.PLAIN, (int) (12*scaleY)));

        lifeSecond = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        ((JSpinner.DefaultEditor) lifeSecond.getEditor()).getTextField().setEditable(false);
        lifeSecond.setBorder(new GradientTitled("Second",Color.BLACK,Color.black,"src/Resources/cosmetics/Iron_frame.png",
                null,(int)(1*scaleX),0,0,0,scaleY));
        lifeSecond.setPreferredSize(new Dimension(windowWidth / 17, (windowHeight / 10) - 15));
        lifeSecond.setFont(new Font("SansSerif", Font.PLAIN, (int) (12*scaleY)));
        lifeHour.setEnabled(false);
        lifeMinute.setEnabled(false);
        lifeSecond.setEnabled(false);
        timeToLive.add(lifeHour);
        timeToLive.add(lifeMinute);
        timeToLive.add(lifeSecond);

        //The buttons for going back or forward from the list
        nextOrPrevious = new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backGImage, (int)(-30*scaleX), (int)(-15*scaleY) , (int)(getWidth()+56*scaleX), (int)(getHeight()+35*scaleY), this);
            }
        };
//        nextOrPrevious.setBackground(Color.red);
        nextOrPrevious.setBounds(0,0, (int) (500*scaleX), (int) (225*scaleY));
        nextOrPrevious.setLayout(new BorderLayout(0, 10));
        nextOrPrevious.setBorder(setBorders("              Go Back/Go Forward"));
        next.setPreferredSize(new Dimension(windowWidth / 6, windowHeight / 8));
        next.setIcon(new ImageIcon("src/Resources/cosmetics/ezgif.com-crop(next)2.png"));
        next.setFont(new Font("SansSerif", Font.BOLD, (int) (12*scaleY)));
        next.setSelectedIcon(new ImageIcon("src/Resources/cosmetics/ezgif.com-crop(next)2 - Copy.png"));
        next.setOpaque(false);
        next.setBorderPainted(false);
        saveDataOfIndex = new ArrayList<>();
        previous.setPreferredSize(new Dimension(windowWidth / 6, windowHeight / 8));
        previous.setIcon(new ImageIcon("src/Resources/cosmetics/ezgif.com-crop(per).png"));
        previous.setSelectedIcon(new ImageIcon("src/Resources/cosmetics/ezgif.com-crop(perv)2 - Copy.png"));
        previous.setFont(new Font("SansSerif", Font.BOLD, (int) (12*scaleY)));
        previous.setEnabled(false);
        previous.setOpaque(false);
        previous.setBorderPainted(false);
        nextOrPrevious.add(next, BorderLayout.NORTH);
        nextOrPrevious.add(previous, BorderLayout.SOUTH);

        //Shows user's input of the time and updates it until the end, also the confirm buttons of all user's input
        timeMonitor = new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backGImageRock, (int)(-30*scaleX), (int)(-23*scaleY) , (int)(getWidth()+66*scaleX), (int)(getHeight()+35*scaleY), this);
            }
        };
//        timeMonitor.setBackground(Color.GREEN);
        timeMonitor.setBounds(0,0, (int) (500*scaleX), (int) (225*scaleY));
        timeMonitor.setLayout(new FlowLayout(FlowLayout.CENTER,45,30));
        timeMonitor.setEnabled(false);
        confirmSelection.setPreferredSize(new Dimension((windowWidth/3)-4,(windowHeight/5)-3));
        confirmSelection.setIcon(new ImageIcon("src/Resources/cosmetics/ezgif.com-cropsd3.png"));
        confirmSelection.setSelectedIcon(new ImageIcon("src/Resources/cosmetics/ezgif.com-cropsd3(selected).png"));
        confirmSelection.setHorizontalTextPosition(SwingConstants.CENTER);
        confirmSelection.setFont(new Font("SansSerif", Font.BOLD, (int) (12*scaleY)));
        confirmSelection.setVerticalTextPosition(SwingConstants.CENTER);
        confirmSelection.setBorderPainted(false);
        confirmSelection.setOpaque(false);
        confirmSelection.setEnabled(false);
        confirmSelection.setContentAreaFilled(false);
        confirmSelection.setFocusPainted(false);
        timeMonitor.add(confirmSelection);

        //User's pointer for the location of current action
        location = new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backGImage, (int)(-30*scaleX), (int)(-15*scaleY) , (int)(getWidth()+56*scaleX), (int)(getHeight()+35*scaleY), this);
            }
        };
//        location.setBackground(Color.MAGENTA);
        location.setBounds(0,0, (int) (500*scaleX), (int) (225*scaleY));
        location.setLayout(new GridLayout());
        location.setBorder(setBorders("              Point Robot's Action Point"));
        pointLocation.setFont(new Font("SansSerif", Font.BOLD, (int) (12*scaleY)));
        for (ActionListener al : pointLocation.getActionListeners()){
            pointLocation.removeActionListener(al);
        }
            pointLocation.addActionListener(e -> {
                utils.setWindowVisibility(false);
                SwingUtilities.invokeLater(() -> {
                    LocationFinder gui = new LocationFinder(utils, this, pointLocation.getWidth(), pointLocation.getHeight(),scaleX,scaleY);
                    gui.setVisible(true);
                });
            });
        location.add(pointLocation);

        //Instruction of how to put and what things do
        instructions = new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backGImageIn, (int)(-52*scaleX), (int)(-15*scaleY) , (int)(getWidth()+116*scaleX), (int)(getHeight()+35*scaleY), this);
            }
        };
//        instructions.setBackground(Color.yellow);
        instructions.setBorder(setBorders("              Help For Understatement"));
        instructions.setLayout(new GridLayout());
        instructionsText = new JLabel("<html>              The first box of settings you enter the repeat of the action if you<br>" +
                "               want it to loop you will be asked for how long.<br>" +
                "               You will be asked to set the position of where the mouse shall be.<br>" +
                "               To proceed click next and to check back click back.<br>" +
                "               When you end your input click on the back button so it will<br>" +
                "                be saved and then click continue, make sure to time them<br>" +
                "                correctly from your previous input.</html>");
        instructionsText.setForeground(Color.WHITE);
        instructionsText.setFont(new Font("SansSerif", Font.BOLD, (int) (12*scaleY)));
        instructionsText.setPreferredSize(new Dimension(windowWidth / 3,windowHeight/4));
        instructionsText.setHorizontalAlignment(SwingConstants.CENTER);
        for (ActionListener al : next.getActionListeners()){
            next.removeActionListener(al);
        }
        for (ActionListener al : previous.getActionListeners()){
            previous.removeActionListener(al);
        }
//        for (ActionListener al : pointLocation.getActionListeners()){
//            pointLocation.removeActionListener(al);
//        }
//        for (ActionListener al : confirmSelection.getActionListeners()){
//            confirmSelection.removeActionListener(al);
//        }


        if (!actionList.isEmpty()) {
            for (int i = 0; i < actionList.size(); i++) { // Creation of check boxes and his settings
                performanceList.add(new MyCheckBoxUI(actionList.get(i),"src/Resources/cosmetics/checker_text.png",scaleX,scaleY));
                performanceList.get(i).setPreferredSize(new Dimension((int) (150*scaleX), (int) (65*scaleY)));
                performanceList.get(i).setText(actionList.get(i));
                performanceList.get(i).setFont(new Font("SansSerif", Font.BOLD, (int) (12*scaleY)));
                performanceList.get(i).setIcon(new ImageIcon(scaledImage));
                performanceList.get(i).setSelectedIcon(new ImageIcon(scaledCheckerIcon));
                boxList.add(performanceList.get(i));
                checkGroup.add(performanceList.get(i));
                performanceList.get(i).setEnabled(false);
                performanceList.get(0).setSelected(true);
                isSelectedOnThePast.add(0, true);

                if (i > 0) { // In testing
                    isSelectedOnThePast.add(i, false);
                }
            }
            next.addActionListener(e -> {
                    System.out.println("Amount of actions: "+next.getActionListeners().length);
                    System.out.println(currentIndex < actionList.size() - 1 && isFull());
                    System.out.println("List Size: "+ sizeOfList);
                    System.out.println(currentIndex);
                    if (currentIndex < actionList.size() - 1 && isFull()) {
                        if (performanceList.get(currentIndex).getText().equals("[Scroll]")) {
                            saveDataOfIndex.add(10);
                        } else {
                            saveDataOfIndex.add(11);
                        }
                        if (loop.isSelected()) {
                            saveDataOfIndex.add(1);
                        } else {
                            saveDataOfIndex.add(0);
                        }
                        saveDataOfIndex.add((Integer) countOfRepeat.getValue());
                        saveDataOfIndex.add((Integer) frequencyAmountHour.getValue());
                        saveDataOfIndex.add((Integer) frequencyAmountMinute.getValue());
                        saveDataOfIndex.add((Integer) frequencyAmountSecond.getValue());
                        saveDataOfIndex.add((Integer) lifeHour.getValue());
                        saveDataOfIndex.add((Integer) lifeMinute.getValue());
                        saveDataOfIndex.add((Integer) lifeSecond.getValue());
                        saveDataOfIndex.add(locationPoint.getLocation().x);
                        saveDataOfIndex.add(locationPoint.getLocation().y);
                        savingsMap.put(currentIndex, saveDataOfIndex);
                        saveDataOfIndex = new ArrayList<>();
                        ++currentIndex;
                        performanceList.get(currentIndex).setSelected(true);
                        if (!savingsMap.containsKey(currentIndex)) {
                            countOfRepeat.setValue(1);
                            frequencyAmountHour.setValue(0);
                            frequencyAmountMinute.setValue(0);
                            frequencyAmountSecond.setValue(0);
                            lifeHour.setValue(0);
                            lifeMinute.setValue(0);
                            lifeSecond.setValue(0);
                            loop.setSelected(false);
                            countOfRepeat.setEnabled(true);
                            lifeHour.setEnabled(false);
                            lifeMinute.setEnabled(false);
                            lifeSecond.setEnabled(false);
                            pointLocation.setIcon(null);
                            pointLocation.setText("Point The Location");
                            locationPoint.setLocation(0, 0);
                            isSelectedOnThePast.set(currentIndex, true);
                        } else {
                            for (int i = 0; i < savingsMap.get(currentIndex).size(); i++) {
                                switch (i) {
                                    case 1:
                                        pickedSelected(savingsMap.get(currentIndex).get(i));
                                    case 2:
                                        countOfRepeat.setValue(savingsMap.get(currentIndex).get(i));
                                    case 3:
                                        frequencyAmountHour.setValue(savingsMap.get(currentIndex).get(i));
                                    case 4:
                                        frequencyAmountMinute.setValue(savingsMap.get(currentIndex).get(i));
                                    case 5:
                                        frequencyAmountSecond.setValue(savingsMap.get(currentIndex).get(i));
                                    case 6:
                                        lifeHour.setValue(savingsMap.get(currentIndex).get(i));
                                    case 7:
                                        lifeMinute.setValue(savingsMap.get(currentIndex).get(i));
                                    case 8:
                                        lifeSecond.setValue(savingsMap.get(currentIndex).get(i));
                                    case 9:
                                        locationPoint.setLocation(savingsMap.get(currentIndex).get(i), savingsMap.get(currentIndex).get(i + 1));
                                        pointLocation.setText("(" + savingsMap.get(currentIndex).get(i) + "," + savingsMap.get(currentIndex).get(i + 1) + ")");
                                }
                            }
                        }
                    } else if (!isFull()) {
                        JOptionPane.showMessageDialog(null, "YOU DIDN'T FULLY PUT YOUR INPUT", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    if (currentIndex == 0) {
                        previous.setEnabled(false);
                    } else {
                        previous.setEnabled(true);
                    }
                    if (currentIndex == actionList.size() - 1) {
                        next.setEnabled(false);
                        confirmSelection.setEnabled(true);
                    } else {
                        next.setEnabled(true);
                    }
                });
            //<-- Have to fix this button
                previous.addActionListener(e -> {
                    System.out.println("Amount of actions: "+previous.getActionListeners().length);
                    if (!savingsMap.containsKey(currentIndex) && isFull()) {
                        if (performanceList.get(currentIndex).getText().equals("[Scroll]")) {
                            saveDataOfIndex.add(10);
                        } else {
                            saveDataOfIndex.add(11);
                        }
                        if (loop.isSelected()) {
                            saveDataOfIndex.add(1);
                        } else {
                            saveDataOfIndex.add(0);
                        }
                        saveDataOfIndex.add((Integer) countOfRepeat.getValue());
                        saveDataOfIndex.add((Integer) frequencyAmountHour.getValue());
                        saveDataOfIndex.add((Integer) frequencyAmountMinute.getValue());
                        saveDataOfIndex.add((Integer) frequencyAmountSecond.getValue());
                        saveDataOfIndex.add((Integer) lifeHour.getValue());
                        saveDataOfIndex.add((Integer) lifeMinute.getValue());
                        saveDataOfIndex.add((Integer) lifeSecond.getValue());
                        saveDataOfIndex.add(locationPoint.getLocation().x);
                        saveDataOfIndex.add(locationPoint.getLocation().y);
                        savingsMap.put(currentIndex, saveDataOfIndex);
                        saveDataOfIndex = new ArrayList<>();
                    }
                    if (currentIndex > 0) {
                        --currentIndex;
                        // <----map here
                        pointLocation.setIcon(null);
                        for (int i = 0; i < savingsMap.get(currentIndex).size(); i++) {
                            switch (i) {
                                case 1:
                                    pickedSelected(savingsMap.get(currentIndex).get(i));
                                case 2:
                                    countOfRepeat.setValue(savingsMap.get(currentIndex).get(i));
                                case 3:
                                    frequencyAmountHour.setValue(savingsMap.get(currentIndex).get(i));
                                case 4:
                                    frequencyAmountMinute.setValue(savingsMap.get(currentIndex).get(i));
                                case 5:
                                    frequencyAmountSecond.setValue(savingsMap.get(currentIndex).get(i));
                                case 6:
                                    lifeHour.setValue(savingsMap.get(currentIndex).get(i));
                                case 7:
                                    lifeMinute.setValue(savingsMap.get(currentIndex).get(i));
                                case 8:
                                    lifeSecond.setValue(savingsMap.get(currentIndex).get(i));
                                case 9:
                                    locationPoint.setLocation(savingsMap.get(currentIndex).get(i), savingsMap.get(currentIndex).get(i + 1));
                                    pointLocation.setText("(" + savingsMap.get(currentIndex).get(i) + "," + savingsMap.get(currentIndex).get(i + 1) + ")");
                            }
                        }

                        if (loop.isSelected()) {
                            countOfRepeat.setEnabled(false);
                            lifeHour.setEnabled(true);
                            lifeMinute.setEnabled(true);
                            lifeSecond.setEnabled(true);
                        }
                        isSelectedOnThePast.set(currentIndex, true);
                        performanceList.get(currentIndex).setSelected(true);
                    } else if (!isFull()) {
                        JOptionPane.showMessageDialog(null, "YOU DIDN'T FULLY PUT YOUR INPUT", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    if (currentIndex == 0) {
                        previous.setEnabled(false);
                    } else {
                        previous.setEnabled(true);
                    }
                    if (currentIndex == actionList.size() - 1) {
                        next.setEnabled(false);
                    } else {
                        next.setEnabled(true);
                    }
                });
        }


        instructions.add(instructionsText);
        layeredRep.add(infoButtonRep,JLayeredPane.PALETTE_LAYER);
        layeredRep.add(repeat,JLayeredPane.DEFAULT_LAYER);
        boxOfCommand.add(layeredRep);
        layeredToLive.add(infoButtonToLive,JLayeredPane.PALETTE_LAYER);
        layeredToLive.add(timeToLive,JLayeredPane.DEFAULT_LAYER);
        boxOfCommand.add(layeredToLive);
        layeredNorP.add(infoButtonNorP,JLayeredPane.PALETTE_LAYER);
        layeredNorP.add(nextOrPrevious,JLayeredPane.DEFAULT_LAYER);
        boxOfCommand.add(layeredNorP);
        layeredCon.add(infoButtonCon,JLayeredPane.PALETTE_LAYER);
        layeredCon.add(timeMonitor,JLayeredPane.DEFAULT_LAYER);
        boxOfCommand.add(layeredCon);
        layeredLoc.add(infoButtonLoc,JLayeredPane.PALETTE_LAYER);
        layeredLoc.add(location,JLayeredPane.DEFAULT_LAYER);
        boxOfCommand.add(layeredLoc);
        boxOfCommand.add(instructions);
        //Actions

        layeredList.add(infoButtonList,JLayeredPane.PALETTE_LAYER);
        layeredList.add(boxList,JLayeredPane.DEFAULT_LAYER);
        this.add(layeredList);
        this.add(boxOfCommand);
    }

    private boolean isFull(){
        boolean result = false;
        if (loop.isSelected()){
            if (((int) lifeHour.getValue() > 0 || (int) lifeMinute.getValue()>0 || (int) lifeSecond.getValue()>30) &&
                    locationPoint.getLocation().x !=0 && locationPoint.getLocation().y!=0){
                result = true;
            }
        }
        else {
            if (locationPoint.getLocation().x !=0 && locationPoint.getLocation().y!=0){
                result = true;
            }
        }
        return result;
    }
    public boolean isMapsFull(){
        boolean result =false;
        int count = 0;
        for (int i = 0; i < sizeOfList; i++) {
            if (savingsMap.get(i).get(0)==1 &&
                    (savingsMap.get(i).get(5)> 0 || savingsMap.get(i).get(6)> 0 || savingsMap.get(i).get(7)>30) ){
                count++;
            }
            if (savingsMap.get(i).get(9)!=0){
                count++;
            }
            if (sizeOfList==count){
                result=true;
            }
        }
        return result;
    }
    private void pickedSelected(int num){
        if (num==1){
            this.loop.setSelected(true);
        }else {
            this.loop.setSelected(false);
        }
    }
    @Override
    public void changeApperance(Image image, int x, int y) {
        this.pointLocation.setIcon(new ImageIcon(image));
        locationPoint.setLocation(x,y);
    }
    public Map<Integer, List<Integer>> getSavingsMap() {
        return savingsMap;
    }
    public static int getEndHour() {
        return endHour;
    }

    public static int getEndMinute() {
        return endMinute;
    }

    public static int getEndSecond() {
        return endSecond;
    }
    public void setSavingsMap() {
        for (int i = 0; i < this.savingsMap.size(); i++) {
            this.savingsMap.remove(i);
        }
    }
    public void setCurrentIndex(int currentIndex) {this.currentIndex = currentIndex;}
    private TitledBorder setBorders(String text){
        TitledBorder titledBorder = new GradientTitled(text,new Color(255, 255, 255), new Color(250, 250, 250),
                "src/Resources/cosmetics/Iron_frame.png",null,(int)(12*givenScaleX),0,(int)(24*givenScaleX),0,givenScaleY);
        titledBorder.setTitlePosition(TitledBorder.BELOW_TOP);
        titledBorder.setTitleFont(new Font("Arial" , Font.BOLD , (int)(16*givenScaleY)));
        return titledBorder;
    }
    private void setInfoButton(JButton button,Image image) {
        button.setIcon(new ImageIcon(image));
        button.setOpaque(false);
        button.setBorderPainted(false);
    }
}