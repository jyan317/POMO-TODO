package ui;

import model.Task;
import java.util.List;

// List View: Tasks are listed (in no particular order)
public class ListView extends View {

    // REQUIRES: task != null
    // MODIFIES: this
    public ListView(List<Task> tasks) {
        super(tasks);
    }

    @Override
    public String getFxml() {
        return "resources/fxml/ListView.fxml";
    }

    @Override
    public String getName() {
        return "List View";
    }
}