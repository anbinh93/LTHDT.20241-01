package controller;

import javax.swing.*;

import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.event.*;

public class ForceSimulationAppController {
	@FXML
	private Button btnCredits;
	@FXML
	private Button btnStart;
	@FXML
	private Button btnExit;
	
	public static void creditsWindow() {
		JFrame frame = new JFrame("Credits");
		
		JLabel labels = new JLabel("<html>" +
				"Danh sách các thành viên Nhóm 1:" + "<br>" +
				"Nguyễn Bình An - 20225591" + "<br>" +
				"Hoàng Tố An - 20214980" + "<br>" +
				"Cao Đức Anh - 20225781" + "<br>" +
				"Đặng Hải Anh - 20225688" + "<br>" +
				"Đinh Đức Anh - 20225782" + "<br>" +"</html>");
		frame.getContentPane().add(labels);
		
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	}
	
	@FXML
	void btnCreditsPressed(ActionEvent e) {
		creditsWindow();
	}
	
	@FXML
	void btnExitPressed(ActionEvent e) {
		Platform.exit();
	}

}
