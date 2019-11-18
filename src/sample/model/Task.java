package sample.model;

import java.sql.Timestamp;

public class Task {

    private int taskId;
    private int userId;
    private String task;
    private String description;
    private Timestamp dateCreated;

    // Used to create an empty task
    public Task() {

    }

    // Used if all attributes are known at instantiation
    public Task(String task, String description, Timestamp dateCreated) {
        this.task = task;
        this.description = description;
        this.dateCreated = dateCreated;
    }

    // Return Task ID
    public int getTaskId() {
        return taskId;
    }

    // Set Task ID
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    // Return User ID
    public int getUserId() {
        return this.userId;
    }

    // Set User ID
    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Return Task Name
    public String getTask() {
        return task;
    }

    // Set Task Name
    public void setTask(String task) {
        this.task = task;
    }

    // Return Task Description
    public String getDescription() {
        return description;
    }

    // Set Task Description
    public void setDescription(String description) {
        this.description = description;
    }

    // Return Date Created
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    // Set Date Created
    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

}
