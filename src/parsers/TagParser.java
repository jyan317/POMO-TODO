package parsers;

import model.*;
import parsers.exceptions.ParsingException;

import java.util.ArrayList;

public class TagParser extends Parser {

    private Task task;
    private String input;
    private ArrayList<String> inputList;
    private boolean important;
    private boolean urgent;
    private String taskStatus;
    private String taskDueDate;


    @Override
    // MODIFIES: this, task
    // EFFECTS: parses the input to extract properties such as priority or deadline
    //    and updates task with those extracted properties
    //  throws ParsingException if description does not contain the tag deliminator
    public void parse(String input, Task task) throws ParsingException {
        resetImportantUrgent();
        if (!(input.contains("##"))) {
            description = input;
            throw new ParsingException("Cannot parse without deliminator - description set to input");
        }
        this.task = task;
        this.input = input;
        inputList = new ArrayList<>();
        setDescription();
        inputList = inputToList();
        setPriority();
        setDueDate();
        setStatus();
        setTags();
    }

    // MODIFIES: this
    // EFFECTS: resets fields
    private void resetImportantUrgent() {
        important = false;
        urgent = false;
    }

    // MODIFIES: this
    // EFFECTS: updates task description if available, otherwise leaves as task's description
    private void setDescription() {
        description = task.getDescription();
        String inputToReturn = input;
        int index = inputToReturn.indexOf("##");
        input = inputToReturn.substring(index + 2);
        if (inputToReturn.substring(0, index).length() > 0) {
            task.setDescription(inputToReturn.substring(0, index));
            description = inputToReturn.substring(0, index);
        }
    }

    // REQUIRES: elements of list are semi-colon separated
    // MODIFIES: this
    // EFFECTS: returns meta-data/tags as list
    private ArrayList<String> inputToList() {
        String[] array = input.split(";");
        for (String s : array) {
            inputList.add(s.trim());
        }
        return inputList;
    }


    // MODIFIES: this
    // EFFECTS: updates task priority if available, otherwise doesn't change
    private void setPriority() {
        setImportantUrgent();
        int quadrant = 4;
        if (important && urgent) {
            quadrant = 1;
        } else if (important) {
            quadrant = 2;
        } else if (urgent) {
            quadrant = 3;
        }
        Priority newPriority = new Priority(quadrant);
        task.setPriority(newPriority);
    }

    // MODIFIES: this
    // EFFECTS: sets important and urgent values from task
    private void setImportantUrgent() {
        Priority priority = task.getPriority();
        if (priority.isImportant() && priority.isUrgent()) {
            important = true;
            urgent = true;
        } else if (priority.isImportant()) {
            important = true;
            setAndRemoveUrgent();
        } else if (priority.isUrgent()) {
            urgent = true;
            setAndRemoveImportant();
        } else {
            setImportantUrgentFromTags();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets and removes urgent tags if found, otherwise do nothing
    private void setAndRemoveUrgent() {
        ArrayList<String> workingUrgentList = new ArrayList<>();
        for (String input : inputList) {
            workingUrgentList.add(input.toLowerCase());
        }
        if (workingUrgentList.contains("urgent")) {
            urgent = true;
            for (int i = 0; i < inputList.size(); i++) {
                if (inputList.get(i).equalsIgnoreCase("urgent")) {
                    inputList.remove(inputList.get(i));
                    i--;
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sets and removes important tags if found, otherwise do nothing
    private void setAndRemoveImportant() {
        ArrayList<String> workingImportantList = new ArrayList<>();
        for (String input : inputList) {
            workingImportantList.add(input.toLowerCase());
        }
        if (workingImportantList.contains("important")) {
            important = true;
            for (int i = 0; i < inputList.size(); i++) {
                if (inputList.get(i).equalsIgnoreCase("important")) {
                    inputList.remove(inputList.get(i));
                    i--;
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: extracts priority from inputList
    private void setImportantUrgentFromTags() {
        for (String s : inputList) {
            if (s.equalsIgnoreCase("important")) {
                important = true;
            } else if (s.equalsIgnoreCase("urgent")) {
                urgent = true;
            }
        }
        if (important || urgent) {
            removeDuplicatePriorities();
        }
    }


    // MODIFIES: this
    // EFFECTS: removes initial and duplicate priority tags
    private void removeDuplicatePriorities() {
        if (important) {
            for (int i = 0; i < inputList.size(); i++) {
                if (inputList.get(i).equalsIgnoreCase("important")) {
                    inputList.remove(inputList.get(i));
                    i--;
                }
            }
        }
        if (urgent) {
            for (int i = 0; i < inputList.size(); i++) {
                if (inputList.get(i).equalsIgnoreCase("urgent")) {
                    inputList.remove(inputList.get(i));
                    i--;
                }
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: updates task due date if available, otherwise doesn't change
    private void setDueDate() {
        ArrayList<String> workingDateList = new ArrayList<>();
        for (String input : inputList) {
            workingDateList.add(input.toLowerCase());
        }
        if (containsDate(workingDateList)) {
            extractDueDate();
            DueDate dueDate = new DueDate();
            if (taskDueDate.equals("today")) {
                task.setDueDate(dueDate);
            } else {
                dueDate.postponeOneDay();
                task.setDueDate(dueDate);
            }
        }
    }

    // EFFECTS: returns true if given list contains "today" or "tomorrow"
    private boolean containsDate(ArrayList list) {
        return list.contains("today") || list.contains("tomorrow");
    }


    // MODIFIES: this
    // EFFECTS: extracts due date from input string
    private void extractDueDate() {
        for (int i = 0; i < inputList.size(); i++) {
            if (inputList.get(i).equalsIgnoreCase("today")) {
                taskDueDate = "today";
                i = inputList.size();
            } else if (inputList.get(i).equalsIgnoreCase("tomorrow")) {
                taskDueDate = "tomorrow";
                i = inputList.size();
            }
        }
        removeDuplicateDates();
    }

    // MODIFIES: this
    // EFFECTS: removes all instances of taskDueDate's value in inputList
    private void removeDuplicateDates() {
        if (taskDueDate.equals("today")) {
            for (int i = 0; i < inputList.size(); i++) {
                if (inputList.get(i).equalsIgnoreCase("today")) {
                    inputList.remove(inputList.get(i));
                    i--;
                }
            }
        } else {
            for (int i = 0; i < inputList.size(); i++) {
                if (inputList.get(i).equalsIgnoreCase("tomorrow")) {
                    inputList.remove(inputList.get(i));
                    i--;
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: updates task status if available, otherwise doesn't change
    private void setStatus() {
        ArrayList<String> workingStatusList = new ArrayList<>();
        for (String input : inputList) {
            workingStatusList.add(input.toLowerCase());
        }
        if (containsStatus(workingStatusList)) {
            extractStatus();
            switch (taskStatus) {
                case "done":
                    task.setStatus(Status.DONE);
                    break;
                case "up next":
                    task.setStatus(Status.UP_NEXT);
                    break;
                case "in progress":
                    task.setStatus(Status.IN_PROGRESS);
                    break;
                default:
                    task.setStatus(Status.TODO);
                    break;
            }
        }
    }

    // EFFECTS: returns true if given list contains "to do", "in progress", "up next" or "done"
    private boolean containsStatus(ArrayList list) {
        return list.contains("to do") || list.contains("in progress")
                || list.contains("up next") || list.contains("done");
    }


    // MODIFIES: this
    // EFFECTS: extracts status from input string
    private void extractStatus() {
        for (int i = 0; i < inputList.size(); i++) {
            if (inputList.get(i).equalsIgnoreCase("to do")) {
                taskStatus = "to do";
                i = inputList.size();
            } else if (inputList.get(i).equalsIgnoreCase("up next")) {
                taskStatus = "up next";
                i = inputList.size();
            } else if (inputList.get(i).equalsIgnoreCase("in progress")) {
                taskStatus = "in progress";
                i = inputList.size();
            } else if (inputList.get(i).equalsIgnoreCase("done")) {
                taskStatus = "done";
                i = inputList.size();
            }
        }
        removeDuplicateStatus();
    }

    // MODIFIES: this
    // EFFECTS: removes all instances of taskStatus' value in inputList
    private void removeDuplicateStatus() {
        switch (taskStatus) {
            case "to do":
                for (int i = 0; i < inputList.size(); i++) {
                    if (inputList.get(i).equalsIgnoreCase("to do")) {
                        inputList.remove(inputList.get(i));
                        i--;
                    }
                }
                break;
            case "up next":
                for (int i = 0; i < inputList.size(); i++) {
                    if (inputList.get(i).equalsIgnoreCase("up next")) {
                        inputList.remove(inputList.get(i));
                        i--;
                    }
                }
                break;
            case "in progress":
                for (int i = 0; i < inputList.size(); i++) {
                    if (inputList.get(i).equalsIgnoreCase("in progress")) {
                        inputList.remove(inputList.get(i));
                        i--;
                    }
                }
                break;
            default:
                for (int i = 0; i < inputList.size(); i++) {
                    if (inputList.get(i).equalsIgnoreCase("done")) {
                        inputList.remove(inputList.get(i));
                        i--;
                    }
                }
                break;
        }
    }


    // MODIFIES: this
    // EFFECTS: updates task tags if any, otherwise doesn't change
    private void setTags() {
        removeDuplicateTags();
        removeEmptyTags();
        for (String tag : inputList) {
            task.addTag(tag);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes duplicate tags from inputList
    private void removeDuplicateTags() {
        ArrayList<String> workingTagList = new ArrayList<>();
        ArrayList<String> comparingTagList = new ArrayList<>();
        for (Tag taskTag : task.getTags()) {
            workingTagList.add(taskTag.toString().substring(1));
            comparingTagList.add(taskTag.toString().substring(1).toLowerCase());
        }
        for (String tag : inputList) {
            String comparableTag = tag.toLowerCase();
            if (!(comparingTagList.contains(comparableTag))) {
                comparingTagList.add(comparableTag);
                workingTagList.add(tag);
            }
        }
        inputList = workingTagList;
    }

    // MODIFIES: this
    // EFFECTS: removes empty tags from inputList
    private void removeEmptyTags() {
        for (int i = 0; i < inputList.size(); i++) {
            if (inputList.get(i).isEmpty()) {
                inputList.remove(inputList.get(i));
            }
        }
    }
}