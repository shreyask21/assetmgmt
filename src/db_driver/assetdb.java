package db_driver;

/*	Import SQL package	*/
import java.sql.*;
import java.util.Scanner;

/*	Import GUI package	*/
import GUI.*;

/*Class to handle all SQL back-end*/
public class assetdb {

	/* Data members */
	private Connection sqlconnection;
	private Statement sqlstatement;
	private String dbname, tablename, username, password;
	public Scanner in = new Scanner(System.in);

	/* Initialize and connect to database */
	public void init() {
		login_dialog login = new login_dialog();
		createdb_dialog choice = new createdb_dialog();

		/* Keep showing login dialog until correct credentials are received */
		while (this.setCreds(login.getUsername(), login.getPassword())) {
			/* Show login UI dialog */
			login.showDialog();
		}

		/* Show create database UI dialog */
		choice.showDialog(login.getUsername(), login.getPassword());

		/* Create database connection with details from GUI */
		this.connect(choice.getDBName(), choice.getTableName(), choice.getAction());
	}

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

	/* Log in and generate a SQL connection to the given database */
	public void connect(final String database_name, final String table_name, boolean action) {
		this.dbname = database_name;
		this.tablename = table_name;
		try {
			if (action) {
				this.sqlconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", this.username,
						this.password);
				this.sqlstatement = this.sqlconnection.createStatement();
				this.sqlstatement.executeUpdate("CREATE DATABASE " + this.dbname + ";");
				this.sqlstatement.executeUpdate("USE " + this.dbname);
				this.sqlstatement.executeUpdate("CREATE TABLE " + this.tablename
						+ " (Sr INT(11) NOT NULL AUTO_INCREMENT, ID VARCHAR(255), Purchase_Date DATE, Type VARCHAR(255), Price INT NOT NULL, Status VARCHAR(255), CONSTRAINT db_pk PRIMARY Key(Sr))");
				System.out.println("New database generated...");
				System.out.println("Connecting to new database...");
				System.out.println("Connected to the new datbase...");
			} else {
				this.sqlconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.dbname,
						this.username, this.password);
				this.sqlconnection.createStatement().executeUpdate("USE " + this.dbname);
				System.out.println("Connected to database...");
			}
		} catch (final Exception e) {
			e.printStackTrace();
			error_dialog.showError(e.getMessage());
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
			error_dialog.showError(e.getMessage());
		}
	}

	/********************************************************************/

	/* Display all the entries */
	public void display_db() {
		ResultSet rs = null;
		display_dialog table = new display_dialog();
		try {
			this.sqlstatement = this.sqlconnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			this.sqlstatement.executeUpdate("USE " + this.dbname);
			rs = this.sqlstatement.executeQuery("SELECT * FROM " + this.tablename);
			table.display_table(rs, this.getRows(rs));
		} catch (final Exception e) {
			e.printStackTrace();
			error_dialog.showError(e.getMessage());
		}
	}

	/********************************************************************/

	/* Get number of rows in a ResultSet */
	private int getRows(ResultSet rs) {
		int count = 0;
		try {
			rs.last();
			count = rs.getRow();
			rs.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
			error_dialog.showError(e.getMessage());
		}
		return count;
	}

	/********************************************************************/

	/* Close the SQL connection */
	public void close_db() {
		this.in.close();
		try {
			this.sqlconnection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/********************************************************************/
}
