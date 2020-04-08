package GUI;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;

public class addentry_dialog extends JDialog {
	private static final long serialVersionUID = -7667870049504151994L;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					addentry_dialog dialog = new addentry_dialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public addentry_dialog() {
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(179, 91, 85, 21);
		getContentPane().add(btnNewButton);

	}
	
	/*Displays the GUI to user*/
	public void showDialog() {
		this.setVisible(true);
	}
}
