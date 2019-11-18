package sample.model;

public class User {

    private String firstName;

    private String lastName;
    private String username;
    private String password;
    private String gender;
    private String location;

    // Used to create blank user
    public User () {

    }

    // Used if all attributes are known at instantiation
    public User(String firstName, String lastName, String username, String password, String gender, String location) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.location = location;
    }

    // Return first name
    public String getFirstName() {
        return firstName;
    }

    // Set first name
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Return last name
    public String getLastName() {
        return lastName;
    }

    // Set last name
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Return username
    public String getUsername() {
        return username;
    }

    // Set username
    public void setUsername(String username) {
        this.username = username;
    }

    // Return password
    public String getPassword() {
        return password;
    }

    // Set password
    public void setPassword(String password) {
        this.password = password;
    }

    // Return gender
    public String getGender() {
        return gender;
    }

    // Set gender
    public void setGender(String gender) {
        this.gender = gender;
    }

    // Return location
    public String getLocation() {
        return location;
    }

    // Set location
    public void setLocation(String location) {
        this.location = location;
    }


}
