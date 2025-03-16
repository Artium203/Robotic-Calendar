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
    private boolean pressedForward = false;
    private boolean pressedBackward = false;
    private volatile boolean isRunning = false;

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
        new Thread(()->{
            runCurrentOperation();
//            afterActionsComplete();
        }).start();
        BACKWARD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currenIndex > 0) {
                    // Stop the current task and move backward
                    operations[currenIndex].stop();
                    currenIndex--;
                    stopCurrentTaskAndRunNext();
                    pressedBackward = true;
                    updateButtons();
                }
            }
        });

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
                    // Stop the current task and move forward
                    operations[currenIndex].stop();
                    currenIndex++;
                    stopCurrentTaskAndRunNext();
                    pressedForward = true;
                    updateButtons();
                }
            }
        });
//        window.setVisible(true);
//        DataHandler dataHandler =new DataHandler();
//        dataHandler.removeDataFromFile(index);
//        dispose();
    }
    private synchronized void runCurrentOperation() {
        new Thread(() -> {
            if (currenIndex < operations.length) {
                // Ensure that the task is executed if it's not stopped
                RobotRunner currentOperation = operations[currenIndex];
                if (currentOperation != null && currentOperation.isRunning()) {
                    // Execute the task
                    currentOperation.execute();
                }
            }

            // After the task is completed, update the index if no buttons were pressed
            if (!pressedForward && !pressedBackward) {
                currenIndex++;
            }

            pressedBackward = false;
            pressedForward = false;
            updateButtons();

            // Check if there are more tasks left to run, if yes, run the next one
            if (currenIndex < operations.length) {
                runCurrentOperation();  // Recursively call to run the next task
            } else {
                // If all tasks are done, clean up and exit
                DataHandler dataHandler = new DataHandler();
                dataHandler.removeDataFromFile(index);
                stopAllOperations();

                SwingUtilities.invokeLater(() -> {
                    window.setVisible(true);
                    dispose();
                });
            }
        }).start();
    }
    private void updateButtons() {
        BACKWARD.setEnabled(currenIndex > 0);
        FORWARD.setEnabled(currenIndex < operations.length - 1);
    }
    private void stopAllOperations() {
        for (RobotRunner operation : operations) {
            if (operation != null) {
                operation.stop();
            }// Stop each operation
        }
    }
    private void stopCurrentTaskAndRunNext() {
        // Stop the current task when a button is pressed (forward/backward)
        if (operations[currenIndex] != null) {
            operations[currenIndex].stop();
        }

        // Start the new task based on the button pressed
        runCurrentOperation();
    }

//    private void afterActionsComplete() {
//        SwingUtilities.invokeLater(() -> {
////            DataHandler dataHandler =new DataHandler();
////            dataHandler.removeDataFromFile(index);
//            window.setVisible(true); // Make the first frame visible again
//            dispose(); // Close the ActionWindow
//        });
//    }

}
