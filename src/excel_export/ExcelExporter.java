package excel_export;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;

import javax.swing.JFileChooser;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import GUI.Error_Dialog;

public class ExcelExporter {
	private String[] columnNames = { "Sr.", "Asset ID", "Purchase Date", "Asset Type", "Asset Price", "Asset Status" };
	private String[][] data_rows;
	private XSSFSheet sheet;
	private XSSFWorkbook workbook;

	public void export(ResultSet rs, int rows) {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Assets");
		this.data_rows = new String[rows][this.columnNames.length];
		storeData(rs);
		writeHeaderLine(sheet);
		writeDataLines();
		FileOutputStream outputStream;
		try {
			LookAndFeel Default_laf = UIManager.getLookAndFeel();
			String System_laf = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(System_laf);
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.addChoosableFileFilter(
					new FileNameExtensionFilter("Microsoft Excel Spreadsheet (*.xlsx)", "xlsx"));
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int option = fileChooser.showSaveDialog(null);
			if (option == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				String fname = file.getAbsolutePath();
				if (!fname.endsWith(".xlsx")) {
					fname += ".xlsx";
				}
				outputStream = new FileOutputStream(fname);
				workbook.write(outputStream);
				outputStream.close();
				Error_Dialog.showInfo("Data successfully exported.");
			}
			workbook.close();
			UIManager.setLookAndFeel(Default_laf);
		} catch (Exception e) {
			Error_Dialog.showError(e);
		}
	}

	private void storeData(ResultSet result) {
		int currentRowIndex = 0;
		try {
			while (result.next()) {
				this.data_rows[currentRowIndex][0] = result.getString("Sr");
				this.data_rows[currentRowIndex][1] = result.getString("ID");
				this.data_rows[currentRowIndex][2] = result.getString("Purchase_Date");
				this.data_rows[currentRowIndex][3] = result.getString("Type");
				this.data_rows[currentRowIndex][4] = result.getString("Price");
				this.data_rows[currentRowIndex][5] = result.getString("Status");
				currentRowIndex++;
			}

		} catch (Exception e) {
			Error_Dialog.showError(e);
		}
	}

	private void writeDataLines() {
		writeHeaderLine(sheet);
		int rowNum = 1;
		for (String[] current_row : data_rows) {
			Row row = sheet.createRow(rowNum++);
			int colNum = 0;
			for (String field : current_row) {
				Cell cell = row.createCell(colNum++);
				cell.setCellValue(field);
			}
		}

	}

	private void writeHeaderLine(XSSFSheet sheet) {
		Row headerRow = sheet.createRow(0);
		Cell headerCell;
		for (int i = 0; i < this.columnNames.length; i++) {
			headerCell = headerRow.createCell(i);
			headerCell.setCellValue(this.columnNames[i]);
		}
	}

}
