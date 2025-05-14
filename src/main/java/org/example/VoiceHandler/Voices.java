package org.example.VoiceHandler;

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
    private static final String[] AUDIO_EXTENSIONS = {".mp3", ".wav", ".ogg", ".flac", ".aac", ".m4a"};
    private static final String[] VIDEO_EXTENSIONS = {".mp4", ".avi", ".mov", ".mkv", ".webm", ".flv"};
    private DefaultComboBoxModel<File> fileComboBoxModel;
    private JComboBox<File> fileComboBox;
    private File voicesFile = new File("src/main/java/org/example/VoiceHandler/Voices");
    private JProgressBar progressBar;
    public Voices(double scaleX, double scaleY) {
        super("File Spinner Manager");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize((int) (400*scaleX), (int) (190*scaleY));
        setLocationRelativeTo(null);
        if (!voicesFile.exists()) {
            voicesFile.mkdir();
        }
        progressBar = new JProgressBar();
        fileComboBoxModel = new DefaultComboBoxModel<>();
        fileComboBox = new JComboBox<>(fileComboBoxModel);
        fileComboBox.setPreferredSize(new Dimension((int) (300*scaleX), (int) (25*scaleY)));

        JButton addButton = new JButton("Add File");
        JButton removeButton = new JButton("Remove Selected");

        addButton.addActionListener(e -> addFile());
        removeButton.addActionListener(e -> removeSelectedFile());
        fileComboBoxModel.addElement(new File("src/main/java/org/example/VoiceHandler/defaultVoice.wav"));
        if (voicesFile.exists() && isFolderNotEmpty(voicesFile)) {
            for (File f : voicesFile.listFiles()) {
                fileComboBoxModel.addElement(f);
            }
        }

        JPanel topPanel = new JPanel();
        JLabel rules = new JLabel();
        rules.setFont(new Font("Serif", Font.BOLD, (int) (12*scaleY)));
        rules.setText("Please select a file that is an audio or video for your voice");
        JTextArea selectedVoice = new JTextArea();
        selectedVoice.setPreferredSize(new Dimension((int) (300*scaleX), (int) (25*scaleY)));
        selectedVoice.setFont(new Font("Serif", Font.BOLD, (int) (12*scaleY)));
        selectedVoice.setText("Selected Voice:"+fileComboBoxModel.getElementAt(0));
        JButton confirm = new JButton("Confirm choice");
        confirm.addActionListener(e -> {
            if (!isAudioOrVideoFile((File) fileComboBox.getSelectedItem())){
                JOptionPane.showMessageDialog(null,"PLEASE PUT VIDEO FILE OR AUDIO FILE ONLY","ERROR",JOptionPane.ERROR_MESSAGE);
                removeSelectedFile();
            }else {
                selectedVoice.setText("Selected Voice:"+fileComboBox.getSelectedItem().toString());
                JDialog progressDialog = createProgressDialog();

                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() {
                        try {
                            VoiceCaller voiceCaller = new VoiceCaller(fileComboBox.getSelectedItem().toString());
                            if (fileComboBox.getSelectedItem() != fileComboBoxModel.getElementAt(0)) {
                                copyFileToFolder((File) fileComboBox.getSelectedItem(), voicesFile);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        return null;
                    }

                    @Override
                    protected void done() {
                        progressDialog.dispose();
                    }
                };

                worker.execute();
                progressDialog.setVisible(true);
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
        fileChooser.setFileFilter(new VoiceFilters());
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
    public static boolean isAudioOrVideoFile(File file) {
        if (file == null || !file.isFile()) return false;

        String name = file.getName().toLowerCase();
        for (String ext : AUDIO_EXTENSIONS) {
            if (name.endsWith(ext)) return true;
        }
        for (String ext : VIDEO_EXTENSIONS) {
            if (name.endsWith(ext)) return true;
        }
        return false;
    }
    private JDialog createProgressDialog() {
        JDialog dialog = new JDialog(this, "Processing Voice...", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setSize(300, 100);
        dialog.setLocationRelativeTo(this);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setString("Processing... It may take a while...");
        progressBar.setStringPainted(true);

        dialog.getContentPane().add(progressBar, BorderLayout.CENTER);
        return dialog;
    }

}
