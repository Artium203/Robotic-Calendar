package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;


public class Voices extends JFrame {
    private DefaultComboBoxModel<File> fileComboBoxModel;
    private JComboBox<File> fileComboBox;
    private File voicesFile = new File("C:\\Users\\Public\\Documents");
    public Voices(double scaleX, double scaleY) {
        super("File Spinner Manager");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize((int) (400*scaleX), (int) (190*scaleY));
        setLocationRelativeTo(null);
        if (!voicesFile.exists()) {
            voicesFile.mkdir();
        }

        fileComboBoxModel = new DefaultComboBoxModel<>();
        fileComboBox = new JComboBox<>(fileComboBoxModel);
        fileComboBox.setPreferredSize(new Dimension((int) (300*scaleX), (int) (25*scaleY)));

        JButton addButton = new JButton("Add File");
        JButton removeButton = new JButton("Remove Selected");

        addButton.addActionListener(e -> addFile());
        removeButton.addActionListener(e -> removeSelectedFile());
        fileComboBoxModel.addElement(new File("src/ImageTitledBorder.java"));
        if (voicesFile.exists() && isFolderNotEmpty(voicesFile)) {
            for (File f : voicesFile.listFiles()) {
                fileComboBoxModel.addElement(f);
            }
        }

        JPanel topPanel = new JPanel();
        JLabel rules = new JLabel();
        rules.setFont(new Font("Serif", Font.BOLD, (int) (12*scaleY)));
        rules.setText("Please select a file that is an audio or video");
        JTextArea selectedVoice = new JTextArea();
        selectedVoice.setPreferredSize(new Dimension((int) (300*scaleX), (int) (25*scaleY)));
        selectedVoice.setFont(new Font("Serif", Font.BOLD, (int) (12*scaleY)));
        selectedVoice.setText("Selected Voice:"+fileComboBoxModel.getElementAt(0));
        JButton confirm = new JButton("Confirm choice");
        confirm.addActionListener(e -> {
            selectedVoice.setText("Selected Voice:"+fileComboBox.getSelectedItem().toString());
            try {
                copyFileToFolder(new File(fileComboBox.getSelectedItem().toString()),voicesFile);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        topPanel.add(fileComboBox);
        topPanel.add(rules);
        topPanel.add(selectedVoice);
        topPanel.add(confirm);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        getContentPane().add(topPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    private void addFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Check if the file is already in the combo box
            boolean exists = false;
            for (int i = 0; i < fileComboBoxModel.getSize(); i++) {
                if (fileComboBoxModel.getElementAt(i).equals(selectedFile)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                fileComboBoxModel.addElement(selectedFile);
            }
        }
    }

    private void removeSelectedFile() {
        File selectedFile = (File) fileComboBox.getSelectedItem();
        if (selectedFile != null && selectedFile!=fileComboBox.getItemAt(0)) {
            fileComboBoxModel.removeElement(selectedFile);
            try {
                deleteFileFromFolder(voicesFile,selectedFile.getName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public List<String> getFileList() {
        List<String> fileList = new ArrayList<>();
        if (fileComboBoxModel.getSize()!=0) {
            for (int i = 0; i < fileComboBoxModel.getSize(); i++) {
                fileList.add(fileComboBoxModel.getElementAt(i).toString());
            }
        }
        return fileList;
    }
    public static void copyFileToFolder(File sourceFile, File targetFolder) throws IOException {
        if (!targetFolder.exists()) {
            targetFolder.mkdirs(); // Create the folder if it doesn't exist
        }

        Path sourcePath = sourceFile.toPath();
        Path targetPath = new File(targetFolder, sourceFile.getName()).toPath();

        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Copied to: " + targetPath);
    }
    public static void deleteFileFromFolder(File targetFolder, String fileName) throws IOException {
        File fileToDelete = new File(targetFolder, fileName);
        Path pathToDelete = fileToDelete.toPath();

        if (Files.exists(pathToDelete)) {
            Files.delete(pathToDelete);
            System.out.println("Deleted: " + pathToDelete);
        } else {
            System.out.println("File not found: " + pathToDelete);
        }
    }
    public static boolean isFolderNotEmpty(File folder) {
        if (folder.exists() && folder.isDirectory()) {
            String[] files = folder.list();
            return files != null && files.length > 0;
        }
        return false;
    }
}
