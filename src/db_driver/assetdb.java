package db_driver;

/*Import SQL package*/
import java.sql.*;

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
			Connection tempconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+db, uname, pass);
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
			Connection tempconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+db, uname, pass);
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
		if(this.checkCreds(uname, pass)) {
		this.username = uname;
		this.password = pass;
		return false;
		}else {
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
						+ " (tag_id VARCHAR(255), purchase_date DATE, asset_type VARCHAR(255), price INT NOT NULL, status VARCHAR(255) )");
				System.out.println("New database generated...");
				System.out.println("Connecting to new database...");
				System.out.println("Connected to the new datbase...");
				return true;
			} else {
				this.sqlconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.dbname,
						this.username, this.password);
				System.out.println("Connected to database...");
				return true;
			}
		} catch (final Exception e) {
			return false;
		}
	}
	


	/* Write given entry to table */
	public void write_entry(final String id, final String date, final String type, final int price,
			final String status) {
		try {
			String sql = "INSERT INTO " + this.tablename
					+ " (tag_id, purchase_date, asset_type, price, status) VALUES('" + id + "','" + date + "','" + type
					+ "'," + price + ",'" + status + "')";
			this.sqlstatement = this.sqlconnection.createStatement();
			this.sqlstatement.executeUpdate("USE " + this.dbname);
			this.sqlstatement.executeUpdate(sql);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/* List all the entries */
	public void display_db() {
		try {
			this.sqlresultset = this.sqlstatement.executeQuery("SELECT * FROM " + this.tablename);
			System.out.println(
					"-----------------------------------------------------------------------------------------");
			while (this.sqlresultset.next()) {
				System.out.print("|\t" + this.sqlresultset.getString("tag_id"));
				System.out.print("\t|\t" + this.sqlresultset.getDate("purchase_date"));
				System.out.print("\t|\t" + this.sqlresultset.getString("asset_type"));
				System.out.print("\t|\t" + this.sqlresultset.getInt("price"));
				System.out.println("\t|\t" + this.sqlresultset.getString("status") + "\t|\t");
			}
			System.out.println(
					"-----------------------------------------------------------------------------------------");
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/* Close the SQL connection */
	public void close_db() throws SQLException {
		this.sqlresultset.close();
		this.sqlstatement.close();
	}
}
