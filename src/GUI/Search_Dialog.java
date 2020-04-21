package GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import db_driver.Assetdb;
import excel_export.ExcelExporter;

public class Search_Dialog extends JDialog {
	private static final long serialVersionUID = -9162694945599195096L;

	public static boolean checkValidDate(String value) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.parse(value);
			if (!value.equals(sdf.format(date))) {
				date = null;
			}
		} catch (Exception ex) {
		}
		return date != null;
	}

	private Assetdb db;
	private JTextField IDTbox;
	private JTextField pdateTbox;
	private JTextField PriceTbox;
	private ResultSet set;
	private JTextField SrTbox;
	private JTextField StatusTbox;
	private JTable table;
	private JTextField TypeTbox;

	public Search_Dialog(Assetdb dbobj) {
		super((JDialog)null);
		this.db = dbobj;
		this.setTitle("Search Entries");
		setModal(true);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(false);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		this.setBounds(400, 300, 626, 458);
		this.setLocationRelativeTo(null);
		this.setLocation(x, y);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);

		SrTbox = new JTextField();
		SrTbox.setHorizontalAlignment(SwingConstants.TRAILING);
		SrTbox.setBounds(10, 36, 90, 26);
		getContentPane().add(SrTbox);
		SrTbox.setColumns(10);

		IDTbox = new JTextField();
		IDTbox.setHorizontalAlignment(SwingConstants.TRAILING);
		IDTbox.setColumns(10);
		IDTbox.setBounds(110, 36, 90, 26);
		getContentPane().add(IDTbox);

		pdateTbox = new JTextField();
		pdateTbox.setHorizontalAlignment(SwingConstants.TRAILING);
		pdateTbox.setColumns(10);
		pdateTbox.setBounds(210, 36, 90, 26);
		getContentPane().add(pdateTbox);

		TypeTbox = new JTextField();
		TypeTbox.setHorizontalAlignment(SwingConstants.TRAILING);
		TypeTbox.setColumns(10);
		TypeTbox.setBounds(310, 36, 90, 26);
		getContentPane().add(TypeTbox);

		PriceTbox = new JTextField();
		PriceTbox.setHorizontalAlignment(SwingConstants.TRAILING);
		PriceTbox.setColumns(10);
		PriceTbox.setBounds(410, 36, 90, 26);
		getContentPane().add(PriceTbox);

		StatusTbox = new JTextField();
		StatusTbox.setHorizontalAlignment(SwingConstants.TRAILING);
		StatusTbox.setColumns(10);
		StatusTbox.setBounds(510, 36, 90, 26);
		getContentPane().add(StatusTbox);

		JLabel lblNewLabel = new JLabel("Sr.");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 12, 90, 20);
		getContentPane().add(lblNewLabel);

		JLabel lblId = new JLabel("ID");
		lblId.setHorizontalAlignment(SwingConstants.CENTER);
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblId.setBounds(110, 12, 90, 20);
		getContentPane().add(lblId);

		JLabel lblPurchaseDate = new JLabel("Purchase Date");
		lblPurchaseDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblPurchaseDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPurchaseDate.setBounds(210, 12, 90, 20);
		getContentPane().add(lblPurchaseDate);

		JLabel lblType = new JLabel("Type");
		lblType.setHorizontalAlignment(SwingConstants.CENTER);
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblType.setBounds(310, 12, 90, 20);
		getContentPane().add(lblType);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPrice.setBounds(410, 12, 90, 20);
		getContentPane().add(lblPrice);

		JLabel lblStatus = new JLabel("Status");
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblStatus.setBounds(510, 12, 90, 20);
		getContentPane().add(lblStatus);

		JButton searchbtn = new JButton("Search");
		searchbtn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		searchbtn.setBounds(10, 73, 190, 34);
		getContentPane().add(searchbtn);

		JButton ExportBtn = new JButton("Export to Excel");
		ExportBtn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		ExportBtn.setBounds(410, 73, 190, 34);
		getContentPane().add(ExportBtn);

		table = new JTable();
		table.setBounds(10, 118, 590, 292);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 118, 590, 292);
		getContentPane().add(scrollPane);

		FillTable();
		searchbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Search_Dialog.this.FillTable();
			}
		});

		ExportBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ExcelExporter excel = new ExcelExporter();
				excel.export(set, db.getRows(set));
			}
		});

	}

	private ResultSet copy(ResultSet rs) {
		return rs;
	}

	private void FillTable() {
		try {
			ResultSet rs = db.getResultSet(this.getSearchString());
			set = copy(rs);
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

	private String[] getSearchString() {
		String[] searchString = new String[6];
		searchString[0] = this.SrTbox.getText().trim();
		searchString[1] = this.IDTbox.getText().trim();
		if (checkValidDate(this.pdateTbox.getText().trim())) {
			searchString[2] = this.pdateTbox.getText().trim();
		} else {
			searchString[2] = "";
		}
		searchString[3] = this.TypeTbox.getText().trim();
		searchString[4] = this.PriceTbox.getText().trim();
		searchString[5] = this.StatusTbox.getText().trim();
		return searchString;
	}

	public void showDialog() {
		this.setVisible(true);
	}
}
