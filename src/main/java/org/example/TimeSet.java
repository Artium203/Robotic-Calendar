package org.example;

import org.example.VoiceHandler.VoiceManager;
import org.example.cosmetics.ImageBorder;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.List;

public class TimeSet extends JPanel {

    private List<String> actionToList;// Will be used to put on a list
//    private static List<String> plans;

    private static List<JCheckBox> actionList;

    //The chosen date from user
    private static int chosenDay;
    private static int chosenMonth;
    private static int chosenYear;

    private static int nod; // Used for calculations instructions

    //Options of choosing the date
    private final JComboBox<Integer> yearBox;
    private final JComboBox<Integer> monthBox; // number of months in a year
    private final JComboBox<Integer> dayBox; // number of days in a month
    private final JButton addToList;// Button that confirmed user's chose to add to a list
    private final JButton removeFromList; // Button that removes user's chose from the list
    //Action option
    private final JRadioButton dragOption;
    private final JRadioButton pressOption;
    private ButtonGroup buttonGroup;// Only for picking one of the 2 buttons

    private final JLabel textForInstruction; //Simple explanation
    private final JLabel textForDate;//Simple explanation
    private final JPanel dateBox;//Gets the date from user
    private final JPanel performanceBox;//Gets the action from user
    private final JPanel timeBox;//Gets the time from user
    private final JPanel addOrRemoveBox;//Gets to put/remove to/from a list
    private JLayeredPane layeredPer = new JLayeredPane();
    private JLayeredPane layeredDate = new JLayeredPane();
    private JLayeredPane layeredTime = new JLayeredPane();
    private JLayeredPane layeredAorR = new JLayeredPane();
    private JLayeredPane layeredList = new JLayeredPane();


    private final JPanel listBox;//List of actions (max 10 actions to put)
    private final JPanel listBoxInner;
    private final JPanel boxOfBoxes;//Contains the panels together


    //The starting and ending of the action
    private JSpinner hoursStart;
    private JSpinner minutesStart;
    private JSpinner secondStart;
    private JSpinner hoursEnd;
    private JSpinner minutesEnd;
    private JSpinner secondEnd;

    //Title to understand which is who
    private final JLabel hoursStartLabel;
    private final JLabel minutesStartLabel;
    private final JLabel secondsStartLabel;
    private final JLabel hoursEndLabel;
    private final JLabel minutesEndLabel;
    private final JLabel secondsEndLabel;
    private final JLabel StartText;
    private final JLabel endText;
    private final JLabel spaceText;

    private JTextField nameAction;
    private final Image backGImageDate;
    private final Image backGImageList;
    private final Image backGImageName;

    private double givenScaleX, givenScaleY;

    private final ImageIcon backButton= new ImageIcon("src/Resources/cosmetics/wooden-buttons.png");
    private final ImageIcon backIChecker = new ImageIcon("src/Resources/cosmetics/checker_text.png");
    ImageIcon icon = new ImageIcon("src/Resources/cosmetics/info.png");
    private JButton infoButtonPer = new JButton();
    private JButton infoButtonDate = new JButton();
    private JButton infoButtonTime = new JButton();
    private JButton infoButtonAorR = new JButton();
    private JButton infoButtonList = new JButton();
    {
        try {
            backGImageList = ImageIO.read(new File("src/Resources/cosmetics/hd_restoration_result_image - Copy.png"));
            backGImageDate = ImageIO.read(new File("src/Resources/cosmetics/ezgif.com-crop (2)r.png"));
            backGImageName = ImageIO.read(new File("src/Resources/cosmetics/ezgif.com-crop (1)sd3.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    public TimeSet(int windowWidth,int windowHeight, JButton confirm,double scaleX,double scaleY) {
        //Panel layout and dimensions
        this.setLayout(new GridLayout());
        this.setPreferredSize(new Dimension(windowWidth - 8, windowHeight - (windowHeight / 10) - 11));
        this.setVisible(false);
        givenScaleX = scaleX;
        givenScaleY = scaleY;
        Image image = icon.getImage().getScaledInstance((int) (icon.getIconWidth()*scaleX), (int) (icon.getIconHeight()*scaleY), Image.SCALE_SMOOTH);
        setInfoButton(infoButtonPer, image);
        for (ActionListener al : infoButtonPer.getActionListeners()) {
            infoButtonPer.removeActionListener(al);
        }
        infoButtonPer.addActionListener(e -> {
            VoiceManager voiceManager=new VoiceManager();
            try {
                voiceManager.playSound(68000*1000,75000);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        infoButtonPer.setBounds((int) (305*givenScaleX),0,(int) (30*givenScaleX), (int) (30*givenScaleY));
        setInfoButton(infoButtonDate, image);
        for (ActionListener al : infoButtonDate.getActionListeners()) {
            infoButtonDate.removeActionListener(al);
        }
        infoButtonDate.addActionListener(e -> {
            VoiceManager voiceManager=new VoiceManager();
            try {
                voiceManager.playSound(41000*1000,51000);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        infoButtonDate.setBounds((int) (305*givenScaleX),0,(int) (30*givenScaleX), (int) (30*givenScaleY));
        setInfoButton(infoButtonTime, image);
        for (ActionListener al : infoButtonTime.getActionListeners()) {
            infoButtonTime.removeActionListener(al);
        }
        infoButtonTime.addActionListener(e -> {
            VoiceManager voiceManager=new VoiceManager();
            try {
                voiceManager.playSound(51000*1000,68000);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        infoButtonTime.setBounds((int) (305*givenScaleX),0,(int) (30*givenScaleX), (int) (30*givenScaleY));
        setInfoButton(infoButtonAorR, image);
        for (ActionListener al : infoButtonAorR.getActionListeners()) {
            infoButtonAorR.removeActionListener(al);
        }
        infoButtonAorR.addActionListener(e -> {
            VoiceManager voiceManager=new VoiceManager();
            try {
                voiceManager.playSound(75000*1000,89000);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        infoButtonAorR.setBounds((int) (305*givenScaleX),0,(int) (30*givenScaleX), (int) (30*givenScaleY));
        setInfoButton(infoButtonList, image);
        for (ActionListener al : infoButtonList.getActionListeners()) {
            infoButtonList.removeActionListener(al);
        }
        infoButtonList.addActionListener(e -> {
            VoiceManager voiceManager=new VoiceManager();
            try {
                voiceManager.playSound(89000*1000,106000);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        infoButtonList.setBounds((int) (315*givenScaleX), (int) (45*scaleY),(int) (50*givenScaleX), (int) (50*givenScaleY));
        confirm.setFont(new Font("Colonna MT", Font.BOLD, (int)(33*scaleY)));
        confirm.setVerticalAlignment(SwingConstants.NORTH);
        confirm.setOpaque(false);
        confirm.setBorderPainted(false);
        confirm.setContentAreaFilled(false);
        confirm.setFocusPainted(false);
        confirm.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                confirm.setFont(new Font("Colonna MT", Font.BOLD, (int)(22*scaleY)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                confirm.setFont(new Font("Colonna MT", Font.BOLD, (int)(33*scaleY)));
            }
        });
        confirm.setSelectedIcon(null);

        //Container's sets
        boxOfBoxes = new JPanel();
        boxOfBoxes.setLayout(new GridLayout(2, 2));
        boxOfBoxes.setPreferredSize(new Dimension(windowWidth / 2 + (windowWidth - ((windowWidth / 2) + (windowWidth / 3) + 150)), windowHeight - (windowHeight / 10) - 20));
//        boxOfBoxes.setBackground(Color.cyan);


        //Sub panel section
        //Date box sets
        dateBox = new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backGImageDate, -30, (int)(-13*scaleY), (int) (getWidth()+66*scaleX), (int)(getHeight()+35*scaleY), this);

            }
        };
        dateBox.setLayout(new FlowLayout(FlowLayout.CENTER));
        dateBox.setBounds((int) (-10*scaleX), (int) (-7*scaleY),(int)(350*scaleX), (int) (350*scaleY));


        //Action box's sets
        layeredPer.setPreferredSize(new Dimension((int)(400*scaleX), (windowHeight - (windowHeight / 10) - 20) / 2));
        performanceBox = new JPanel(){
            protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backGImageDate, -35, (int)(-23*scaleY), (int) (getWidth()+76*scaleX), (int)(getHeight()+45*scaleY), this);
            }
        };
        performanceBox.setLayout(new FlowLayout(FlowLayout.CENTER,20,25));
        performanceBox.setBounds((int) (-10*scaleX), (int) (-10*scaleY),(int)(350*scaleX), (int) (500*scaleY));



        //Time box's sets
        timeBox = new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backGImageDate, (int) (-30*scaleX), (int)(-13*scaleY), (int) (getWidth()+66*scaleX), (int)(getHeight()+35*scaleY), this);
            }
        };
        timeBox.setLayout(new FlowLayout(FlowLayout.CENTER, (int) (5*scaleX),30));
        timeBox.setBounds(0,(int) (-7*scaleY),(int)(340*scaleX), (int) (350*scaleY));


        //Sets of add/remove to/from a list box
        addOrRemoveBox = new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backGImageDate, -30, (int)(-23*scaleY) , getWidth()+66, (int)(getHeight()+35*scaleY), this);
            }
        };
        addOrRemoveBox.setLayout(new FlowLayout(FlowLayout.CENTER,5,45));
        addOrRemoveBox.setBounds(0,0, (int) (350*scaleX), (int) (350*scaleY));


        //list box's sets
        int betterSize = (windowWidth - windowWidth / 2 + (windowWidth - ((windowWidth / 2) + 445)));

        listBox = new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backGImageList, (int)(-170*scaleX), (int)(-95*scaleY), (getWidth()+windowWidth / 4), (int)(getHeight()+(windowHeight / 10)+60*scaleY), this);
            }
        };
        listBox.setLayout(new GridBagLayout());
        listBox.setBounds((int) (50*scaleX),0,(int) (580*scaleX), (int) ((700)*scaleY));
//        listBox.setBackground(Color.GREEN);
        listBoxInner = new JPanel(new FlowLayout(FlowLayout.CENTER));
        listBoxInner.setPreferredSize(new Dimension((int) (500*scaleX), (int) ((450)*scaleY)));
        listBoxInner.setOpaque(false);



        // Date selection components
        textForDate = new JLabel("Choose a date for instruction:");
        textForDate.setPreferredSize(new Dimension((int)(450*scaleX), (int)(80*scaleY)));
        textForDate.setFont(new Font("Arial", Font.BOLD, (int)(20*scaleY)));
        textForDate.setHorizontalAlignment(SwingConstants.CENTER);
//        add(textForDate);

        yearBox = new JComboBox<>(new Integer[]{0, 2024, 2025, 2026, 2027, 2028, 2029, 2030, 2031, 2032, 2033, 2034, 2035, 2036, 2037, 2038, 2039, 2040});
        yearBox.setPreferredSize(new Dimension((int)(80*scaleX), (int)(50*scaleY)));
        yearBox.setFont(new Font("Arial", Font.BOLD, (int)(20*scaleY)));
        yearBox.setBorder(new ImageBorder("src/Resources/cosmetics/Iron_frame.png",true,new Insets((int)(3*scaleY), (int)(5*scaleX), (int)(3*scaleY), (int)(5*scaleX)),(int)(3*scaleX),0));


        monthBox = new JComboBox<>(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
        monthBox.setPreferredSize(new Dimension((int)(80*scaleX), (int)(50*scaleY)));
        monthBox.setFont(new Font("Arial", Font.BOLD, (int)(20*scaleY)));
        monthBox.setBorder(new ImageBorder("src/Resources/cosmetics/Iron_frame.png",true,new Insets((int)(3*scaleY), (int)(5*scaleX), (int)(3*scaleY), (int)(5*scaleX)),(int)(3*scaleX),0));
        monthBox.setVisible(false);

        dayBox = new JComboBox<>();
        dayBox.setPreferredSize(new Dimension((int)(80*scaleX), (int)(50*scaleY)));
        dayBox.setFont(new Font("Arial", Font.BOLD, (int)(20*scaleY)));
        dayBox.setBorder(new ImageBorder("src/Resources/cosmetics/Iron_frame.png",true,new Insets((int)(3*scaleY), (int)(5*scaleX), (int)(3*scaleY), (int)(5*scaleX)),(int)(3*scaleX),0));
        dayBox.setVisible(false);


        //Actions for the choices of the date
        for (ActionListener al : yearBox.getActionListeners()) {
            yearBox.removeActionListener(al);
        }
            yearBox.addActionListener(e -> yearSelection());
        for (ActionListener al : monthBox.getActionListeners()) {
            monthBox.removeActionListener(al);
        }
            monthBox.addActionListener(e -> monthSelection());
        for (ActionListener al : dayBox.getActionListeners()) {
            dayBox.removeActionListener(al);
        }
            dayBox.addActionListener(e -> daySelection());



        //Sets of action (on a mouse)
        textForInstruction = createTextLabel("Choose one of the following actions: ",  new Font("Arial", Font.BOLD, (int)(16*scaleY)));
        dragOption = createTextRadioButton("Scroll", (int)(110*scaleX), (int)(60*scaleY), new Font("Arial", Font.BOLD, (int)(17*scaleY)));
        dragOption.setIcon(new ImageIcon("src/Resources/cosmetics/ezgif_radio.png"));
        dragOption.setSelectedIcon(new ImageIcon("src/Resources/cosmetics/ezgif_radioON.png"));
        dragOption.setHorizontalTextPosition(SwingConstants.CENTER);
        dragOption.setOpaque(false);
        pressOption = createTextRadioButton("Press", (int)(110*scaleX), (int)(60*scaleY), new Font("Arial", Font.BOLD, (int)(17*scaleY)));
        pressOption.setIcon(new ImageIcon("src/Resources/cosmetics/ezgif_radio.png"));
        pressOption.setSelectedIcon(new ImageIcon("src/Resources/cosmetics/ezgif_radioON.png"));
        pressOption.setHorizontalTextPosition(SwingConstants.CENTER);
        pressOption.setOpaque(false);
        nameAction = new JTextField(){
            protected void paintComponent(Graphics g) {
                g.drawImage(backGImageName, (int)(-12*scaleX), (int)(-5*scaleY), getWidth()+30, (int)(getHeight()+8*scaleY), this);
                super.paintComponent(g);
            }
        };
        TitledBorder titledBorder = BorderFactory.createTitledBorder("        Name your action");
        titledBorder.setTitlePosition(TitledBorder.BELOW_TOP);
        titledBorder.setTitleFont(new Font("Arial" , Font.BOLD , (int)(13*scaleY)));
        nameAction.setPreferredSize(new Dimension(windowWidth/5 , windowHeight/12));
        nameAction.setOpaque(false);
        nameAction.setForeground(Color.WHITE);
        nameAction.setHorizontalAlignment(JTextField.CENTER);
        nameAction.setFont(new Font("Arial" , Font.BOLD , (int)(18*scaleY)));
        nameAction.setBorder(BorderFactory.createCompoundBorder(titledBorder,null));
        buttonGroup = new ButtonGroup();  // על מנת שתהיה למשתמש רק אופציה אחת לבחירה, נשתמש בדבר הבא
        textForInstruction.setBounds(0,(int)(400*scaleY),(int)(250*scaleX),(int)(70*scaleY));
        buttonGroup.add(dragOption);
        buttonGroup.add(pressOption);

        actionList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            actionList.add(new JCheckBox(){
                protected void paintComponent(Graphics g) {
                    if (backIChecker != null) {
                        g.drawImage(backIChecker.getImage(), 0, 0, getWidth(), getHeight(), this);
                    }
                    super.paintComponent(g);
                }
            });
            actionList.get(i).setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-15));
            actionList.get(i).setIcon(new ImageIcon("src/Resources/cosmetics/checker_empty.png"));
            actionList.get(i).setSelectedIcon(new ImageIcon("src/Resources/cosmetics/checker.png"));
            actionList.get(i).setFont(new Font("SansSerif", Font.BOLD, (int)(12*scaleY)));
            actionList.get(i).setOpaque(false);
            listBoxInner.add(actionList.get(i));
        }
        System.out.println((windowWidth / 4));

        actionToList = new ArrayList<>();
        addToList = new JButton("ADD");
        removeFromList = new JButton("REMOVE");
        addToList.setIcon(new ImageIcon(backButton.getImage().getScaledInstance((windowWidth/6)-4, (windowHeight/10)-3, Image.SCALE_SMOOTH)));
        addToList.setHorizontalAlignment(SwingConstants.CENTER);
        addToList.setVerticalAlignment(SwingConstants.CENTER);
        addToList.setHorizontalTextPosition(SwingConstants.CENTER);
        addToList.setVerticalTextPosition(SwingConstants.CENTER);
        addToList.setFont(new Font("SansSerif" , Font.BOLD , (int)(12*scaleY)));
        addToList.setOpaque(false);
        addToList.setBorderPainted(false);
        addToList.setContentAreaFilled(false);
        addToList.setFocusPainted(false);
        addToList.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        for (ActionListener al : addToList.getActionListeners()) {
            addToList.removeActionListener(al);
        }
            addToList.addActionListener(e -> {
                if (actionToList.size() == 9) {
                    addToList.setEnabled(false);
                }
                if (dragOption.isSelected()) {
                    actionToList.add(Arrays.toString(dragOption.getSelectedObjects()));
                    for (int i = 0; i < 10; i++) {
                        if (actionList.get(i).getText().isEmpty()) {
                            actionList.get(i).setText(Arrays.toString(dragOption.getSelectedObjects()));
                            break;
                        }
                    }
                } else if (pressOption.isSelected()) {
                    actionToList.add(Arrays.toString(pressOption.getSelectedObjects()));
                    for (int i = 0; i < 10; i++) {
                        if (actionList.get(i).getText().isEmpty()) {
                            actionList.get(i).setText(Arrays.toString(pressOption.getSelectedObjects()));
                            break;
                        }
                    }
                }
            });
        removeFromList.setIcon(new ImageIcon(backButton.getImage().getScaledInstance((windowWidth/6)-4, (windowHeight/10)-3, Image.SCALE_SMOOTH)));
        removeFromList.setHorizontalAlignment(SwingConstants.CENTER);
        removeFromList.setVerticalAlignment(SwingConstants.CENTER);
        removeFromList.setHorizontalTextPosition(SwingConstants.CENTER);
        removeFromList.setVerticalTextPosition(SwingConstants.CENTER);
        removeFromList.setFont(new Font("SansSerif" , Font.BOLD , (int)(12*scaleY)));
        removeFromList.setOpaque(false);
        removeFromList.setBorderPainted(false);
        removeFromList.setFocusPainted(false);
        removeFromList.setContentAreaFilled(false);
        removeFromList.setPreferredSize(new Dimension((windowWidth/6)-4,(windowHeight/10)-3));
        for (ActionListener al : removeFromList.getActionListeners()) {
            removeFromList.removeActionListener(al);
        }
            removeFromList.addActionListener(e -> {
                if (actionToList.isEmpty()) {
                    addToList.setEnabled(true);
                } else {
                    removeFromList.setEnabled(true);
                }
                if (!actionToList.isEmpty()) {
                    for (JCheckBox box : actionList) {
                        if (box.isSelected()) {
                            actionToList.remove(box.getText());
                            box.setText("");
                        }
                    }
                    addToList.setEnabled(true);
                }
            });
        listBoxInner.add(confirm);
        listBox.add(listBoxInner);

        //Adding/combining things together
        boxOfBoxes.add(layeredDate);
        layeredDate.add(infoButtonDate,JLayeredPane.PALETTE_LAYER);
        layeredDate.add(dateBox,JLayeredPane.DEFAULT_LAYER);
        dateBox.add(textForDate);

        dateBox.add(yearBox);
        dateBox.add(monthBox);
        dateBox.add(dayBox);



        //Sets of starting/ending time

        hoursStart = createSpinner( 23);
        hoursStart.setBorder(new ImageBorder("src/Resources/cosmetics/Iron_frame.png",true,new Insets((int)(3*scaleY), (int)(5*scaleX), (int)(3*scaleY), (int)(5*scaleX)),(int)(3*scaleX),0));
        hoursEnd = createSpinner(  23);
        hoursEnd.setBorder(new ImageBorder("src/Resources/cosmetics/Iron_frame.png",true,new Insets((int)(3*scaleY), (int)(5*scaleX), (int)(3*scaleY), (int)(5*scaleX)),(int)(3*scaleX),0));
        minutesStart = createSpinner(59);
        minutesStart.setBorder(new ImageBorder("src/Resources/cosmetics/Iron_frame.png",true,new Insets((int)(3*scaleY), (int)(5*scaleX), (int)(3*scaleY), (int)(5*scaleX)),(int)(3*scaleX),0));
        minutesEnd = createSpinner(59);
        minutesEnd.setBorder(new ImageBorder("src/Resources/cosmetics/Iron_frame.png",true,new Insets((int)(3*scaleY), (int)(5*scaleX), (int)(3*scaleY), (int)(5*scaleX)),(int)(3*scaleX),0));
        secondStart = createSpinner(59);
        secondStart.setBorder(new ImageBorder("src/Resources/cosmetics/Iron_frame.png",true,new Insets((int)(3*scaleY), (int)(5*scaleX), (int)(3*scaleY), (int)(5*scaleX)),(int)(3*scaleX),0));
        secondEnd = createSpinner(59);
        secondEnd.setBorder(new ImageBorder("src/Resources/cosmetics/Iron_frame.png",true,new Insets((int)(3*scaleY), (int)(5*scaleX), (int)(3*scaleY), (int)(5*scaleX)),(int)(3*scaleX),0));

        StartText = createLabel("Choose time the robot will start  the instruction:");
        hoursStartLabel = createLabel("Hour:");
        minutesStartLabel = createLabel("Minute:");
        secondsStartLabel = createLabel("Second:");
        spaceText = createLabel("                                                  ");
        endText = createLabel("Choose time the robot will finish the instruction:");
        hoursEndLabel = createLabel("Hour:");
        minutesEndLabel = createLabel("Minute:");
        secondsEndLabel = createLabel("Second:");

        timeBox.add(StartText);
        timeBox.add(hoursStartLabel);
        timeBox.add(hoursStart);
        timeBox.add(minutesStartLabel);
        timeBox.add(minutesStart);
        timeBox.add(secondsStartLabel);
        timeBox.add(secondStart);
        timeBox.add(spaceText);
        timeBox.add(endText);
        timeBox.add(hoursEndLabel);
        timeBox.add(hoursEnd);
        timeBox.add(minutesEndLabel);
        timeBox.add(minutesEnd);
        timeBox.add(secondsEndLabel);
        timeBox.add(secondEnd);
        layeredTime.add(infoButtonTime,JLayeredPane.PALETTE_LAYER);
        layeredTime.add(timeBox,JLayeredPane.DEFAULT_LAYER);
        boxOfBoxes.add(layeredTime);


        boxOfBoxes.add(layeredPer);
        layeredPer.add(infoButtonPer,JLayeredPane.PALETTE_LAYER);
        layeredPer.add(performanceBox, JLayeredPane.DEFAULT_LAYER);
        performanceBox.add(textForInstruction);
        performanceBox.add(dragOption);
        performanceBox.add(pressOption);
        performanceBox.add(nameAction);

        boxOfBoxes.add(layeredAorR);
        layeredAorR.add(infoButtonAorR,JLayeredPane.PALETTE_LAYER);
        layeredAorR.add(addOrRemoveBox,JLayeredPane.DEFAULT_LAYER);
        addOrRemoveBox.add(addToList);
        addOrRemoveBox.add(removeFromList);
        this.add(boxOfBoxes);
        layeredList.add(infoButtonList,JLayeredPane.PALETTE_LAYER);
        layeredList.add(listBox,JLayeredPane.DEFAULT_LAYER);
        this.add(layeredList);
    }

    private void yearSelection() {
        if(!Objects.equals(yearBox.getSelectedItem(), 0)) {
            monthBox.setVisible(true);
            chosenYear = (int) yearBox.getSelectedItem();
        }
    }
    private void monthSelection() {
        if (!Objects.equals(monthBox.getSelectedItem(), 0) && !Objects.equals(yearBox.getSelectedItem(), 0)) {
            chosenMonth = (int) monthBox.getSelectedItem() - 1;
            GregorianCalendar cal2 = new GregorianCalendar(chosenYear, chosenMonth,1);

            nod = cal2.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            updateDayBox();
            dayBox.setVisible(true);
        }
        if (dayBox.getItemCount() != 0) {
            dayBox.removeAllItems();
            GregorianCalendar cal2 = new GregorianCalendar(chosenYear, chosenMonth,1);
            nod = cal2.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            updateDayBox();
        }
    }
    private void daySelection() {
        if (dayBox.getItemCount() != 0) {
            chosenDay = (int) dayBox.getSelectedItem();
        }
    }

    private JLabel createTextLabel(String text, Font font){
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, (int) (13*givenScaleX)));
        label.setForeground(Color.BLACK);
        return label;
    }

    private JRadioButton createTextRadioButton(String text, int width, int height, Font font){
        JRadioButton jRadioButton = new JRadioButton(text);
        jRadioButton.setPreferredSize(new Dimension(width, height));
        jRadioButton.setFont(font);
        jRadioButton.setFocusPainted(false);
        return jRadioButton;
    }

    private JSpinner createSpinner( int limit){
        JSpinner jSpinner = new JSpinner(new SpinnerNumberModel(0,0,limit,1));
        ((JSpinner.DefaultEditor) jSpinner.getEditor()).getTextField().setEditable(false);

        return jSpinner;
    }

    //Making/Drawing the fences
//    public void drawRectangles(Graphics g) {
//
//        Graphics2D graphics2D = (Graphics2D) g; // Makes the graphics be more advanced as Graphics2D to make implementations in 2D
//        Stroke borderStroke = new BasicStroke(6);
//
//        JPanel[] panels = {dateBox, performanceBox, timeBox, addOrRemoveBox};
//        for(JPanel panel : panels) {
//            Stroke boringStroke = graphics2D.getStroke();
//
//            graphics2D.setStroke(borderStroke);
//            graphics2D.drawRect(panel.getX() + 2, panel.getY() + 1, panel.getWidth(), panel.getHeight());
//            graphics2D.setStroke(boringStroke);
//        }
//    }
//    public void paint(Graphics g) {
//        super.paint(g);
//        drawRectangles(g);
//    }

    //Getters
    public static int getChosenDay() {return chosenDay;}

    public static int getChosenMonth() {
        return chosenMonth;}

    public static int getChosenYear() {return chosenYear;}
    public  List<String> getActionToList() {
        return actionToList;
    }
    public  void setActionToList(List<String> actionToList) {
        this.actionToList = actionToList;
    }
    public String getHoursStart() {return hoursStart.getValue().toString();}

    public String getMinutesStart() {return minutesStart.getValue().toString();}

    public String getSecondStart() {return secondStart.getValue().toString();}

    public String getHoursEnd() {return hoursEnd.getValue().toString();}

    public String getMinutesEnd() {return minutesEnd.getValue().toString();}
    public String getSecondEnd() {return secondEnd.getValue().toString();}
    public String getNameAction(){return nameAction.getText();}
    public String getPlans(){
        return "Date:"+chosenYear+"/"+(chosenMonth+1)+"/"+chosenDay+"\n"+
                "Time Starts:"+hoursStart.getValue().toString()+":"+minutesStart.getValue().toString()+":"+secondStart.getValue().toString()+"\n"+
                "Time Ends:"+hoursEnd.getValue().toString()+":"+minutesEnd.getValue().toString()+":"+secondEnd.getValue().toString()+"\n";
    }
    public String getPlans(int chosenYear,int chosenMonth,int chosenDay,int hoursStart,int minutesStart,int secondStart,
                           int hoursEnd, int minutesEnd,int secondEnd){
        return "Date:"+chosenYear+"/"+chosenMonth+"/"+chosenDay+"\n"+
                "Time Starts:"+hoursStart+":"+minutesStart+":"+secondStart+"\n"+
                "Time Ends:"+hoursEnd+":"+minutesEnd+":"+secondEnd+"\n";
    }


    //Updates the days to mach the month
    private void updateDayBox(){
        for (int i = 1; i <= nod; i++) {
            dayBox.addItem(i);
        }
    }

    public boolean isTimeValid(){
        boolean time = false;
        try {
            if (Integer.parseInt(hoursStart.getValue().toString())<Integer.parseInt(hoursEnd.getValue().toString())){
                time =true;
            } else if (Integer.parseInt(hoursStart.getValue().toString())==Integer.parseInt(hoursEnd.getValue().toString())) {
                if (Integer.parseInt(minutesStart.getValue().toString())<Integer.parseInt(minutesEnd.getValue().toString())){
                    time =true;
                } else if (Integer.parseInt(minutesStart.getValue().toString())==Integer.parseInt(minutesEnd.getValue().toString())) {
                    if (Integer.parseInt(secondStart.getValue().toString())==0 && Integer.parseInt(secondEnd.getValue().toString())>=30){
                        time =true;
                    }
                   else if (Integer.parseInt(secondStart.getValue().toString())<Integer.parseInt(secondEnd.getValue().toString()) &&
                            Integer.parseInt(secondStart.getValue().toString())!=0){
                        time =true;
                    }
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"ERROR IN USER'S INPUT","ERROR",JOptionPane.ERROR_MESSAGE);
        }
        return time;
    }
    public boolean isDateValid(){ //<-- need to change
        boolean date = true;
        GregorianCalendar cal = new GregorianCalendar();
        if (chosenYear< cal.get(GregorianCalendar.YEAR)){
            date = false;
            JOptionPane.showMessageDialog(null,"DATE INPUT WAS ALREADY PASSED","ERROR",JOptionPane.ERROR_MESSAGE);
        } else if (chosenYear== cal.get(GregorianCalendar.YEAR)) {
            if ((chosenMonth+1)<cal.get(GregorianCalendar.MONTH)){
                date = false;
                JOptionPane.showMessageDialog(null,"DATE INPUT WAS ALREADY PASSED","ERROR",JOptionPane.ERROR_MESSAGE);
            } else if ((chosenMonth+1)==cal.get(GregorianCalendar.MONTH)) {
                if (chosenDay<cal.get(GregorianCalendar.DAY_OF_MONTH)){
                    date=false;
                    JOptionPane.showMessageDialog(null,"DATE INPUT WAS ALREADY PASSED","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (chosenYear<=0){
            date = false;
            JOptionPane.showMessageDialog(null,"ERROR IN DATE INPUT","ERROR",JOptionPane.ERROR_MESSAGE);
        } else if (chosenYear>= cal.get(GregorianCalendar.YEAR)) {
            if (chosenMonth+1<=0){
                date = false;
                JOptionPane.showMessageDialog(null,"ERROR IN DATE INPUT","ERROR",JOptionPane.ERROR_MESSAGE);
            } else if ((chosenMonth+1)>=cal.get(GregorianCalendar.MONTH)) {
                if (chosenDay<=0){
                    date = false;
                    JOptionPane.showMessageDialog(null,"ERROR IN DATE INPUT","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        return date;
    }
    public boolean isTodayValid(){
        boolean validation=true;
        LocalTime localTime = LocalTime.now();
        GregorianCalendar cal = new GregorianCalendar();
        if (chosenYear== cal.get(GregorianCalendar.YEAR) && (chosenMonth+1)==cal.get(GregorianCalendar.MONTH) && chosenDay==cal.get(GregorianCalendar.DAY_OF_MONTH)){
            if (localTime.getHour()>Integer.parseInt(hoursStart.getValue().toString())){
                validation=false;
                JOptionPane.showMessageDialog(null,"TIME INPUT ALREADY PASSED","ERROR",JOptionPane.ERROR_MESSAGE);
            } else if (localTime.getHour()==Integer.parseInt(hoursStart.getValue().toString()) && localTime.getMinute()>Integer.parseInt(minutesStart.getValue().toString())) {
                validation=false;
                JOptionPane.showMessageDialog(null,"TIME INPUT ALREADY PASSED","ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
        return validation;
    }
    private void setInfoButton(JButton button,Image image) {
        button.setIcon(new ImageIcon(image));
        button.setOpaque(false);
        button.setBorderPainted(false);
    }

//    public void setTodefult(){
//        dateBox.removeAll();
//        addOrRemoveBox.removeAll();
//        timeBox.removeAll();
//        listBox.removeAll();
//        boxOfBoxes.removeAll();
//        this.removeAll();
//    }
}