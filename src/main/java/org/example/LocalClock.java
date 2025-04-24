package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class LocalClock extends JPanel {

    protected static final DateFormat CLOCK_FORMAT = new SimpleDateFormat("hh:mm:ss a");
    private JLabel clock;

    public LocalClock(double scaleX, double scaleY) {
        setLayout(new GridBagLayout());
        clock = new JLabel("...");
        clock.setFont(clock.getFont().deriveFont(Font.BOLD, (int)(24f*scaleY)));
        add(clock);
        updateClock();

        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateClock();
            }
        });
        timer.start();
    }
    protected void updateClock() {
        clock.setText(CLOCK_FORMAT.format(System.currentTimeMillis()));
    }

}