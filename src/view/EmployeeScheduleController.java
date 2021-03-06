package view;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.entites.Constraints;
import model.entites.Day;
import model.entites.DaysOfWeek;
import model.entites.Employee;

public class EmployeeScheduleController implements Initializable {

	@FXML private GridPane gridContainer;
	@FXML private TextField txtName;
	@FXML private Label lblErrorMessage;
	
	private Employee employee;
	private Stage dialogStage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	private Node getNodeFromGridPane(GridPane gridPane, int row, int col) {
		for (Node node : gridPane.getChildren()) {
			if (GridPane.getRowIndex(node) != null && GridPane.getColumnIndex(node) != null
					&& GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
				return node;
			}
		}
		return null;
	}

	public void setEmployeeData(Employee employee) {
		this.employee = employee;
		txtName.setText(this.employee.getName());
		if(this.employee.getWorkSchedule() != null) {
			for(int i = 1; i <=7; i++) {
				Day day = this.employee.getDaySchedule(DaysOfWeek.fromInteger(i));
				if(day != null) {
					String morning = day.getMorning();
					String afternoon = day.getAfternoon();
					String night = day.getNight();
					if(morning.equals(" ")) {morning = "";};
					if(afternoon.equals(" ")) {afternoon = "";};
					if(night.equals(" ")) {night = "";};
					TextField txtMorning = ((TextField) getNodeFromGridPane(gridContainer, i+1, 1));
					TextField txtAfternoon = ((TextField) getNodeFromGridPane(gridContainer, i+1, 2));
					TextField txtNight= ((TextField) getNodeFromGridPane(gridContainer, i+1, 3));
					txtMorning.setText(morning);
					txtAfternoon.setText(afternoon);
					txtNight.setText(night);
					Constraints.setTextFieldDbLimitation(txtMorning);
					Constraints.setTextFieldDbLimitation(txtAfternoon);
					Constraints.setTextFieldDbLimitation(txtNight);
					
				}
			}
			
		}
	}
	
	public void handleSaveBtn(ActionEvent event) {
		if(txtName.getText().length() > 0) {
			employee.setName(txtName.getText());
			for(int i = 1; i <=7; i++) {
				String morning = ((TextField) getNodeFromGridPane(gridContainer, i+1, 1)).getText();
				String afternoon = ((TextField) getNodeFromGridPane(gridContainer, i+1, 2)).getText();
				String night = ((TextField) getNodeFromGridPane(gridContainer, i+1, 3)).getText();
				if(morning.equals("")){morning = " ";}
				if(afternoon.equals("")){afternoon = " ";}
				if(night.equals("")){night = " ";};
				Day day = new Day();
				day.setMorning(morning);
				day.setAfternoon(afternoon);
				day.setNight(night);
				
				DaysOfWeek dayOfWeek = DaysOfWeek.fromInteger(i);
				if(employee.getDaySchedule(dayOfWeek) == null) {
					employee.putDaySchedule(dayOfWeek, day);
				} else {
					employee.changeDaySchedule(dayOfWeek, day);
				}
			}
			this.dialogStage.close();
		} else {
			lblErrorMessage.setText("Erro: insira um nome.");
		}
	}
	
	public void handleRemoveBtn(ActionEvent event) {
		Main.getEmployeeData().remove(employee);
		this.dialogStage.close();
	}

	public void setStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

}
