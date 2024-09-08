package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {
    private static final String FILE_PATH = "C:\\Users\\artem\\IdeaProjects\\Robotic-Calendar\\src\\Resources\\text.txt";

    // Method to read the list of DataContainer objects from the JSON file
    public List<DataContainer> readDataFromFile() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<DataContainer>>() {
            }.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            // If the file doesn't exist or is empty, return a new empty list
            return new ArrayList<>();
        }
    }

    // Method to add a new DataContainer to the JSON file
    public void addDataContainer(DataContainer newDataContainer) {
        // Read existing data
        List<DataContainer> dataContainers = readDataFromFile();
        // Add the new DataContainer to the list
        if (dataContainers==null){
            dataContainers = new ArrayList<>();
        }
        dataContainers.add(newDataContainer);

        // Write the updated list back to the file
        writeDataToFile(dataContainers);
    }

    // Method to write the list of DataContainer objects back to the JSON file
    private void writeDataToFile(List<DataContainer> dataContainers) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(dataContainers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}