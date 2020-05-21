package view;

import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    FileWriter fileWriter;

    public FileHandler(String filename) {
        try {
            fileWriter = new FileWriter(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String text) {
        try {
            fileWriter.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeln(String text) {
        write(text);
        write("\n");
    }

    public void close() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
