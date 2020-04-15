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

/*	Import GUI package	*/
import GUI.Createdb_Dialog;
import GUI.Display_Table_Dialog;
import GUI.Error_Dialog;
import GUI.Login_Dialog;
import excel_export.ExcelExporter;

/*Class to handle all SQL back-end*/
public class Assetdb {

	private String dbname, tablename, username, password;
	/* Data members */
	private Connection sqlconnection;
	private Statement sqlstatement;

	/********************************************************************/

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

	/********************************************************************/

	/********************************************************************/

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

	/********************************************************************/

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

	/********************************************************************/

	/* Close the SQL connection */
	public void close_db() {
		try {
			this.sqlconnection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/********************************************************************/

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
						int choice = JOptionPane.showConfirmDialog(null, "The Database '" + this.dbname
								+ "' Already exists but the table '" + this.tablename
								+ "' does not.\n\nPress OK to create new table in the existing database or press cancel to go back.",
								"Confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
						System.out.println(choice);
						if (choice == JOptionPane.CANCEL_OPTION) {
							return true;
						}
					} else {
						e.printStackTrace();
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
			e.printStackTrace();
			Error_Dialog.showError(e);
			return true;
		}
	}

	/********************************************************************/

	/* Display all the entries */
	public void display_db() {
		ResultSet rs = this.getEntries();
		Display_Table_Dialog table = new Display_Table_Dialog();
		table.display_table(rs, this.getRows(rs));
	}

	/********************************************************************/

	/* Write given entry to table */
	public void export_all() {
		ExcelExporter excel = new ExcelExporter();
		ResultSet rs = this.getEntries();
		excel.export(rs, this.getRows(rs));
	}
	/********************************************************************/

	/********************************************************************/

	/* Get all the entries */
	public ResultSet getEntries() {
		ResultSet rs = null;
		try {
			this.sqlstatement = this.sqlconnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			this.sqlstatement.executeUpdate("USE " + this.dbname);
			rs = this.sqlstatement.executeQuery("SELECT * FROM " + this.tablename);
		} catch (final Exception e) {
			e.printStackTrace();
			Error_Dialog.showError(e);
		}
		return rs;
	}

	/********************************************************************/

	/* Get number of rows in a ResultSet */
	public int getRows(ResultSet rs) {
		int count = 0;
		try {
			rs.last();
			count = rs.getRow();
			rs.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
			Error_Dialog.showError(e);
		}
		return count;
	}

	/********************************************************************/

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

	/********************************************************************/

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

	/********************************************************************/

	/* Write given entry to table */
	public void write_entry(final String id, final String date, final String type, final int Price,
			final String Status) {
		try {
			this.sqlstatement.executeUpdate("USE " + this.dbname);
			String query = "INSERT INTO " + this.tablename + "  ?, ?, ?, ?, ?)";
			PreparedStatement stmt = this.sqlconnection.prepareStatement(query);
			this.sqlconnection.createStatement().executeUpdate("USE " + this.dbname);
			stmt.setString(1, id);
			stmt.setString(2, date);
			stmt.setString(3, type);
			stmt.setInt(4, Price);
			stmt.setString(5, Status);
			stmt.execute();
		} catch (final Exception e) {
			e.printStackTrace();
			Error_Dialog.showError(e);
		}
	}
}
