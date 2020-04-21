package GUI;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class About_Dialog extends JDialog {
	private static final long serialVersionUID = 5381052862136001506L;

	public About_Dialog() {
		setModal(true);
		setResizable(false);
		setTitle("About Project");
		setBackground(Color.WHITE);
		getContentPane().setBackground(Color.WHITE);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setBounds(x, y, 516, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		getContentPane().setLayout(null);
		JLabel L = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource("about.png")));
		L.setHorizontalAlignment(SwingConstants.CENTER);
		L.setBounds(0, 0, 500, 500);
		getContentPane().add(L);

		JButton gitbtn = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("git.png")));
		gitbtn.setBackground(Color.WHITE);
		gitbtn.setBounds(208, 511, 100, 40);
		getContentPane().add(gitbtn);
		gitbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
				if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
					try {
						desktop.browse(new URL("https://github.com/shreyask21/assetmgmt/").toURI());
						dispose();
					} catch (Exception ex) {
						Error_Dialog.showError(ex);
					}
				}
			}
		});

	}

	public void showDialog() {
		this.setVisible(true);
	}

}
