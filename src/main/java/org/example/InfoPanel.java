package org.example;

import org.example.VoiceHandler.VoiceManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.Clock;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class InfoPanel extends JLayeredPane {
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
    private final Image backgroundImage;
    private double givenScaleX;
    private double givenScaleY;
    ImageIcon icon = new ImageIcon("src/Resources/cosmetics/info.png");
    private JButton infoButton = new JButton();

    {
        try {
            backgroundImage = ImageIO.read(new File("src/Resources/cosmetics/hd_restoration_result_image.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public InfoPanel(int windowWidth, int windowHeight,String plans, int day,int month, int year, String seconds, String minutes , String hours
    ,double scaleX,double scaleY) {
        this.setLayout(new BorderLayout());
        this.setVisible(false);
        this.setPreferredSize(new Dimension((windowWidth/2)-17,windowHeight/2));
        Image image = icon.getImage().getScaledInstance((int) (icon.getIconWidth()*scaleX), (int) (icon.getIconHeight()*scaleY), Image.SCALE_SMOOTH);
        infoButton.setIcon(new ImageIcon(image));
        infoButton.setPreferredSize(new Dimension((int) (50*scaleX), (int) (50*scaleY)));
        infoButton.setOpaque(false);
        infoButton.setBorderPainted(false);
        for (ActionListener al : infoButton.getActionListeners()) {
            infoButton.removeActionListener(al);
        }
        infoButton.addActionListener(e -> {
            VoiceManager voiceManager=new VoiceManager();
            try {
                voiceManager.playSound((long) 18000*1000,26000);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        givenScaleX = scaleX;
        givenScaleY = scaleY;
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
        listPanel.setPreferredSize(new Dimension((windowWidth/2)-27,(windowHeight/2)-(windowHeight/60)));
        listActionTime = new JLabel();
        addToPlans(plans);
        fixed = plansList.stream().distinct().collect(Collectors.toList());
        listActionTime.setHorizontalAlignment(SwingConstants.CENTER);
        listActionTime.setVerticalAlignment(SwingConstants.CENTER);
        listActionTime.setText("<html> "+ lastTwoPlansString()+ "</html>");
        listActionTime.setFont(new Font("Arial", Font.PLAIN, (int) (12*scaleY)));
        this.add(infoButton,BorderLayout.NORTH,JLayeredPane.PALETTE_LAYER);
        this.add(listActionTime,BorderLayout.CENTER,JLayeredPane.DEFAULT_LAYER);
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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        g.drawImage(backgroundImage, 0,(int) (-50*givenScaleY), getWidth(),(int) (getHeight()+100*givenScaleY), this);
    }
}
