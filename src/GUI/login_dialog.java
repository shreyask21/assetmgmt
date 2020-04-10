package GUI;

import db_driver.assetdb;
import GUI.about;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;

/*Shows the login dialog*/
public class login_dialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField textbox_username;
	private JPasswordField textbox_password;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JButton aboutbtn;

	/* Generates layout */
	public login_dialog() {
		/* Create layout */
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		this.setTitle("Login Prompt");
		this.setAlwaysOnTop(false);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		assetdb db = new assetdb();
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
			public void actionPerformed(ActionEvent e) {
				if (!db.checkCreds(getUsername(), getPassword())) {
					JOptionPane.showMessageDialog(login_dialog.this, "Invalid username or password", "Login",
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
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.exit(ABORT);
			}
		});
		exitbtn.setBounds(140, 194, 120, 25);
		getContentPane().add(exitbtn);
		
		aboutbtn = new JButton("About");
		aboutbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				about info = new about();
				info.display();
			}
		});
		aboutbtn.setForeground(Color.DARK_GRAY);
		aboutbtn.setBackground(Color.WHITE);
		aboutbtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		aboutbtn.setBounds(316, 238, 68, 23);
		getContentPane().add(aboutbtn);

	}

	public String getUsername() {
		return textbox_username.getText().trim();
	}

	public String getPassword() {
		return new String(textbox_password.getPassword());
	}

	/* Displays the GUI to user */
	public void showDialog() {
		this.setVisible(true);
	}
}
