package org.example;

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
    private final JButton FORWARD=new JButton(new ImageIcon("src/Resources/forward.png"));
    private final JButton BACKWARD= new JButton(new ImageIcon("src/Resources/backwards.png"));
    private final JButton PAUSE_RESUME= new JButton(new ImageIcon("src/Resources/pause.png"));
    private int index;
    private int currenIndex=0;
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
        this.setVisible(true);
        JPanel panelButton = new JPanel();
        panelButton.add(BACKWARD);
        panelButton.add(PAUSE_RESUME);
        panelButton.add(FORWARD);
        this.add(panelButton);
        BACKWARD.setEnabled(currenIndex>0);
        FORWARD.setEnabled(currenIndex<map.size()-1);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stopAllOperations();
                window.setVisible(true);  // Make the previous window visible again
                dispose();  // Close the current window
            }
        });
        operations= new RobotRunner[map.size()];
        for (int i = 0; i < map.size(); i++) {
            operations[i]=new RobotRunner(map.get(i));
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
                    currenIndex--;
                    updateButtons();
                    runCurrentOperation();
                }
            }
        });

        PAUSE_RESUME.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (operations[currenIndex].isPaused()) {
                    operations[currenIndex].resume();
                    PAUSE_RESUME.setText("Pause");
                } else {
                    operations[currenIndex].pause();
                    PAUSE_RESUME.setText("Resume");
                }
            }
        });
        FORWARD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currenIndex < operations.length - 1) {
                    currenIndex++;
                    updateButtons();
                    runCurrentOperation();
                }
            }
        });
//        window.setVisible(true);
//        DataHandler dataHandler =new DataHandler();
//        dataHandler.removeDataFromFile(index);
//        dispose();
    }
    private void runCurrentOperation() {
        new Thread(() -> {
            operations[currenIndex].execute(); // Run current task
            currenIndex++; // Move to the next task

            // Check if all tasks have finished
            if (currenIndex < operations.length) {
                // Run the next task if there are remaining
                runCurrentOperation();
            } else {
                // If all tasks are finished, close ActionWindow and show the first frame
                SwingUtilities.invokeLater(() -> {
                    window.setVisible(true); // Make first frame visible
                    dispose(); // Close this window
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
            operation.stop(); // Stop each operation
        }
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
