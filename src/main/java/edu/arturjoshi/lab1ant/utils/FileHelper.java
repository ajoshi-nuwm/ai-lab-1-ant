package edu.arturjoshi.lab1ant.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FileHelper {

    public static List<String> readFromFile(String fileName) throws IOException {
        List<String> result = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String currentLine = reader.readLine();
        while(currentLine != null) {
            result.add(currentLine);
            currentLine = reader.readLine();
        }
        return result;
    }

    public static List<String> getFileNamesInFolder(String folderName) {
        File file = new File(folderName);
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            if(files != null && files.length > 0) {
                return Arrays.stream(files).map(File::getAbsolutePath).collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

    public static void writeToFile(String folder, String filename, String text) throws IOException {
        File originFile = new File(filename);
        File file = new File(folder, originFile.getName());
        file.getParentFile().mkdirs();
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(text);
        writer.close();
    }
}
