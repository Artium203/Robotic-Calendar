package org.example;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class TimeSet extends JPanel {

    private static List<String> actionToList;// Will be used to put on a list

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
    private final JPanel boxOfBoxes;//Contains the panels together


    //The starting and ending of the action
    private JTextField hoursStart;
    private JTextField minutesStart;
    private JTextField secondStart;
    private JTextField hoursEnd;
    private JTextField minutesEnd;
    private JTextField secondEnd;

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

    public TimeSet(int windowWidth,int windowHeight, JButton confirm){
        //Panel layout and dimensions
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setPreferredSize(new Dimension(windowWidth-8,windowHeight-(windowHeight/10)-11));
        this.setVisible(false);
        this.setBackground(Color.gray);

        //Container's sets
        boxOfBoxes = new JPanel();
        boxOfBoxes.setLayout(new FlowLayout(FlowLayout.LEFT));
        boxOfBoxes.setPreferredSize(new Dimension(windowWidth/2+(windowWidth-((windowWidth/2)+(windowWidth/3)+150)),windowHeight-(windowHeight/10)-20));
        boxOfBoxes.setBackground(Color.cyan);


        //Sub panel section
        //Date box sets
        dateBox = new JPanel();
        dateBox.setLayout(new FlowLayout(FlowLayout.LEFT));
        dateBox.setPreferredSize(new Dimension((windowWidth/2+(windowWidth-((windowWidth/2)+445)))/3, (windowHeight-(windowHeight/10)-20)/2));

        //Action box's sets
        performanceBox = new JPanel();
        performanceBox.setLayout(new FlowLayout(FlowLayout.CENTER));
        performanceBox.setPreferredSize(new Dimension(400, (windowHeight-(windowHeight/10)-20)/2));
        performanceBox.setBackground(Color.white);

        //Time box's sets
        timeBox = new JPanel();
        timeBox.setLayout(new FlowLayout(FlowLayout.CENTER));
        timeBox.setPreferredSize(new Dimension(430, (windowHeight-(windowHeight/10)-20)/2));
        timeBox.setBackground(Color.DARK_GRAY);

        //Sets of add/remove to/from a list box
        addOrRemoveBox = new JPanel();
        addOrRemoveBox.setPreferredSize(new Dimension(
                (windowWidth / 2 + (windowWidth - ((windowWidth / 2) + 445))) / 4,
                (windowHeight - (windowHeight / 6) - 150) / 2));
        addOrRemoveBox.setBackground(Color.DARK_GRAY);

        //list box's sets
        listBox = new JPanel();
        int betterSize = windowWidth-windowWidth/2+(windowWidth-((windowWidth/2) + 445));
        listBox.setPreferredSize(new Dimension(betterSize-windowWidth/3, windowHeight/2 -(windowHeight/10)+150));
        listBox.setBackground(Color.GREEN);


        // Date selection components
        textForDate = new JLabel("Choose a date for instruction:");
        textForDate.setPreferredSize(new Dimension(450, 80));
        textForDate.setFont(new Font("Arial" , Font.BOLD , 22));
        add(textForDate);

        yearBox = new JComboBox<>(new Integer[]{0,2024, 2025, 2026, 2027, 2028, 2029, 2030, 2031, 2032 , 2033, 2034, 2035, 2036, 2037, 2038, 2039, 2040});
        yearBox.setPreferredSize(new Dimension(80 , 50));        yearBox.setFont(new Font("Arial", Font.BOLD, 20));

        monthBox = new JComboBox<>(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
        monthBox.setPreferredSize(new Dimension(80 , 50));        monthBox.setFont(new Font("Arial" , Font.BOLD , 20));
        monthBox.setVisible(false);

        dayBox = new JComboBox<>();
        dayBox.setPreferredSize(new Dimension(80 , 50));        dayBox.setFont(new Font("Arial" , Font.BOLD , 20));
        dayBox.setVisible(false);


        //Actions for the choices of the date

        yearBox.addActionListener(e -> yearSelection());
        monthBox.addActionListener(e -> monthSelection());
        dayBox.addActionListener(e -> daySelection());


        //Sets of action (on a mouse)
        textForInstruction = createTextLabel("Choose one of the following actions: ", 0, 400, 250, 70, new Font("Arial", Font.BOLD, 18));
        dragOption = createTextRadioButton("Scroll", 80, 60, new Font("Arial", Font.BOLD, 17));
        pressOption = createTextRadioButton("Press", 80, 60, new Font("Arial", Font.BOLD, 17));
        buttonGroup = new ButtonGroup();  // על מנת שתהיה למשתמש רק אופציה אחת לבחירה, נשתמש בדבר הבא
        textForInstruction.setBounds(0,400,250,70);
        buttonGroup.add(dragOption);
        buttonGroup.add(pressOption);


        //Sets of starting/ending time

        hoursStart = createTextField(2, "Hours Start", 30, 20, 23);
        hoursEnd = createTextField(2, "Hours End", 30, 20, 23);
        minutesStart = createTextField(2, "Minutes Start", 30, 20, 59);
        minutesEnd = createTextField(2, null, 30, 20, 59);

        secondStart = createTextField(2, null, 30, 20, 59);
        secondEnd = createTextField(2, null, 30, 20, 59);

        StartText = createLabel(" Choose time the robot will start the instruction:", 400, 40);
        hoursStartLabel = createLabel("Hour:", 45, 50);
        minutesStartLabel = createLabel("Minute:", 60, 50);
        secondsStartLabel = createLabel("Second:", 70, 50);
        spaceText = createLabel("                                         ", 400, 60);
        endText = createLabel(" Choose time the robot will finish the instruction:", 400, 40);
        hoursEndLabel = createLabel("Hour:", 45, 50);
        minutesEndLabel = createLabel("Minute:", 60, 50);
        secondsEndLabel = createLabel("Second:", 70, 60);
        //I changed here ^ the type of how you implement the font so please feel free to adjust it

        actionList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            actionList.add(new JCheckBox());
            actionList.get(i).setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-15));
            listBox.add(actionList.get(i));
        }

        actionToList = new ArrayList<>();
        addToList = new JButton("ADD");
        removeFromList = new JButton("REMOVE");
        addToList.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        addToList.addActionListener(e -> {
            if (e.getSource()==addToList && actionToList.size()==9){
                addToList.setEnabled(false);
            }
            if (e.getSource()==addToList && dragOption.isSelected()){
                actionToList.add(Arrays.toString(dragOption.getSelectedObjects()));
                for (int i = 0; i < 10; i++) {
                    if (actionList.get(i).getText().isEmpty()){
                        actionList.get(i).setText(Arrays.toString(dragOption.getSelectedObjects()));
                        break;
                    }
                }
            } else if (e.getSource()==addToList && pressOption.isSelected()) {
                actionToList.add(Arrays.toString(pressOption.getSelectedObjects()));
                for (int i = 0; i < 10; i++) {
                    if (actionList.get(i).getText().isEmpty()){
                        actionList.get(i).setText(Arrays.toString(pressOption.getSelectedObjects()));
                        break;
                    }
                }
            }
        });
        removeFromList.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        removeFromList.addActionListener(e -> {
            if (e.getSource()==removeFromList && actionToList.isEmpty()){
                addToList.setEnabled(true);
            }else {
                removeFromList.setEnabled(true);
            }
            if (e.getSource()==removeFromList && !actionToList.isEmpty()){
                for(JCheckBox box:actionList){
                    if (box.isSelected()){
                        actionToList.remove(box.getText());
                        box.setText("");
                    }
                }
                addToList.setEnabled(true);
            }
        });

        listBox.add(confirm);

        //Adding/combining things together
        this.add(boxOfBoxes);
        boxOfBoxes.add(dateBox);
        dateBox.add(textForDate);
        dateBox.add(yearBox);
        dateBox.add(monthBox);
        dateBox.add(dayBox);

        boxOfBoxes.add(timeBox);
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


        boxOfBoxes.add(performanceBox);
        performanceBox.add(textForInstruction);
        performanceBox.add(dragOption);
        performanceBox.add(pressOption);

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

    private JLabel createTextLabel(String text, int x, int y , int width, int height, Font font){
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setFont(font);
        return label;
    }

    private JLabel createLabel(String text, int width, int height) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(width, height));
        label.setFont(new Font("Arial", Font.BOLD, 17));
        label.setForeground(Color.white);
        return label;
    }

    private JRadioButton createTextRadioButton(String text, int width, int height, Font font){
        JRadioButton jRadioButton = new JRadioButton(text);
        jRadioButton.setPreferredSize(new Dimension(width, height));
        jRadioButton.setFont(font);
        jRadioButton.setFocusPainted(false);
        return jRadioButton;
    }

    private JTextField createTextField(int col, String command, int width, int height, int limit){
        JTextField jTextField = new JTextField(col);
        jTextField.setPreferredSize(new Dimension(width, height));
        if(command != null){
            jTextField.setActionCommand(command);
        }
        Utils.limitJTextField(jTextField, limit);
        return jTextField;
    }

    //Making/Drawing the fences
    public void drawRectangles(Graphics g) {

        Graphics2D graphics2D = (Graphics2D) g; // Makes the graphics be more advanced as Graphics2D to make implementations in 2D
        Stroke borderStroke = new BasicStroke(6);

        JPanel[] panels = {dateBox, performanceBox, timeBox, addOrRemoveBox};
        for(JPanel panel : panels) {
            Stroke boringStroke = graphics2D.getStroke();

            graphics2D.setStroke(borderStroke);
            graphics2D.drawRect(panel.getX() + 2, panel.getY() + 1, panel.getWidth(), panel.getHeight());
            graphics2D.setStroke(boringStroke);
        }
    }
    public void paint(Graphics g) {
        super.paint(g);
        drawRectangles(g);
    }

    //Getters
    public static int getChosenDay() {
        return chosenDay;
    }

    public static int getChosenMonth() {
        return chosenMonth;
    }

    public static int getChosenYear() {
        return chosenYear;
    }
    public static List<String> getActionToList() {
        return actionToList;
    }
    public String getHoursStart() {return hoursStart.getText();}

    public String getMinutesStart() {return minutesStart.getText();}

    public String getSecondStart() {return secondStart.getText();}

    public String getHoursEnd() {return hoursEnd.getText();}

    public String getMinutesEnd() {return minutesEnd.getText();}
    public String getSecondEnd() {return secondEnd.getText();}

    //Updates the days to mach the month
    private void updateDayBox(){
        for (int i = 1; i <= nod; i++) {
            dayBox.addItem(i);
        }
    }

    public boolean isTimeValid(){
        boolean time = false;
        if (Integer.parseInt(hoursStart.getText())<Integer.parseInt(hoursEnd.getText())){
            time =true;
        } else if (Integer.parseInt(hoursStart.getText())==Integer.parseInt(hoursEnd.getText())) {
            if (Integer.parseInt(minutesStart.getText())<Integer.parseInt(minutesEnd.getText())){
                time =true;
            } else if (Integer.parseInt(minutesStart.getText())==Integer.parseInt(minutesEnd.getText())) {
                if (Integer.parseInt(secondStart.getText())<Integer.parseInt(secondEnd.getText())){
                    time =true;
                }
            }
        }
        return time;
    }

    //Limits the amount of text for time writing (so no illegal term would catch)
//    private void limitJTextField(JTextField textField, int maxValue){
//        AbstractDocument document = (AbstractDocument) textField.getDocument();
//        document.setDocumentFilter(new DocumentFilter() {
//            @Override
//            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
//                if (isNumeric(string) && (fb.getDocument().getLength() + string.length()) <= 2) {
//                    String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
//                    if (isValid(newText)) {
//                        super.insertString(fb, offset, string, attr);
//                    }
//                }
//            }
//
//            // Allow only numeric characters and check text length
//            @Override
//            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
//                if (isNumeric(text) && (fb.getDocument().getLength() + text.length() - length) <= 2) {
//                    String newText = fb.getDocument().getText(0, offset) + text + fb.getDocument().getText(offset + length, fb.getDocument().getLength() - offset - length);
//                    if (isValid(newText)) {
//                        super.replace(fb, offset, length, text, attrs);
//                    }
//                }
//            }
//
//            //checks whether the String contains only numbers
//            private boolean isNumeric(String text) {
//                return text != null && text.matches("\\d");
//            }
//
//            // Checks whether the String passes the wanting limitation
//            private boolean isValid(String text) {
//                if (text.isEmpty()) {
//                    return true;
//                }
//                int numberInput = Integer.parseInt(text);
//                return numberInput >= 0 && numberInput <= maxValue;
//            }
//        });
//    }
}
