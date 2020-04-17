package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import db_driver.Assetdb;

/*Shows the database creation option dialog*/
public class Createdb_Dialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 718615945910521098L;
	private boolean action = false; // Default: existing database
	private JPanel contentPanel = new JPanel();
	private JTextField tbox_dbname;
	private JTextField tbox_tablename;
	private String username, password;

	/* Generates layout */
	public Createdb_Dialog() {
		super((JDialog)null);
		Assetdb db = new Assetdb();
		/* Create layout */
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setModal(true);
		setAlwaysOnTop(true);
		setTitle("Choose What To Do...");
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setBounds(400, 300, 400, 300);
		setLocation(x, y);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		/* Create radio buttons */
		JRadioButton radio_newdb = new JRadioButton("Create New Database");
		radio_newdb.setFont(new Font("Tahoma", Font.PLAIN, 13));
		radio_newdb.setBounds(35, 20, 153, 21);
		contentPanel.add(radio_newdb);
		radio_newdb.setActionCommand("new");

		JRadioButton radio_olddb = new JRadioButton("Use Existing database");
		radio_olddb.setFont(new Font("Tahoma", Font.PLAIN, 13));
		radio_olddb.setBounds(210, 20, 168, 21);
		contentPanel.add(radio_olddb);
		radio_olddb.setActionCommand("old");
		radio_olddb.setSelected(true);

		/* Group radio buttons so only one can be pushed */
		ButtonGroup group = new ButtonGroup();
		group.add(radio_olddb);
		group.add(radio_newdb);

		/* Add action listeners to both radio buttons. Calls actionPerformed() */
		radio_newdb.addActionListener(this);
		radio_olddb.addActionListener(this);

		/* Create text boxes */
		tbox_dbname = new JTextField();
		tbox_dbname.setBounds(135, 88, 130, 25);
		contentPanel.add(tbox_dbname);
		tbox_dbname.setColumns(10);

		tbox_tablename = new JTextField();
		tbox_tablename.setColumns(10);
		tbox_tablename.setBounds(135, 123, 130, 25);
		contentPanel.add(tbox_tablename);

		/* Create labels for text boxes */
		JLabel lblNewLabel = new JLabel("Database Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(35, 93, 93, 13);
		contentPanel.add(lblNewLabel);

		JLabel lblTableName = new JLabel("Table Name");
		lblTableName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTableName.setBounds(56, 128, 69, 13);
		contentPanel.add(lblTableName);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}

		JButton exitbtn = new JButton("EXIT");
		exitbtn.setForeground(new Color(255, 0, 0));
		exitbtn.setBounds(135, 193, 130, 25);
		contentPanel.add(exitbtn);
		exitbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.exit(0);
			}
		});

		JButton okbtn = new JButton("OK");
		okbtn.setForeground(new Color(0, 128, 0));
		okbtn.setBounds(135, 158, 130, 25);
		contentPanel.add(okbtn);
		okbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (action) {
					if (!db.checkDBexisting(Createdb_Dialog.this.username, Createdb_Dialog.this.password, getDBName(),
							getTableName())) {
						dispose();
					} else {
						JOptionPane.showMessageDialog(Createdb_Dialog.this, "Database and table already exists",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					if (db.checkDBexisting(Createdb_Dialog.this.username, Createdb_Dialog.this.password, getDBName(),
							getTableName())) {
						dispose();
					} else {
						JOptionPane.showMessageDialog(Createdb_Dialog.this, "Invalid database or table", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

	}

	/* Setter for radio buttons */
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("new".equals(e.getActionCommand())) {
			this.action = true;
		} else {
			this.action = false;
		}

	}

	/* Returns the action type. true-> new database, false -> existing */
	public boolean getAction() {
		return action;
	}

	/* Getters to return the data entered in text boxes */
	public String getDBName() {
		return tbox_dbname.getText().trim();
	}

	public String getTableName() {
		return tbox_tablename.getText().trim();
	}

	/* Displays the GUI to user */
	public void showDialog(String usrn, String psw) {
		this.username = usrn;
		this.password = psw;
		this.setVisible(true);
	}
}
