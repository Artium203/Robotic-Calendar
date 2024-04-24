package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.GregorianCalendar;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CalendarForProject extends JPanel {
   private static JLabel lblMonth;

   //User's map of dates input and the given time
   private static int chosenYear= 2024;
   private static int chosenMonth=5;
   private static int chosenDay= 17;
   private static Time time;
   private static JButton btnPrev, btnNext;
   private static JTable tblCalendar;
   private static DefaultTableModel mtblCalendar; //Table model
   private static JScrollPane stblCalendar; //The scrollPanel
   private static int realYear, realMonth, realDay, currentYear, currentMonth;

   public CalendarForProject(int windowWidth, int windowHeight){

      this.setLayout(new BorderLayout());
      this.setVisible(false);
      this.setPreferredSize(new Dimension(windowWidth/2,windowHeight/2));
      this.setBackground(Color.blue);

      //Look and feel
      try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
      catch (ClassNotFoundException e) {}
      catch (InstantiationException e) {}
      catch (IllegalAccessException e) {}
      catch (UnsupportedLookAndFeelException e) {}


      lblMonth = new JLabel ("January");
      btnPrev = new JButton ("Prev");
      btnNext = new JButton ("Next");
      mtblCalendar = new DefaultTableModel(){
         public boolean isCellEditable(int rowIndex, int mColIndex) {
            return false;
         }
      };
      tblCalendar = new JTable(mtblCalendar);

      stblCalendar = new JScrollPane(tblCalendar);
      this.add(stblCalendar, BorderLayout.CENTER);


      //Register action listeners
      btnPrev.addActionListener(new btnPrev_Action());
      btnNext.addActionListener(new btnNext_Action());

      this.add(lblMonth, BorderLayout.NORTH);
      this.add(btnPrev, BorderLayout.WEST);
      this.add(btnNext, BorderLayout.EAST);

      //Sets
//      this.setPreferredSize(new Dimension(windowWidth-8,windowHeight-(windowHeight/10)-11));
      lblMonth.setPreferredSize(new Dimension(50,20));
      lblMonth.setHorizontalTextPosition(JLabel.CENTER);
      btnPrev.setBounds(10, 25, 70, 25);
      btnNext.setBounds(240, 25, 70, 25);
      stblCalendar.setBounds(10, 50, 300, 250);


      //Get real month/year
      GregorianCalendar cal = new GregorianCalendar(); //Create calendar
      realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
      realMonth = cal.get(GregorianCalendar.MONTH); //Get month
      realYear = cal.get(GregorianCalendar.YEAR); //Get year
      currentMonth = realMonth; //Match month and year
      currentYear = realYear;


      //Add headers
      String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
      for (int i=0; i<7; i++){
         mtblCalendar.addColumn(headers[i]);
      }

      tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //Set background

      //No resize/reorder
      tblCalendar.getTableHeader().setResizingAllowed(false);
      tblCalendar.getTableHeader().setReorderingAllowed(false);

      //Single cell selection
      tblCalendar.setColumnSelectionAllowed(true);
      tblCalendar.setRowSelectionAllowed(true);
      tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

      //Set row/column count
      tblCalendar.setRowHeight(38);
      mtblCalendar.setColumnCount(7);
      mtblCalendar.setRowCount(6);


      //Refresh calendar
      refreshCalendar (realMonth, realYear);//Refresh calendar
   }

   public static void refreshCalendar(int month, int year){
      //Variables
      String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
      int nod, som; //Number Of Days, Start Of Month

      //Allow/disallow buttons
      btnPrev.setEnabled(true);
      btnNext.setEnabled(true);
      if (month == 0 && year <= realYear-10){btnPrev.setEnabled(false);} //Too early
      if (month == 11 && year >= realYear+100){btnNext.setEnabled(false);} //Too late
      lblMonth.setText(months[month]); //Refresh the month label (at the top)
      lblMonth.setHorizontalAlignment(JLabel.CENTER);

      //Clear table
      for (int i=0; i<6; i++){
         for (int j=0; j<7; j++){
            mtblCalendar.setValueAt(null, i, j);
         }
      }

      //Get first day of month and number of days
      GregorianCalendar cal = new GregorianCalendar(year, month, 1);
      nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
      som = cal.get(GregorianCalendar.DAY_OF_WEEK);


      //Draw calendar
      for (int i=1; i<=nod; i++){
         int row = ((i+som-2)/7);
         int column  =  (i+som-2)%7;
         if (i == chosenDay && lblMonth.getText().contains(months[chosenMonth-1]) && chosenYear==year){
            mtblCalendar.setValueAt(i, row, column);
         }else {
            mtblCalendar.setValueAt(i, row, column);
         }
      }

      //Apply renderers
      tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
   }

    private static class tblCalendarRenderer extends DefaultTableCellRenderer {
      public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
         super.getTableCellRendererComponent(table, value, selected, focused, row, column);
         if (column == 0 || column == 6){ //Weekend
            setBackground(new Color(255, 220, 220));
         }
         else{ //Week
            setBackground(new Color(23, 129, 91, 182));
         }
         if (value != null && !value.toString().matches(".*[a-zA-Z]+.*")){
            if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
               setBackground(new Color(220, 220, 255));
            }
         }
         setBorder(null);
         setForeground(Color.black);
         return this;
      }
   }

   private static class btnPrev_Action implements ActionListener {
      public void actionPerformed (ActionEvent e){
         if (currentMonth == 0){ //Back one year
            currentMonth = 11;
            currentYear -= 1;
         }
         else{ //Back one month
            currentMonth -= 1;
         }
         refreshCalendar(currentMonth, currentYear);
      }
   }
   private static class btnNext_Action implements ActionListener{
      public void actionPerformed (ActionEvent e){
         if (currentMonth == 11){ //Forward one year
            currentMonth = 0;
            currentYear += 1;
         }
         else{ //Forward one month
            currentMonth += 1;
         }refreshCalendar(currentMonth, currentYear);
      }
   }
}