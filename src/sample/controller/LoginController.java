package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.database.Const;
import sample.database.DatabaseHandler;
import sample.animations.Shaker;
import sample.model.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {


    public static int userId;

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private Button bLogin;

    @FXML
    private Button bSignUp;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {
        // Initialise database handler
        databaseHandler = new DatabaseHandler();

        // Setup behaviour for sign up button
        bSignUp.setOnAction(actionEvent -> {
            // Close login window
            ((Stage) bSignUp.getScene().getWindow()).close();

            // Create sign up window
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/signup.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });

        // Setup behaviour for login button
        bLogin.setOnAction(actionEvent -> {
            // Get text from text boxes
            String usernameText = tfUsername.getText().trim();
            String passwordText = pfPassword.getText().trim();

            User user = new User();
            user.setUsername(usernameText);
            user.setPassword(passwordText);

            // Get corresponding user from database
            ResultSet userRow = databaseHandler.getUser(user);

            int counter = 0;
            try {
                while (userRow.next()) {
                    counter++;
                    LoginController.userId = userRow.getInt(Const.USERS_ID);
                }

                if (counter == 1) {
                    // Login successful; move on to list screen
                    showListScreen();
                } else {
                    // Login unsuccessful; provide feedback
                    Shaker usernameShaker = new Shaker(tfUsername);
                    Shaker passwordShaker = new Shaker(pfPassword);
                    usernameShaker.shake();
                    passwordShaker.shake();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
    }

    // Create list screen
    private void showListScreen() {
        // Close current window
        ((Stage) bSignUp.getScene().getWindow()).close();

        // Open list window
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/list.fxml"));
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

}

