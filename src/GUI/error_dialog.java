package GUI;

import javax.swing.JOptionPane;

public class error_dialog {
	public static void showError(String error) {
		JOptionPane.showMessageDialog (null, error, "Something went Wrong", JOptionPane.ERROR_MESSAGE);
	}
}
