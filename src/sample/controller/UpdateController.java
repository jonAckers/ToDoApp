package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.Task;

import java.sql.SQLException;

public class UpdateController {

    private Task task;
    private DatabaseHandler databaseHandler;
    private ListController listController;

    @FXML
    private TextField tfTask;

    @FXML
    private TextField tfDescription;

    @FXML
    private Button bUpdate;

    @FXML
    void initialize() {
        // Setup behaviour for update button
        bUpdate.setOnAction(actionEvent -> {
            // Create instance of database handler
            databaseHandler = new DatabaseHandler();

            // Check if the task name is being changed
            if (!tfTask.getText().equals("")) {
                // Store the new name
                task.setTask(tfTask.getText().trim());
            }

            // Check if the task description is being changed
            if (!tfDescription.getText().equals("")) {
                // Store the new description
                task.setDescription(tfDescription.getText().trim());
            }

            // Change task in database
            databaseHandler.updateTask(task);

            // Refresh list screen to show new details
            try {
                listController.initialize();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Close window
            ((Stage) bUpdate.getScene().getWindow()).close();

        });
    }

    // Store the task that is being updated
    public void setTask(Task t) {
        this.task = t;
    }

    // Store the list controller that the task is being displayed by
    public void setListController(ListController listController) {
        this.listController = listController;
    }
}
