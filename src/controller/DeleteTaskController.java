package controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import model.Task;
import ui.ListView;
import run.PomoTodoApp;
import utility.Logger;

// Controller class for AddTask UI
public class DeleteTaskController {
    @FXML
    private JFXTextArea description;
    private Task task;

    // EFFECTS: delete task with given description from list of Tasks in PomoTodoApp
    //          return to the list view UI
    @FXML
    public void deleteTask() {
        Logger.log("DeleteTaskController", "Delete new Task with description " + description.getText());
        try {
            PomoTodoApp.getTasks().removeIf(t -> t.getDescription().equals(description.getText()));
        } catch (RuntimeException e) {
            Logger.log("DeleteTaskController", "Failed to find task from description " + description.getText());
        } finally {
            returnToListView();
        }
    }

    // EFFECTS: return to the list view UI
    private void returnToListView() {
        Logger.log("DeleteTaskController", "Return to the list view UI.");
        PomoTodoApp.setScene(new ListView(PomoTodoApp.getTasks()));
    }
}
