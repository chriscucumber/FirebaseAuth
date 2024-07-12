package com.example.csc325_firebase_webview_auth.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private final StringProperty id = new SimpleStringProperty(this, "id", "");
    private final StringProperty firstName = new SimpleStringProperty(this, "firstName", "");
    private final StringProperty lastName = new SimpleStringProperty(this, "lastName", "");
    private final StringProperty department = new SimpleStringProperty(this, "department", "");
    private final StringProperty major = new SimpleStringProperty(this, "major", "");
    private final StringProperty email = new SimpleStringProperty(this, "email", "");

    public Student(String id, String firstName, String lastName, String department, String major, String email) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setDepartment(department);
        setMajor(major);
        setEmail(email);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String value) {
        id.set(value);
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String value) {
        firstName.set(value);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String value) {
        lastName.set(value);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getDepartment() {
        return department.get();
    }

    public void setDepartment(String value) {
        department.set(value);
    }

    public StringProperty departmentProperty() {
        return department;
    }

    public String getMajor() {
        return major.get();
    }

    public void setMajor(String value) {
        major.set(value);
    }

    public StringProperty majorProperty() {
        return major;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String value) {
        email.set(value);
    }

    public StringProperty emailProperty() {
        return email;
    }
}
