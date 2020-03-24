package GUI;

import java.awt.EventQueue;

import javax.swing.JDialog;

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
		setBounds(100, 100, 450, 300);

	}
	
	/*Displays the GUI to user*/
	public void showDialog() {
		this.setVisible(true);
	}

}
