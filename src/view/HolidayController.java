package view;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	@FXML private Label lblErrorMessage;
	private Holiday holiday;
	private Stage dialogStage;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Constraints.setTextFieldDayPeriod(txtDay);
	}
	
	public void handleCancelBtn(ActionEvent event) {
		Main.getHolidays().remove(holiday);
		dialogStage.close();
	}
	
	public void handleAddBtn(ActionEvent event) {
		String dayInput = txtDay.getText();
		if(dayInput.contains("-")) {
			String[] data = dayInput.split("-");
			Integer dayStart = Integer.parseInt(data[0]);
			Integer dayEnd = Integer.parseInt(data[1]);
			if(dayStart <= 31 && dayStart >= 1 && dayEnd <= 31 && dayEnd >= 1 && dayStart < dayEnd) {
				String[] periods =  new String[3];
				periods[0] = txtMorning.getText();
				periods[1] = txtAfternoon.getText();
				periods[2] = txtNight.getText();
				setInformationToHoliday(dayStart, holiday, periods);
				for(int i = dayStart+1; i <= dayEnd; i++) {
					Holiday h = new Holiday();
					Main.getHolidays().add(h);
					setInformationToHoliday(i, h, periods);
				}
				this.dialogStage.close();
			}else {
				lblErrorMessage.setText("Erro: insira um intervalo válido.");
			}
			
		} else if(dayInput.equals("")){
			lblErrorMessage.setText("Erro: insira um dia válido.");		
			
		}else if(Integer.parseInt(dayInput) <= 31 && Integer.parseInt(dayInput) > 1) {
			String[] periods = new String[3];
			periods[0] = txtMorning.getText();
			periods[1] = txtAfternoon.getText();
			periods[2] = txtNight.getText();
			setInformationToHoliday(Integer.parseInt(dayInput), holiday, periods);
			this.dialogStage.close();
			
		} else {
			lblErrorMessage.setText("Erro: insira um dia válido.");
		}
		
	}
	
	private void setInformationToHoliday(Integer dayOfMonth, Holiday holiday,String[] periods) {
		holiday.setDayOfMonth(dayOfMonth);
		for(int i = 0; i < periods.length; i++) {
			if(periods[i] != null && periods[i].equals("")) {periods[i] = null;}
		}
		holiday.setMorning(periods[0]);
		holiday.setAfternoon(periods[1]);
		holiday.setNight(periods[2]);
		holiday.updateName();
	}

	public void setHolidayData(Holiday holiday) {
		this.holiday = holiday;
	}

	public void setStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
}
