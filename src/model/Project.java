package model;

import model.exceptions.NullArgumentException;

import java.util.*;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order of tasks is preserved
public class Project extends Todo implements Iterable<Todo> {
    private String description;
    private List<Todo> tasks;

    // MODIFIES: this
    // EFFECTS: constructs a project with the given description
    //     the constructed project shall have no tasks.
    //  throws EmptyStringException if description is null or empty
    public Project(String description) {
        super(description);
        this.description = description;
        tasks = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: task is added to this project (if it was not already part of it)
    //   throws NullArgumentException when task is null
    public void add(Todo task) {
        if (task == null) {
            throw new NullArgumentException("task must be non-null");
        }
        if (!(this.equals(task) || contains(task))) {
            tasks.add(task);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes task from this project
    //   throws NullArgumentException when task is null
    public void remove(Todo task) {
        if (task == null) {
            throw new NullArgumentException("task must be non-null");
        }
        if (contains(task)) {
            tasks.remove(task);
        }
    }

    // EFFECTS: returns an unmodifiable list of tasks in this project.
    @Deprecated
    public List<Task> getTasks() {
        throw new UnsupportedOperationException();
    }

    // EFFECTS: returns an integer between 0 and 100 which represents
    //     the percentage of completion (rounded down to the nearest integer).
    //     The value returned is the average of the percentage of completion of
    //     all the tasks and sub-projects in this project.
    public int getProgress() {
        int numTodo = tasks.size();
        int sumProgress = 0;
        for (Todo t : tasks) {
            sumProgress += t.getProgress();
        }
        if (numTodo == 0) {
            return 0;
        } else {
            int avgCompletion = sumProgress / numTodo;
            return avgCompletion;
        }
    }

    @Override
    public int getEstimatedTimeToComplete() {
        int etcSoFar = 0;
        for (Todo t : tasks) {
            etcSoFar += t.getEstimatedTimeToComplete();
        }
        return etcSoFar;
    }

    // EFFECTS: returns the number of tasks (and sub-projects) in this project
    public int getNumberOfTasks() {
        return tasks.size();
    }

    // EFFECTS: returns true if every task (and sub-project) in this project is completed, and false otherwise
    //     If this project has no tasks (or sub-projects), return false.
    public boolean isCompleted() {
        return getNumberOfTasks() != 0 && getProgress() == 100;
    }

    // EFFECTS: returns true if this project contains the task
    //   throws NullArgumentException when task is null
    public boolean contains(Todo task) {
        if (task == null) {
            throw new NullArgumentException("Illegal argument: task/sub-project is null");
        }
        return tasks.contains(task);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(description, project.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public Iterator<Todo> iterator() {
        return new ProjectIterator();
    }

    private class ProjectIterator implements Iterator<Todo> {

        private int priorityToIterate;
        private int index;
        private int itemsReturned;

        public ProjectIterator() {
            priorityToIterate = 1;
            index = 0;
            itemsReturned = 0;
        }

        @Override
        public boolean hasNext() {
            return itemsReturned < tasks.size();
        }

        @Override
        public Todo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            switch (priorityToIterate) {
                default:
                    return case1();
                case 2:
                    return case2();
                case 3:
                    return case3();
                case 4:
                    return case4();
            }
        }

        private Todo case1() {
            Todo output1 = importantAndUrgentIteration();
            index++;
            return output1;
        }

        private Todo case2() {
            Todo output2 = importantIteration();
            index++;
            return output2;
        }

        private Todo case3() {
            Todo output3 = urgentIteration();
            index++;
            return output3;
        }

        private Todo case4() {
            Todo output4 = notImportantNotUrgentIteration();
            index++;
            return output4;
        }

        private Todo importantAndUrgentIteration() {
            if (index > tasks.size() - 1) {
                index = 0;
                priorityToIterate++;
                return importantIteration();
            } else {
                Priority todoPriority = tasks.get(index).getPriority();
                if (todoPriority.isImportant() && todoPriority.isUrgent()) {
                    itemsReturned++;
                } else {
                    index++;
                    importantAndUrgentIteration();
                }
            }
            return tasks.get(index);
        }

        private Todo importantIteration() {
            if (index > tasks.size() - 1) {
                index = 0;
                priorityToIterate++;
                return urgentIteration();
            } else {
                Priority todoPriority = tasks.get(index).getPriority();
                if (todoPriority.isImportant() && !todoPriority.isUrgent()) {
                    itemsReturned++;
                } else {
                    index++;
                    importantIteration();
                }
            }
            return tasks.get(index);
        }

        private Todo urgentIteration() {
            if (index > tasks.size() - 1) {
                index = 0;
                priorityToIterate++;
                return notImportantNotUrgentIteration();
            } else {
                Priority todoPriority = tasks.get(index).getPriority();
                if (!todoPriority.isImportant() && todoPriority.isUrgent()) {
                    itemsReturned++;
                } else {
                    index++;
                    urgentIteration();
                }
            }
            return tasks.get(index);
        }

        private Todo notImportantNotUrgentIteration() {
            Priority todoPriority = tasks.get(index).getPriority();
            if (!todoPriority.isImportant() && !todoPriority.isUrgent()) {
                itemsReturned++;
            } else {
                index++;
                notImportantNotUrgentIteration();
            }
            return tasks.get(index);
        }
    }
}
