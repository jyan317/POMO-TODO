package controller;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Status;
import model.Task;
import ui.EditTask;
import ui.ListView;
import ui.PomoTodoApp;
import utility.JsonFileIO;
import utility.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// Controller class for Todobar UI
public class TodobarController implements Initializable {
    private static final String todoOptionsPopUpFXML = "resources/fxml/TodoOptionsPopUp.fxml";
    private static final String todoActionsPopUpFXML = "resources/fxml/TodoActionsPopUp.fxml";
    private File todoOptionsPopUpFxmlFile = new File(todoOptionsPopUpFXML);
    private File todoActionsPopUpFxmlFile = new File(todoActionsPopUpFXML);


    @FXML
    private Label descriptionLabel;
    @FXML
    private JFXHamburger todoActionsPopUpBurger;
    @FXML
    private StackPane todoActionsPopUpContainer;
    @FXML
    private JFXRippler todoOptionsPopUpRippler;
    @FXML
    private StackPane todoOptionsPopUpBurger;

    private JFXPopup todoOptionsPopUp;
    private JFXPopup todoActionsPopUp;

    private Task task;

    // REQUIRES: task != null
    // MODIFIES: this
    // EFFECTS: sets the task in this Todobar
    //          updates the Todobar UI label to task's description
    public void setTask(Task task) {
        this.task = task;
        descriptionLabel.setText(task.getDescription());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTodoOptionsPopUp();
        loadTodoOptionsPopUpActionListener();
        loadTodoActionsPopUp();
        loadTodoActionsPopUpActionListener();
    }

    // EFFECTS: load options pop up (edit, delete)
    private void loadTodoOptionsPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoOptionsPopUpFxmlFile.toURI().toURL());
            fxmlLoader.setController(new TodoOptionsPopUpController());
            todoOptionsPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    // EFFECTS: show options pop up when its icon is clicked
    private void loadTodoOptionsPopUpActionListener() {
        todoOptionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                todoOptionsPopUp.show(todoOptionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.RIGHT,
                        -12,
                        15);
            }
        });
    }

    // Inner class: option pop up controller
    class TodoOptionsPopUpController {
        @FXML
        private JFXListView<?> optionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = optionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    Logger.log("TodoOptionsPopUpController", "Edit Task " + task.getDescription());
                    PomoTodoApp.setScene(new EditTask(task));
                    break;
                case 1:
                    Logger.log("TodoOptionsPopUpController", "Delete Task " + task.getDescription());
                    PomoTodoApp.getTasks().remove(task);
                    PomoTodoApp.setScene(new ListView(PomoTodoApp.getTasks()));
                    break;
                default:
                    Logger.log("TodoOptionsPopUpController", "No action is implemented for the selected option");
            }
            todoOptionsPopUp.hide();
        }
    }

    // EFFECTS: loads actions pop up (status and pomodoro)
    private void loadTodoActionsPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoActionsPopUpFxmlFile.toURI().toURL());
            fxmlLoader.setController(new TodoActionsPopUpController());
            todoActionsPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    // EFFECTS: show actions pop up when its icon is clicked
    private void loadTodoActionsPopUpActionListener() {
        todoActionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                todoActionsPopUp.show(todoActionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.RIGHT,
                        -12,
                        15);
            }
        });
    }

    // Inner class: action pop up controller
    class TodoActionsPopUpController {
        @FXML
        private JFXListView<?> actionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = actionPopUpList.getSelectionModel().getSelectedIndex();
            toLoggerLog(selectedIndex);
        }

        private void toLoggerLog(int selectedIndex) {
            switch (selectedIndex) {
                case 0:
                    setTodo();
                    break;
                case 1:
                    setUpNext();
                    break;
                case 2:
                    setInProgress();
                    break;
                case 3:
                    setDone();
                    break;
                default:
                    Logger.log("TodoActionsPopUpController",
                            "No action is implemented for the selected option");
            }
            todoActionsPopUp.hide();
        }

        private void setTodo() {
            task.setStatus(Status.TODO);
            Logger.log("TodoActionsPopUpController",
                    "Status set to Todo");
        }

        private void setUpNext() {
            task.setStatus(Status.UP_NEXT);
            Logger.log("TodoActionsPopUpController",
                    "Status set to Up Next");
        }

        private void setInProgress() {
            task.setStatus(Status.IN_PROGRESS);
            Logger.log("TodoActionsPopUpController",
                    "Status set to In Progress");
        }

        private void setDone() {
            task.setStatus(Status.DONE);
            Logger.log("TodoActionsPopUpController",
                    "Status set to Done");
        }
    }
}




