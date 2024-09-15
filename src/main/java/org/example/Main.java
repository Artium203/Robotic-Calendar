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
            File file = new File("C:\\Users\\Public\\Documents\\test.txt");
            DataHandler handler= new DataHandler();
            List<DataContainer> dataContainers = handler.readDataFromFile();
            if (file.createNewFile()) {
                Window window = new Window();
            } else if (dataContainers==null) {
                Window window = new Window();
            } else {
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

