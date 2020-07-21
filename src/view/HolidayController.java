package view;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.entites.Constraints;
import model.entites.Holiday;

public class HolidayController implements Initializable {

	@FXML private TextField txtDay;
	@FXML private TextField txtMorning;
	@FXML private TextField txtAfternoon;
	@FXML private TextField txtNight;
	@FXML private Button btnCancel;
	@FXML private Button btnAdd;
	private Holiday holiday;
	private Stage dialogStage;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Constraints.setTextFieldIntegerPeriod(txtDay);
	}
	
	public void handleCancelBtn(ActionEvent event) {
		Main.getHolidays().remove(holiday);
		dialogStage.close();
	}
	
	public void handleAddBtn(ActionEvent event) {
		holiday.setDayOfMonth(Integer.parseInt(txtDay.getText()));
		String[] day = new String[3];
		day[0] = txtMorning.getText();
		day[1] = txtAfternoon.getText();
		day[2] = txtNight.getText();
		for(int i = 0; i < day.length; i++) {
			if(day[i].equals("")) {day[i] = null;}
		}
		holiday.setMorning(day[0]);
		holiday.setAfternoon(day[1]);
		holiday.setNight(day[2]);
		holiday.updateName();
		this.dialogStage.close();
	}

	public void setHolidayData(Holiday holiday) {
		this.holiday = holiday;
	}

	public void setStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

}
