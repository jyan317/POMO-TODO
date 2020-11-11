package ui;

import controller.ViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import model.Task;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class View extends StackPane {
    private File fxmlFile;
    private List<Task> tasks;
    private String name;

    // REQUIRES: task != null
    // MODIFIES: this
    public View(List<Task> tasks) {
        fxmlFile = new File(getFxml());
        name = getName();
        this.tasks = tasks;
        load();
    }

    private void load() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlFile.toURI().toURL());
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            ViewController controller = fxmlLoader.<ViewController>getController();
            controller.setData(tasks);
            controller.setName(name);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    abstract String getFxml();

    abstract String getName();
}
