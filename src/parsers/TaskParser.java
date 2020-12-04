package parsers;

import model.DueDate;
import model.Task;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// Represents Task parser
public class TaskParser {
    // MODIFIES: this
    // EFFECTS: iterates over every JSONObject in the JSONArray represented by the input
    // string and parses it as a task; each parsed task is added to the list of tasks.
    // Any task that cannot be parsed due to malformed JSON data is not added to the
    // list of tasks.
    // Note: input is a string representation of a JSONArray
    public List<Task> parse(String input) {
        JSONArray fullArray = new JSONArray(input);

        ArrayList<Task> outputList = new ArrayList<>();
        for (int i = 0; i < fullArray.length(); i++) {
            try {
                JSONObject thisTaskObj = (JSONObject) fullArray.get(i);
                String strToParse = toParse(thisTaskObj);
                Task task = new Task(strToParse);
                DueDate date = dateToDueDate(thisTaskObj);
                task.setDueDate(date);
                outputList.add(task);
            } catch (JSONException je) {
                System.out.println("couldn't parse task " + i);
            }
        }
        return outputList;
    }

    // EFFECTS: returns parsing String for creating a task
    private String toParse(JSONObject json) {
        JSONArray tagsArray = json.getJSONArray("tags");
        JSONObject priorityObj = json.getJSONObject("priority");
        String statusStr = json.getString("status");

        String desc = json.getString("description");
        String tags = getTagList(tagsArray);
        String priority = getPriorityList(priorityObj);
        String status = getStatus(statusStr);

        return (desc + "##" + tags + priority + status);
    }

    // EFFECTS: returns tag section of parsing String
    private String getTagList(JSONArray tags) {
        String tagList = ";";
        for (int i = 0; i < tags.length(); i++) {
            JSONObject tagObj = tags.getJSONObject(i);
            String tagName = tagObj.getString("name");
            tagList = tagList + tagName + "; ";
        }
        return tagList;
    }

    // EFFECTS: returns priority section of parsing String
    private String getPriorityList(JSONObject priority) {
        boolean important = priority.getBoolean("important");
        boolean urgent = priority.getBoolean("urgent");
        String priorityList = ";";
        if (important) {
            priorityList = priorityList + "important;";
        }
        if (urgent) {
            priorityList = priorityList + "urgent;";
        }
        return priorityList;
    }

    // EFFECTS: returns status section of parsing String
    private String getStatus(String status) {
        String statusString;
        switch (status) {
            case "DONE":
                statusString = "done;";
                break;
            case "IN_PROGRESS":
                statusString = "in progress;";
                break;
            case "UP_NEXT":
                statusString = "up next;";
                break;
            default:
                statusString = ";";
                break;
        }
        return statusString;
    }

    // EFFECTS: returns null if due date is null, calls helper if due date is not null
    //   throws JSONException if due-date key not found
    private DueDate dateToDueDate(JSONObject json) throws JSONException {
        if (json.has("due-date")) {
            if (json.isNull("due-date")) {
                return null;
            } else {
                return dateToDueDateNotNull(json.getJSONObject("due-date"));
            }
        } else {
            throw new JSONException("could not find due-date");
        }
    }

    // EFFECTS: if all date fields are integers, constructs due date
    //   throws JSONException if any date fields are of incorrect type
    private DueDate dateToDueDateNotNull(JSONObject date) throws JSONException {
        Object yearObj = date.get("year");
        Object monthObj = date.get("month");
        Object dayObj = date.get("day");
        Object hourObj = date.get("hour");
        Object minObj = date.get("minute");

        if (checkParams(yearObj, monthObj, dayObj, hourObj, minObj)) {
            int year = (int) yearObj;
            int month = (int) monthObj;
            int day = (int) dayObj;
            int hour = (int) hourObj;
            int min = (int) minObj;

            Calendar workingCal = Calendar.getInstance();
            workingCal.set(Calendar.YEAR, year);
            workingCal.set(Calendar.MONTH, month);
            workingCal.set(Calendar.DATE, day);
            workingCal.set(Calendar.HOUR_OF_DAY, hour);
            workingCal.set(Calendar.MINUTE, min);

            DueDate output = new DueDate();
            Date workingDate = workingCal.getTime();
            output.setDueDate(workingDate);

            return output;
        } else {
            throw new JSONException("incorrect date field type(s)");
        }

    }

    private boolean checkParams(Object year, Object month, Object day, Object hour, Object min) throws JSONException {
        if (!(year instanceof Integer)) {
            throw new JSONException("year must be an integer");
        }
        if (!(month instanceof Integer)) {
            throw new JSONException("month must be an integer");
        }
        if (!(day instanceof Integer)) {
            throw new JSONException("day must be an integer");
        }
        if (!(hour instanceof Integer)) {
            throw new JSONException("hour must be an integer");
        }
        if (!(min instanceof Integer)) {
            throw new JSONException("min must be an integer");
        }
        return true;
    }
}