package db_driver;

/*	Import SQL packages	*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

/*	Import GUI packages	*/
import GUI.Createdb_Dialog;
import GUI.Delete_Dialog;
import GUI.Display_Table_Dialog;
import GUI.Error_Dialog;
import GUI.Login_Dialog;
import GUI.Modify_Dialog;
import GUI.New_Entry_Dialog;
import GUI.Search_Dialog;
import excel_export.ExcelExporter;

/*Class to handle all SQL back-end*/
public class Assetdb {

	private String dbname, tablename, username, password;
	/* Data members */
	private Connection sqlconnection;
	private Statement sqlstatement;

	/* Add new entry */
	public void add() {
		New_Entry_Dialog dialog = new New_Entry_Dialog(this);
		dialog.showDialog();
	}

	/* Check if MySQL is installed and available */
	private void checkConnection() {
		try {
			DriverManager.getConnection("jdbc:mysql://localhost:3306/");
		} catch (Exception e) {
			if (e instanceof CommunicationsException) {
				e.printStackTrace();
				Error_Dialog.showError(
						"Error Connecting to MySQL Server!\nIs MySQL Server Installed and properly Set-Up?\nPlease confirm and try again...");
				System.exit(0);
			}
		}
	}

	/* Check if given user name and password are valid */
	public boolean checkCreds(final String uname, final String pass) {

		try {
			Connection tempconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", uname, pass);
			tempconnection.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/* Check if given database name and table name are valid */
	public boolean checkDBexisting(final String uname, final String pass, final String db, final String tab) {

		try {
			Connection tempconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db, uname, pass);
			Statement tempstatement = tempconnection.createStatement();
			tempstatement.executeQuery("SELECT * FROM " + tab);
			tempstatement.close();
			tempconnection.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/* Close the SQL connection */
	public void close_db() {
		try {
			this.sqlconnection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* Log in and generate a SQL connection to the given database */
	public boolean connect(final String database_name, final String table_name, boolean action) {
		this.dbname = database_name;
		this.tablename = table_name;
		try {
			if (action) {
				this.sqlconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", this.username,
						this.password);
				this.sqlstatement = this.sqlconnection.createStatement();
				try {
					this.sqlstatement.executeUpdate("CREATE DATABASE " + this.dbname + ";");
				} catch (SQLException e) {
					if (e.getErrorCode() == 1007) {
						int choice = JOptionPane.showOptionDialog(null,
								"The Database '" + this.dbname + "' Already exists but the table '" + this.tablename
										+ "' does not.",
								"Confirmation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
								new String[] { "Create New Table", "Go Back", "Exit Program" }, "default");
						if (choice == JOptionPane.NO_OPTION) {
							return true;
						} else if (choice == JOptionPane.CANCEL_OPTION) {
							System.exit(0);
						}
					} else {
						Error_Dialog.showError(e);
					}
				}
				this.sqlstatement.executeUpdate("USE " + this.dbname);
				this.sqlstatement.executeUpdate("CREATE TABLE " + this.tablename
						+ " (Sr INT(11) NOT NULL AUTO_INCREMENT, ID VARCHAR(255), Purchase_Date DATE, Type VARCHAR(255), Price INT NOT NULL, Status VARCHAR(255), CONSTRAINT db_pk PRIMARY Key(Sr))");
				System.out.println("New database generated...");
				System.out.println("Connecting to new database...");
				System.out.println("Connected to the new datbase...");
				return false;
			} else {
				this.sqlconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.dbname,
						this.username, this.password);
				this.sqlconnection.createStatement().executeUpdate("USE " + this.dbname);
				System.out.println("Connected to database...");
				return false;
			}
		} catch (final Exception e) {
			Error_Dialog.showError(e);
			return true;
		}
	}

	/* Delete particular entries */
	public void delete() {
		Delete_Dialog dialog = new Delete_Dialog(this);
		dialog.showDialog();
	}

	/* Delete particular entries */
	public void deleteEntrySr(String Sr) {
		try {
			this.sqlconnection.createStatement().executeUpdate("DELETE FROM " + this.tablename + " WHERE Sr = " + Sr);
		} catch (final Exception e) {
			Error_Dialog.showError(e);
		}
	}

	/* Display all the entries */
	public void display() {
		ResultSet rs = this.getEntries();
		Display_Table_Dialog table = new Display_Table_Dialog(rs);
		table.showDialog();
	}

	/* Display entries in a ResultSet */
	public void display(ResultSet rs) {
		Display_Table_Dialog table = new Display_Table_Dialog(rs);
		table.showDialog();
	}

	/* Export all entries to excel file */
	public void export() {
		ExcelExporter excel = new ExcelExporter();
		ResultSet rs = this.getEntries();
		excel.export(rs, this.getRows(rs));
	}

	/* Get all the entries */
	public ResultSet getEntries() {
		ResultSet rs = null;
		try {
			this.sqlstatement = this.sqlconnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			this.sqlstatement.executeUpdate("USE " + this.dbname);
			rs = this.sqlstatement.executeQuery("SELECT * FROM " + this.tablename);
		} catch (final Exception e) {
			Error_Dialog.showError(e);
		}
		return rs;
	}

	/* Get particular entries */
	public ResultSet getResultSet(String[] data) {
		ResultSet rs = null;
		String[] columnID = { "Sr", "ID", "Purchase_Date", "Type", "Price", "Status" };
		boolean firstValue = true;
		String query = "SELECT * FROM " + this.tablename;
		for (int i = 0; i < data.length; i++) {
			if (!(data[i].isBlank() || data[i] == null)) {
				if (firstValue) {
					query += " WHERE ";
					firstValue = false;
				} else {
					query += " AND ";
				}
				query += " " + columnID[i] + " = '" + data[i] + "' ";
			}
		}
		try {
			rs = this.sqlconnection.createStatement().executeQuery(query);
		} catch (Exception e) {
			Error_Dialog.showError(e);
		}
		return rs;
	}

	/* Get number of rows in a ResultSet */
	public int getRows(ResultSet rs) {
		int count = 0;
		try {
			rs.last();
			count = rs.getRow();
			rs.beforeFirst();
		} catch (SQLException e) {
			Error_Dialog.showError(e);
		}
		return count;
	}

	/* Initialize and connect to database */
	public void init() {
		Login_Dialog login = new Login_Dialog();
		Createdb_Dialog choice = new Createdb_Dialog();
		this.checkConnection();
		/* Keep showing login dialog until correct credentials are received */
		while (this.setCreds(login.getUsername(), login.getPassword())) {
			/* Show login UI dialog */
			login.showDialog();
		}

		/* Create database connection with details from GUI */
		do {
			/* Show create database UI dialog */
			choice.showDialog(login.getUsername(), login.getPassword());
		} while (this.connect(choice.getDBName(), choice.getTableName(), choice.getAction()));
	}

	public void init(String uname, String psw, String dbname, String tabname) {
		this.checkConnection();
		this.setCreds(uname, psw);
		this.connect(dbname, tabname, false);
	}

	/* Modify entries */
	public void modify() {
		Modify_Dialog dialog = new Modify_Dialog(this);
		dialog.showDialog();

	}

	/* Modify particular entry */
	public void modify_entries(String[] data) {
		try {
			String query = "UPDATE " + this.tablename
					+ " SET ID= ?, Purchase_Date= ?, Type= ?, Price= ?, Status= ? WHERE Sr= ?";
			PreparedStatement stmt = this.sqlconnection.prepareStatement(query);
			stmt.setString(1, data[1]);
			stmt.setString(2, data[2]);
			stmt.setString(3, data[3]);
			stmt.setString(4, data[4]);
			stmt.setString(5, data[5]);
			stmt.setString(6, data[0]);
			stmt.execute();
		} catch (final Exception e) {
			Error_Dialog.showError(e);
		}
	}

	/* Search entries */
	public void search() {
		Search_Dialog srch = new Search_Dialog(this);
		srch.showDialog();
	}

	/*
	 * As the user name and password are checked from different object we need a
	 * setter to copy them
	 */
	public boolean setCreds(final String uname, final String pass) {
		if (this.checkCreds(uname, pass)) {
			this.username = uname;
			this.password = pass;
			return false;
		} else {
			return true;
		}
	}

	/* Write given entry to table */
	public void write_entry(String[] data) {
		try {
			this.sqlstatement.executeUpdate("USE " + this.dbname);
			String query = "INSERT INTO " + this.tablename
					+ " (ID, Purchase_Date, Type, Price, Status) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement stmt = this.sqlconnection.prepareStatement(query);
			stmt.setString(1, data[0]);
			stmt.setString(2, data[1]);
			stmt.setString(3, data[2]);
			stmt.setString(4, data[3]);
			stmt.setString(5, data[4]);
			stmt.execute();
		} catch (final Exception e) {
			Error_Dialog.showError(e);
		}
	}
}
