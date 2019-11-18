package sample.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.Task;

public class CellController extends ListCell<Task> {

    private int taskId;
    private ListController listController;

    @FXML
    private AnchorPane pAnchor;

    @FXML
    private Label lDescription;

    @FXML
    private Label lTask;

    @FXML
    private Label lDate;

    @FXML
    private ImageView bDelete;

    @FXML
    private ImageView bRefresh;

    private FXMLLoader fxmlLoader;

    private DatabaseHandler databaseHandler;

    // Constructor
    public CellController(ListController listController) {
        this.listController = listController;
    }

    // Called automatically on update
    @Override
    protected void updateItem(Task myTask, boolean empty) {
        super.updateItem(myTask, empty);

        // If no task has been provided, don't display anything
        if (empty || myTask == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoader == null) {
                // Setup instance of FXMLLoader
                fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/cell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Populate labels with values from task
            lTask.setText(myTask.getTask());
            lDate.setText(myTask.getDateCreated().toString());
            lDescription.setText(myTask.getDescription());

            taskId = myTask.getTaskId();

            // Setup behaviour for delete button
            bDelete.setOnMouseClicked(event -> {
                // Remove cell list
                getListView().getItems().remove(getItem());

                // Remove task from database
                databaseHandler = new DatabaseHandler();
                databaseHandler.deleteTask(LoginController.userId, taskId);
            });

            // Setup behaviour for refresh button
            bRefresh.setOnMouseClicked(event -> {
                // Setup FXMLLoader sao that next scene can be created
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/update.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                loader.setLocation(getClass().getResource("/sample/view/update.fxml"));

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/sample/assets/icon_tasks.png")));


                UpdateController updateController = loader.getController();
                updateController.setTask(myTask);
                updateController.setListController(listController);

                stage.showAndWait();
            });

            setText(null);
            setGraphic(pAnchor);
        }
    }
}
