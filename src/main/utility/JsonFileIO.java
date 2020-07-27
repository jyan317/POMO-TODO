package utility;

import model.Task;
import parsers.TaskParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.readAllLines;
import static persistence.Jsonifier.taskListToJson;

// File input/output operations
public class JsonFileIO {
    public static final File jsonDataFile = new File("./resources/json/tasks.json");

    // EFFECTS: attempts to read jsonDataFile and parse it
    //           returns a list of tasks from the content of jsonDataFile
    public static List<Task> read() {
        List<Task> lot = new ArrayList<>();
        try {
            TaskParser taskParser = new TaskParser();
            List<String> los = readAllLines(jsonDataFile.toPath());
            String toParse = "";
            for (String s : los) {
                toParse = toParse + s;
            }
            lot = taskParser.parse(toParse);
        } catch (IOException ioe) {
            System.out.println("Couldn't read/parse file");
        }
        return lot;
    }

    // EFFECTS: saves the tasks to jsonDataFile
    public static void write(List<Task> tasks) {
        try {
            String tasksString = taskListToJson(tasks).toString(1);
            Files.write(jsonDataFile.toPath(), tasksString.getBytes());
        } catch (IOException ioe) {
            System.out.println("Couldn't write to file");
        }
    }

}
