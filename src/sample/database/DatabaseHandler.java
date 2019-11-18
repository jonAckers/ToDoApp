package sample.database;


import sample.model.Task;
import sample.model.User;

import java.security.MessageDigest;
import java.sql.*;

public class DatabaseHandler extends Configs {

    Connection dbConnection;

    // Setup connection with database
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    // Encrypt a string so that password can be stored safely
    private String encryptString(String text) {
        MessageDigest msgDig;
        String encrypted = null;

        try {
            msgDig = MessageDigest.getInstance("SHA-256");
            msgDig.update(text.getBytes());
            encrypted = new String(msgDig.digest());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return encrypted;
    }

    /* Create */
    // Add new user to database
    public void signUpUser(User user) {
        // Create SQL statement
        String insert = "INSERT INTO " + Const.TABLE_USERS + "(" + Const.USERS_FIRSTNAME + "," +
                        Const.USERS_LASTNAME + "," + Const.USERS_USERNAME + "," + Const.USERS_PASSWORD  +"," +
                        Const.USERS_GENDER + "," + Const.USERS_LOCATION + ")" + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            // Populate statement
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, encryptString(user.getPassword()));
            preparedStatement.setString(5, user.getGender());
            preparedStatement.setString(6, user.getLocation());

            // Execute statement
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Add new task to database
    public void insertTask(Task task) {
        // Create SQL statement
        String insert = "INSERT INTO " + Const.TABLE_TASKS + "("  +  Const.USERS_ID + "," + Const.TASKS_TASK + "," +
                        Const.TASKS_DATE + "," + Const.TASKS_DESCRIPTION + ")" + "VALUES (?, ?, ?, ?)";

        try {
            // Populate statement
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

            preparedStatement.setInt(1, task.getUserId());
            preparedStatement.setString(2, task.getTask());
            preparedStatement.setTimestamp(3, task.getDateCreated());
            preparedStatement.setString(4, task.getDescription());

            // Execute statement
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* Read */
    // Get user from database
    public ResultSet getUser(User user) {
        ResultSet resultSet = null;

        // Check a name and password has been supplied
        if (!user.getUsername().equals("") || !user.getPassword().equals("")) {
            // Create SQL statement
            String query = "SELECT * FROM " + Const.TABLE_USERS + " WHERE " +
                            Const.USERS_USERNAME + "= ?" + " AND " + Const.USERS_PASSWORD + " = ?";

            try {
                // Populate statement
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, encryptString(user.getPassword()));

                // Execute statement
                resultSet = preparedStatement.executeQuery();

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        return resultSet;
    }

    // Return all tasks for a specific user
    public ResultSet getTasksByUser(int userId) {
        ResultSet resultSet = null;

        // Create SQL statement
        String query = "SELECT * FROM " + Const.TABLE_TASKS + " WHERE " + Const.USERS_ID + "= ?";

        try {
            // Populate statement
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setInt(1, userId);

            // Execute statement
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    // Return all usernames in database
    public ResultSet getUsernames () {
        ResultSet resultSet = null;

        // Create SQL statement
        String query = "SELECT " + Const.USERS_USERNAME + " FROM " + Const.TABLE_USERS;

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            // Execute statement
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    /* Update */
    // Update task already in database
    public void updateTask(Task task) {
        // Create SQL statement
        String query = "UPDATE " + Const.TABLE_TASKS + " SET " + Const.TASKS_TASK + "=?, " +
                        Const.TASKS_DESCRIPTION + "=?" + " WHERE " + Const.TASKS_ID + "=?";

        try {
            // Populate statement
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setString(1, task.getTask());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setInt(3, task.getTaskId());

            // Execute statement
            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* Delete */
    // Remove task from database
     public void deleteTask(int userId, int taskId) {
         // Create SQL statement
        String query = "DELETE FROM " + Const.TABLE_TASKS + " WHERE " + Const.USERS_ID + "=?" +
                                                                " AND " + Const.TASKS_ID + "=?";

        try {
            // Populate statement
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, taskId);

            // Execute statement
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
