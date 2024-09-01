package org.example;

import java.awt.*;
import java.awt.event.InputEvent;
import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

public class RobotRunner {
    private int ttl;
    private Point actLocation= new Point(60,90);
    private boolean isLoop=true;
    private int repeater=6;
    //Name stands for Hours Of Repeat
    private int HOR=0;
    private int MOR=10;
    private int SOR=3;
    //Name stands for Hours Of Loop
    private int HOL=0;
    private int MOL=0;
    private int SOL=0;
    private boolean isPress=false;
    // A need to add remaining time and buttons for stoping,pusing, (maybe skipping)

    public RobotRunner(Map<Integer, List<Integer>> actions){

        //Make it to a mathod for only one action that gets his needs
        try {
            Robot robot = new Robot();
            Calendar calendar = new GregorianCalendar();
            Time time = new Time(calendar.getTime().getHours(),calendar.getTime().getMinutes(),calendar.getTime().getSeconds());
            Time givenTime = new Time(calendar.getTime().getHours()+HOL,calendar.getTime().getMinutes()+MOL,calendar.getTime().getSeconds()+SOL);
            int delay = (HOR*60*60+MOR*60+SOR)*1000;
            if (isLoop){
                robot.mouseMove(actLocation.x, actLocation.y);
                while (time.toLocalTime().getHour() != givenTime.toLocalTime().getHour() ||
                        time.toLocalTime().getMinute() != givenTime.toLocalTime().getMinute() ||
                        time.toLocalTime().getSecond() != givenTime.toLocalTime().getSecond()){
                    calendar = new GregorianCalendar();
                    time = new Time(calendar.getTime().getHours(),calendar.getTime().getMinutes(),calendar.getTime().getSeconds());
                    robot.setAutoDelay(delay);
                    if (isPress) {
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    } else {
                        robot.mouseWheel(2);
                    }
                }
            }else {
                for (int i = 0; i < repeater; i++) {
                    robot.mouseMove(actLocation.x, actLocation.y);
                    robot.setAutoDelay(delay);
                    if (isPress) {
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    } else {
                        robot.mouseWheel(2);
                    }
                }
            }
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }





    }
}
