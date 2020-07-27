package ui;

import model.Task;
import java.util.List;

// Priority View: Tasks are listed in order of decreasing priority
public class PriorityView extends View {

    // REQUIRES: task != null
    // MODIFIES: this
    public PriorityView(List<Task> tasks) {
        super(tasks);
    }

    @Override
    public String getFxml() {
        return "resources/fxml/PriorityView.fxml";
    }

    @Override
    public String getName() {
        return "Priority View";
    }
}