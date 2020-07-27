package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import model.Task;
import ui.Toolbar;

import java.util.List;

public abstract class ViewController {
    @FXML
    protected VBox tasks;
    @FXML
    protected VBox toolBar;

    public abstract void setData(List<Task> tasks);

    public void setName(String name) {
        Toolbar tb = new Toolbar(name);
        toolBar.getChildren().add(tb);
    }
}
