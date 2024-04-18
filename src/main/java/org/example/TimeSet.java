package org.example;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import javax.xml.crypto.Data;
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





    public TimeSet(int windowWidth,int windowHeight){

        this.addKeyListener(this);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setPreferredSize(new Dimension(windowWidth-8,windowHeight-(windowHeight/10)-11));
        this.setVisible(false);
        this.setBackground(Color.gray);
        box1 = new JPanel();
        box1.setPreferredSize(new Dimension(430, 320));

        box2 = new JPanel();
        box2.setPreferredSize(new Dimension(430, 320));

        box3 = new JPanel();
        box3.setPreferredSize(new Dimension(430, 320));
        box3.setBackground(Color.DARK_GRAY);

        box4 = new JPanel();
        box4.setPreferredSize(new Dimension(430, 320));
        box4.setBackground(Color.DARK_GRAY);

        box5 = new JPanel();
        box5.setPreferredSize(new Dimension(330, windowHeight-50));
        box5.setBackground(Color.DARK_GRAY);


        textForDate = new JLabel();
        textForDate.setText("Choose a date for instruction: ");
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
        textForInstruction.setText("Choose one of the following actions: ");
        textForInstruction.setBounds(20,400,450,80);
        textForInstruction.setFont(new Font("Arial" , Font.BOLD , 22));


        dragOption = new JRadioButton();
        dragOption.setText("Drag");     //פעולת גרירה
        dragOption.setBounds(150,500,80 , 60);
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
        hoursStart.setPreferredSize(new Dimension(20 , 20));
        hoursStart.setActionCommand("Hours Start");


        hoursEnd = new JTextField(2);
        hoursEnd.setPreferredSize(new Dimension(20 , 20));
        hoursEnd.setActionCommand("Hours End");

        minutesStart = new JTextField(2);
        minutesStart.setPreferredSize(new Dimension(20 , 20));
        minutesStart.setActionCommand("Minutes Start");


        minutesEnd = new JTextField(2);
        
        minutesEnd.setPreferredSize(new Dimension(20 , 20));


        secondStart = new JTextField(2);

        secondStart.setPreferredSize(new Dimension(20 , 20));


        secondEnd = new JTextField(2);
        secondEnd.setSelectionEnd(1);
        secondEnd.setPreferredSize(new Dimension(20 , 20));




//        secondEnd.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                if (secondEnd.getText().length() == 2) {
//                    secondEnd.setEnabled(false);
//                    secondEnd.setSelectionEnd(1);
//
//
//                }
//            }
//
//
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                // No action needed
//                if (secondEnd.getText().length() == 2) {
//
//                    secondEnd.setEnabled(true);
////                    secondEnd.setInputVerifier();
//                    secondEnd.setSelectionEnd(1);
//                }
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                // No action needed
//            }
//        });










        this.add(box1);
        box1.add(textForDate);
        box1.add(yearBox);
        box1.add(monthBox);
        box1.add(dayBox);
        this.add(box3);
        box3.add(hoursStart);
        box3.add(hoursEnd);
        box3.add(minutesStart);
        box3.add(minutesEnd);
        box3.add(secondStart);
        box3.add(secondEnd);
        this.add(box5);
        this.add(box2);
        box2.add(textForInstruction);
        box2.add(dragOption);
        box2.add(pressOption);
        this.add(box4);





    }

    public void drawRectangles(Graphics g) {
        rect1 = (Graphics2D) g;
        Stroke oldStroke = rect1.getStroke();
        Stroke borderStroke = new BasicStroke(5); // ניתן לשנות את המספר לקבע רוחב מסגרת שונה
        rect1.setStroke(borderStroke);
        rect1.drawRect(box1.getX(), box1.getY(), 430, 320);
        rect1.setStroke(oldStroke);

        rect2 = (Graphics2D) g;
        oldStroke = rect2.getStroke();
        rect2.setStroke(borderStroke);
        rect2.drawRect(10, 400, 430, 320);
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

    @Override
    public void keyTyped(KeyEvent e) {
        e.getSource();
        if(secondEnd.getText().length() >= 2){
            System.out.println("here");

            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }




    

}