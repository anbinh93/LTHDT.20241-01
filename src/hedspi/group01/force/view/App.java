package hedspi.group01.force.view;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application{
	
	private Stage mainStage;
	private BorderPane fg;
	private StackPane root;
	private HBox controlPanels = new HBox();
	
	@Override
	public void start(Stage stage) throws Exception{
		this.mainStage = stage;
		this.mainStage.setTitle("Newton's Laws of Physics Simulator");

		initRootLayout();
		initForeground();
		showPanels();
	}
	
	public void initRootLayout() {
		try {
			root = (StackPane) FXMLLoader.load(getClass().getResource("/resource/fxml/RootLayout.fxml"));
			
			Scene scene = new Scene(root, 600, 400);
			mainStage.setScene(scene);
			mainStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initForeground() {
		fg = (BorderPane) root.getChildren().get(1);
	}
	
	public void showPanels() {
		showAnimationPanel();
		showStatsPanel();
		showControlPanel();
		showHBoxPanels();
	}
	
	public void showStatsPanel() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/resource/fxml/StatisticsPanel.fxml"));
			AnchorPane statsPanel = (AnchorPane) loader.load();
		
			fg.setLeft(statsPanel);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showControlPanel() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/resource/fxml/ControlPanel.fxml"));
			AnchorPane controlPanel = (AnchorPane) loader.load();
		
			fg.setRight(controlPanel);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showHBoxPanels() {
		try {
			String[] panelName = {"Object", "Force", "Surface"};
			AnchorPane[] panel = new AnchorPane[3];
			
			for (int i = 0; i < 3; i++) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/resource/fxml/" + panelName[i] + "Panel.fxml"));
				panel[i] = (AnchorPane) loader.load();
			
				controlPanels.getChildren().add(i, panel[i]);
				fg.setBottom(controlPanels);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showAnimationPanel() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/resource/fxml/Animation.fxml"));
			Pane panel = (Pane) loader.load();
			
			fg.setCenter(panel);
			fg.getCenter().setLayoutX(-300);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Stage getMainStage() {
		return mainStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
