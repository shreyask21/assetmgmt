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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import db_driver.Assetdb;

public class New_Entry_Dialog extends JDialog {
	private static final long serialVersionUID = -6344531645190860298L;

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

	private JButton AppendBtn;
	private Assetdb db;
	private JTextField IDTbox;
	private JTextField pdateTbox;
	private JTextField PriceTbox;
	private JTextField StatusTbox;
	private JTable table;
	private boolean[] textBoxesFilled = new boolean[5];

	private JTextField TypeTbox;

	public New_Entry_Dialog(Assetdb dbobj) {
		super((JDialog)null);
		this.db = dbobj;
		this.setTitle("New Entry");
		setModal(true);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(false);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		this.setBounds(400, 300, 629, 460);
		this.setLocationRelativeTo(null);
		this.setLocation(x, y);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);

		AppendBtn = new JButton("Append Entry");
		AppendBtn.setEnabled(false);
		AppendBtn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		AppendBtn.setBounds(185, 375, 280, 34);
		getContentPane().add(AppendBtn);

		IDTbox = new JTextField();
		IDTbox.setHorizontalAlignment(SwingConstants.CENTER);
		IDTbox.setColumns(10);
		IDTbox.setBounds(75, 338, 90, 26);
		getContentPane().add(IDTbox);

		pdateTbox = new JTextField();
		pdateTbox.setHorizontalAlignment(SwingConstants.CENTER);
		pdateTbox.setColumns(10);
		pdateTbox.setBounds(175, 338, 90, 26);
		getContentPane().add(pdateTbox);

		TypeTbox = new JTextField();
		TypeTbox.setHorizontalAlignment(SwingConstants.CENTER);
		TypeTbox.setColumns(10);
		TypeTbox.setBounds(275, 338, 90, 26);
		getContentPane().add(TypeTbox);

		PriceTbox = new JTextField();
		PriceTbox.setHorizontalAlignment(SwingConstants.CENTER);
		PriceTbox.setColumns(10);
		PriceTbox.setBounds(375, 338, 90, 26);
		getContentPane().add(PriceTbox);

		StatusTbox = new JTextField();
		StatusTbox.setHorizontalAlignment(SwingConstants.CENTER);
		StatusTbox.setColumns(10);
		StatusTbox.setBounds(475, 338, 90, 26);
		getContentPane().add(StatusTbox);

		JLabel lblId_1 = new JLabel("ID");
		lblId_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblId_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblId_1.setBounds(75, 314, 90, 20);
		getContentPane().add(lblId_1);

		JLabel lblPurchaseDate_1 = new JLabel("Purchase Date");
		lblPurchaseDate_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPurchaseDate_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPurchaseDate_1.setBounds(175, 314, 90, 20);
		getContentPane().add(lblPurchaseDate_1);

		JLabel lblType_1 = new JLabel("Type");
		lblType_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblType_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblType_1.setBounds(275, 314, 90, 20);
		getContentPane().add(lblType_1);

		JLabel lblPrice_1 = new JLabel("Price");
		lblPrice_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrice_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPrice_1.setBounds(375, 314, 90, 20);
		getContentPane().add(lblPrice_1);

		JLabel lblStatus_1 = new JLabel("Status");
		lblStatus_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblStatus_1.setBounds(475, 314, 90, 20);
		getContentPane().add(lblStatus_1);

		table = new JTable();
		table.setBounds(10, 118, 590, 292);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 590, 292);
		getContentPane().add(scrollPane);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		FillTable();

		IDTbox.getDocument().addDocumentListener(new DocumentListener() {
			public void changed() {
				if (IDTbox.getText().equals("")) {
					textBoxesFilled[0] = false;
				} else {
					textBoxesFilled[0] = true;
				}
				checkAllBoxesFilled();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				changed();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				changed();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				changed();
			}
		});

		pdateTbox.getDocument().addDocumentListener(new DocumentListener() {
			public void changed() {
				if (pdateTbox.getText().equals("")) {
					textBoxesFilled[1] = false;
				} else {
					textBoxesFilled[1] = true;
				}
				checkAllBoxesFilled();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				changed();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				changed();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				changed();
			}
		});

		TypeTbox.getDocument().addDocumentListener(new DocumentListener() {
			public void changed() {
				if (TypeTbox.getText().equals("")) {
					textBoxesFilled[2] = false;
				} else {
					textBoxesFilled[2] = true;
				}
				checkAllBoxesFilled();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				changed();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				changed();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				changed();
			}
		});
		PriceTbox.getDocument().addDocumentListener(new DocumentListener() {
			public void changed() {
				if (PriceTbox.getText().equals("")) {
					textBoxesFilled[3] = false;
				} else {
					textBoxesFilled[3] = true;
				}
				checkAllBoxesFilled();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				changed();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				changed();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				changed();
			}
		});

		StatusTbox.getDocument().addDocumentListener(new DocumentListener() {
			public void changed() {
				if (StatusTbox.getText().equals("")) {
					textBoxesFilled[4] = false;
				} else {
					textBoxesFilled[4] = true;
				}
				checkAllBoxesFilled();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				changed();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				changed();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				changed();
			}
		});

		AppendBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				New_Entry_Dialog.this.appendEntry();
			}
		});
	}

	private void appendEntry() {
		String[] newData = new String[5];
		if (checkValidDate(this.pdateTbox.getText().trim())) {
			int choice = JOptionPane.showOptionDialog(null, "Do you want to add selected entry?", "Confirmation",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Add", "Go back" },
					"default");
			if (choice == JOptionPane.YES_OPTION) {
				newData[0] = this.IDTbox.getText().trim();
				newData[1] = this.pdateTbox.getText().trim();
				newData[2] = this.TypeTbox.getText().trim();
				newData[3] = this.PriceTbox.getText().trim();
				newData[4] = this.StatusTbox.getText().trim();
				db.write_entry(newData);
				FillTable();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Please enter valid date\nThe valid format is: 'YYYY-MM-DD'", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private void checkAllBoxesFilled() {
		boolean allTextBoxesFilled = true;
		for (boolean CurrentTextBox : textBoxesFilled) {
			allTextBoxesFilled &= CurrentTextBox;
		}
		if (allTextBoxesFilled) {
			AppendBtn.setEnabled(true);
		} else {
			AppendBtn.setEnabled(false);
		}
	}

	public void FillTable() {
		try {
			table.clearSelection();
			ResultSet rs = db.getEntries();
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
