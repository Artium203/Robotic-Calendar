package org.example;

import javax.swing.*;
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


//    private JRadioButton


    public TimeSet(int windowWidth,int windowHeight){

        this.setLayout(null);
        this.setPreferredSize(new Dimension(windowWidth-8,windowHeight-(windowHeight/10)-11));
        this.setVisible(false);
        this.setBackground(Color.gray);
        button = new JButton();
        button.setBounds( 300,200,70,70);

        textForDate = new JLabel();
        textForDate.setText("Choose a date for instruction: ");
        textForDate.setBounds(20,50,450,80);
        textForDate.setFont(new Font("Arial" , Font.BOLD , 22));
        add(textForDate);

        yearBox = new JComboBox<>(new Integer[]{0,2024, 2025, 2026, 2027, 2028, 2029, 2030, 2031, 2032 , 2033, 2034, 2035, 2036, 2037, 2038, 2039, 2040});
        monthBox = new JComboBox<>(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
        dayBox = new JComboBox<>();
        yearBox.setBounds(30 , 200 , 80 , 50);
        monthBox.setBounds(yearBox.getX() + 110, 200, 80 , 50);
        dayBox.setBounds(monthBox.getX() + 110, 200, 80, 50);

        dayBox.setFont(new Font("Arial" , Font.BOLD , 20));
        monthBox.setFont(new Font("Arial" , Font.BOLD , 20));
        yearBox.setFont(new Font("Arial", Font.BOLD, 20));

        this.add(yearBox);
        this.add(monthBox);
        this.add(dayBox);
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
        add(textForInstruction);

        dragOption = new JRadioButton();
        dragOption.setText("Drag");     //פעולת גרירה
        dragOption.setBounds(150,500,80 , 60);
        dragOption.setFont(new Font("Arial" , Font.BOLD , 17));
        dragOption.setFocusPainted(false);
        add(dragOption);

        pressOption = new JRadioButton();
        pressOption.setText("Press");     //פעולת לחיצה
        pressOption.setBounds(150,570,80 , 60);
        pressOption.setFont(new Font("Arial" , Font.BOLD , 17));
        pressOption.setFocusPainted(false);
        add(pressOption);

        bg = new ButtonGroup();  // על מנת שתהיה למשתמש רק אופציה אחת לבחירה, נשתמש בדבר הבא
        bg.add(dragOption);
        bg.add(pressOption);










    }

    public void drawRectangles(Graphics g) {
        rect1 = (Graphics2D) g;
        Stroke oldStroke = rect1.getStroke();
        Stroke borderStroke = new BasicStroke(5); // ניתן לשנות את המספר לקבע רוחב מסגרת שונה
        rect1.setStroke(borderStroke);
        rect1.drawRect(10, 40, 430, 320);
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
}
