package GUI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainUI extends JDialog {
	static final long serialVersionUID = -9205958915197635758L;
	public int Choice = 0;

	public MainUI() {
		getContentPane().setBackground(Color.WHITE);
		setModal(true);
		setResizable(false);
		setTitle("Asset Management System Menu");
		setAlwaysOnTop(false);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setBounds(400, 300, 626, 500);
		setLocation(x, y);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		JLabel Logo = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource("logo.png")));
		Logo.setHorizontalAlignment(SwingConstants.CENTER);
		Logo.setBounds(210, 11, 190, 191);
		getContentPane().add(Logo);

		JButton WriteBtn = new JButton("New Entry");

		WriteBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		WriteBtn.setBounds(210, 250, 190, 50);
		getContentPane().add(WriteBtn);

		JButton DisplayBtn = new JButton("Display Entries");
		DisplayBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		DisplayBtn.setBounds(10, 250, 190, 50);
		getContentPane().add(DisplayBtn);

		JButton DeleteBtn = new JButton("Delete Entry");
		DeleteBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		DeleteBtn.setBounds(410, 250, 190, 50);
		getContentPane().add(DeleteBtn);

		JButton SearchBtn = new JButton("Search Entries");
		SearchBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		SearchBtn.setBounds(10, 311, 190, 50);
		getContentPane().add(SearchBtn);

		JButton btnModifyEntries = new JButton("Modify Entries");

		btnModifyEntries.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnModifyEntries.setBounds(210, 311, 190, 50);
		getContentPane().add(btnModifyEntries);

		JButton ExportBtn = new JButton("Export Database");

		ExportBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ExportBtn.setBounds(410, 311, 190, 50);
		getContentPane().add(ExportBtn);

		JButton ExitBtn = new JButton("EXIT");
		ExitBtn.setForeground(new Color(255, 0, 0));
		ExitBtn.setFont(new Font("Tahoma", Font.PLAIN, 19));
		ExitBtn.setBounds(210, 372, 190, 50);
		getContentPane().add(ExitBtn);

		JButton AboutBtn = new JButton("About Project");
		AboutBtn.setForeground(Color.GRAY);
		AboutBtn.setFont(new Font("Tahoma", Font.PLAIN, 9));
		AboutBtn.setBackground(Color.WHITE);
		AboutBtn.setBounds(500, 437, 110, 23);
		getContentPane().add(AboutBtn);

		ExitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int reply = JOptionPane.showConfirmDialog(null, "Do you really want to exit?", "Confirmation",
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					MainUI.this.Choice = 0;
					dispose();
				} else {
					MainUI.this.Choice = -1;
				}
			}
		});
		DisplayBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainUI.this.Choice = 1;
				dispose();
			}
		});
		WriteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainUI.this.Choice = 2;
				dispose();
			}
		});
		DeleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainUI.this.Choice = 3;
				dispose();
			}
		});

		SearchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainUI.this.Choice = 4;
				dispose();
			}
		});
		btnModifyEntries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainUI.this.Choice = 5;
				dispose();
			}
		});
		ExportBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainUI.this.Choice = 6;
				dispose();
			}
		});
		AboutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				about info = new about();
				info.showDialog();
			}
		});
	}

	public void showDialog() {
		this.setVisible(true);
	}
}
