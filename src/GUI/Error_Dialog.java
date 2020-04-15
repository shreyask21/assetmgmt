package GUI;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;

public class Error_Dialog {
	public static void showError(Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		JOptionPane.showMessageDialog(null, "The Stack Trace:\n" + sw.toString(), "Something went wrong  :(",
				JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}

	public static void showError(String error) {
		JOptionPane.showMessageDialog(null, error, "Something went wrong  :(", JOptionPane.ERROR_MESSAGE);
	}

	public static void showInfo(String info) {
		JOptionPane.showMessageDialog(null, info, "Information", JOptionPane.INFORMATION_MESSAGE);
	}
}
