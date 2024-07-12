package com.example.csc325_firebase_webview_auth.view;

import com.example.csc325_firebase_webview_auth.model.FirestoreContext;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class App extends Application {

    public static Firestore fstore;
    public static FirebaseAuth fauth;
    public static Scene scene;
    private final FirestoreContext contxtFirebase = new FirestoreContext();
    private SplashScreen splashScreen;
    private final ObservableList<Student> studentList = FXCollections.observableArrayList();

    public void init() throws Exception {
        Platform.runLater(() -> {
            splashScreen = new SplashScreen();
            splashScreen.getSplashStage().show();
        });
        Thread.sleep(3000);
    }

    @Override
    public void start(Stage primaryStage) {
        if (splashScreen != null) {
            splashScreen.getSplashStage().hide();
        }

        fstore = contxtFirebase.firebase();
        fauth = FirebaseAuth.getInstance();
        BorderPane root = new BorderPane();

        // Menu bar
        MenuBar menuBar = new MenuBar();
        Account account = new Account(fauth);
        Menu accountMenu = new Menu("Account");
        MenuItem loginRegisterItem = new MenuItem("Login or Register");
        loginRegisterItem.setOnAction(e -> account.showPopup());
        accountMenu.getItems().add(loginRegisterItem);

        Menu fileMenu = new Menu("File");
        MenuItem newItem = new MenuItem("New");
        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");
        fileMenu.getItems().addAll(newItem, openItem, saveItem);

        Menu editMenu = new Menu("Edit");
        MenuItem cutItem = new MenuItem("Cut");
        MenuItem copyItem = new MenuItem("Copy");
        MenuItem pasteItem = new MenuItem("Paste");
        editMenu.getItems().addAll(cutItem, copyItem, pasteItem);

        Menu themeMenu = new Menu("Theme");
        MenuItem lightItem = new MenuItem("Light");
        MenuItem darkItem = new MenuItem("Dark");
        themeMenu.getItems().addAll(lightItem, darkItem);

        Menu helpMenu = new Menu("Help");
        MenuItem aboutItem = new MenuItem("About");
        helpMenu.getItems().add(aboutItem);

        menuBar.getMenus().addAll(fileMenu, accountMenu, editMenu, themeMenu, helpMenu);
        root.setTop(menuBar);

        // Left sidebar
        VBox leftSidebar = new VBox();
        leftSidebar.setPrefWidth(150);
        leftSidebar.getStyleClass().add("left-sidebar");

        if(getClass().getResourceAsStream("/20079.png") != null) {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/20079.png")));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(130);
            imageView.setFitHeight(130);
            imageView.setPreserveRatio(true);
            VBox.setMargin(imageView, new Insets(10));
            leftSidebar.getChildren().add(imageView);
        }
        root.setLeft(leftSidebar);

        // Center table
        TableView<Student> tableView = new TableView<>(studentList);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Student, String> idCol = new TableColumn<>("ID");
        TableColumn<Student, String> firstNameCol = new TableColumn<>("First Name");
        TableColumn<Student, String> lastNameCol = new TableColumn<>("Last Name");
        TableColumn<Student, String> departmentCol = new TableColumn<>("Department");
        TableColumn<Student, String> majorCol = new TableColumn<>("Major");
        TableColumn<Student, String> emailCol = new TableColumn<>("Email");

        idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        firstNameCol.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameCol.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        departmentCol.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
        majorCol.setCellValueFactory(cellData -> cellData.getValue().majorProperty());
        emailCol.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        tableView.getColumns().addAll(idCol, firstNameCol, lastNameCol, departmentCol, majorCol, emailCol);
        root.setCenter(tableView);

        // Right sidebar
        VBox rightSidebar = new VBox(10);
        rightSidebar.getStyleClass().add("right-sidebar");

        TextField idField = new TextField();
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField departmentField = new TextField();
        TextField majorField = new TextField();
        TextField emailField = new TextField();

        idField.setPromptText("ID");
        firstNameField.setPromptText("First Name");
        lastNameField.setPromptText("Last Name");
        departmentField.setPromptText("Department");
        majorField.setPromptText("Major");
        emailField.setPromptText("Email");

        VBox textFields = new VBox(10);
        textFields.getChildren().addAll(
                idField, firstNameField, lastNameField, departmentField, majorField, emailField
        );

        Button clearBtn = new Button("Clear");
        Button addBtn = new Button("Add");
        Button deleteBtn = new Button("Delete");
        Button editBtn = new Button("Edit");

        clearBtn.setOnAction(e -> clearFields(idField, firstNameField, lastNameField, departmentField, majorField, emailField));
        addBtn.setOnAction(e -> {
            String id = idField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String department = departmentField.getText();
            String major = majorField.getText();
            String email = emailField.getText();

            Student student = new Student(id, firstName, lastName, department, major, email);
            studentList.add(student);
            clearFields(idField, firstNameField, lastNameField, departmentField, majorField, emailField);
        });
        deleteBtn.setOnAction(e -> {
            Student selectedStudent = tableView.getSelectionModel().getSelectedItem();
            if (selectedStudent != null) {
                studentList.remove(selectedStudent);
                clearFields(idField, firstNameField, lastNameField, departmentField, majorField, emailField);
            }
        });

        editBtn.setOnAction(e -> {
            Student selectedStudent = tableView.getSelectionModel().getSelectedItem();
            if (selectedStudent != null) {
                selectedStudent.setId(idField.getText());
                selectedStudent.setFirstName(firstNameField.getText());
                selectedStudent.setLastName(lastNameField.getText());
                selectedStudent.setDepartment(departmentField.getText());
                selectedStudent.setMajor(majorField.getText());
                selectedStudent.setEmail(emailField.getText());

                tableView.refresh();
                clearFields(idField, firstNameField, lastNameField, departmentField, majorField, emailField);
            }
        });

        VBox buttons = new VBox(10);
        buttons.getChildren().addAll(clearBtn, addBtn, deleteBtn, editBtn);
        buttons.setSpacing(10);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        rightSidebar.getChildren().addAll(textFields, spacer, buttons);
        root.setRight(rightSidebar);

        // Bottom bar
        HBox bottomBar = new HBox();
        bottomBar.setMinHeight(20);
        bottomBar.getStyleClass().add("grey-box");
        root.setBottom(bottomBar);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("styles.css");

        primaryStage.setTitle("FSC CSC325");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Add default data
        studentList.addAll(
                new Student("1", "Chris", "C", "CS", "CS", "carrcj7@farmingdale.edu")
        );
    }

    // @dev Clears the text fields
    // Used to clear text after an action is done
    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}