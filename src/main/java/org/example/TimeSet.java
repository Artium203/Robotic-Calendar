package org.example;

import org.example.cosmetics.ImageBorder;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.List;

public class TimeSet extends JPanel {

    private List<String> actionToList;// Will be used to put on a list
//    private static List<String> plans;

    private static List<JCheckBox> actionList;

    //The chosen date from user
    private static int chosenDay;
    private static int chosenMonth;
    private static int chosenYear;

    private static int nod; // Used for calculations instructions

    //Options of choosing the date
    private final JComboBox<Integer> yearBox;
    private final JComboBox<Integer> monthBox; // number of months in a year
    private final JComboBox<Integer> dayBox; // number of days in a month
    private final JButton addToList;// Button that confirmed user's chose to add to a list
    private final JButton removeFromList; // Button that removes user's chose from the list
    //Action option
    private final JRadioButton dragOption;
    private final JRadioButton pressOption;
    private ButtonGroup buttonGroup;// Only for picking one of the 2 buttons

    private final JLabel textForInstruction; //Simple explanation
    private final JLabel textForDate;//Simple explanation
    private final JPanel dateBox;//Gets the date from user
    private final JPanel performanceBox;//Gets the action from user
    private final JPanel timeBox;//Gets the time from user
    private final JPanel addOrRemoveBox;//Gets to put/remove to/from a list


    private final JPanel listBox;//List of actions (max 10 actions to put)
    private final JPanel listBoxInner;
    private final JPanel boxOfBoxes;//Contains the panels together


    //The starting and ending of the action
    private JSpinner hoursStart;
    private JSpinner minutesStart;
    private JSpinner secondStart;
    private JSpinner hoursEnd;
    private JSpinner minutesEnd;
    private JSpinner secondEnd;

    //Title to understand which is who
    private final JLabel hoursStartLabel;
    private final JLabel minutesStartLabel;
    private final JLabel secondsStartLabel;
    private final JLabel hoursEndLabel;
    private final JLabel minutesEndLabel;
    private final JLabel secondsEndLabel;
    private final JLabel StartText;
    private final JLabel endText;
    private final JLabel spaceText;

    private JTextField nameAction;
    private final Image backGImageDate;
    private final Image backGImageList;
    private final Image backGImageName;

    private final ImageIcon backButton= new ImageIcon("src/Resources/cosmetics/wooden-buttons.png");
    private final ImageIcon backIChecker = new ImageIcon("src/Resources/cosmetics/checker_text.png");

    {
        try {
            backGImageList = ImageIO.read(new File("src/Resources/cosmetics/hd_restoration_result_image - Copy.png"));
            backGImageDate = ImageIO.read(new File("src/Resources/cosmetics/ezgif.com-crop (2)r.png"));
            backGImageName = ImageIO.read(new File("src/Resources/cosmetics/ezgif.com-crop (1)sd3.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    public TimeSet(int windowWidth,int windowHeight, JButton confirm) {
        //Panel layout and dimensions
        this.setLayout(new GridLayout());
        this.setPreferredSize(new Dimension(windowWidth - 8, windowHeight - (windowHeight / 10) - 11));
        this.setVisible(false);
//        this.setBackground(Color.gray);
        confirm.setFont(new Font("Colonna MT", Font.BOLD, 33));
        confirm.setVerticalAlignment(SwingConstants.NORTH);
        confirm.setOpaque(false);
        confirm.setBorderPainted(false);
        confirm.setContentAreaFilled(false);
        confirm.setFocusPainted(false);
        confirm.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                confirm.setFont(new Font("Colonna MT", Font.BOLD, 22));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                confirm.setFont(new Font("Colonna MT", Font.BOLD, 33));
            }
        });
        confirm.setSelectedIcon(null);

        //Container's sets
        boxOfBoxes = new JPanel();
        boxOfBoxes.setLayout(new GridLayout(2, 2));
        boxOfBoxes.setPreferredSize(new Dimension(windowWidth / 2 + (windowWidth - ((windowWidth / 2) + (windowWidth / 3) + 150)), windowHeight - (windowHeight / 10) - 20));
//        boxOfBoxes.setBackground(Color.cyan);


        //Sub panel section
        //Date box sets
        dateBox = new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backGImageDate, -30, -13, getWidth()+66, getHeight()+35, this);

            }
        };
        dateBox.setLayout(new FlowLayout(FlowLayout.LEFT,20,25));
        dateBox.setPreferredSize(new Dimension((windowWidth / 2 + (windowWidth - ((windowWidth / 2) + 445))) / 3, (windowHeight - (windowHeight / 10) - 20) / 2));


        //Action box's sets
        performanceBox = new JPanel(){
            protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backGImageDate, -35, -23, getWidth()+76, getHeight()+45, this);
            }
        };
        performanceBox.setLayout(new FlowLayout(FlowLayout.CENTER,20,25));
        performanceBox.setPreferredSize(new Dimension(400, (windowHeight - (windowHeight / 10) - 20) / 2));


        //Time box's sets
        timeBox = new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backGImageDate, -30, -13, getWidth()+66, getHeight()+35, this);
            }
        };
        timeBox.setLayout(new FlowLayout(FlowLayout.CENTER,5,30));
        timeBox.setPreferredSize(new Dimension(430, (windowHeight - (windowHeight / 10) - 20) / 2));


        //Sets of add/remove to/from a list box
        addOrRemoveBox = new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backGImageDate, -30, -23 , getWidth()+66, getHeight()+35, this);
            }
        };
        addOrRemoveBox.setLayout(new FlowLayout(FlowLayout.CENTER,5,45));
        addOrRemoveBox.setPreferredSize(new Dimension(
                (windowWidth / 2 + (windowWidth - ((windowWidth / 2) + 445))) / 4,
                (windowHeight - (windowHeight / 6) - 150) / 2));


        //list box's sets
        int betterSize = windowWidth - windowWidth / 2 + (windowWidth - ((windowWidth / 2) + 445));

        listBox = new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backGImageList, -165, -95, getWidth()+windowWidth / 4, getHeight()+(windowHeight / 10)+60, this);
            }
        };
        listBox.setLayout(new GridBagLayout());
        listBox.setPreferredSize(new Dimension(betterSize - windowWidth / 4, windowHeight - (windowHeight / 10) - 20));
//        listBox.setBackground(Color.GREEN);
        listBoxInner = new JPanel(new FlowLayout(FlowLayout.CENTER));
        listBoxInner.setPreferredSize(new Dimension(betterSize - windowWidth / 4, (windowHeight - (windowHeight / 10) - 20)-250));
        listBoxInner.setOpaque(false);



        // Date selection components
        textForDate = new JLabel("Choose a date for instruction:");
        textForDate.setPreferredSize(new Dimension(450, 80));
        textForDate.setFont(new Font("Arial", Font.BOLD, 22));
        add(textForDate);

        yearBox = new JComboBox<>(new Integer[]{0, 2024, 2025, 2026, 2027, 2028, 2029, 2030, 2031, 2032, 2033, 2034, 2035, 2036, 2037, 2038, 2039, 2040});
        yearBox.setPreferredSize(new Dimension(80, 50));
        yearBox.setFont(new Font("Arial", Font.BOLD, 20));
        yearBox.setBorder(new ImageBorder("src/Resources/cosmetics/Iron_frame.png",true,new Insets(3, 5, 3, 5),3,0));


        monthBox = new JComboBox<>(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
        monthBox.setPreferredSize(new Dimension(80, 50));
        monthBox.setFont(new Font("Arial", Font.BOLD, 20));
        monthBox.setBorder(new ImageBorder("src/Resources/cosmetics/Iron_frame.png",true,new Insets(3, 5, 3, 5),3,0));
        monthBox.setVisible(false);

        dayBox = new JComboBox<>();
        dayBox.setPreferredSize(new Dimension(80, 50));
        dayBox.setFont(new Font("Arial", Font.BOLD, 20));
        dayBox.setBorder(new ImageBorder("src/Resources/cosmetics/Iron_frame.png",true,new Insets(3, 5, 3, 5),3,0));
        dayBox.setVisible(false);


        //Actions for the choices of the date
        for (ActionListener al : yearBox.getActionListeners()) {
            yearBox.removeActionListener(al);
        }
            yearBox.addActionListener(e -> yearSelection());
        for (ActionListener al : monthBox.getActionListeners()) {
            monthBox.removeActionListener(al);
        }
            monthBox.addActionListener(e -> monthSelection());
        for (ActionListener al : dayBox.getActionListeners()) {
            dayBox.removeActionListener(al);
        }
            dayBox.addActionListener(e -> daySelection());



        //Sets of action (on a mouse)
        textForInstruction = createTextLabel("Choose one of the following actions: ",  new Font("Arial", Font.BOLD, 16));
        dragOption = createTextRadioButton("Scroll", 110, 60, new Font("Arial", Font.BOLD, 17));
        dragOption.setIcon(new ImageIcon("src/Resources/cosmetics/ezgif_radio.png"));
        dragOption.setSelectedIcon(new ImageIcon("src/Resources/cosmetics/ezgif_radioON.png"));
        dragOption.setHorizontalTextPosition(SwingConstants.CENTER);
        dragOption.setOpaque(false);
        pressOption = createTextRadioButton("Press", 110, 60, new Font("Arial", Font.BOLD, 17));
        pressOption.setIcon(new ImageIcon("src/Resources/cosmetics/ezgif_radio.png"));
        pressOption.setSelectedIcon(new ImageIcon("src/Resources/cosmetics/ezgif_radioON.png"));
        pressOption.setHorizontalTextPosition(SwingConstants.CENTER);
        pressOption.setOpaque(false);
        nameAction = new JTextField(){
            protected void paintComponent(Graphics g) {
                g.drawImage(backGImageName, -12, -5, getWidth()+30, getHeight()+8, this);
                super.paintComponent(g);
            }
        };
        TitledBorder titledBorder = BorderFactory.createTitledBorder("        Name your action");
        titledBorder.setTitlePosition(TitledBorder.BELOW_TOP);
        titledBorder.setTitleFont(new Font("Arial" , Font.BOLD , 13));
        nameAction.setPreferredSize(new Dimension(windowWidth/5 , windowHeight/12));
        nameAction.setOpaque(false);
        nameAction.setForeground(Color.WHITE);
        nameAction.setHorizontalAlignment(JTextField.CENTER);
        nameAction.setFont(new Font("Arial" , Font.BOLD , 18));
        nameAction.setBorder(BorderFactory.createCompoundBorder(titledBorder,null));
        buttonGroup = new ButtonGroup();  // על מנת שתהיה למשתמש רק אופציה אחת לבחירה, נשתמש בדבר הבא
        textForInstruction.setBounds(0,400,250,70);
        buttonGroup.add(dragOption);
        buttonGroup.add(pressOption);

        actionList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            actionList.add(new JCheckBox(){
                protected void paintComponent(Graphics g) {
                    if (backIChecker != null) {
                        g.drawImage(backIChecker.getImage(), 0, 0, getWidth(), getHeight(), this);
                    }
                    super.paintComponent(g);
                }
            });
            actionList.get(i).setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-15));
            actionList.get(i).setIcon(new ImageIcon("src/Resources/cosmetics/checker_empty.png"));
            actionList.get(i).setSelectedIcon(new ImageIcon("src/Resources/cosmetics/checker.png"));
            actionList.get(i).setOpaque(false);
            listBoxInner.add(actionList.get(i));
        }

        actionToList = new ArrayList<>();
        addToList = new JButton("ADD");
        removeFromList = new JButton("REMOVE");
        addToList.setIcon(new ImageIcon(backButton.getImage().getScaledInstance((windowWidth/6)-4, (windowHeight/10)-3, Image.SCALE_SMOOTH)));
        addToList.setHorizontalAlignment(SwingConstants.CENTER);
        addToList.setVerticalAlignment(SwingConstants.CENTER);
        addToList.setHorizontalTextPosition(SwingConstants.CENTER);
        addToList.setVerticalTextPosition(SwingConstants.CENTER);
        addToList.setOpaque(false);
        addToList.setBorderPainted(false);
        addToList.setContentAreaFilled(false);
        addToList.setFocusPainted(false);
        addToList.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        for (ActionListener al : addToList.getActionListeners()) {
            addToList.removeActionListener(al);
        }
            addToList.addActionListener(e -> {
                if (actionToList.size() == 9) {
                    addToList.setEnabled(false);
                }
                if (dragOption.isSelected()) {
                    actionToList.add(Arrays.toString(dragOption.getSelectedObjects()));
                    for (int i = 0; i < 10; i++) {
                        if (actionList.get(i).getText().isEmpty()) {
                            actionList.get(i).setText(Arrays.toString(dragOption.getSelectedObjects()));
                            break;
                        }
                    }
                } else if (pressOption.isSelected()) {
                    actionToList.add(Arrays.toString(pressOption.getSelectedObjects()));
                    for (int i = 0; i < 10; i++) {
                        if (actionList.get(i).getText().isEmpty()) {
                            actionList.get(i).setText(Arrays.toString(pressOption.getSelectedObjects()));
                            break;
                        }
                    }
                }
            });
        removeFromList.setIcon(new ImageIcon(backButton.getImage().getScaledInstance((windowWidth/6)-4, (windowHeight/10)-3, Image.SCALE_SMOOTH)));
        removeFromList.setHorizontalAlignment(SwingConstants.CENTER);
        removeFromList.setVerticalAlignment(SwingConstants.CENTER);
        removeFromList.setHorizontalTextPosition(SwingConstants.CENTER);
        removeFromList.setVerticalTextPosition(SwingConstants.CENTER);
        removeFromList.setOpaque(false);
        removeFromList.setBorderPainted(false);
        removeFromList.setFocusPainted(false);
        removeFromList.setContentAreaFilled(false);
        removeFromList.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        for (ActionListener al : removeFromList.getActionListeners()) {
            removeFromList.removeActionListener(al);
        }
            removeFromList.addActionListener(e -> {
                if (actionToList.isEmpty()) {
                    addToList.setEnabled(true);
                } else {
                    removeFromList.setEnabled(true);
                }
                if (!actionToList.isEmpty()) {
                    for (JCheckBox box : actionList) {
                        if (box.isSelected()) {
                            actionToList.remove(box.getText());
                            box.setText("");
                        }
                    }
                    addToList.setEnabled(true);
                }
            });
        listBoxInner.add(confirm);
        listBox.add(listBoxInner);

        //Adding/combining things together
        this.add(boxOfBoxes);
        boxOfBoxes.add(dateBox);
        dateBox.add(textForDate);

        dateBox.add(yearBox);
        dateBox.add(monthBox);
        dateBox.add(dayBox);



        //Sets of starting/ending time

        hoursStart = createSpinner( 23);
        hoursStart.setBorder(new ImageBorder("src/Resources/cosmetics/Iron_frame.png",true,new Insets(3, 5, 3, 5),3,0));
        hoursEnd = createSpinner(  23);
        hoursEnd.setBorder(new ImageBorder("src/Resources/cosmetics/Iron_frame.png",true,new Insets(3, 5, 3, 5),3,0));
        minutesStart = createSpinner(59);
        minutesStart.setBorder(new ImageBorder("src/Resources/cosmetics/Iron_frame.png",true,new Insets(3, 5, 3, 5),3,0));
        minutesEnd = createSpinner(59);
        minutesEnd.setBorder(new ImageBorder("src/Resources/cosmetics/Iron_frame.png",true,new Insets(3, 5, 3, 5),3,0));
        secondStart = createSpinner(59);
        secondStart.setBorder(new ImageBorder("src/Resources/cosmetics/Iron_frame.png",true,new Insets(3, 5, 3, 5),3,0));
        secondEnd = createSpinner(59);
        secondEnd.setBorder(new ImageBorder("src/Resources/cosmetics/Iron_frame.png",true,new Insets(3, 5, 3, 5),3,0));

        StartText = createLabel("Choose time the robot will start the instruction:");
        hoursStartLabel = createLabel("Hour:");
        minutesStartLabel = createLabel("Minute:");
        secondsStartLabel = createLabel("Second:");
        spaceText = createLabel("                                         ");
        endText = createLabel("Choose time the robot will finish the instruction:");
        hoursEndLabel = createLabel("Hour:");
        minutesEndLabel = createLabel("Minute:");
        secondsEndLabel = createLabel("Second:");

        timeBox.add(StartText);
        timeBox.add(hoursStartLabel);
        timeBox.add(hoursStart);
        timeBox.add(minutesStartLabel);
        timeBox.add(minutesStart);
        timeBox.add(secondsStartLabel);
        timeBox.add(secondStart);
        timeBox.add(spaceText);
        timeBox.add(endText);
        timeBox.add(hoursEndLabel);
        timeBox.add(hoursEnd);
        timeBox.add(minutesEndLabel);
        timeBox.add(minutesEnd);
        timeBox.add(secondsEndLabel);
        timeBox.add(secondEnd);
        boxOfBoxes.add(timeBox);


        boxOfBoxes.add(performanceBox);
        performanceBox.add(textForInstruction);
        performanceBox.add(dragOption);
        performanceBox.add(pressOption);
        performanceBox.add(nameAction);

        boxOfBoxes.add(addOrRemoveBox);
        addOrRemoveBox.add(addToList);
        addOrRemoveBox.add(removeFromList);
        this.add(listBox);
    }

    private void yearSelection() {
        if(!Objects.equals(yearBox.getSelectedItem(), 0)) {
            monthBox.setVisible(true);
            chosenYear = (int) yearBox.getSelectedItem();
        }
    }
    private void monthSelection() {
        if (!Objects.equals(monthBox.getSelectedItem(), 0) && !Objects.equals(yearBox.getSelectedItem(), 0)) {
            chosenMonth = (int) monthBox.getSelectedItem() - 1;
            GregorianCalendar cal2 = new GregorianCalendar(chosenYear, chosenMonth,1);

            nod = cal2.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            updateDayBox();
            dayBox.setVisible(true);
        }
        if (dayBox.getItemCount() != 0) {
            dayBox.removeAllItems();
            GregorianCalendar cal2 = new GregorianCalendar(chosenYear, chosenMonth,1);
            nod = cal2.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            updateDayBox();
        }
    }
    private void daySelection() {
        if (dayBox.getItemCount() != 0) {
            chosenDay = (int) dayBox.getSelectedItem();
        }
    }

    private JLabel createTextLabel(String text, Font font){
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setForeground(Color.BLACK);
        return label;
    }

    private JRadioButton createTextRadioButton(String text, int width, int height, Font font){
        JRadioButton jRadioButton = new JRadioButton(text);
        jRadioButton.setPreferredSize(new Dimension(width, height));
        jRadioButton.setFont(font);
        jRadioButton.setFocusPainted(false);
        return jRadioButton;
    }

    private JSpinner createSpinner( int limit){
        JSpinner jSpinner = new JSpinner(new SpinnerNumberModel(0,0,limit,1));
        ((JSpinner.DefaultEditor) jSpinner.getEditor()).getTextField().setEditable(false);

        return jSpinner;
    }

    //Making/Drawing the fences
//    public void drawRectangles(Graphics g) {
//
//        Graphics2D graphics2D = (Graphics2D) g; // Makes the graphics be more advanced as Graphics2D to make implementations in 2D
//        Stroke borderStroke = new BasicStroke(6);
//
//        JPanel[] panels = {dateBox, performanceBox, timeBox, addOrRemoveBox};
//        for(JPanel panel : panels) {
//            Stroke boringStroke = graphics2D.getStroke();
//
//            graphics2D.setStroke(borderStroke);
//            graphics2D.drawRect(panel.getX() + 2, panel.getY() + 1, panel.getWidth(), panel.getHeight());
//            graphics2D.setStroke(boringStroke);
//        }
//    }
//    public void paint(Graphics g) {
//        super.paint(g);
//        drawRectangles(g);
//    }

    //Getters
    public static int getChosenDay() {return chosenDay;}

    public static int getChosenMonth() {
        return chosenMonth;}

    public static int getChosenYear() {return chosenYear;}
    public  List<String> getActionToList() {
        return actionToList;
    }
    public  void setActionToList(List<String> actionToList) {
        this.actionToList = actionToList;
    }
    public String getHoursStart() {return hoursStart.getValue().toString();}

    public String getMinutesStart() {return minutesStart.getValue().toString();}

    public String getSecondStart() {return secondStart.getValue().toString();}

    public String getHoursEnd() {return hoursEnd.getValue().toString();}

    public String getMinutesEnd() {return minutesEnd.getValue().toString();}
    public String getSecondEnd() {return secondEnd.getValue().toString();}
    public String getNameAction(){return nameAction.getText();}
    public String getPlans(){
        return "Date:"+chosenYear+"/"+(chosenMonth+1)+"/"+chosenDay+"\n"+
                "Time Starts:"+hoursStart.getValue().toString()+":"+minutesStart.getValue().toString()+":"+secondStart.getValue().toString()+"\n"+
                "Time Ends:"+hoursEnd.getValue().toString()+":"+minutesEnd.getValue().toString()+":"+secondEnd.getValue().toString()+"\n";
    }
    public String getPlans(int chosenYear,int chosenMonth,int chosenDay,int hoursStart,int minutesStart,int secondStart,
                           int hoursEnd, int minutesEnd,int secondEnd){
        return "Date:"+chosenYear+"/"+chosenMonth+"/"+chosenDay+"\n"+
                "Time Starts:"+hoursStart+":"+minutesStart+":"+secondStart+"\n"+
                "Time Ends:"+hoursEnd+":"+minutesEnd+":"+secondEnd+"\n";
    }


    //Updates the days to mach the month
    private void updateDayBox(){
        for (int i = 1; i <= nod; i++) {
            dayBox.addItem(i);
        }
    }

    public boolean isTimeValid(){
        boolean time = false;
        try {
            if (Integer.parseInt(hoursStart.getValue().toString())<Integer.parseInt(hoursEnd.getValue().toString())){
                time =true;
            } else if (Integer.parseInt(hoursStart.getValue().toString())==Integer.parseInt(hoursEnd.getValue().toString())) {
                if (Integer.parseInt(minutesStart.getValue().toString())<Integer.parseInt(minutesEnd.getValue().toString())){
                    time =true;
                } else if (Integer.parseInt(minutesStart.getValue().toString())==Integer.parseInt(minutesEnd.getValue().toString())) {
                    if (Integer.parseInt(secondStart.getValue().toString())==0 && Integer.parseInt(secondEnd.getValue().toString())>=30){
                        time =true;
                    }
                   else if (Integer.parseInt(secondStart.getValue().toString())<Integer.parseInt(secondEnd.getValue().toString()) &&
                            Integer.parseInt(secondStart.getValue().toString())!=0){
                        time =true;
                    }
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"ERROR IN USER'S INPUT","ERROR",JOptionPane.ERROR_MESSAGE);
        }
        return time;
    }
    public boolean isDateValid(){ //<-- need to change
        boolean date = true;
        GregorianCalendar cal = new GregorianCalendar();
        if (chosenYear< cal.get(GregorianCalendar.YEAR)){
            date = false;
            JOptionPane.showMessageDialog(null,"DATE INPUT WAS ALREADY PASSED","ERROR",JOptionPane.ERROR_MESSAGE);
        } else if (chosenYear== cal.get(GregorianCalendar.YEAR)) {
            if ((chosenMonth+1)<cal.get(GregorianCalendar.MONTH)){
                date = false;
                JOptionPane.showMessageDialog(null,"DATE INPUT WAS ALREADY PASSED","ERROR",JOptionPane.ERROR_MESSAGE);
            } else if ((chosenMonth+1)==cal.get(GregorianCalendar.MONTH)) {
                if (chosenDay<cal.get(GregorianCalendar.DAY_OF_MONTH)){
                    date=false;
                    JOptionPane.showMessageDialog(null,"DATE INPUT WAS ALREADY PASSED","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (chosenYear<=0){
            date = false;
            JOptionPane.showMessageDialog(null,"ERROR IN DATE INPUT","ERROR",JOptionPane.ERROR_MESSAGE);
        } else if (chosenYear>= cal.get(GregorianCalendar.YEAR)) {
            if (chosenMonth+1<=0){
                date = false;
                JOptionPane.showMessageDialog(null,"ERROR IN DATE INPUT","ERROR",JOptionPane.ERROR_MESSAGE);
            } else if ((chosenMonth+1)>=cal.get(GregorianCalendar.MONTH)) {
                if (chosenDay<=0){
                    date = false;
                    JOptionPane.showMessageDialog(null,"ERROR IN DATE INPUT","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        return date;
    }
    public boolean isTodayValid(){
        boolean validation=true;
        LocalTime localTime = LocalTime.now();
        GregorianCalendar cal = new GregorianCalendar();
        if (chosenYear== cal.get(GregorianCalendar.YEAR) && (chosenMonth+1)==cal.get(GregorianCalendar.MONTH) && chosenDay==cal.get(GregorianCalendar.DAY_OF_MONTH)){
            if (localTime.getHour()>Integer.parseInt(hoursStart.getValue().toString())){
                validation=false;
                JOptionPane.showMessageDialog(null,"TIME INPUT ALREADY PASSED","ERROR",JOptionPane.ERROR_MESSAGE);
            } else if (localTime.getHour()==Integer.parseInt(hoursStart.getValue().toString()) && localTime.getMinute()>Integer.parseInt(minutesStart.getValue().toString())) {
                validation=false;
                JOptionPane.showMessageDialog(null,"TIME INPUT ALREADY PASSED","ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
        return validation;
    }

//    public void setTodefult(){
//        dateBox.removeAll();
//        addOrRemoveBox.removeAll();
//        timeBox.removeAll();
//        listBox.removeAll();
//        boxOfBoxes.removeAll();
//        this.removeAll();
//    }
}