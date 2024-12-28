package hedspi.group01.force;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Load the image from resources
        Image image = new Image(getClass().getResourceAsStream("/hedspi/group01/force/images/app_icon.png"));
        ImageView imageView = new ImageView(image);

        // Set up the layout
        StackPane root = new StackPane();
        root.getChildren().add(imageView);

        // Set up the scene
        Scene scene = new Scene(root, 800, 600);

        // Set up the stage
        primaryStage.setTitle("Image Viewer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
