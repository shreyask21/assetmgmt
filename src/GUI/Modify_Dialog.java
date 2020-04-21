package GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import db_driver.Assetdb;

public class Modify_Dialog extends JDialog {
	private static final long serialVersionUID = -4369329442937708110L;

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

	private JButton CancelBtn;
	private Assetdb db;
	private boolean enableActions = true;
	private JButton HelpBtn;
	private JTextField IDTboxModify;
	private JTextField IDTboxSearch;
	private JButton ModifyBtn;
	private JButton ModifySelectBtn;
	private JTextField pdateTboxModify;
	private JTextField pdateTboxSearch;
	private JTextField PriceTboxModify;
	private JTextField PriceTboxSearch;
	private JButton SearchBtn;
	private int selectedRow;
	private String SelectedSr;
	private JTextField SrTboxSearch;
	private JTextField StatusTboxModify;
	private JTextField StatusTboxSearch;
	private JTable table;
	private JTextField TypeTboxModify;

	private JTextField TypeTboxSearch;

	public Modify_Dialog(Assetdb dbobj) {
		super((JDialog)null);
		this.db = dbobj;
		this.setTitle("Modify Entries");
		setModal(true);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(false);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		this.setBounds(400, 300, 629, 570);
		this.setLocationRelativeTo(null);
		this.setLocation(x, y);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);

		SrTboxSearch = new JTextField();
		SrTboxSearch.setHorizontalAlignment(SwingConstants.CENTER);
		SrTboxSearch.setBounds(10, 36, 90, 26);
		getContentPane().add(SrTboxSearch);
		SrTboxSearch.setColumns(10);

		IDTboxSearch = new JTextField();
		IDTboxSearch.setHorizontalAlignment(SwingConstants.CENTER);
		IDTboxSearch.setColumns(10);
		IDTboxSearch.setBounds(110, 36, 90, 26);
		getContentPane().add(IDTboxSearch);

		pdateTboxSearch = new JTextField();
		pdateTboxSearch.setHorizontalAlignment(SwingConstants.CENTER);
		pdateTboxSearch.setColumns(10);
		pdateTboxSearch.setBounds(210, 36, 90, 26);
		getContentPane().add(pdateTboxSearch);

		TypeTboxSearch = new JTextField();
		TypeTboxSearch.setHorizontalAlignment(SwingConstants.CENTER);
		TypeTboxSearch.setColumns(10);
		TypeTboxSearch.setBounds(310, 36, 90, 26);
		getContentPane().add(TypeTboxSearch);

		PriceTboxSearch = new JTextField();
		PriceTboxSearch.setHorizontalAlignment(SwingConstants.CENTER);
		PriceTboxSearch.setColumns(10);
		PriceTboxSearch.setBounds(410, 36, 90, 26);
		getContentPane().add(PriceTboxSearch);

		StatusTboxSearch = new JTextField();
		StatusTboxSearch.setHorizontalAlignment(SwingConstants.CENTER);
		StatusTboxSearch.setColumns(10);
		StatusTboxSearch.setBounds(510, 36, 90, 26);
		getContentPane().add(StatusTboxSearch);

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

		ModifyBtn = new JButton("Modify");
		ModifyBtn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		ModifyBtn.setBounds(75, 486, 190, 34);
		getContentPane().add(ModifyBtn);

		IDTboxModify = new JTextField();
		IDTboxModify.setHorizontalAlignment(SwingConstants.CENTER);
		IDTboxModify.setColumns(10);
		IDTboxModify.setBounds(75, 445, 90, 26);
		getContentPane().add(IDTboxModify);

		pdateTboxModify = new JTextField();
		pdateTboxModify.setHorizontalAlignment(SwingConstants.CENTER);
		pdateTboxModify.setColumns(10);
		pdateTboxModify.setBounds(175, 445, 90, 26);
		getContentPane().add(pdateTboxModify);

		TypeTboxModify = new JTextField();
		TypeTboxModify.setHorizontalAlignment(SwingConstants.CENTER);
		TypeTboxModify.setColumns(10);
		TypeTboxModify.setBounds(275, 445, 90, 26);
		getContentPane().add(TypeTboxModify);

		PriceTboxModify = new JTextField();
		PriceTboxModify.setHorizontalAlignment(SwingConstants.CENTER);
		PriceTboxModify.setColumns(10);
		PriceTboxModify.setBounds(375, 445, 90, 26);
		getContentPane().add(PriceTboxModify);

		StatusTboxModify = new JTextField();
		StatusTboxModify.setHorizontalAlignment(SwingConstants.CENTER);
		StatusTboxModify.setColumns(10);
		StatusTboxModify.setBounds(475, 445, 90, 26);
		getContentPane().add(StatusTboxModify);

		JLabel lblId_1 = new JLabel("ID");
		lblId_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblId_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblId_1.setBounds(75, 421, 90, 20);
		getContentPane().add(lblId_1);

		JLabel lblPurchaseDate_1 = new JLabel("Purchase Date");
		lblPurchaseDate_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPurchaseDate_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPurchaseDate_1.setBounds(175, 421, 90, 20);
		getContentPane().add(lblPurchaseDate_1);

		JLabel lblType_1 = new JLabel("Type");
		lblType_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblType_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblType_1.setBounds(275, 421, 90, 20);
		getContentPane().add(lblType_1);

		JLabel lblPrice_1 = new JLabel("Price");
		lblPrice_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrice_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPrice_1.setBounds(375, 421, 90, 20);
		getContentPane().add(lblPrice_1);

		JLabel lblStatus_1 = new JLabel("Status");
		lblStatus_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblStatus_1.setBounds(475, 421, 90, 20);
		getContentPane().add(lblStatus_1);

		SearchBtn = new JButton("Search");
		SearchBtn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		SearchBtn.setBounds(10, 73, 190, 34);
		getContentPane().add(SearchBtn);

		table = new JTable();
		table.setBounds(10, 118, 590, 292);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 118, 590, 292);
		getContentPane().add(scrollPane);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		HelpBtn = new JButton("Help");
		HelpBtn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		HelpBtn.setBounds(210, 73, 190, 34);
		getContentPane().add(HelpBtn);

		ModifySelectBtn = new JButton("Modify Selected Row");
		ModifySelectBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				disableModificationFields(2);
			}
		});
		ModifySelectBtn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		ModifySelectBtn.setBounds(410, 73, 190, 34);
		getContentPane().add(ModifySelectBtn);

		CancelBtn = new JButton("Cancel");
		CancelBtn.setBounds(375, 486, 190, 34);
		getContentPane().add(CancelBtn);
		FillTable();
		disableModificationFields(0);

		ModifyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Modify_Dialog.this.modifyEntry();
			}
		});

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				if (enableActions) {
					JTable target = (JTable) me.getSource();
					int[] rows = target.getSelectedRows(); // select a row
					if (rows.length > 0) {
						if (me.getClickCount() == 1) {
							disableModificationFields(1);
						}
						if (me.getClickCount() == 2) {
							disableModificationFields(2);
						}
					}
				}
			}
		});
		HelpBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "1. Double click on the table row\n"
						+ "2. Modify data in the text boxes below table\n" + "3. Press 'Modify' button\n", "Help",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		SearchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Modify_Dialog.this.FillTable();
				disableModificationFields(0);
			}
		});
		CancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				disableModificationFields(1);
				enableActions = true;
			}
		});
	}

	private void disableModificationFields(int status) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		if (status == 2) {
			enableActions = false;
			table.setEnabled(false);
			ModifySelectBtn.setEnabled(false);
			SearchBtn.setEnabled(false);
			SrTboxSearch.setEditable(false);
			IDTboxSearch.setEditable(false);
			pdateTboxSearch.setEditable(false);
			TypeTboxSearch.setEditable(false);
			PriceTboxSearch.setEditable(false);
			StatusTboxSearch.setEditable(false);
			selectedRow = table.getSelectedRow();
			SelectedSr = model.getValueAt(selectedRow, 0).toString();
			ModifyBtn.setEnabled(true);
			CancelBtn.setEnabled(true);
			IDTboxModify.setText(model.getValueAt(selectedRow, 1).toString());
			IDTboxModify.setEnabled(true);
			IDTboxModify.setEditable(true);
			pdateTboxModify.setText(model.getValueAt(selectedRow, 2).toString());
			pdateTboxModify.setEnabled(true);
			pdateTboxModify.setEditable(true);
			TypeTboxModify.setText(model.getValueAt(selectedRow, 3).toString());
			TypeTboxModify.setEnabled(true);
			TypeTboxModify.setEditable(true);
			PriceTboxModify.setText(model.getValueAt(selectedRow, 4).toString());
			PriceTboxModify.setEnabled(true);
			PriceTboxModify.setEditable(true);
			StatusTboxModify.setText(model.getValueAt(selectedRow, 5).toString());
			StatusTboxModify.setEnabled(true);
			StatusTboxModify.setEditable(true);

		} else if (status == 1) {
			table.setEnabled(true);
			ModifySelectBtn.setEnabled(true);
			SearchBtn.setEnabled(true);
			SrTboxSearch.setEditable(true);
			IDTboxSearch.setEditable(true);
			pdateTboxSearch.setEditable(true);
			TypeTboxSearch.setEditable(true);
			PriceTboxSearch.setEditable(true);
			StatusTboxSearch.setEditable(true);
			selectedRow = table.getSelectedRow();
			SelectedSr = model.getValueAt(selectedRow, 0).toString();
			ModifyBtn.setEnabled(false);
			CancelBtn.setEnabled(false);
			IDTboxModify.setText(model.getValueAt(selectedRow, 1).toString());
			IDTboxModify.setEnabled(true);
			IDTboxModify.setEditable(false);
			pdateTboxModify.setText(model.getValueAt(selectedRow, 2).toString());
			pdateTboxModify.setEnabled(true);
			pdateTboxModify.setEditable(false);
			TypeTboxModify.setText(model.getValueAt(selectedRow, 3).toString());
			TypeTboxModify.setEnabled(true);
			TypeTboxModify.setEditable(false);
			PriceTboxModify.setText(model.getValueAt(selectedRow, 4).toString());
			PriceTboxModify.setEnabled(true);
			PriceTboxModify.setEditable(false);
			StatusTboxModify.setText(model.getValueAt(selectedRow, 5).toString());
			StatusTboxModify.setEnabled(true);
			StatusTboxModify.setEditable(false);

		} else {
			enableActions = true;
			table.setEnabled(true);
			ModifySelectBtn.setEnabled(false);
			SearchBtn.setEnabled(true);
			SrTboxSearch.setEditable(true);
			IDTboxSearch.setEditable(true);
			pdateTboxSearch.setEditable(true);
			TypeTboxSearch.setEditable(true);
			PriceTboxSearch.setEditable(true);
			StatusTboxSearch.setEditable(true);
			CancelBtn.setEnabled(false);
			ModifyBtn.setEnabled(false);
			IDTboxModify.setText("");
			IDTboxModify.setEnabled(false);
			IDTboxModify.setEditable(false);
			pdateTboxModify.setText("");
			pdateTboxModify.setEnabled(false);
			pdateTboxModify.setEditable(false);
			TypeTboxModify.setText("");
			TypeTboxModify.setEnabled(false);
			TypeTboxModify.setEditable(false);
			PriceTboxModify.setText("");
			PriceTboxModify.setEnabled(false);
			PriceTboxModify.setEditable(false);
			StatusTboxModify.setText("");
			StatusTboxModify.setEnabled(false);
			StatusTboxModify.setEditable(false);
		}
	}

	public void FillTable() {
		try {
			table.clearSelection();
			ResultSet rs = db.getResultSet(this.getSearchString());
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
		searchString[0] = this.SrTboxSearch.getText().trim();
		searchString[1] = this.IDTboxSearch.getText().trim();
		if (checkValidDate(this.pdateTboxSearch.getText().trim())) {
			searchString[2] = this.pdateTboxSearch.getText().trim();
		} else {
			searchString[2] = "";
		}
		searchString[3] = this.TypeTboxSearch.getText().trim();
		searchString[4] = this.PriceTboxSearch.getText().trim();
		searchString[5] = this.StatusTboxSearch.getText().trim();
		return searchString;
	}

	private void modifyEntry() {
		String[] ModifiedData = new String[6];
		if (checkValidDate(this.pdateTboxModify.getText().trim())) {
			int choice = JOptionPane.showOptionDialog(null, "Do you want to modify selected entry?", "Confirmation",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Modify", "Go back" },
					"default");
			if (choice == JOptionPane.YES_OPTION) {
				ModifiedData[0] = this.SelectedSr;
				ModifiedData[1] = this.IDTboxModify.getText().trim();
				ModifiedData[2] = this.pdateTboxModify.getText().trim();
				ModifiedData[3] = this.TypeTboxModify.getText().trim();
				ModifiedData[4] = this.PriceTboxModify.getText().trim();
				ModifiedData[5] = this.StatusTboxModify.getText().trim();
				db.modify_entries(ModifiedData);
				disableModificationFields(0);
				table.clearSelection();
				FillTable();
				table.clearSelection();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Please enter valid date\nThe valid format is: 'YYYY-MM-DD'", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	public void showDialog() {
		this.setVisible(true);
	}
}
