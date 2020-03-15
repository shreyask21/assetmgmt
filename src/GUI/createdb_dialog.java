package GUI;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class createdb_dialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 718615945910521098L;
	private final JPanel contentPanel = new JPanel();
	private JTextField tbox_dbname;
	private JTextField tbox_tablename;
	private boolean action = true;

	public createdb_dialog() {
		this.setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(400, 300, 400, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JRadioButton radio_newdb = new JRadioButton("Create New Database");
		radio_newdb.setFont(new Font("Tahoma", Font.PLAIN, 13));
		radio_newdb.setBounds(35, 20, 153, 21);
		contentPanel.add(radio_newdb);
		radio_newdb.setActionCommand("new");
		radio_newdb.setSelected(true);

		JRadioButton radio_olddb = new JRadioButton("Use Existing database");
		radio_olddb.setFont(new Font("Tahoma", Font.PLAIN, 13));
		radio_olddb.setBounds(210, 20, 147, 21);
		contentPanel.add(radio_olddb);
		radio_olddb.setActionCommand("old");

		ButtonGroup group = new ButtonGroup();
		group.add(radio_newdb);
		group.add(radio_olddb);

		radio_newdb.addActionListener(this);
		radio_olddb.addActionListener(this);

		tbox_dbname = new JTextField();
		tbox_dbname.setBounds(135, 95, 130, 25);
		contentPanel.add(tbox_dbname);
		tbox_dbname.setColumns(10);

		tbox_tablename = new JTextField();
		tbox_tablename.setColumns(10);
		tbox_tablename.setBounds(135, 155, 130, 25);
		contentPanel.add(tbox_tablename);

		JButton okbtn = new JButton("OK");
		okbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		okbtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		okbtn.setBounds(135, 218, 130, 25);
		contentPanel.add(okbtn);

		JLabel lblNewLabel = new JLabel("Database Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(35, 101, 93, 13);
		contentPanel.add(lblNewLabel);

		JLabel lblTableName = new JLabel("Table Name");
		lblTableName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTableName.setBounds(56, 161, 69, 13);
		contentPanel.add(lblTableName);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if ("new".equals(e.getActionCommand())) {
			this.action = true;
		} else {
			this.action = false;
		}

	}
	public void showDialog() {
		this.setVisible(true);
	}

	public ModalityType getSetModal() {
		return getModalityType();
	}

	public void setSetModal(ModalityType modalityType) {
		setModalityType(modalityType);
	}

	public boolean getAction() {
		return action;
	}

	public String getDBName() {
		return tbox_dbname.getText().trim();
	}

	public String getTableName() {
		return tbox_tablename.getText().trim();
	}
}
