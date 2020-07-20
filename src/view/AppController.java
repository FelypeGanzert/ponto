package view;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import model.entites.Employee;

public class AppController implements Initializable{
	@FXML private TableView<Employee> tableEmployees;
	@FXML private TableColumn<Employee, Boolean> selectEmployee;
	@FXML private TableColumn<Employee, String> nameEmployee;
	@FXML private Button editBtn;
	@FXML private Button addBtn;
	
	private ObservableList<Employee> employeeData;
	private Main main;
	
	public AppController() {}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameEmployee.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		selectEmployee.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
		selectEmployee.setCellFactory(CheckBoxTableCell.forTableColumn(selectEmployee));
		
		tableEmployees.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldSelection, newSelection) -> {
	            	if(newSelection != null) {
	            		editBtn.setText("Editar " + newSelection.getName().split(" ")[0]);
	            		editBtn.setDisable(false);
	            	}
	            }
	    );
	};
	
	public void handleEditBtn(ActionEvent event) {
		this.main.showEmployeeSchedule(tableEmployees.getSelectionModel().getSelectedItem());
	}
	
	public void handleAddBtn(ActionEvent event) {
		Employee newEmployee = new Employee();
		this.employeeData.add(newEmployee);
		this.main.showEmployeeSchedule(newEmployee);
	}
	
	public void setEmployeeData(ObservableList<Employee> employeeData) {
		this.employeeData = employeeData;
		tableEmployees.setItems(employeeData);
	}

	public void setMainApp(Main main) {
		this.main = main;		
	}
	
}
