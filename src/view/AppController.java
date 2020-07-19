package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.ImageView;
import model.entites.Employee;

public class AppController implements Initializable{
	@FXML private TableView<Employee> tableEmployees;
	@FXML private TableColumn<Employee, Boolean> selectEmployee;
	@FXML private TableColumn<Employee, String> nameEmployee;
	
	private ObservableList<Employee> employeeData;
	
	public AppController() {}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameEmployee.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		
		selectEmployee.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
		selectEmployee.setCellFactory(CheckBoxTableCell.forTableColumn(selectEmployee));
		
		tableEmployees.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> {
	            	System.out.println("-----");
	            	employeeData.forEach(e -> System.out.println(e.isSelected() + " - " + e.getName()));
	            	System.out.println("-----");
	            	
	            }
	            
	    );
		
	};
	
	public void setEmployeeData(ObservableList<Employee> employeeData) {
		this.employeeData = employeeData;
		tableEmployees.setItems(employeeData);
	}
	
	
	
	
	
}
