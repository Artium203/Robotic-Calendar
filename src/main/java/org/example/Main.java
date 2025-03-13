package org.example;

import org.example.Data.DataContainer;
import org.example.Data.DataHandler;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main{
    public static void main(String[] args) {
        try {
            File file = new File("C:\\Users\\Public\\Documents\\test.txt");
            DataHandler handler= new DataHandler();
            List<DataContainer> dataContainers = handler.readDataFromFile();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image curserImage = toolkit.getImage("src/Resources/pixel-old-united.png");
            Cursor cursor = toolkit.createCustomCursor(curserImage,new Point(0,0),"Pixel Mouse");
            if (file.createNewFile()) {
                Window window = new Window();
                window.setCursor(cursor);
            }
            else if (dataContainers==null) {
                Window window = new Window();
                window.setCursor(cursor);
            } else if (dataContainers.isEmpty()){
                Window window = new Window();
                window.setCursor(cursor);
            }else {
                Window window = new Window(dataContainers);
                window.setCursor(cursor);
            }
//            file.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        Window window = new Window();
         //The opening window for the operation
//        window.showWindow(); // Making the window visible
    }
}

