package persistence;


import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

// Converts model elements to JSON objects
public class Jsonifier {

    // EFFECTS: returns JSON representation of tag
    public static JSONObject tagToJson(Tag tag) {
        JSONObject tagJson = new JSONObject();
        tagJson.put("name", tag.toString().substring(1));
        return tagJson;
    }

    // EFFECTS: returns JSON representation of priority
    public static JSONObject priorityToJson(Priority priority) {
        JSONObject priorityJson = new JSONObject();
        if (priority.isImportant()) {
            priorityJson.put("important", true);
        } else {
            priorityJson.put("important", false);
        }
        if (priority.isUrgent()) {
            priorityJson.put("urgent", true);
        } else {
            priorityJson.put("urgent", false);
        }
        return priorityJson;
    }

    // EFFECTS: returns JSON representation of dueDate
    public static JSONObject dueDateToJson(DueDate dueDate) {
        JSONObject dueDateJson = new JSONObject();
        if (dueDate == null) {
            return null;
        } else {
            Calendar dueDateAsCalendar = Calendar.getInstance();
            dueDateAsCalendar.setTime(dueDate.getDate());
            dueDateJson.put("year", dueDateAsCalendar.get(Calendar.YEAR));
            dueDateJson.put("month", dueDateAsCalendar.get(Calendar.MONTH));
            dueDateJson.put("day", dueDateAsCalendar.get(Calendar.DAY_OF_MONTH));
            dueDateJson.put("hour", dueDateAsCalendar.get(Calendar.HOUR_OF_DAY));
            dueDateJson.put("minute", dueDateAsCalendar.get(Calendar.MINUTE));
            return dueDateJson;
        }
    }

    // EFFECTS: returns JSON representation of task
    public static JSONObject taskToJson(Task task) {
        JSONObject taskJson = new JSONObject();
        DueDate d = task.getDueDate();
        Priority p = task.getPriority();
        Status s = task.getStatus();
        taskJson.put("description", task.getDescription());
        taskJson.put("tags", makeTagArray(task));
        if (dueDateToJson(d) == null) {
            taskJson.put("due-date", JSONObject.NULL);
        } else {
            taskJson.put("due-date", dueDateToJson(d));
        }
        taskJson.put("priority", priorityToJson(p));
        taskJson.put("status", statusToPrint(s));
        return taskJson;
    }

    private static JSONArray makeTagArray(Task task) {
        JSONArray tagArray = new JSONArray();
        Set<Tag> setTags = task.getTags();
        List<Tag> tags = new ArrayList<>(setTags);
        for (Tag t : tags) {
            tagArray.put(tagToJson(t));
        }
        return tagArray;
    }

    private static String statusToPrint(Status status) {
        if (status == Status.TODO) {
            return "TODO";
        } else if (status == Status.UP_NEXT) {
            return "UP_NEXT";
        } else if (status == Status.IN_PROGRESS) {
            return "IN_PROGRESS";
        } else {
            return "DONE";
        }
    }

    // EFFECTS: returns JSON array representing list of tasks
    public static JSONArray taskListToJson(List<Task> tasks) {
        JSONArray taskListJson = new JSONArray();
        taskListJson = new JSONArray();
        for (Task t : tasks) {
            taskListJson.put(taskToJson(t));
        }
        return taskListJson;
    }
}