package GUI;
import db_driver.assetdb;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class login_ui extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField textbox_username;
	private JPasswordField textbox_password;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	
	public login_ui() {
		this.setModal(true);
		this.setTitle("Login Prompt");
		assetdb db = new assetdb();
		setBounds(100, 100, 400, 300);
		getContentPane().setLayout(null);
		{
			textbox_username = new JTextField();
			textbox_username.setBounds(140, 89, 120, 25);
			getContentPane().add(textbox_username);
			textbox_username.setColumns(10);
		}
		{
			textbox_password = new JPasswordField();
			textbox_password.setBounds(140, 124, 120, 25);
			getContentPane().add(textbox_password);
		}
		{
			JButton btnNewButton = new JButton("SUMBIT");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (db.login_db(getUsername(), getPassword())) {
						JOptionPane.showMessageDialog(login_ui.this,
								"Hi " + getUsername() + "! You have successfully logged in.", "Login",
								JOptionPane.INFORMATION_MESSAGE);
						dispose();
					} else {
						JOptionPane.showMessageDialog(login_ui.this, "Invalid username or password", "Login",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			btnNewButton.setBounds(140, 159, 120, 21);
			getContentPane().add(btnNewButton);
		}
		{
			lblNewLabel = new JLabel("Log in");
			lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(140, 54, 120, 25);
			getContentPane().add(lblNewLabel);
		}
		{
			lblNewLabel_1 = new JLabel("Username");
			lblNewLabel_1.setBounds(70, 95, 60, 13);
			getContentPane().add(lblNewLabel_1);
		}
		{
			lblNewLabel_2 = new JLabel("Password");
			lblNewLabel_2.setBounds(70, 130, 60, 13);
			getContentPane().add(lblNewLabel_2);
		}
	}

	public String getUsername() {
		return textbox_username.getText().trim();
	}

	public String getPassword() {
		return new String(textbox_password.getPassword());
	}

	public void login() {
		this.setVisible(true);
	}
}
