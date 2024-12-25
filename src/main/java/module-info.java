module hedspi.group01.force {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens hedspi.group01.force.controller to javafx.fxml;
    exports hedspi.group01.force;

    // Set the main class
    uses hedspi.group01.force.Main;
}
