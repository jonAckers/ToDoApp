package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.animations.Shaker;
import sample.model.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpController {

    public static LoginController loginController;

    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private CheckBox cbFemale;

    @FXML
    private TextField tfLocation;

    @FXML
    private Button bSignUp;

    @FXML
    void initialize() {
        // Setup behaviour for sign up button
        bSignUp.setOnAction(actionEvent -> {
            // Add user to database
            boolean added = createUser();

            if (added) {
                // Close sign up screen
                ((Stage) bSignUp.getScene().getWindow()).close();

                // Return to login screen
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/login.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/sample/assets/icon_tasks.png")));
                stage.showAndWait();
            }
        });
    }

    // Add new user to database
    private boolean createUser() {
        // Setup instance of database handler
        DatabaseHandler databaseHandler = new DatabaseHandler();

        // Get values to populate database
        String fName = tfFirstName.getText();
        String lName = tfLastName.getText();
        String username = tfUsername.getText();
        String password = pfPassword.getText();
        String location = tfLocation.getText();

        String gender = "";
        if (cbFemale.isSelected()) {
            gender = "Female";
        } else {
            gender = "Male";
        }

        // Check all fields have been populated
        if ((!fName.equals("") || !lName.equals("") || !username.equals("") ||
                !password.equals("") || !location.equals("")) && validUsername(username)) {
            // Create new user
            User user = new User(fName, lName, username, password, gender, location);

            // Add user
            databaseHandler.signUpUser(user);

            return true;
        }
        //Provide feedback that sign up failed
        Shaker shaker = new Shaker(bSignUp);
        shaker.shake();

        return false;
    }

    // Check if username is unique in the database
    private boolean validUsername(String username) {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getUsernames();

        try {
            while (resultSet.next()) {
                if (resultSet.getString("username").equals(username)) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

}
