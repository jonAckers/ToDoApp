package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.database.DatabaseHandler;
import sample.model.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class ListController {

    @FXML
    private ListView<Task> lvTask;

    @FXML
    private TextField tfTask;

    @FXML
    private TextField tfDescription;

    @FXML
    private Button bSaveTask;

    private DatabaseHandler databaseHandler;

    @FXML
    public void initialize() throws SQLException {
        // Create list to store all tasks
        ObservableList<Task> tasks = FXCollections.observableArrayList();

        // Setup database handler so the tasks can be read
        databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTasksByUser(LoginController.userId);

        // Create a new task for all of the records in the database
        while (resultSet.next()) {
            Task task = new Task();
            task.setTaskId(resultSet.getInt("taskid"));
            task.setTask(resultSet.getString("task"));
            task.setDateCreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));

            tasks.addAll(task);
        }

        lvTask.setItems(tasks);
        lvTask.setCellFactory(CellController -> new CellController(this));

        // Setup behaviour for saving tasks
        bSaveTask.setOnAction(actionEvent -> {
            addNewTask();
        });

    }

    // Adds new tasks to database and list in app
    private void addNewTask() {
        // Make sure both boxes have been filled in
        if (!tfTask.getText().equals("") || !tfDescription.getText().equals("")) {
            // Create new task
            Task newTask = new Task();

            Calendar calendar = Calendar.getInstance();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTimeInMillis());

            newTask.setUserId(LoginController.userId);
            newTask.setTask(tfTask.getText().trim());
            newTask.setDescription(tfDescription.getText().trim());
            newTask.setDateCreated(timestamp);

            databaseHandler.insertTask(newTask);

            tfTask.setText("");
            tfDescription.setText("");

            // Call initialize again to refresh screen
            try {
                initialize();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}

