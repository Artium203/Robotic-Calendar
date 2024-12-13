package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
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
    private Calendar calendar = GregorianCalendar.getInstance();
    private int currentHour;
    private int currentMinut;
    private int currentSecond;

    private int chosenYear;
    private int chosenMonth;
    private int chosenDay;
    private int chosenStartHours;
    private int chosenStartMinutes;
    private int chosenStartSeconds;
    private List<String> fixed;
//    Font customFontSized;
//
//    {
//        try {
//            customFontSized = Font.createFont(Font.TRUETYPE_FONT,new File("src/Resources/Pixel.ttf")).deriveFont(20f);
//        } catch (FontFormatException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }


    public InfoPanel(int windowWidth, int windowHeight,String plans, int day,int month, int year, String seconds, String minutes , String hours){
        this.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.setVisible(false);
        this.setBackground(Color.red);
        this.setPreferredSize(new Dimension((windowWidth/2)-17,windowHeight/2));
        chosenYear=year;
        chosenMonth=month;
        chosenDay=day;
        chosenStartHours= Integer.parseInt(hours);
        chosenStartMinutes= Integer.parseInt(minutes);
        chosenStartSeconds= Integer.parseInt(seconds);
        calendar.setTime(cal.getTime());
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinut= calendar.get(Calendar.MINUTE);
        currentSecond = calendar.get(Calendar.SECOND);
        listPanel = new JPanel();
        listPanel.setBackground(Color.gray);
        listPanel.setPreferredSize(new Dimension((windowWidth/2)-27,(windowHeight/2)-(windowHeight/60)));
        listActionTime = new JLabel();
        listActionTime.setPreferredSize(new Dimension((windowWidth/2)-27,(windowHeight/2)-(windowHeight/60)));
        addToPlans(plans);
        fixed = plansList.stream().distinct().collect(Collectors.toList());
        listActionTime.setText("<html> "+ lastTwoPlansString()+ "</html>");
//        listActionTime.setFont(customFontSized.deriveFont(15f));
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
    public void addToPlans(String plan){
        if (currentYear<chosenYear){
            if (this.plansList.size() > 9){
                plansList.remove(0);
                plansList.add(plan);
                Collections.sort(plansList);
            }else {
                plansList.add(plan);
                Collections.sort(plansList);
            }
        }
        if (currentYear == chosenYear ){
            if (currentMonth<chosenMonth){
                if (this.plansList.size() > 9){
                    plansList.remove(0);
                    plansList.add(plan);
                    Collections.sort(plansList);
                }else {
                    plansList.add(plan);
                    Collections.sort(plansList);
                }
            } else if (currentMonth==chosenMonth) {
                if (currentDay < chosenDay){
                    if (this.plansList.size() > 9){
                        plansList.remove(0);
                        plansList.add(plan);
                        Collections.sort(plansList);
                    }else {
                        plansList.add(plan);
                        Collections.sort(plansList);
                    }
                } else if (currentDay == chosenDay) {
                    if (currentHour<chosenStartHours){
                        if (this.plansList.size() > 9){
                            plansList.remove(0);
                            plansList.add(plan);
                            Collections.sort(plansList);
                        }else {
                            plansList.add(plan);
                            Collections.sort(plansList);
                        }
                    } else if (currentHour==chosenStartHours && currentMinut<chosenStartMinutes) {
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
    public void setToDefult(){
        plansList.clear();
        fixed.clear();
    }
}
