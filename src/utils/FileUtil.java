package utils;

import java.nio.file.*;
import java.io.*;
import java.util.ArrayList;
import exception.ManagerSaveException;
import task.Epic;
import task.Subtask;
import task.Task;
import java.util.Arrays;

public class FileUtil {

    public Path fileCreate(Path path) throws ManagerSaveException {
        Path file = null;

        try {
            file = Files.createFile(path);

            if (Files.exists(path)) {
                System.out.println("Файл успешно создан.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return file;
    }
    public void fileWrite(Path path,
                          ArrayList<Task> tasks,
                          ArrayList<Epic> epics,
                          ArrayList<Subtask> subtasks,
                          String[] headers) throws ManagerSaveException {
        clearFile(path);

        try(FileWriter fileWriter = new FileWriter(path.toFile())) {

            fileWriter.write(String.join(",", headers));
            fileWriter.write( "\n");

            for (Task task : tasks) {
                fileWriter.write(task.toString() + "\n");
            }

            for (Epic epic : epics) {
                String[] EpicArray = Arrays.copyOfRange(epic.toString().split(","), 0, headers.length);
                fileWriter.write(String.join(",", EpicArray) + "\n");
            }

            for (Subtask subtask : subtasks) {
                fileWriter.write(subtask.toString() + "\n");
            }

            fileWriter.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public ArrayList<String[]> loadFromFile(File file) throws ManagerSaveException {
        ArrayList<String[]> csvArray = new ArrayList<>();

        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);

            while (br.ready()) {
                String line = br.readLine();
                csvArray.add(line.split(","));
            }

            br.close();

            return csvArray;
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        }
        catch (IOException e) {
            System.out.println(e);
        }

        return csvArray;
    }

    public boolean fileIsExist(Path path) {
        return Files.exists(path);
    }

    public static void clearFile(Path path) throws ManagerSaveException {
        try (PrintWriter writer = new PrintWriter(path.toFile())) {
            writer.print("");
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
