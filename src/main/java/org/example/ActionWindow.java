package org.example;

import org.example.Data.DataHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class ActionWindow extends JFrame {
    private RobotRunner[] operations;
    private Window window;
    private final JButton FORWARD=new JButton("⏭");
    private final JButton BACKWARD= new JButton("⏮");
    //►
    private final JButton PAUSE_RESUME= new JButton("⏸");
    private int index;
    private static int currenIndex=0;
    private final AtomicBoolean pressedForward = new AtomicBoolean(false);
    private final AtomicBoolean pressedBackward = new AtomicBoolean(false);
    private static final AtomicBoolean pressedPause = new AtomicBoolean(false);
    private static final AtomicBoolean pressedResume = new AtomicBoolean(false);
    private final AtomicBoolean stop = new AtomicBoolean(false);
    private Thread thread;

    public ActionWindow(Map<Integer,List<Integer>> map,int windowWidth,int windowHeight,Window window,int index,double scaleX,double scaleY){
        this.window=window;
        this.index=index;
        this.setTitle("Actions");
        this.setLayout(new GridLayout());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setSize(windowWidth/4,windowHeight/8);
        this.setLocation((windowWidth-this.getWidth())/2,0);
//        this.setOpacity(0.5f);
        this.setVisible(true);
        BACKWARD.setFont(new Font("SansSerif", Font.PLAIN, (int)(12*scaleY)));
        PAUSE_RESUME.setFont(new Font("SansSerif", Font.PLAIN, (int)(12*scaleY)));
        FORWARD.setFont(new Font("SansSerif", Font.PLAIN, (int)(12*scaleY)));

        this.add(BACKWARD);
        this.add(PAUSE_RESUME);
        this.add(FORWARD);
        BACKWARD.setEnabled(currenIndex>0);
        FORWARD.setEnabled(currenIndex<map.size()-1);
        for (ActionListener al : BACKWARD.getActionListeners()){
            BACKWARD.removeActionListener(al);
        }
        for (ActionListener al : FORWARD.getActionListeners()){
            FORWARD.removeActionListener(al);
        }
        for (ActionListener al : PAUSE_RESUME.getActionListeners()){
            PAUSE_RESUME.removeActionListener(al);
        }

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("windowClosing");
                stopAllOperations();
                DataHandler dataHandler =new DataHandler();
                dataHandler.removeDataFromFile(index);
                window.setVisible(true);
                dispose();
            }
        });
        PAUSE_RESUME.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (PAUSE_RESUME.getText().equals("⏸")){
                    pressedPause.set(true);
                    PAUSE_RESUME.setText("►");
                }else {
                    pressedResume.set(true);
                    PAUSE_RESUME.setText("⏸");
                }
            }
        });
        FORWARD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pressedForward.set(true);
                updateButtonStates();
            }
        });

        BACKWARD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pressedBackward.set(true);
                updateButtonStates();
            }
        });
        System.out.println(map);
        operations= new RobotRunner[map.size()];
        thread =new Thread(() -> {
            for (int i = 0; i < map.size() && !stop.get();) {
                currenIndex = i;
                operations[i]=new RobotRunner(map.get(i),i);
                operations[i].start();
                while (!operations[i].isFinished()) {
                    if (pressedPause.getAndSet(false)) {
                        System.out.println("paused");
                        operations[i].pauseR();
                    }
                    if (pressedResume.getAndSet(false)) {
                        operations[i].resumeR();
                    }
                    if (pressedForward.getAndSet(false)) {
                        operations[i].stopR();
                        i++; break;
                    }
                    if (pressedBackward.getAndSet(false)) {
                        operations[i].stopR();
                        i = Math.max(0, i - 1); break;
                    }
                    try {
                        Thread.sleep(100); // Let other events run
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (operations[i]!=null && operations[i].isFinished()) {
                    i++;
                    updateButtonStates();
                }
                System.out.println("Using index: "+i);
            }
        });
        thread.start();
    }
    private void updateButtonStates() {
        BACKWARD.setEnabled(currenIndex > 0);
        FORWARD.setEnabled(currenIndex < operations.length - 1);
    }
    private void stopAllOperations() {
        stop.set(true);

        for (RobotRunner runner : operations) {
            if (runner != null && runner.isAlive()) {
                runner.stopR();
                try {
                    runner.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (thread != null && thread.isAlive()) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
