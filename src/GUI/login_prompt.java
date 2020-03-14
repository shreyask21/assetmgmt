package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login_prompt {
	public JFrame LoginForm;
	private JTextField tbox_uname;
	private JPasswordField tbox_psw;
	private String username;
	private String password;

	public login_prompt() {
		initialize();
	}

	private void initialize() {
		LoginForm = new JFrame();
		LoginForm.setTitle("Asset Management System");
		LoginForm.setBounds(450, 220, 510, 320);
		LoginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LoginForm.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(95, 89, 75, 13);
		LoginForm.getContentPane().add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPassword.setBounds(95, 135, 75, 13);
		LoginForm.getContentPane().add(lblPassword);
		
		tbox_uname = new JTextField();
		tbox_uname.setBounds(180, 84, 150, 25);
		LoginForm.getContentPane().add(tbox_uname);
		tbox_uname.setColumns(10);
		
		tbox_psw = new JPasswordField();
		tbox_psw.setBounds(180, 130, 150, 25);
		LoginForm.getContentPane().add(tbox_psw);
		
		JLabel lblNewLabel_1 = new JLabel("Log in");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Calibri", Font.BOLD, 25));
		lblNewLabel_1.setBounds(180, 29, 150, 30);
		LoginForm.getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCreds(tbox_uname.getText(), new String(tbox_psw.getPassword()));
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton.setBounds(180, 185, 150, 40);
		LoginForm.getContentPane().add(btnNewButton);
	}
	
	private void setCreds(String uname, String psw) {
		this.username = uname;
		this.password = psw;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
}
