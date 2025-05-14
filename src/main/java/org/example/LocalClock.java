package org.example;

import org.example.VoiceHandler.Voices;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class LocalClock extends JPanel {

    protected static final DateFormat CLOCK_FORMAT = new SimpleDateFormat("hh:mm:ss a");
    private JLabel clock;
    private JButton settingVoice =new JButton("Setting Voice");
    private int count=0;
    private Voices voices;

    public LocalClock(double scaleX, double scaleY) {
        setLayout(new GridLayout(2,0));
        settingVoice.setFont(new Font("SansSerif", Font.PLAIN, (int)(12*scaleY)));
        settingVoice.addActionListener(e ->  {
            if(count==0) {
                voices = new Voices(scaleX,scaleY);
                count=1;
            } else if (count==1 && voices.isShowing()) {
                voices.dispose();
                count=0;
            }
        });
        clock = new JLabel("...");
        clock.setFont(clock.getFont().deriveFont(Font.BOLD, (int)(24f*scaleY)));
        add(clock);
        add(settingVoice);
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