package application;

import db.DB;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.entites.Employee;
import view.AppController;

public class Main extends Application {
	
	private Stage primaryStage;
	private ObservableList<Employee> employeeData = FXCollections.observableArrayList();
	
	public Main() {
		DB db = new DB(employeeData);
		db.getData();
		db.saveData();
	}
	
	public ObservableList<Employee> getEmployeeData(){
		return employeeData;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/App.fxml"));
		
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		this.primaryStage.setTitle("Ponto dos Funcionários");
		this.primaryStage.setScene(scene);
		this.primaryStage.setResizable(false);
		this.primaryStage.show();
		
		AppController appController = loader.getController();
		appController.setEmployeeData(getEmployeeData());
		
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

}
