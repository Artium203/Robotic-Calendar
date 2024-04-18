package org.example;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.GregorianCalendar;

public class TimeSet extends JPanel {
    private static String user;

    private static int chosenDay;
    private static int chosenMonth;
    private static int chosenYear;

    private static int nod;
    private JComboBox<Integer> yearBox;
    private JComboBox<Integer> monthBox;
    private JComboBox<Integer> dayBox;
    private JButton button;

    private JRadioButton dragOption;
    private JRadioButton pressOption;
    private ButtonGroup bg;
    private JLabel textForInstruction;
    private JLabel textForDate;
    private Graphics2D rect1;
    private Graphics2D rect2;

    private JPanel box1;
    private JPanel box2;
    private JPanel box3;
    private JPanel box4;
    private JPanel box5;

    private JTextField hoursStart;
    private JTextField minutesStart;
    private JTextField secondStart;
    private JTextField hoursEnd;
    private JTextField minutesEnd;
    private JTextField secondEnd;
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

        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setPreferredSize(new Dimension(windowWidth-8,windowHeight-(windowHeight/10)-11));
        this.setVisible(false);
        this.setBackground(Color.gray);
        box1 = new JPanel();
        box1.setLayout(new FlowLayout(FlowLayout.LEFT));
        box1.setPreferredSize(new Dimension(335, 300));

        box2 = new JPanel();
        box2.setLayout(new FlowLayout(FlowLayout.CENTER));
        box2.setPreferredSize(new Dimension(350, 300));
        box2.setBackground(Color.white);

        box3 = new JPanel();
        box3.setLayout(new FlowLayout(FlowLayout.CENTER));
        box3.setPreferredSize(new Dimension(400, 320));
        box3.setBackground(Color.DARK_GRAY);

        box4 = new JPanel();
        box4.setPreferredSize(new Dimension(430, 320));
        box4.setBackground(Color.DARK_GRAY);

        box5 = new JPanel();
        box5.setPreferredSize(new Dimension(420, windowHeight-50));
        box5.setBackground(Color.DARK_GRAY);


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





        box1.add(textForDate);
        box1.add(yearBox);
        box1.add(monthBox);
        box1.add(dayBox);
        this.add(box1);

        box3.add(StartText);
        box3.add(hoursStartLabel);
        box3.add(hoursStart);
        box3.add(minutesStartLabel);
        box3.add(minutesStart);
        box3.add(secondsStartLabel);
        box3.add(secondStart);
        box3.add(spaceText);
        box3.add(endText);
        box3.add(hoursEndLabel);
        box3.add(hoursEnd);
        box3.add(minutesEndLabel);
        box3.add(minutesEnd);
        box3.add(secondsEndLabel);
        box3.add(secondEnd);
        this.add(box3);

        box2.add(textForInstruction);
        box2.add(dragOption);
        box2.add(pressOption);
        this.add(box2);

        this.add(box5);

        this.add(box4);







    }

    public void drawRectangles(Graphics g) {
        rect1 = (Graphics2D) g;
        Stroke oldStroke = rect1.getStroke();
        Stroke borderStroke = new BasicStroke(5); // ניתן לשנות את המספר לקבע רוחב מסגרת שונה
        rect1.setStroke(borderStroke);
        rect1.drawRect(box1.getX(), box1.getY(), box1.getWidth(), box1.getHeight());
        rect1.setStroke(oldStroke);

        rect2 = (Graphics2D) g;
        oldStroke = rect2.getStroke();
        rect2.setStroke(borderStroke);
        rect2.drawRect(box2.getX(), box2.getY(), box2.getWidth(), box2.getHeight());
        rect2.setStroke(oldStroke);


    }

    public void paint(Graphics g) {
        super.paint(g);
        drawRectangles(g);
    }

    public static int getChosenDay() {
        return chosenDay;
    }

    public static int getChosenMonth() {
        return chosenMonth;
    }

    public static int getChosenYear() {
        return chosenYear;
    }

    public void updateDayBox(){
        for (int i = 1; i <= nod; i++) {
            dayBox.addItem(i);
        }
    }

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