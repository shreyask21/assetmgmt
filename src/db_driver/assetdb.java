package db_driver;

/*Import SQL package*/
import java.sql.*;
import GUI.error_dialog;

/*Class to handle all SQL back-end*/
public class assetdb {

	/* Data members */
	private Connection sqlconnection;
	private Statement sqlstatement;
	private ResultSet sqlresultset;
	private String dbname, tablename, username, password;

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

	public boolean checkDBnew(final String uname, final String pass, final String db, final String tab) {

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

	/* Log in and generate a SQL connection to the given database */
	public Boolean connect(final String database_name, final String table_name, boolean action) throws SQLException {
		this.dbname = database_name;
		this.tablename = table_name;
		try {
			if (action) {
				this.sqlconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", this.username,
						this.password);
				this.sqlstatement = this.sqlconnection.createStatement();
				this.sqlstatement.executeUpdate("CREATE DATABASE " + this.dbname);
				this.sqlstatement.executeUpdate("USE " + this.dbname);
				this.sqlstatement.executeUpdate("CREATE TABLE " + this.tablename
						+ " (Sr INT(11) NOT NULL AUTO_INCREMENT, ID VARCHAR(255), Purchase_Date DATE, Type VARCHAR(255), Price INT NOT NULL, Status VARCHAR(255), CONSTRAINT db_pk PRIMARY Key(Sr))");
				System.out.println("New database generated...");
				System.out.println("Connecting to new database...");
				System.out.println("Connected to the new datbase...");
				return true;
			} else {
				this.sqlconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.dbname,
						this.username, this.password);
				this.sqlconnection.createStatement().executeUpdate("USE " + this.dbname);
				System.out.println("Connected to database...");
				return true;
			}
		} catch (final Exception e) {
			return false;
		}
	}

	/* Write given entry to table */
	public void write_entry(final String id, final String date, final String type, final int Price,
			final String Status) {
		try {
			this.sqlstatement.executeUpdate("USE " + this.dbname);
			String query = "INSERT INTO " + this.tablename
					+ " (ID, Purchase_Date, Type, Price, Status) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement stmt = this.sqlconnection.prepareStatement(query);
			this.sqlconnection.createStatement().executeUpdate("USE " + this.dbname);
			stmt.setString(1, id);
			stmt.setString(2, date);
			stmt.setString(3, type);
			stmt.setInt(4, Price);
			stmt.setString(5, Status);
			stmt.execute();
		} catch (final Exception e) {
			error_dialog.showError(e.getMessage());
		}
	}

	/* List specific entries */
	public void display_db(ResultSet rs) {
		try {
			while (rs.next()) {
				System.out.println(
						"---------------------------------------------------------------------------------------------------------");
				System.out.print("|\t" + rs.getInt("Sr"));
				System.out.print("\t|\t" + rs.getString("ID"));
				System.out.print("\t|\t" + rs.getDate("Purchase_Date"));
				System.out.print("\t|\t" + rs.getString("Type"));
				System.out.print("\t|\t" + rs.getInt("Price"));
				System.out.println("\t|\t" + rs.getString("Status") + "\t|\t");
			}
			System.out.println(
					"---------------------------------------------------------------------------------------------------------");
		} catch (final Exception e) {
			error_dialog.showError(e.getMessage());
		}
	}

	/* List particular entry */
	public void display_db(final int serial) {
		try {
			this.sqlstatement.executeUpdate("USE " + this.dbname);
			this.sqlresultset = this.sqlconnection.createStatement()
					.executeQuery("SELECT * FROM " + this.tablename + " WHERE Sr = " + serial);
			while (this.sqlresultset.next()) {
				System.out.println(
						"---------------------------------------------------------------------------------------------------------");
				System.out.print("|\t" + this.sqlresultset.getInt("Sr"));
				System.out.print("\t|\t" + this.sqlresultset.getString("ID"));
				System.out.print("\t|\t" + this.sqlresultset.getDate("Purchase_Date"));
				System.out.print("\t|\t" + this.sqlresultset.getString("Type"));
				System.out.print("\t|\t" + this.sqlresultset.getInt("Price"));
				System.out.println("\t|\t" + this.sqlresultset.getString("Status") + "\t|\t");
			}
			System.out.println(
					"---------------------------------------------------------------------------------------------------------");
		} catch (final Exception e) {
			error_dialog.showError(e.getMessage());
		}
	}

	/* List all the entries */
	public void display_db() {
		try {
			this.sqlstatement.executeUpdate("USE " + this.dbname);
			this.sqlresultset = this.sqlstatement.executeQuery("SELECT * FROM " + this.tablename);
			while (this.sqlresultset.next()) {
				System.out.println(
						"---------------------------------------------------------------------------------------------------------");
				System.out.print("|\t" + this.sqlresultset.getInt("Sr"));
				System.out.print("\t|\t" + this.sqlresultset.getString("ID"));
				System.out.print("\t|\t" + this.sqlresultset.getDate("Purchase_Date"));
				System.out.print("\t|\t" + this.sqlresultset.getString("Type"));
				System.out.print("\t|\t" + this.sqlresultset.getInt("Price"));
				System.out.println("\t|\t" + this.sqlresultset.getString("Status") + "\t|\t");
			}
			System.out.println(
					"---------------------------------------------------------------------------------------------------------");
		} catch (final Exception e) {
			error_dialog.showError(e.getMessage());
		}
	}

	/* Close the SQL connection */
	public void close_db() {
		try {
			this.sqlconnection.close();
		} catch (Exception e) {
			error_dialog.showError(e.getMessage());
		}
	}
}
