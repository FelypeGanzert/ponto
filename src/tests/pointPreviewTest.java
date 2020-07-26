package tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class pointPreviewTest extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {		

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/PointPreview.fxml"));
		
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
		
		primaryStage.setTitle("Ponto dos Funcionários");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
        root.requestFocus();
	}
	
	
	public static void main(String[] args) {
		launch(args);

	}

}
