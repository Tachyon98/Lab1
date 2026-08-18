package com.acu;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import java.io.InputStream;

public class GraphicsBinding extends ScrollPane {
    public GraphicsBinding() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(15));

        // 1. Part C: Shapes, Rotation & Centering
        Pane canvasPane = new Pane();
        canvasPane.setPrefSize(400, 220);
        canvasPane.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #cccccc; -fx-border-radius: 5;");

        // Wrap Circle and Line in a Group so rotation is VISIBLE
        Group circleGroup = new Group();
        Circle circle = new Circle(0, 0, 50, Color.DODGERBLUE);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(1);

        // Visual indicator for rotation
        Line radialLine = new Line(0, 0, 50, 0);
        radialLine.setStroke(Color.WHITE);
        radialLine.setStrokeWidth(3);

        circleGroup.getChildren().addAll(circle, radialLine);

        // Binding group layout to stay centered in the canvasPane
        circleGroup.layoutXProperty().bind(canvasPane.widthProperty().divide(2));
        circleGroup.layoutYProperty().bind(canvasPane.heightProperty().divide(2));
        canvasPane.getChildren().add(circleGroup);

        Slider radiusSlider = new Slider(10, 100, 50);
        // Unidirectional binding for circle's radius and radialLine's length
        circle.radiusProperty().bind(radiusSlider.valueProperty());
        radialLine.endXProperty().bind(radiusSlider.valueProperty());

        Slider rotateSlider = new Slider(0, 360, 0);
        // Rotation binding
        circleGroup.rotateProperty().bind(rotateSlider.valueProperty());

        Button pulseBtn = new Button("Pulse (Scale Animation)");
        pulseBtn.setOnAction(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(300), circleGroup);
            st.setByX(0.4); 
            st.setByY(0.4);
            st.setAutoReverse(true); 
            st.setCycleCount(2);
            st.play();
        });

        ColorPicker colorPicker = new ColorPicker(Color.DODGERBLUE);
        colorPicker.setOnAction(e -> {
            String hexColor = colorPicker.getValue().toString().substring(2, 8);
            circle.setStyle("-fx-fill: #" + hexColor + ";");
        });

        GridPane circleControls = new GridPane();
        circleControls.setHgap(10); 
        circleControls.setVgap(10);
        circleControls.add(new Label("Radius:"), 0, 0);
        circleControls.add(radiusSlider, 1, 0);
        circleControls.add(new Label("Rotate:"), 0, 1);
        circleControls.add(rotateSlider, 1, 1);
        circleControls.add(new Label("Actions & Styling:"), 0, 2);
        circleControls.add(new HBox(10, pulseBtn, colorPicker), 1, 2);

        TitledPane shapeSection = new TitledPane("1. Shapes, Rotation & Unidirectional Binding",
                new VBox(10, canvasPane, circleControls));
        shapeSection.setCollapsible(false);

        // 2. Part C: Bidirectional Binding
        TextField captionField = new TextField("Enter text here...");
        Label captionLabel = new Label();
        captionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        // Bidirectional binding
        captionLabel.textProperty().bindBidirectional(captionField.textProperty());

        HBox bindingBox = new HBox(15, new Label("Caption (TextField):"), captionField,
                new Label("Display (Label):"), captionLabel);
        bindingBox.setAlignment(Pos.CENTER_LEFT);
        bindingBox.setPadding(new Insets(10));
        TitledPane bindingSection = new TitledPane("2. Bidirectional Binding Demonstration", bindingBox);
        bindingSection.setCollapsible(false);

        // 3. Part D: Images & Custom Fonts
        VBox imageBox = new VBox(10);
        imageBox.setPadding(new Insets(10));

        // Custom font for header label
        Label headerLabel = new Label("Image Viewer & Resizing (Part D)");
        headerLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        headerLabel.setStyle("-fx-text-fill: #2c3e50;");

        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        Slider imageWidthSlider = new Slider(50, 400, 200);
        // Binding the ImageView's fitWidth to the slider's value
        imageView.fitWidthProperty().bind(imageWidthSlider.valueProperty());

        // Try/catch block to load image from resources robustly
        try {
            InputStream imgStream = getClass().getResourceAsStream("/images/icon.png");
            if (imgStream == null) {
                throw new Exception("Cannot find file /images/icon.png in the resources directory!");
            }
            Image img = new Image(imgStream);
            imageView.setImage(img);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning: Image Not Found");
            alert.setHeaderText("Cannot find image file!");
            alert.setContentText(e.getMessage() + "\nPlease ensure you have placed icon.png in src/main/resources/images/");
            alert.showAndWait();
        }

        HBox sliderBox = new HBox(10, new Label("Fit Width:"), imageWidthSlider);
        sliderBox.setAlignment(Pos.CENTER_LEFT);
        imageBox.getChildren().addAll(headerLabel, sliderBox, imageView);

        TitledPane imageSection = new TitledPane("3. Images & Custom Fonts (Part D)", imageBox);
        imageSection.setCollapsible(false);

        // Add all sections to the main container
        container.getChildren().addAll(shapeSection, bindingSection, imageSection);
        this.setContent(container);
        this.setFitToWidth(true);
    }
}
