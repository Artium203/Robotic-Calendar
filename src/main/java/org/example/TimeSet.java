package org.example;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.GregorianCalendar;

public class TimeSet extends JPanel {
    private static String dateAndTime;// Will be used to put on a list

    //The chosen date from user
    private static int chosenDay;
    private static int chosenMonth;
    private static int chosenYear;

    private static int nod;//  Used for calculations instructions

    //Options of choosing the date
    private JComboBox<Integer> yearBox;
    private JComboBox<Integer> monthBox;
    private JComboBox<Integer> dayBox;

    private JButton addToList;// Button that confirmed user's chose to add to a list
    private JButton removeFromList; // Button that removes user's chose from the list

    //Action option
    private JRadioButton dragOption;
    private JRadioButton pressOption;
    private ButtonGroup bg;// Only to pick one of the 2 buttons

    private JLabel textForInstruction; //Simple explanation
    private JLabel textForDate;//Simple explanation

    //Feces around the user's input
    private Graphics2D rect1;
    private Graphics2D rect2;
    private Graphics2D rect3;
    private Graphics2D rect4;

    private JPanel dateBox;//Gets the date from user
    private JPanel performanceBox;//Gets the action from user
    private JPanel timeBox;//Gets the time from user
    private JPanel addOrRemoveBox;//Gets to put/remove to/from a list
    private JPanel listBox;//List of actions (max 10 actions to put)
    private JPanel boxOfBoxes;//Contains the panels together

    //The starting and ending of the action
    private JTextField hoursStart;
    private JTextField minutesStart;
    private JTextField secondStart;
    private JTextField hoursEnd;
    private JTextField minutesEnd;
    private JTextField secondEnd;

    //Title to understand which is who
    private JLabel hoursStartLabel;
    private JLabel minutesStartLabel;
    private JLabel secondsStartLabel;
    private JLabel hoursEndLabel;
    private JLabel minutesEndLabel;
    private JLabel secondsEndLabel;
    private JLabel StartText;
    private JLabel endText;
    private JLabel spaceText;






    public TimeSet(int windowWidth,int windowHeight){
        //Pane's sets
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setPreferredSize(new Dimension(windowWidth-8,windowHeight-(windowHeight/10)-11));
        this.setVisible(false);
        this.setBackground(Color.gray);

        //Container's sets
        boxOfBoxes = new JPanel();
        boxOfBoxes.setLayout(new FlowLayout(FlowLayout.LEFT));
        boxOfBoxes.setPreferredSize(new Dimension(windowWidth/2+(windowWidth-((windowWidth/2)+445)),windowHeight-(windowHeight/10)-20));
        boxOfBoxes.setBackground(Color.cyan);

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
        addOrRemoveBox.setPreferredSize(new Dimension((windowWidth/2+(windowWidth-((windowWidth/2)+445)))/2+10, (windowHeight-(windowHeight/10)-20)/2));
        addOrRemoveBox.setBackground(Color.DARK_GRAY);

        //list box's sets
        listBox = new JPanel();
        listBox.setPreferredSize(new Dimension(420, windowHeight-(windowHeight/10)-20));
        listBox.setBackground(Color.DARK_GRAY);


        //Sets for date
        textForDate = new JLabel();
        textForDate.setText("Choose a date for instruction:");
        textForDate.setPreferredSize(new Dimension(450,80));
        textForDate.setFont(new Font("Arial" , Font.BOLD , 22));
        add(textForDate);
        yearBox = new JComboBox<>(new Integer[]{0,2024, 2025, 2026, 2027, 2028, 2029, 2030, 2031, 2032 , 2033, 2034, 2035, 2036, 2037, 2038, 2039, 2040});
        monthBox = new JComboBox<>(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
        dayBox = new JComboBox<>();

        yearBox.setPreferredSize(new Dimension(80 , 50));
        monthBox.setPreferredSize(new Dimension(80 , 50));
        dayBox.setPreferredSize(new Dimension(80 , 50));

        dayBox.setFont(new Font("Arial" , Font.BOLD , 20));
        monthBox.setFont(new Font("Arial" , Font.BOLD , 20));
        yearBox.setFont(new Font("Arial", Font.BOLD, 20));


        monthBox.setVisible(false);
        dayBox.setVisible(false);

        //Actions for the choices of the date
        yearBox.addActionListener(e -> {
            if (!yearBox.getSelectedItem().equals("0")){
                monthBox.setVisible(true);
                chosenYear = (int) yearBox.getSelectedItem();
            }
        });
        monthBox.addActionListener(e -> {
            if (!monthBox.getSelectedItem().equals("0") && !yearBox.getSelectedItem().equals("0")){
                chosenMonth = (int) monthBox.getSelectedItem()-1;
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
        });
        dayBox.addActionListener(e -> {
            if (dayBox.getItemCount() != 0){
                chosenDay = (int) dayBox.getSelectedItem();
            }
        });


        //Sets of action (on a mouse)
        textForInstruction = new JLabel();
        textForInstruction.setText("Choose one of the following actions:");
        textForInstruction.setBounds(0,400,250,70);
        textForInstruction.setFont(new Font("Arial" , Font.BOLD , 18));

        dragOption = new JRadioButton();
        dragOption.setText("Drag");     //פעולת גרירה
        dragOption.setPreferredSize(new Dimension(80 , 60));
        dragOption.setFont(new Font("Arial" , Font.BOLD , 17));
        dragOption.setFocusPainted(false);

        pressOption = new JRadioButton();
        pressOption.setText("Press");     //פעולת לחיצה
        pressOption.setPreferredSize(new Dimension(80 , 60));
        pressOption.setFont(new Font("Arial" , Font.BOLD , 17));
        pressOption.setFocusPainted(false);

        bg = new ButtonGroup();  // על מנת שתהיה למשתמש רק אופציה אחת לבחירה, נשתמש בדבר הבא
        bg.add(dragOption);
        bg.add(pressOption);

        //Sets of starting/ending time
        hoursStart = new JTextField(2);
        hoursStart.setPreferredSize(new Dimension(30 , 20));
        hoursStart.setActionCommand("Hours Start");
        limitJTextField(hoursStart, 23);

        hoursEnd = new JTextField(2);
        hoursEnd.setPreferredSize(new Dimension(30 , 20));
        hoursEnd.setActionCommand("Hours End");
        limitJTextField(hoursEnd, 23);

        minutesStart = new JTextField(2);
        minutesStart.setPreferredSize(new Dimension(30 , 20));
        minutesStart.setActionCommand("Minutes Start");
        limitJTextField(minutesStart, 59);

        minutesEnd = new JTextField(2);
        minutesEnd.setPreferredSize(new Dimension(30 , 20));
        limitJTextField(minutesEnd, 59);

        secondStart = new JTextField(2);
        secondStart.setPreferredSize(new Dimension(30 , 20));
        limitJTextField(secondStart, 59);

        secondEnd = new JTextField(2);
        secondEnd.setSelectionEnd(1);
        secondEnd.setPreferredSize(new Dimension(30 , 20));
        limitJTextField(secondEnd, 59);

        StartText = new JLabel();
        StartText.setText(" Choose time the robot will start the instruction:");
        StartText.setPreferredSize(new Dimension(400 , 40));
        StartText.setFont(new Font("Arial" , Font.BOLD , 17));
        StartText.setForeground(Color.white);

        hoursStartLabel = new JLabel();
        hoursStartLabel.setText("Hour:");
        hoursStartLabel.setPreferredSize(new Dimension(45 , 50));
        hoursStartLabel.setFont(new Font("Arial" , Font.BOLD , 17));
        hoursStartLabel.setForeground(Color.white);

        minutesStartLabel = new JLabel();
        minutesStartLabel.setText("Minute:");
        minutesStartLabel.setPreferredSize(new Dimension(60 , 50));
        minutesStartLabel.setFont(new Font("Arial" , Font.BOLD , 17));
        minutesStartLabel.setForeground(Color.white);

        secondsStartLabel = new JLabel();
        secondsStartLabel.setText("Second:");
        secondsStartLabel.setPreferredSize(new Dimension(70 , 50));
        secondsStartLabel.setFont(new Font("Arial" , Font.BOLD , 17));
        secondsStartLabel.setForeground(Color.white);

        spaceText = new JLabel();
        spaceText.setText("                                         ");
        spaceText.setPreferredSize(new Dimension(400 , 60));

        endText = new JLabel();
        endText.setText(" Choose time the robot will finish the instruction:");
        endText.setPreferredSize(new Dimension(400 , 40));
        endText.setFont(new Font("Arial" , Font.BOLD , 17));
        endText.setForeground(Color.white);

        hoursEndLabel = new JLabel();
        hoursEndLabel.setText("Hour:");
        hoursEndLabel.setPreferredSize(new Dimension(45 , 50));
        hoursEndLabel.setFont(new Font("Arial" , Font.BOLD , 17));
        hoursEndLabel.setForeground(Color.white);

        minutesEndLabel = new JLabel();
        minutesEndLabel.setText("Minute:");
        minutesEndLabel.setPreferredSize(new Dimension(60 , 50));
        minutesEndLabel.setFont(new Font("Arial" , Font.BOLD , 17));
        minutesEndLabel.setForeground(Color.white);

        secondsEndLabel = new JLabel();
        secondsEndLabel.setText("Second:");
        secondsEndLabel.setPreferredSize(new Dimension(70 , 60));
        secondsEndLabel.setFont(new Font("Arial" , Font.BOLD , 17));
        secondsEndLabel.setForeground(Color.white);

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

        this.add(listBox);








    }
    //Making/Drawing the fences
    public void drawRectangles(Graphics g) {
        rect1 = (Graphics2D) g;
        Stroke oldStroke = rect1.getStroke();
        Stroke borderStroke = new BasicStroke(6); // ניתן לשנות את המספר לקבע רוחב מסגרת שונה
        rect1.setStroke(borderStroke);
        rect1.drawRect(dateBox.getX()+2, dateBox.getY()+1, dateBox.getWidth(), dateBox.getHeight());
        rect1.setStroke(oldStroke);

        rect2 = (Graphics2D) g;
        oldStroke = rect2.getStroke();
        rect2.setStroke(borderStroke);
        rect2.drawRect(performanceBox.getX()+2, performanceBox.getY()+2, performanceBox.getWidth(), performanceBox.getHeight());
        rect2.setStroke(oldStroke);

        rect3 = (Graphics2D) g;
        oldStroke = rect3.getStroke();
        rect3.setStroke(borderStroke);
        rect3.drawRect(timeBox.getX()+4, timeBox.getY()+2, timeBox.getWidth(), timeBox.getHeight());
        rect3.setStroke(oldStroke);

        rect4 = (Graphics2D) g;
        oldStroke = rect4.getStroke();
        rect4.setStroke(borderStroke);
        rect4.drawRect(addOrRemoveBox.getX()+4, addOrRemoveBox.getY()+2, addOrRemoveBox.getWidth(), addOrRemoveBox.getHeight());
        rect4.setStroke(oldStroke);


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

    //Updates the days to mach the month
    public void updateDayBox(){
        for (int i = 1; i <= nod; i++) {
            dayBox.addItem(i);
        }

    }
    //Limits the amount of text for time writing (so no illegal term would catch)
    public void limitJTextField(JTextField textField, int maxValue){
        AbstractDocument document = (AbstractDocument) textField.getDocument();
        document.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (isNumeric(string) && (fb.getDocument().getLength() + string.length()) <= 2) {
                    String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
                    if (isValid(newText)) {
                        super.insertString(fb, offset, string, attr);
                    }
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                // Allow only numeric characters and check text length
                if (isNumeric(text) && (fb.getDocument().getLength() + text.length() - length) <= 2) {
                    String newText = fb.getDocument().getText(0, offset) + text + fb.getDocument().getText(offset + length, fb.getDocument().getLength() - offset - length);
                    if (isValid(newText)) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
            }

            private boolean isNumeric(String text) {
                return text != null && text.matches("\\d");
            }

            private boolean isValid(String text) {
                if (text.length() == 0) return true;
                int numberInput = Integer.parseInt(text);
                return numberInput >= 0 && numberInput <= maxValue;
            }
        });
    }
}