package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import db_driver.Assetdb;

/*Shows the login dialog*/
public class Login_Dialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JPasswordField textbox_password;
	private JTextField textbox_username;

	/* Generates layout */
	public Login_Dialog() {
		/* Create layout */
		super((JDialog)null);
		setModal(true);
		setResizable(false);
		setTitle("Please Login");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		Assetdb db = new Assetdb();
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setBounds(400, 300, 400, 300);
		setLocation(x, y);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		textbox_username = new JTextField();
		textbox_username.setBounds(140, 89, 120, 25);
		getContentPane().add(textbox_username);
		textbox_username.setColumns(10);
		textbox_password = new JPasswordField();
		textbox_password.setBounds(140, 124, 120, 25);
		getContentPane().add(textbox_password);

		JButton submitbtn = new JButton("SUMBIT");
		submitbtn.setForeground(new Color(0, 128, 0));
		submitbtn.setBounds(140, 159, 120, 25);
		submitbtn.setMnemonic(KeyEvent.VK_ENTER);
		getContentPane().add(submitbtn);
		submitbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!db.checkCreds(getUsername(), getPassword())) {
					JOptionPane.showMessageDialog(Login_Dialog.this, "Invalid username or password", "Login",
							JOptionPane.ERROR_MESSAGE);
				} else {
					dispose();
				}
			}
		});

		lblNewLabel = new JLabel("Log in");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(140, 54, 120, 25);
		getContentPane().add(lblNewLabel);

		lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setBounds(70, 95, 60, 13);
		getContentPane().add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setBounds(70, 130, 60, 13);
		getContentPane().add(lblNewLabel_2);

		JButton exitbtn = new JButton("EXIT");
		exitbtn.setForeground(new Color(255, 0, 0));
		exitbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.exit(ABORT);
			}
		});
		exitbtn.setBounds(140, 194, 120, 25);
		getContentPane().add(exitbtn);

	}

	public String getPassword() {
		return new String(textbox_password.getPassword());
	}

	public String getUsername() {
		return textbox_username.getText().trim();
	}

	/* Displays the GUI to user */
	public void showDialog() {
		this.setVisible(true);
	}
}
