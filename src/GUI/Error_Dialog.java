package GUI;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;

public class Error_Dialog {
	public static void showError(Exception e) {
		e.printStackTrace();
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		int choice = JOptionPane.showOptionDialog(null, "The Stack Trace:\n" + sw.toString(),
				"Something went wrong  :(", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null,
				new String[] { "Continue", "Exit program" }, "default");
		if (choice == JOptionPane.NO_OPTION) {
			System.exit(0);
		}
	}

	public static void showError(String error) {
		JOptionPane.showMessageDialog(null, error, "Something went wrong  :(", JOptionPane.ERROR_MESSAGE);
	}

	public static void showInfo(String info) {
		JOptionPane.showMessageDialog(null, info, "Information", JOptionPane.INFORMATION_MESSAGE);
	}
}
