module hedspi.group01.force {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens hedspi.group01.force.controller to javafx.fxml;
    opens hedspi.group01.force to javafx.graphics;
    
    exports hedspi.group01.force;

    // Set the main class
    uses hedspi.group01.force.ForceSimulationApp;
}
