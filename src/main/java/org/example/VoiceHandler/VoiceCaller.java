package org.example.VoiceHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VoiceCaller {

    public VoiceCaller(String voiceFile) {
        // Input data
        String text =
                "Here is a calendar where you can see your tasks by name at the time you putted it. Use the two buttons to move between time, on top you can see the year and month."+
                "Here is a list of your recent putted actions by date and time."+
                "By clicking start you are starting your action and you get to choose which task you want to do. After choosing, the task will start running like a video."+
                "Here you put the date of your task to be. Of course invalid date won't be accepted."+
                "Here you put your time you want the task to start and end. Of course the minimum that can be is above 30 seconds and not at the same time or it will throw an error."+
                "Here you choose what kind of task you want it to be and give it a name."+
                "Here you add or remove the type of task to or from the list. When removing please select the task you want to remove."+
                "Here you can see your chosen task and confirm your option to continue setting. The amount of tasks wont be lower then 2 other wise it will throw an error."+
                "Here is the list of your previous selected tasks."+
                "Here is where you need to put if you want the amount of repeats, his time per your choosing and if you want it get be done by a loop and not the amount of rounds."+
                "Here you set for how much time it will run."+
                "Here you can go forward or backward between each task."+
                "Here you choose the location to where the action will do it's task and confirm it's location."+
                "Here you confirm your settings that will be valid to confirm and not to go over the life time you gave it at the start. Of course you can change every moment at this window, your settings but after confirming you won't be able to.";
//        String referenceAudio = "C:\\Users\\artem\\F5-TTS\\src\\f5_tts\\infer\\examples\\basic\\basic_ref_en.wav";
        String outputAudio = "src/main/Python";

        // Python script path
        String pythonScript = "src/main/Python/speak2.0.py";

        // Build the command to execute the Python script with arguments
            String outputPath = outputAudio + "_.wav";

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "python", pythonScript, text, voiceFile, outputPath
            );

            try {
                Process process = processBuilder.start();

                // Print stdout
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("OUT: " + line);
                }

                // Print stderr
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                while ((line = errorReader.readLine()) != null) {
                    System.err.println("ERR: " + line);
                }

                int exitCode = process.waitFor();
                System.out.println("Process for text exited with code " + exitCode);

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
    }
}
