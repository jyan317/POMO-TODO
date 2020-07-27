package controller;

import model.Task;
import ui.Todobar;

import java.util.List;

// Controller class for ListView UI
public class ListViewController extends ViewController {
    
    // EFFECTS: adds Todobar UI to ListView UI for each task
    public void setData(List<Task> tasks) {
        for (Task t : tasks) {
            Todobar todobar = new Todobar(t);
            this.tasks.getChildren().add(todobar);
        }
    }

    public void setName(String name) {
        super.setName(name);
    }
}