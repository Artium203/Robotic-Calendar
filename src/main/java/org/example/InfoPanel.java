package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.time.Clock;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class InfoPanel extends JPanel {
    private JPanel listPanel;
    private JLabel listActionTime;

    private static List<String> plansList = new ArrayList<>();

    private final GregorianCalendar cal = new GregorianCalendar();
    private int currentDay = cal.get(GregorianCalendar.DAY_OF_MONTH);
    private int currentMonth = 1+cal.get(GregorianCalendar.MONTH);
    private int currentYear = cal.get(GregorianCalendar.YEAR);
    private int chosenYear;
    private int chosenMonth;
    private int chosenDay;
    private String chosenStartHours;
    private String chosenStartMinutes;
    private String chosenStartSeconds;
    private List<String> fixed;


    public InfoPanel(int windowWidth, int windowHeight,String plans, int day,int month, int year, String seconds, String minutes , String hours){
        this.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.setVisible(false);
        this.setBackground(Color.red);
        this.setPreferredSize(new Dimension((windowWidth/2)-17,windowHeight/2));

        chosenYear=year;
        chosenMonth=month;
        chosenDay=day;
        chosenStartHours=hours;
        chosenStartMinutes=minutes;
        chosenStartSeconds=seconds;
//        System.out.println(chosenDay+"/"+chosenMonth+"/"+chosenYear);
//        System.out.println(currentDay+"/"+currentMonth+"/"+currentYear);

        listPanel = new JPanel();
        listPanel.setBackground(Color.gray);
        listPanel.setPreferredSize(new Dimension((windowWidth/2)-27,(windowHeight/2)-(windowHeight/60)));
        listActionTime = new JLabel();
        listActionTime.setPreferredSize(new Dimension((windowWidth/2)-27,(windowHeight/2)-(windowHeight/60)));
        addToLastTwoPlans(plans);
        fixed = plansList.stream().distinct().collect(Collectors.toList());
        listActionTime.setText("<html> "+ lastTwoPlansString()+ "</html>");
        listPanel.add(listActionTime);
        this.add(listPanel);
    }
    public String lastTwoPlansString(){
        StringBuilder get = new StringBuilder();
        
        for (String plan : fixed){
            get.append(plan);
            get.append("<br><br>");
        }
        return get.toString();
    }
    public void addToLastTwoPlans(String plan){
        if (currentYear <= chosenYear ){
            if (currentMonth<chosenMonth){
                if (this.plansList.size() > 9){
                    plansList.remove(0);
                    plansList.add(plan);
                    Collections.sort(plansList);
                }else {
                    plansList.add(plan);
                    Collections.sort(plansList);
                }
            } else if (currentMonth+1==chosenMonth) {
                if (currentDay <= chosenDay){
                    if (this.plansList.size() > 9){
                        plansList.remove(0);
                        plansList.add(plan);
                        Collections.sort(plansList);
                    }else {
                        plansList.add(plan);
                        Collections.sort(plansList);
                    }
                }
            }
        }
    }
}
