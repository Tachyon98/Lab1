package com.acu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // 1. MenuBar with Help -> About
        MenuBar menuBar = new MenuBar();
        Menu helpMenu = new Menu("Help");
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.setOnAction(e -> showAboutAlert());
        helpMenu.getItems().add(aboutItem);
        menuBar.getMenus().add(helpMenu);
        root.setTop(menuBar);

        // 2. TabPane for Part B, C, E
        TabPane tabPane = new TabPane();
        
        Tab tabLayout = new Tab("Layout Playground", new LayoutPlayground());
        Tab tabGraphics = new Tab("Graphics & Binding", new GraphicsBinding());
        Tab tabClock = new Tab("Clock Widget", new ClockPane());

        tabPane.getTabs().addAll(tabLayout, tabGraphics, tabClock);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        root.setCenter(tabPane);

        // 3. Set application title & minimum window size
        Scene scene = new Scene(root, 900, 650);
        primaryStage.setTitle("FX Mini Studio");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Application info alert window
    private void showAboutAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About FX Mini Studio");
        alert.setHeaderText("FX Mini Studio v1.0");
        alert.setContentText("Lab 1 Exercise: JavaFX Basics\nStudent: [Student Name: Vuong Trung Khang Tang - Student ID: S00430964]");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}