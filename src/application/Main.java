package application;

import java.io.IOException;

import db.DB;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entites.Employee;
import model.entites.Holiday;
import view.AppController;
import view.EmployeeScheduleController;
import view.HolidayController;

public class Main extends Application {
	
	private Stage primaryStage;
	private static ObservableList<Employee> employeeData = FXCollections.observableArrayList();
	private static ObservableList<Holiday> holidays = FXCollections.observableArrayList();
	public static DB db;
	
	public Main() {
		Main.db = new DB(employeeData, holidays);
		db.getData();
	}
	
	public static ObservableList<Employee> getEmployeeData(){
		return employeeData;
	}
	
	public static ObservableList<Holiday> getHolidays(){
		return holidays;
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
        root.requestFocus();
		
		AppController appController = loader.getController();
		appController.setEmployeeData(getEmployeeData());
		appController.setHolidays(getHolidays());
		appController.setMainApp(this);
		
	}
	
	@Override
	public void stop() {
		db.saveData();
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public void showEmployeeSchedule(Employee employee) {
	    try {
	        // Carrega o arquivo fxml e cria um novo stage para a janela popup.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource("/view/EmployeeSchedule.fxml"));
	        Parent page = loader.load();

	        // Cria o palco dialogStage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Horários de Trabalho");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        dialogStage.setResizable(false);
	        page.requestFocus();

	        // Define a pessoa no controller.
	        EmployeeScheduleController controller = loader.getController();
	        controller.setEmployeeData(employee);
	        controller.setStage(dialogStage);

	        // Mostra a janela e espera até o usuário fechar.
	        dialogStage.showAndWait();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void showHolidayInfo(Holiday holiday) {
	    try {
	        // Carrega o arquivo fxml e cria um novo stage para a janela popup.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource("/view/Holiday.fxml"));
	        Parent page = loader.load();

	        // Cria o palco dialogStage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Feriado");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        dialogStage.setResizable(false);
	        page.requestFocus();

	        // Define a holiday no controller
	        HolidayController controller = loader.getController();
	        controller.setHolidayData(holiday);
	        controller.setStage(dialogStage);

	        // Mostra a janela e espera até o usuário fechar.
	        dialogStage.showAndWait();
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	

}
