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

public class ActionWindow extends JFrame {
    private RobotRunner[] operations;
    private Window window;
    private final JButton FORWARD=new JButton("⏭");
    private final JButton BACKWARD= new JButton("⏮");
    //►
    private final JButton PAUSE_RESUME= new JButton("⏸");
    private int index;
    private int currenIndex=0;
    private Thread currentThread = null;  // Track the currently running thread


    public ActionWindow(Map<Integer,List<Integer>> map,int windowWidth,int windowHeight,Window window,int index){
        this.window=window;
        this.index=index;
        this.setTitle("Actions");
        this.setLayout(new GridLayout());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setSize(windowWidth/4,windowHeight/8);
        this.setLocation((windowWidth-this.getWidth())/2,0);
//        this.setOpacity(0.5f);
        this.setVisible(true);
        this.add(BACKWARD);
        this.add(PAUSE_RESUME);
        this.add(FORWARD);
        BACKWARD.setEnabled(currenIndex>0);
        FORWARD.setEnabled(currenIndex<map.size()-1);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stopAllOperations();
                DataHandler dataHandler =new DataHandler();
                dataHandler.removeDataFromFile(index);
                window.setVisible(true);  // Make the previous window visible again
                dispose();  // Close the current window
            }
        });
        System.out.println(map);
        operations= new RobotRunner[map.size()];
        for (int i = 0; i < map.size(); i++) {
            operations[i]=new RobotRunner(map.get(i),i);
            System.out.println(i);
        }
        runCurrentTask();

        PAUSE_RESUME.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (operations[currenIndex].isPaused()) {
                    operations[currenIndex].resume();
                    PAUSE_RESUME.setText("⏸");
                } else {
                    operations[currenIndex].pause();
                    PAUSE_RESUME.setText("►");
                }
            }
        });
        FORWARD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currenIndex < operations.length - 1) {
                    operations[currenIndex].stop();
                    currenIndex++;
                    if (!operations[currenIndex].isRunning()) {
                        operations[currenIndex].runAgain();
                    }
                    runCurrentTask();
                    updateButtonStates();
                }
            }
        });

        BACKWARD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currenIndex > 0) {
                    operations[currenIndex].stop();
                    currenIndex--;
                    if (!operations[currenIndex].isRunning()) {
                        operations[currenIndex].runAgain();
                    }
                    runCurrentTask();
                    updateButtonStates();
                }
            }
        });
    }
    private void runCurrentTask() {
//        // Stop the current running task if it exists
//        if (currentThread != null && currentThread.isAlive()) {
//            operations[currenIndex].stop();  // Stop the current task
//        }

        // Start the new task in a new thread
        currentThread = new Thread(new Runnable() {
            @Override
            public void run() {
                operations[currenIndex].execute();  // Run the current task
            }
        });
        currentThread.start();
    }
    private void updateButtonStates() {
        BACKWARD.setEnabled(currenIndex > 0);
        FORWARD.setEnabled(currenIndex < operations.length - 1);
    }
    private void stopAllOperations() {
        for (RobotRunner operation : operations) {
            if (operation != null) {
                operation.stop();
            }// Stop each operation

        }
        if (currentThread != null && currentThread.isAlive()) {
            currentThread.interrupt();  // Interrupt the current task if it's running
        }

    }


}
