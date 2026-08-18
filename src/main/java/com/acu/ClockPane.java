package com.acu;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import java.time.LocalTime;


public class ClockPane extends BorderPane {
    private final Pane clockCanvas = new Pane();
    private final Timeline timeline;

    // Proper bindable JavaFX properties for state & encapsulation
    private final IntegerProperty hour = new SimpleIntegerProperty();
    private final IntegerProperty minute = new SimpleIntegerProperty();
    private final IntegerProperty second = new SimpleIntegerProperty();
    private final BooleanProperty running = new SimpleBooleanProperty(true);
    private final BooleanProperty darkMode = new SimpleBooleanProperty(false);

    public ClockPane() {
        // Set up the canvas where the clock is drawn
        clockCanvas.setPrefSize(400, 400);
        setCenter(clockCanvas);

        // Bottom controls: Start, Stop, Dark Mode Toggle
        Button startBtn = new Button("Start");
        Button stopBtn = new Button("Stop");
        ToggleButton themeToggle = new ToggleButton("Dark Mode");
        HBox controls = new HBox(10, startBtn, stopBtn, themeToggle);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(10));
        setBottom(controls);

        // Initialize state properties to current time
        setCurrentTime();

        // Timeline to update properties every second
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> setCurrentTime()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Event handlers to update the running property and let bindings/listeners control the timeline
        startBtn.setOnAction(e -> setRunning(true));
        stopBtn.setOnAction(e -> setRunning(false));
        
        // Listen to runningProperty to play/stop timeline
        running.addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                timeline.play();
            } else {
                timeline.stop();
            }
        });

        // Theme Toggle action
        themeToggle.setOnAction(e -> setDarkMode(themeToggle.isSelected()));

        // Listeners for properties to trigger redraw automatically
        hour.addListener((obs, oldVal, newVal) -> drawClock());
        minute.addListener((obs, oldVal, newVal) -> drawClock());
        second.addListener((obs, oldVal, newVal) -> drawClock());
        darkMode.addListener((obs, oldVal, newVal) -> drawClock());

        // Redraw clock when the pane is resized (responsive UI)
        clockCanvas.widthProperty().addListener((obs, oldVal, newVal) -> drawClock());
        clockCanvas.heightProperty().addListener((obs, oldVal, newVal) -> drawClock());
    }

    // Set properties to the current system time.
     
    private void setCurrentTime() {
        LocalTime now = LocalTime.now();
        setHour(now.getHour());
        setMinute(now.getMinute());
        setSecond(now.getSecond());
    }

    // Draw the analog clock (hands, tick marks, background).
    
    private void drawClock() {
        clockCanvas.getChildren().clear();
        double w = clockCanvas.getWidth();
        double h = clockCanvas.getHeight();

        // Avoid drawing if size is invalid
        if (w <= 0 || h <= 0) return;

        double radius = Math.min(w, h) * 0.4;
        double centerX = w / 2;
        double centerY = h / 2;

        boolean isDark = isDarkMode();

        // 1. Circle for the clock face
        Circle face = new Circle(centerX, centerY, radius);
        face.setFill(isDark ? Color.web("#2d3748") : Color.WHITE);
        face.setStroke(isDark ? Color.WHITE : Color.BLACK);
        face.setStrokeWidth(3);
        clockCanvas.getChildren().add(face);

        // 2. Draw 60 Tick Marks (Part E requirement)
        for (int i = 0; i < 60; i++) {
            double angle = i * (2 * Math.PI / 60);
            double r1;
            double strokeWidth;
            Color tickColor;

            if (i % 5 == 0) {
                r1 = radius * 0.88; // Major hour tick
                strokeWidth = 2.5;
                tickColor = isDark ? Color.WHITE : Color.BLACK;
            } else {
                r1 = radius * 0.94; // Minor minute tick
                strokeWidth = 1.0;
                tickColor = isDark ? Color.LIGHTGRAY : Color.GRAY;
            }

            double x1 = centerX + r1 * Math.sin(angle);
            double y1 = centerY - r1 * Math.cos(angle);
            double x2 = centerX + radius * Math.sin(angle);
            double y2 = centerY - radius * Math.cos(angle);

            Line tick = new Line(x1, y1, x2, y2);
            tick.setStroke(tickColor);
            tick.setStrokeWidth(strokeWidth);
            clockCanvas.getChildren().add(tick);
        }

        // Calculate hand positions
        double sVal = getSecond();
        double mVal = getMinute() + sVal / 60.0;
        double hVal = (getHour() % 12) + mVal / 60.0;

        // 3. Second hand (Red line)
        double sLength = radius * 0.8;
        double sX = centerX + sLength * Math.sin(sVal * (2 * Math.PI / 60));
        double sY = centerY - sLength * Math.cos(sVal * (2 * Math.PI / 60));
        Line sLine = new Line(centerX, centerY, sX, sY);
        sLine.setStroke(Color.RED);
        sLine.setStrokeWidth(1.5);

        // 4. Minute hand (Blue/Cyan line)
        double mLength = radius * 0.65;
        double mX = centerX + mLength * Math.sin(mVal * (2 * Math.PI / 60));
        double mY = centerY - mLength * Math.cos(mVal * (2 * Math.PI / 60));
        Line mLine = new Line(centerX, centerY, mX, mY);
        mLine.setStroke(isDark ? Color.CYAN : Color.DARKBLUE);
        mLine.setStrokeWidth(2.5);

        // 5. Hour hand (Black/White line)
        double hLength = radius * 0.5;
        double hX = centerX + hLength * Math.sin(hVal * (2 * Math.PI / 12));
        double hY = centerY - hLength * Math.cos(hVal * (2 * Math.PI / 12));
        Line hLine = new Line(centerX, centerY, hX, hY);
        hLine.setStroke(isDark ? Color.WHITE : Color.BLACK);
        hLine.setStrokeWidth(4);

        // 6. Center dot (Red circle)
        Circle centerDot = new Circle(centerX, centerY, 5, Color.RED);

        clockCanvas.getChildren().addAll(sLine, mLine, hLine, centerDot);
    }

    // JavaFX Properties Getters, Setters, and Property Exposures 
    public int getHour() { return hour.get(); }
    public void setHour(int h) { hour.set(h); }
    public IntegerProperty hourProperty() { return hour; }

    public int getMinute() { return minute.get(); }
    public void setMinute(int m) { minute.set(m); }
    public IntegerProperty minuteProperty() { return minute; }

    public int getSecond() { return second.get(); }
    public void setSecond(int s) { second.set(s); }
    public IntegerProperty secondProperty() { return second; }

    public boolean isRunning() { return running.get(); }
    public void setRunning(boolean r) { running.set(r); }
    public BooleanProperty runningProperty() { return running; }

    public boolean isDarkMode() { return darkMode.get(); }
    public void setDarkMode(boolean d) { darkMode.set(d); }
    public BooleanProperty darkModeProperty() { return darkMode; }
}
