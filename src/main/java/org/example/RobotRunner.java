package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

public class RobotRunner extends Thread {
    private Point actLocation;
    private boolean isLoop;
    private int repeater;
    //Name stands for Hours Of Repeat
    private int HOR;
    private int MOR;
    private int SOR;
    //Name stands for Hours Of Loop
    private int HOL;
    private int MOL;
    private int SOL;
    private boolean isPress;
    private volatile boolean running;
    private volatile boolean paused;
    private volatile boolean finished = false;
    int iTheRunner;


    public RobotRunner(List<Integer> actions,int whoruns){
            if (actions.get(0)==11){
                isPress=true;
            }else {
                isPress=false;
            }
            if (actions.get(1)==1){
                isLoop=true;
            }else {
                isLoop=false;
            }
            this.repeater=actions.get(2);
            this.HOR=actions.get(3);
            this.MOR=actions.get(4);
            this.SOR=actions.get(5);
            this.HOL=actions.get(6);
            this.MOL=actions.get(7);
            this.SOL=actions.get(8);
            this.actLocation= new Point(actions.get(9),actions.get(10));
            running=true;
            paused=false;
            this.iTheRunner = whoruns;
    }
    public void run(){
        try {
            Robot robot = new Robot();
            Calendar calendar = new GregorianCalendar();
            Time time = new Time(calendar.getTime().getHours(),calendar.getTime().getMinutes(),calendar.getTime().getSeconds());
            Time givenTime = new Time(calendar.getTime().getHours()+HOL,calendar.getTime().getMinutes()+MOL,calendar.getTime().getSeconds()+SOL);
            System.out.println("The "+ iTheRunner+ " is running");
            System.out.println("The given time is "+givenTime+" and is: "+time);
            int delay = (HOR*60*60+MOR*60+SOR)*1000;
            if (isLoop){
                robot.mouseMove(actLocation.x, actLocation.y);
                while (running && (time.getTime()<givenTime.getTime())){
                    synchronized (this) {
                        while (paused) {
                            wait();
                        }
                    }
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
                robot.mouseMove(actLocation.x, actLocation.y);
                for (int i = 0; running && i < repeater; i++) {
                    synchronized (this) {
                        while (paused) {
                            wait();
                        }
                    }
                    robot.setAutoDelay(delay);
                    if (isPress) {
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    } else {
                        robot.mouseWheel(2);
                    }
                }
            }
            System.out.println(iTheRunner+" has finished");
        } catch (AWTException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        finished = true;
    }
    public void stopR() {
        running = false;
        System.out.println("The "+ iTheRunner+" has stopped");
    }
    public synchronized void pauseR() {
        paused = true;
    }
    public synchronized void resumeR() {
        paused = false;
        notify();
    }
    public boolean isPausedR() {
        return paused;
    }
    public boolean isRunningR(){return running;}
    public boolean isFinished(){return finished;}
    public void runAgainR(){running=true;}
}
