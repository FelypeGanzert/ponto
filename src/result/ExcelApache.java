package result;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.entites.DateUtil;
import model.entites.Day;
import model.entites.Employee;
import model.entites.PixelUtil;

public class ExcelApache {

	private Workbook wb;
	private int lastSheetIndex;
	private final String outputPath = "Folha gerada.xlsx";
	private Font font;
	private CellStyle cs;
	private CellStyle styleCenter;
	private CellStyle styleCenterBold;
	private CellStyle styleLeft;
	private CellStyle styleRight;

	// 0 Top, 1 TopLeft, 2 TopRight, 3 Bottom, 4 BottomLeft, 5 BottomRight, 6 Left, 7 Right, 8 none
	private CellStyle[] borders = new CellStyle[9];


	private String[] WEEKDAYS = { "domingo", "segunda", "terça", "quarta", "quinta", "sexta", "sábado" };

	public ExcelApache() {
		createNewWorkbook();
	}

	public void createNewWorkbook() {
		this.wb = new XSSFWorkbook();
		lastSheetIndex = 1;
		// Font
		font = wb.getFontAt(0);
		font.setFontHeightInPoints((short) 11);
		font.setFontName("Calibri");
		cs = wb.createCellStyle();
		cs.setFont(font);

		// Styles with aligns and all borders
		Font fontToCenter = wb.createFont();
		styleCenter = wb.createCellStyle();
		styleCenter.setFont(fontToCenter);
		styleCenter.setBorderBottom(BorderStyle.THIN);
		styleCenter.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styleCenter.setBorderLeft(BorderStyle.THIN);
		styleCenter.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		styleCenter.setBorderRight(BorderStyle.THIN);
		styleCenter.setRightBorderColor(IndexedColors.BLACK.getIndex());
		styleCenter.setBorderTop(BorderStyle.THIN);
		styleCenter.setTopBorderColor(IndexedColors.BLACK.getIndex());
		styleCenter.setVerticalAlignment(VerticalAlignment.CENTER);
		styleCenter.setAlignment(HorizontalAlignment.CENTER);
		
		Font fontToCenterBold = wb.createFont();
		fontToCenterBold.setBold(true);
		styleCenterBold = wb.createCellStyle();
		styleCenterBold.setFont(fontToCenterBold);
		styleCenterBold.setBorderBottom(BorderStyle.THIN);
		styleCenterBold.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styleCenterBold.setBorderLeft(BorderStyle.THIN);
		styleCenterBold.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		styleCenterBold.setBorderRight(BorderStyle.THIN);
		styleCenterBold.setRightBorderColor(IndexedColors.BLACK.getIndex());
		styleCenterBold.setBorderTop(BorderStyle.THIN);
		styleCenterBold.setTopBorderColor(IndexedColors.BLACK.getIndex());
		styleCenterBold.setVerticalAlignment(VerticalAlignment.CENTER);
		styleCenterBold.setAlignment(HorizontalAlignment.CENTER);

		Font fontToLeft = wb.createFont();
		styleLeft = wb.createCellStyle();
		styleLeft.setFont(fontToLeft);
		styleLeft.setBorderBottom(BorderStyle.THIN);
		styleLeft.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styleLeft.setBorderLeft(BorderStyle.THIN);
		styleLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		styleLeft.setBorderRight(BorderStyle.THIN);
		styleLeft.setRightBorderColor(IndexedColors.BLACK.getIndex());
		styleLeft.setBorderTop(BorderStyle.THIN);
		styleLeft.setTopBorderColor(IndexedColors.BLACK.getIndex());
		styleLeft.setVerticalAlignment(VerticalAlignment.CENTER);
		styleLeft.setAlignment(HorizontalAlignment.LEFT);

		Font fontToRight = wb.createFont();
		styleRight = wb.createCellStyle();
		styleRight.setFont(fontToRight);
		styleRight.setBorderBottom(BorderStyle.THIN);
		styleRight.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styleRight.setBorderLeft(BorderStyle.THIN);
		styleRight.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		styleRight.setBorderRight(BorderStyle.THIN);
		styleRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		styleRight.setBorderTop(BorderStyle.THIN);
		styleRight.setTopBorderColor(IndexedColors.BLACK.getIndex());
		styleRight.setVerticalAlignment(VerticalAlignment.CENTER);
		styleRight.setAlignment(HorizontalAlignment.RIGHT);

		// Styles for borders - for some reason if I use just one he affect every others
		// cells...
		for(int i = 0; i < borders.length; i++) {
			Font fontBorder = wb.createFont();
			fontBorder.setFontHeightInPoints((short) 11);
			fontBorder.setFontName("Calibri");
			borders[i] = wb.createCellStyle();
			borders[i].setFont(fontToRight);
			borders[i].setBorderBottom(BorderStyle.NONE);
			borders[i].setBottomBorderColor(IndexedColors.BLACK.getIndex());
			borders[i].setBorderLeft(BorderStyle.NONE);
			borders[i].setLeftBorderColor(IndexedColors.BLACK.getIndex());
			borders[i].setBorderRight(BorderStyle.NONE);
			borders[i].setRightBorderColor(IndexedColors.BLACK.getIndex());
			borders[i].setBorderTop(BorderStyle.NONE);
			borders[i].setTopBorderColor(IndexedColors.BLACK.getIndex());
			borders[i].setVerticalAlignment(VerticalAlignment.CENTER);
			borders[i].setAlignment(HorizontalAlignment.LEFT);
		}
		// Border Top
		borders[0].setBorderTop(BorderStyle.THIN);
		// Border TopLeft
		borders[1].setBorderTop(BorderStyle.THIN);
		borders[1].setBorderLeft(BorderStyle.THIN);
		// Border TopRight
		borders[2].setBorderTop(BorderStyle.THIN);
		borders[2].setBorderRight(BorderStyle.THIN);
		// Border Bottom
		borders[3].setBorderBottom(BorderStyle.THIN);
		// Border BottomLeft
		borders[4].setBorderBottom(BorderStyle.THIN);
		borders[4].setBorderLeft(BorderStyle.THIN);
		// Border BottomRight
		borders[5].setBorderBottom(BorderStyle.THIN);
		borders[5].setBorderRight(BorderStyle.THIN);
		// Border Left
		borders[6].setBorderLeft(BorderStyle.THIN);
		// Border Right
		borders[7].setBorderRight(BorderStyle.THIN);

	}

	public void saveToFile() throws ExcelException {
		try (OutputStream fileOut = new FileOutputStream(this.outputPath)) {
			wb.write(fileOut);
		} catch (IOException e) {
			throw new ExcelException("Erro: o arquivo está aberto. Feche-o e tente novamente");
		}
		try {
			Desktop.getDesktop().open(new File(outputPath));
		} catch (IOException e) {
			throw new ExcelException("Não foi possível abrir o arquivo gerado.");
		}
	}

	public void closeWorkbook() throws ExcelException {
		try {
			this.wb.close();
		} catch (IOException e) {
			throw new ExcelException("Não foi possível fechar a Pasta de Trabalho do Excel.\n Erro: " + e.getMessage());
		}
	}

	public void createSheetToEmployee(Employee employee, String monthReference, int monthIndex, int year) throws ParseException {
		Sheet sheet = this.wb.createSheet(Integer.toString(this.lastSheetIndex++));
		setDefaultStyle(sheet);
		setColumnsWidth(sheet);

		Row firstRow = sheet.createRow(0);
		createCellWithAllBorder(firstRow, 0, "Colaborador", styleLeft);
		createCellWithAllBorder(firstRow, 1, employee.getName().toUpperCase(), styleCenterBold);
		createCellWithAllBorder(firstRow, 2, "", styleCenter);
		createCellWithAllBorder(firstRow, 3, "", styleCenter);
		createCellWithAllBorder(firstRow, 4, "", styleCenter);
		createCellWithAllBorder(firstRow, 5, "", styleCenter);
		createCellWithAllBorder(firstRow, 6, "", styleCenter);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 6));

		Row secondRow = sheet.createRow(1);
		createCellWithAllBorder(secondRow, 0, "Referente ao mês:", styleLeft);
		createCellWithAllBorder(secondRow, 1, monthReference, styleRight);
		createCellWithAllBorder(secondRow, 6, "", borders[7]);

		Row thirdRow = sheet.createRow(2);
		createCellWithAllBorder(thirdRow, 0, "Dia do mês", styleCenter);
		createCellWithAllBorder(thirdRow, 1, "Dia da semana", styleCenter);
		createCellWithAllBorder(thirdRow, 2, "Manhã", styleCenter);
		createCellWithAllBorder(thirdRow, 3, "Tarde", styleCenter);
		createCellWithAllBorder(thirdRow, 4, "Noite", styleCenter);
		createCellWithAllBorder(thirdRow, 5, "ASSINATURA", styleCenter);
		createCellWithAllBorder(thirdRow, 6, "Observações", styleCenter);
		
		// base Month
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date baseDate = sdf.parse("01" + "/" + monthIndex + "/" + year);
		Calendar calDate = DateUtil.dateToCalendar(baseDate);
		int lastDayOfMonth = calDate.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		for (int i = 1; i <= lastDayOfMonth; i++) {
			Day day = employee.getDay(i);
			if (day != null) {
				Row row = sheet.createRow(2 + i);
				createCellWithAllBorder(row, 0, String.format("%02d", i), styleRight);
				String weekDay = WEEKDAYS[DateUtil.dateToCalendar(day.getDay()).get(Calendar.DAY_OF_WEEK) - 1];
				createCellWithAllBorder(row, 1, weekDay, styleLeft);

				String morning = employee.getDay(i).getMorning();
				String afternoon = employee.getDay(i).getAfternoon();
				String night = employee.getDay(i).getNight();
				createCellWithAllBorder(row, 2, morning, styleLeft);
				createCellWithAllBorder(row, 3, afternoon, styleLeft);
				createCellWithAllBorder(row, 4, night, styleLeft);

				if (morning.contains("à") || afternoon.contains("à") || night.contains("à") ||
						morning.contains("À") || afternoon.contains("À") || night.contains("À")) {
					createCellWithAllBorder(row, 5, "", styleCenter);
				} else {
					createCellWithAllBorder(row, 5, "---------------------", styleCenter);
				}
				createCellWithAllBorder(row, 6, "", styleCenter);
			}
		}

		int secondPartStart = 42;

		Row secondPartRow = sheet.createRow(secondPartStart-1);
		createCellWithAllBorder(secondPartRow, 0, "", borders[1]);
		createCellWithAllBorder(secondPartRow, 1, "", borders[0]);
		createCellWithAllBorder(secondPartRow, 2, "", borders[0]);
		createCellWithAllBorder(secondPartRow, 3, "", borders[0]);
		createCellWithAllBorder(secondPartRow, 4, "", borders[2]);


		Row row1 = sheet.createRow(secondPartStart);
		createCellWithAllBorder(row1, 0, "Faltas:", borders[6]);
		createCellWithAllBorder(row1, 1, "_____________", borders[8]);
		createCellWithAllBorder(row1, 2, "Observações:", borders[8]);
		createCellWithAllBorder(row1, 3, "", borders[8]);
		createCellWithAllBorder(row1, 4, "", borders[7]);

		Row row2 = sheet.createRow(secondPartStart+1);
		createCellWithAllBorder(row2, 0, "", borders[6]);
		createCellWithAllBorder(row2, 4, "", borders[7]);

		Row row3 = sheet.createRow(secondPartStart+2);
		createCellWithAllBorder(row3, 0, "Assinatura do colaborador", borders[6]);
		createCellWithAllBorder(row3, 2, "________________________________________", borders[8]);
		createCellWithAllBorder(row3, 4, "", borders[7]);
		

		Row row4 = sheet.createRow(secondPartStart+3);
		createCellWithAllBorder(row4, 0, "", borders[6]);
		createCellWithAllBorder(row4, 4, "", borders[7]);
		

		Row row5 = sheet.createRow(secondPartStart+4);
		createCellWithAllBorder(row5, 0, "Assinatura secretária", borders[6]);
		createCellWithAllBorder(row5, 2, "________________________________________", borders[8]);
		createCellWithAllBorder(row5, 4, "", borders[7]);
		
		Row row6 = sheet.createRow(secondPartStart+5);
		createCellWithAllBorder(row6, 0, "", borders[6]);
		createCellWithAllBorder(row6, 4, "", borders[7]);
		
		Row row7 = sheet.createRow(secondPartStart+6);
		createCellWithAllBorder(row7, 0, "Assinatura direção", borders[4]);
		createCellWithAllBorder(row7, 1, "", borders[3]);
		createCellWithAllBorder(row7, 2, "________________________________________", borders[3]);
		createCellWithAllBorder(row7, 3, "", borders[3]);
		createCellWithAllBorder(row7, 4, "", borders[5]);

	}

	private void setDefaultStyle(Sheet sheet) {
		for (int i = 0; i <= 10; i++) {
			sheet.setDefaultColumnStyle(i, this.cs);

		}
		sheet.getPrintSetup().setLandscape(true);
		sheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
	}

	private void createCellWithAllBorder(Row row, int column, String value, CellStyle style) {
		Cell cell = row.createCell(column);
		cell.setCellValue(value);
		cell.setCellStyle(style);
	}

	private void setColumnsWidth(Sheet sheet) {
		sheet.setColumnWidth(0, PixelUtil.pixel2WidthUnits(123)); // A
		sheet.setColumnWidth(1, PixelUtil.pixel2WidthUnits(115)); // B
		sheet.setColumnWidth(2, PixelUtil.pixel2WidthUnits(129)); // C
		sheet.setColumnWidth(3, PixelUtil.pixel2WidthUnits(129)); // D
		sheet.setColumnWidth(4, PixelUtil.pixel2WidthUnits(129)); // E
		sheet.setColumnWidth(5, PixelUtil.pixel2WidthUnits(129)); // F
		sheet.setColumnWidth(6, PixelUtil.pixel2WidthUnits(213)); // G

		// Margins (have to be in inches)
		sheet.setMargin(Sheet.LeftMargin, 0.393701); // 1 cm
		sheet.setMargin(Sheet.RightMargin, 0.393701); // 1 cm
		sheet.setMargin(Sheet.TopMargin, 0.787402); // 2,00 cm
		sheet.setMargin(Sheet.BottomMargin, 0.390551); // 1,50 cm
		sheet.setMargin(Sheet.FooterMargin, 0);
		sheet.setMargin(Sheet.HeaderMargin, 0);
	}

}
