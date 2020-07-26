package result;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.collections.ObservableList;
import model.entites.Employee;
import model.entites.Holiday;
import model.entites.PixelUtil;

public class ExcelApache {

	private Workbook wb;
	private int lastSheetIndex;
	private final String outputPath = "Folha gerada.xlsx";
	private Font font;
	private CellStyle cs;
	private CellStyle styleCenter;
	private CellStyle styleLeft;
	private CellStyle styleRight;
	private ObservableList<Holiday> holidays;

	public ExcelApache(ObservableList<Holiday> holidays) {
		this.holidays = holidays;
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

		// Styles with aligns and alll borders
		Font fontToCenter = wb.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setFontName("Calibri");
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

		// Styles with aligns and alll borders
		Font fontToLeft = wb.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setFontName("Calibri");
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

		// Styles with aligns and alll borders
		Font fontToRight = wb.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setFontName("Calibri");
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

	public void closeWorkbook() throws ExcelException{
		try {
			this.wb.close();
		} catch (IOException e) {
			throw new ExcelException("Não foi possível fechar a Pasta de Trabalho do Excel.\n Erro: " + e.getMessage());
		}
	}

	public void createSheetToEmployee(Employee employee, String monthReference) {
		Sheet sheet = this.wb.createSheet(Integer.toString(this.lastSheetIndex++));
		setDefaultStyle(sheet);
		setColumnsWidth(sheet);

		Row firstRow = sheet.createRow(0);
		createCellWithAllBorder(firstRow, 0, "Colaborador", styleLeft);

		Row secondRow = sheet.createRow(1);
		createCellWithAllBorder(secondRow, 0, "Referente ao mês:", styleLeft);
		createCellWithAllBorder(secondRow, 1, monthReference, styleRight);

		Row thirdRow = sheet.createRow(2);
		createCellWithAllBorder(thirdRow, 0, "Dia do mês", styleCenter);
		createCellWithAllBorder(thirdRow, 1, "Dia da semana", styleCenter);
		createCellWithAllBorder(thirdRow, 2, "Manhã", styleCenter);
		createCellWithAllBorder(thirdRow, 3, "Tarde", styleCenter);
		createCellWithAllBorder(thirdRow, 4, "Noite", styleCenter);
		createCellWithAllBorder(thirdRow, 5, "ASSINATURA", styleCenter);
		createCellWithAllBorder(thirdRow, 6, "Observações", styleCenter);
	}

	private void setDefaultStyle(Sheet sheet) {
		for (int i = 0; i <= 10; i++) {
			sheet.setDefaultColumnStyle(i, this.cs);

		}
		sheet.getPrintSetup().setLandscape(true);
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
		sheet.setColumnWidth(6, PixelUtil.pixel2WidthUnits(216)); // G

		// Margins (have to be in inches)
		sheet.setMargin(Sheet.LeftMargin, 0.511811); // 1,30 cm
		sheet.setMargin(Sheet.RightMargin, 0.511811); // 1,30 cm
		sheet.setMargin(Sheet.TopMargin, 0.787402); // 2,00 cm
		sheet.setMargin(Sheet.BottomMargin, 0.590551); // 1,50 cm
		sheet.setMargin(Sheet.FooterMargin, 0);
		sheet.setMargin(Sheet.HeaderMargin, 0);
	}

}
