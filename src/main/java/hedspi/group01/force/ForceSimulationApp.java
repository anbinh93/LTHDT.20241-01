package hedspi.group01.force;

import java.net.URL;

import hedspi.group01.force.controller.ForceSimulationAppController;
import hedspi.group01.force.model.Simulation;
import hedspi.group01.force.model.surface.Surface;
import hedspi.group01.force.model.vector.AppliedForce;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ForceSimulationApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Name for the application
        final String appName = "Force Simulation App";
        primaryStage.setTitle(appName);

        // Debug logging
        URL iconUrl = ForceSimulationApp.class.getResource("/hedspi/group01/force/images/app_icon.png");
        // System.out.println("Icon URL: " + iconUrl);
        URL fxmlUrl = ForceSimulationApp.class.getResource("/hedspi/group01/force/view/RootLayout.fxml");
        // System.out.println("FXML URL: " + fxmlUrl);
        URL cssUrl = ForceSimulationApp.class.getResource("/hedspi/group01/force/css/sliderStyle.css");
        // System.out.println("CSS URL: " + cssUrl);

        // Set icon for the application
        primaryStage.getIcons().add(new Image(ForceSimulationApp.class.getResourceAsStream("/hedspi/group01/force/images/app_icon.png")));

        // Set minimum windows size to avoid unusual look
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(850);

        // Load view for the stage
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ForceSimulationApp.class.getResource("/hedspi/group01/force/view/RootLayout.fxml"));
        Parent rootLayout = loader.load();
        Scene scene = new Scene(rootLayout);
        
        // Add CSS files
        scene.getStylesheets().add(ForceSimulationApp.class.getResource("/hedspi/group01/force/css/sliderStyle.css").toExternalForm());
        scene.getStylesheets().add(ForceSimulationApp.class.getResource("/hedspi/group01/force/css/errorTheme.css").toExternalForm());
        scene.getStylesheets().add(ForceSimulationApp.class.getResource("/hedspi/group01/force/css/checkboxStyle.css").toExternalForm());
        
        primaryStage.setScene(scene);

        // Load model for view through its controller
        ForceSimulationAppController appController = loader.getController();
        Simulation simul = new Simulation(null, new Surface(), new AppliedForce(0));
        appController.init(simul);

        // Display the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}