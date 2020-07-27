package controller;

import model.Project;
import model.Task;
import model.Todo;
import ui.Todobar;


import java.util.List;

// Controller class for ListView UI
public class PriorityViewController extends ViewController {

    private Project project = new Project("project");


    // EFFECTS: adds Todobar UI to PriorityView UI for each task
    public void setData(List<Task> tasks) {
        for (Task t : tasks) {
            project.add(t);
        }
        for (Todo td : project) {
            Task t = (Task) td;
            Todobar todobar = new Todobar(t);
            this.tasks.getChildren().add(todobar);
        }
    }

    // EFFECTS: sets name of Toolbar to given name
    public void setName(String name) {
        super.setName(name);
    }
}