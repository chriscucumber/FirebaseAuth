package com.example.csc325_firebase_webview_auth.view;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Account {

    private FirebaseAuth fauth;

    public Account(FirebaseAuth _fauth) {
        fauth = _fauth;
    }

    // Show popup to login/register
    public void showPopup() {
        // Create modal
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Login/Register");

        // Create buttons
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        ButtonType registerButtonType = new ButtonType("Register", ButtonBar.ButtonData.OTHER);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, registerButtonType, ButtonType.CANCEL);

        // Styling
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // modal fields/labels
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        grid.add(new Label("Email:"), 0, 0);
        grid.add(emailField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Do different actions depending on register/login click
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                login(emailField.getText(), passwordField.getText());
            } else if (dialogButton == registerButtonType) {
                register(emailField.getText(), passwordField.getText());
            }
            return null;
        });

        dialog.showAndWait();
    }

    // Login user to their account
    private void login(String email, String password) {
        try {
            // Get user by their email
            UserRecord userRecord = fauth.getUserByEmail(email);

            // Login
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Successful");
            alert.setHeaderText(null);
            alert.setContentText("Welcome back, " + userRecord.getDisplayName());
            alert.showAndWait();

        } catch (FirebaseAuthException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login failed");
            alert.setHeaderText(null);
            alert.setContentText("Try again or you used the wrong password ");
            alert.showAndWait();
        }
    }

    // Create a new account based on input
    private void register(String email, String password) {
        System.out.println("Registration with email: " + email);
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password)
                .setEmailVerified(false);

        // fix bug where application thread or something
        Platform.runLater(() -> {
            try {
                UserRecord userRecord = fauth.createUser(request);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Register succeeded");
                alert.setHeaderText(null);
                alert.setContentText("Welcome, " + userRecord.getEmail());
                alert.showAndWait();
            } catch (FirebaseAuthException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Register failed");
                alert.setHeaderText(null);
                alert.setContentText("Try again");
                alert.showAndWait();
            }
        });

    }

}
