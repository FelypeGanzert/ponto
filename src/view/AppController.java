package view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.ImageView;
import model.entites.Constraints;
import model.entites.DateUtil;
import model.entites.Employee;
import model.entites.Holiday;

public class AppController implements Initializable{
	@FXML private ImageView logoImgView;
	
	@FXML private TableView<Employee> tableEmployees;
	@FXML private TableColumn<Employee, Boolean> selectEmployee;
	@FXML private TableColumn<Employee, String> nameEmployee;
	@FXML private Button editBtn;
	@FXML private Button addBtn;
	
	@FXML private TableView<Holiday> tableHolidays;
	@FXML private TableColumn<Holiday, Number> dayHoliday;
	@FXML private TableColumn<Holiday, String> nameHoliday;
	@FXML private TableColumn<Holiday, Boolean> selectHoliday;
	@FXML private Button removeHolidays;
	
	@FXML private ChoiceBox<String> monthsChoiceBox;
	@FXML private TextField yearTxt;
	@FXML private Button generateBtn;
	
	@FXML private Button addHolidayBtn;
	
	
	private ObservableList<Employee> employeeData;
	private ObservableList<Holiday> holidays;
	private ObservableList<String> months = FXCollections.observableArrayList();
	private final String[] monthsArray = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
			"Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
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
		
		dayHoliday.setCellValueFactory(cellData -> cellData.getValue().getDayOfMonthProperty());
		nameHoliday.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		selectHoliday.setCellValueFactory(cellData -> cellData.getValue().getSelectedProperty());
		selectHoliday.setCellFactory(CheckBoxTableCell.forTableColumn(selectHoliday));
		
		Date now = new Date();
		months.addAll(monthsArray);
		monthsChoiceBox.setItems(months);
		String nextMonth = monthsArray[DateUtil.dateToCalendar(now).get(Calendar.MONTH)+1];
		monthsChoiceBox.setValue(nextMonth);
		
		Constraints.setTextFieldIntegerYear(yearTxt);
		yearTxt.setText(Integer.toString(DateUtil.dateToCalendar(now).get(Calendar.YEAR)));
	};
	
	public void handleEditBtn(ActionEvent event) {
		this.main.showEmployeeSchedule(tableEmployees.getSelectionModel().getSelectedItem());
	}
	
	public void handleAddBtn(ActionEvent event) {
		Employee newEmployee = new Employee();
		this.employeeData.add(newEmployee);
		this.main.showEmployeeSchedule(newEmployee);
	}
	
	public void handleRemoveHolidayBtn(ActionEvent event) {
		List<Holiday> holidaysCopy = new ArrayList<>(holidays);
		for(Holiday h : holidaysCopy) {
			if(h.isSelected()) {
				holidays.remove(h);
			}
		}
	}
	
	public void handleAddHolidayBtn(ActionEvent event) {
		Holiday newHoliday = new Holiday();
		holidays.add(newHoliday);
		this.main.showHolidayInfo(newHoliday);
		dayHoliday.setSortType(TableColumn.SortType.ASCENDING);
		tableHolidays.getSortOrder().add(dayHoliday);
	}
	
	public void setEmployeeData(ObservableList<Employee> employeeData) {
		this.employeeData = employeeData;
		tableEmployees.setItems(employeeData);
	}

	public void setMainApp(Main main) {
		this.main = main;
		tableHolidays.setItems(holidays);
		dayHoliday.setSortType(TableColumn.SortType.ASCENDING);
		tableHolidays.getSortOrder().add(dayHoliday);
	}

	public void setHolidays(ObservableList<Holiday> holidays) {
		this.holidays = holidays;
	}
	
	public void handleGenerateHolidayBtn(ActionEvent event) {
		String monthYear =  this.monthsChoiceBox.getValue() + "/" + this.yearTxt.getText();
		List<String> monthsIndex = new ArrayList<String>();
		monthsIndex.addAll(Arrays.asList(monthsArray));
		this.main.generatePoints(monthYear, monthsIndex.indexOf(this.monthsChoiceBox.getValue())+1, Integer.parseInt(this.yearTxt.getText()));
	}
	
	
}
