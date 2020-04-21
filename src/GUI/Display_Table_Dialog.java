package GUI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class Display_Table_Dialog extends JDialog {
	private static final long serialVersionUID = 70344042872926412L;

	public Display_Table_Dialog(ResultSet rs) {
		super((JDialog)null);
		this.setTitle("Database Entries");
		setModal(true);
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
		JTable table = new JTable();
		table.setBounds(30, 40, 200, 300);
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 564, 339);
		this.getContentPane().add(scrollPane);
		try {
			DefaultTableModel tableModel = new DefaultTableModel();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				tableModel.addColumn(metaData.getColumnLabel(columnIndex));
			}
			Object[] row = new Object[columnCount];
			while (rs.next()) {
				for (int i = 0; i < columnCount; i++) {
					row[i] = rs.getObject(i + 1);
				}
				tableModel.addRow(row);
			}
			table.setModel(tableModel);
			table.setDefaultEditor(Object.class, null);
			table.getTableHeader().setReorderingAllowed(false);
		} catch (Exception e) {
			Error_Dialog.showError(e);
		}

	}

	public void showDialog() {
		this.setVisible(true);
	}

}