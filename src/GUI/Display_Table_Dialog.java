package GUI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

public class Display_Table_Dialog extends JDialog {
	private static final long serialVersionUID = 70344042872926412L;

	private String[] columnNames = { "Sr.", "Asset ID", "Purchase Date", "Asset Type", "Asset Price", "Asset Status" };
	private String[][] rows;

	public Display_Table_Dialog() {
		this.setTitle("Database Entries");
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(false);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		this.setBounds(400, 300, 600, 400);
		this.setLocation(x, y);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);
	}

	public void display_table(ResultSet rs, int RowCount) {
		this.rows = new String[RowCount][this.columnNames.length];
		this.storeData(rs);
		JTable table = new JTable(this.rows, this.columnNames);
		table.setBounds(30, 40, 200, 300);
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 564, 339);
		this.getContentPane().add(scrollPane);
		this.setVisible(true);
	}

	private void storeData(ResultSet rs) {
		int currentRowIndex = 0;
		try {
			while (rs.next()) {
				this.rows[currentRowIndex][0] = rs.getString("Sr");
				this.rows[currentRowIndex][1] = rs.getString("ID");
				this.rows[currentRowIndex][2] = rs.getString("Purchase_Date");
				this.rows[currentRowIndex][3] = rs.getString("Type");
				this.rows[currentRowIndex][4] = rs.getString("Price");
				this.rows[currentRowIndex][5] = rs.getString("Status");
				currentRowIndex++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Error_Dialog.showError(e);
		}
	}
}