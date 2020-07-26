package application;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import db.DB;
import db.DBException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entites.DateUtil;
import model.entites.Day;
import model.entites.DaysOfWeek;
import model.entites.Employee;
import model.entites.Holiday;
import result.ExcelApache;
import result.ExcelException;
import view.AppController;
import view.EmployeeScheduleController;
import view.HolidayController;

public class Main extends Application {

	private Stage primaryStage;
	private static ObservableList<Employee> employeeData = FXCollections.observableArrayList();
	private static ObservableList<Holiday> holidays = FXCollections.observableArrayList();
	public static DB db;
	private static ExcelApache excelApache;

	public Main() {
		Main.db = new DB(employeeData, holidays);
		try{
			db.getData();
		}catch (DBException dbExc) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro ao ler.");
			alert.setHeaderText("ERRO");
			alert.setContentText(dbExc.getMessage());
			alert.showAndWait();
		}
		excelApache = new ExcelApache();
	}

	public static ObservableList<Employee> getEmployeeData() {
		return employeeData;
	}

	public static ObservableList<Holiday> getHolidays() {
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
		try {
			excelApache.closeWorkbook();
		} catch(ExcelException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro ao fechar");
			alert.setHeaderText("ERRO");
			alert.setContentText(e.getMessage());
			alert.show();
		}
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

			if (employee.getName().length() == 0) {
				getEmployeeData().remove(employee);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showHolidayInfo(Holiday holiday) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Holiday.fxml"));
			Parent page = loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Feriado");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			dialogStage.setResizable(false);
			page.requestFocus();

			HolidayController controller = loader.getController();
			controller.setHolidayData(holiday);
			controller.setStage(dialogStage);

			dialogStage.showAndWait();
			if (holiday.getDayOfMonth() == 0) {
				holidays.remove(holiday);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generatePoints(String monthYear, int monthIndex, int year) {
		Alert alertProcessing = new Alert(AlertType.INFORMATION);
		alertProcessing.setTitle("Processando");
		alertProcessing.setHeaderText("Aguarde");
		alertProcessing.setContentText("Está sendo gerado.");
		alertProcessing.show();
		getEmployeeData().forEach(e -> {
			try {
				if(e.isSelected()) {
					putDaysInEmployee(e, getHolidays(), monthIndex, year);
					excelApache.createSheetToEmployee(e, monthYear.toLowerCase());
				}
			} catch (ParseException e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erro");
				alert.setHeaderText("ERRO");
				alert.setContentText("Erro durante a lógica de atribuir os dias: " + e1.getMessage());
				alert.show();
			}
		});		
		try {
			excelApache.saveToFile();
		} catch (ExcelException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("ERRO");
			alert.setContentText(e1.getMessage());
			alert.show();
		}
		excelApache.createNewWorkbook();
		alertProcessing.close();
		Alert alertDone = new Alert(AlertType.INFORMATION);
		alertDone.setTitle("Concluído");
		alertDone.setHeaderText("Concluído");
		alertDone.setContentText("As folhas de ponto foram geradas com sucesso.");
		alertDone.show();
	}

	private void putDaysInEmployee(Employee employee, ObservableList<Holiday> holidays, int monthIndex, int year) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		// base Month
		Date baseDate = sdf.parse("01" + "/" + monthIndex + "/" + year);
		Calendar calDate = DateUtil.dateToCalendar(baseDate);
		int lastDayOfMonth = calDate.getActualMaximum(Calendar.DAY_OF_MONTH);
		// return one day
		calDate.add(Calendar.DAY_OF_MONTH, -1);

		// Will make a loop to add all the days on the map days with the key being the
		// dayOfMonth
		for (Integer i = 1; i <= lastDayOfMonth; i++) {
			calDate.add(Calendar.DAY_OF_MONTH, 1);
			Date date = DateUtil.calendarToDate(calDate);
			// get the day of the week
			Integer dayOfWeek = calDate.get(Calendar.DAY_OF_WEEK);
			final int dayOfMonth = i;
			// get the informatinons of the work schedule by the day of the week
			Holiday holiday = null;
			try {
				holiday = holidays.stream().filter(h -> h.getDayOfMonth() == dayOfMonth).findFirst().get();
			} catch (Exception e) {
			}
			;
			Day dayToAdd = getDayInformation(holiday, employee.getDaySchedule(DaysOfWeek.fromInteger(dayOfWeek)));
			dayToAdd.setDay(date);
			// put the day
			if (employee.getDay(i) == null) {
				employee.putDay(i, dayToAdd);
			} else {
				employee.changeDay(i, dayToAdd);

			}
		}
	}

	public Day getDayInformation(Holiday holiday, Day schedule) {
		String morning = schedule.getMorning();
		String afternoon = schedule.getAfternoon();
		String night = schedule.getNight();

		if (holiday != null) {
			String[] holidayInfos = { holiday.getMorning(), holiday.getAfternoon(), holiday.getNight() };
			if (holidayInfos[0] != null) {
				morning = holidayInfos[0];
			}
			if (holidayInfos[1] != null) {
				afternoon = holidayInfos[1];
			}
			if (holidayInfos[2] != null) {
				night = holidayInfos[2];
			}
		}
		Day d = new Day(null, morning, afternoon, night);
		return d;
	}

}
