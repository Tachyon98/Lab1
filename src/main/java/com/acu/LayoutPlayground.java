package com.acu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class LayoutPlayground extends ScrollPane {

    public LayoutPlayground() {
        // Main Container containing all sections
        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(15));

        // 1. BorderPane Section
        BorderPane borderSection = new BorderPane();
        borderSection.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-padding: 10;");

        // Top: HBox (Label + TextField + Button)
        HBox topHBox = new HBox(10);
        topHBox.setAlignment(Pos.CENTER_LEFT);
        topHBox.setPadding(new Insets(0, 0, 10, 0));
        
        Label inputLabel = new Label("Text:");
        TextField inputField = new TextField();
        Button addButton = new Button("Add to ListView");
        topHBox.getChildren().addAll(inputLabel, inputField, addButton);
        borderSection.setTop(topHBox);

        // Center: ListView
        ListView<String> listView = new ListView<>();
        borderSection.setCenter(listView);

        // Handler for the Add button 
        addButton.setOnAction(e -> {
            String text = inputField.getText().trim();
            if (!text.isEmpty()) {
                listView.getItems().add(text);
                inputField.clear();
            }
        });

        // Right: VBox (ComboBox + CheckBox)
        VBox rightVBox = new VBox(10);
        rightVBox.setPadding(new Insets(0, 0, 0, 10));
        
        ComboBox<String> themeCombo = new ComboBox<>();
        themeCombo.getItems().addAll("Theme A", "Theme B", "Theme C");
        themeCombo.getSelectionModel().selectFirst();

        CheckBox featureCheckBox = new CheckBox("Enable Feature");
        rightVBox.getChildren().addAll(new Label("Options:"), themeCombo, featureCheckBox);
        borderSection.setRight(rightVBox);

        // 2. GridPane Area Section (Form 2x2)
        TitledPane gridSection = new TitledPane();
        gridSection.setText("GridPane Area (Form 2x2)");
        gridSection.setCollapsible(false);

        GridPane gridForm = new GridPane();
        gridForm.setHgap(10);
        gridForm.setVgap(10);
        gridForm.setPadding(new Insets(10));

        TextField nameField = new TextField();
        TextField emailField = new TextField();
        Button submitBtn = new Button("Submit (Send)");

        // Organise nodes in a grid layout: add(node, column, row)
        gridForm.add(new Label("Full Name:"), 0, 0);
        gridForm.add(nameField, 1, 0);
        gridForm.add(new Label("Email:"), 0, 1);
        gridForm.add(emailField, 1, 1);
        gridForm.add(submitBtn, 1, 2);

        // Print values to Console when Submit button is clicked
        submitBtn.setOnAction(e -> {
            System.out.println("Submit data -> Name: " + nameField.getText() + " | Email: " + emailField.getText());
        });
        gridSection.setContent(gridForm);

        // 3. FlowPane Area Section (Buttons auto-wrap)
        TitledPane flowSection = new TitledPane();
        flowSection.setText("FlowPane Area (Buttons auto-wrap)");
        flowSection.setCollapsible(false);

        FlowPane flowPane = new FlowPane(10, 10);
        flowPane.setPadding(new Insets(10));
        for (int i = 1; i <= 8; i++) {
            flowPane.getChildren().add(new Button("Button " + i));
        }
        flowSection.setContent(flowPane);

        // Wrap all sections in a ScrollPane
        mainContainer.getChildren().addAll(
            new Label("Section 1: BorderPane (HBox on Top, ListView in Center, VBox on Right)"),
            borderSection,
            gridSection,
            flowSection
        );

        this.setContent(mainContainer);
        this.setFitToWidth(true);
    }
}