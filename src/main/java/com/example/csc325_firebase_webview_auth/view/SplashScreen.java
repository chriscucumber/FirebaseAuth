package com.example.csc325_firebase_webview_auth.view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashScreen {
    private final Stage splashStage;
    private final Scene splashScene;

    public SplashScreen() {
        splashStage = new Stage();

        VBox root = new VBox();
        root.setStyle("-fx-background-color: white; -fx-padding: 20;");

        // Load and display the logo
        Image logo = new Image(getClass().getResourceAsStream("/splashscreen.png"));
        ImageView imageView = new ImageView(logo);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);

        root.getChildren().add(imageView);

        splashScene = new Scene(root);
        splashStage.initStyle(StageStyle.UNDECORATED);
        splashStage.setScene(splashScene);
    }

    // returns accessor for stage and scene
    public Scene getSplashScene() {
        return splashScene;
    }
    public Stage getSplashStage() {
        return splashStage;
    }
}