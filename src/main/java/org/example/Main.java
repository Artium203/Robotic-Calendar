package org.example;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("C:\\Users\\artem\\IdeaProjects\\Robotic-Calendar\\src\\Resources\\text.txt");
            if (file.createNewFile()) {
                Window window = new Window();
            }else {
                DataHandler handler= new DataHandler();
                List<DataContainer> dataContainers = handler.readDataFromFile();
                Window window = new Window(dataContainers);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        Window window = new Window();
         //The opening window for the operation
//        window.showWindow(); // Making the window visible
    }
}

