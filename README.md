# Student Information
- **Full Name:** Vuong Trung Khang Tang
- **Student ID:** S00430964
- **Class ID:** ITEC621

# FX Mini Studio (JavaFX Implementation Lab)

This repository contains a completed, polished JavaFX application called **FX Mini Studio** which implements all requirements from the **Lab 1 Assignment**.

## Technical Stack & Compatibility
* **Java Development Kit (JDK):** Version 17 or higher (tested on JDK 17 and JDK 21).
* **JavaFX SDK:** Version 21.0.1 or higher.
* **Build System:** Apache Maven (or Gradle/IDE-native).

---

## Key Features & Lab Alignment

### 1. App Navigation & Window Lifecycle (Part A)
* Extends `javafx.application.Application` and launches via a dedicated bootstrap main class (`Main.java`) to bypass standard modular/classpath issues on modern IDEs.
* Implements a desktop window of standard size **900x650** with a strict minimum window constraint of **800x600** to maintain a clean responsive UI layout.
* Includes a top global `MenuBar` with a `Help -> About` menu option opening a modal `Alert` showing student metadata.

### 2. Layout Playground (Part B)
* **BorderPane Configuration:**
  * **Top (HBox):** Contains an input `TextField`, a labeling text `Text:`, and an "Add to ListView" `Button` which appends the field values into the list.
  * **Center (ListView):** Scrollable `ListView<String>` with constrained preferred heights to preserve UI balance during resizing.
  * **Right (VBox):** Custom combo box for selection (`Theme A`, `Theme B`, `Theme C`) and a checkbox labeled `Enable Feature`.
* **GridPane Form (2x2):** Features structured labels and input fields ("Full Name" and "Email") aligned with a "Submit" button that outputs data to the system console.
* **FlowPane Wrap Area:** Automatically repositions and wraps a list of 8 buttons when the window undergoes physical horizontal resizing.

### 3. Graphics & Properties Binding (Part C)
* **Centered Group & Circle:** Features a graphical `Circle` and a radial `Line` visual indicator housed within a custom `Group` inside a `Pane` container.
* **Property Centering:** Uses bidirectional and mathematical bindings to dynamically link the `Group`'s layout coordinates to the center of the resizable canvas width and height properties.
* **Unidirectional Binding:** Binds the `radiusProperty` of the circle and the line's endpoint `endXProperty` directly to a "Radius" `Slider` value property (ranges 10 to 100).
* **Rotation Binding:** Binds the `rotateProperty` of the circle's container `Group` to a "Rotation" `Slider` (0 to 360 degrees), resolving the static appearance of simple solid circles.
* **Timeline Animation:** An interactive "Pulse (Scale Animation)" button executes a multi-cycle `ScaleTransition` that smoothly expands and contracts the graphical circle.
* **Bidirectional Binding:** Connects a "Caption" input text field to a bold header label, where updating either input instantly pushes modifications to the other.
* **Style Manipulation:** Employs a JavaFX `ColorPicker` to update the graphical node fill color through explicit inline `-fx-fill` CSS modification.

### 4. Image Loading & Custom Fonts (Part D)
* Demonstrates modular asset loading using Java resource streams (`getClass().getResourceAsStream()`).
* **Resizing & Preservation:** Binds the image's `fitWidthProperty` to a layout slider while calling `.setPreserveRatio(true)` to avoid structural squishing.
* **Fault-Tolerant Loading:** Wraps image stream construction inside robust try-catch blocks and automatically triggers a warning `Alert` dialog suggesting the correct filepath (`src/main/resources/images/`) if the resource is missing.
* **Custom Typography:** Sets headers to a specialized styled "Georgia" serif font in bold to contrast standard system sans-serif labels.

### 5. Reusable ClockPane Widget (Part E)
* Encapsulates an analog clock within a modular subclass (`ClockPane.java` extending `BorderPane`) that can be instantiated and placed in any standard JavaFX layout.
* **Proper JavaFX Properties:** Exposes bindable properties (`hourProperty()`, `minuteProperty()`, `secondProperty()`, `runningProperty()`, and `darkModeProperty()`) alongside corresponding getters and setters.
* **Mathematical Face Mapping:** Translates clock hand coordinate angles using polar trigonometry driven by property bindings.
* **Detailed Face Decoration:** Implements a dynamic drawing loop that generates **60 distinctive tick marks** (longer, thicker marks for hours; thinner, smaller marks for minutes) utilizing JavaFX lines.
* **Responsive Layout:** Listens to pane size changes to recalculate the clock circle and ticks dynamically on window resize.
* **Controls & Theming:** Exposes "Start" and "Stop" controls which update the clock's `running` state (binding directly to a 1Hz clock timeline) and includes a "Dark Mode" theme toggle swapping the face CSS classes and fill properties from light to dark.

---

## Build and Run Steps

### Option 1: Command Line (Maven) - Recommended
To build and run the application on Mac (VSCode/Terminal) using Maven:
1. Ensure **Apache Maven** and **JDK 17+** are installed and mapped to your system `PATH`.
2. Open a terminal and navigate to the root folder (where `pom.xml` resides).
3. **Compile the project:**
   ```bash
   mvn clean compile
   ```
4. **Run the JavaFX application:**
   ```bash
   mvn javafx:run
   ```

### Option 2: VS Code (with Java Extensions)
1. Install the **Extension Pack for Java** and **JavaFX Support** extensions from Microsoft.
2. Ensure you are using JDK 17+ in your VS Code settings.
3. Open the root folder of this project in VS Code.
4. VS Code will automatically resolve the Maven dependencies.
5. Open the `Main.java` file and click the **Run** button above the main method (or press `F5` / `Ctrl+F5`).

---

## Resources & Image Attributions
1. **Kung Fu Panda Image (`icon.png`):** The image viewer displays a placeholder image from DreamWorks' "Kung Fu Panda", loaded locally via classpath from `src/main/resources/images/icon.png`.
2. **JavaFX Documentation:** JavaFX API JavaDocs (Oracle/OpenJFX) were referenced for layouts and bindable property structures.
