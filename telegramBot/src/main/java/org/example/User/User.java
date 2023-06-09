package org.example.User;

public class User {
    private String id;
    private UserStatus status;
    private String firstname;
    private String lastname;

    public User(String id, UserStatus status, String firstname, String lastname) {
        this.id = id;
        this.status = status;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
