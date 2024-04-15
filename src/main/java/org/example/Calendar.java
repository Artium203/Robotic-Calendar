package org.example;

import javax.swing.*;
import java.awt.*;

public class Calendar extends JPanel {

   public Calendar(int windowWidth,int windowHeight){
      this.setLayout(new FlowLayout(FlowLayout.LEFT));
      this.setVisible(false);
      this.setPreferredSize(new Dimension(windowWidth-8,windowHeight-(windowHeight/10)-11));
      this.setBackground(Color.blue);


   }
}
