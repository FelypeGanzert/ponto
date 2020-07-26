package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class PointPreviewController implements Initializable {
	
	@FXML private TextField txtTest;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.txtTest.setStyle("-fx-border-color: #333;");		
	}
	
}
