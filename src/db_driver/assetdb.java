package db_driver;

import java.sql.*;

public class assetdb {

	private Connection sqlconnection;
	private Statement sqlstatement;
	private ResultSet sqlresultset;
	private String dbname, tablename, username, password;

	/* Log in to SQL database */
	public boolean checkCreds(final String uname, final String pass) {
		/*Check if given user name and password are valid*/
		try {
			Connection tempconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", uname, pass);
			tempconnection.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*As the user name and password are checked from diffrent object we need a setter to copy them*/
	public void setCreds(final String uname, final String pass) {
		this.username=uname;
		this.password=pass;
	}
	public void connect(final String database_name, final String table_name, boolean action) throws SQLException {
		this.dbname = database_name;
		this.tablename = table_name;
		try {
			if (action) {
				this.sqlconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", this.username, this.password);
				this.sqlstatement = this.sqlconnection.createStatement();
				this.sqlstatement.executeUpdate("CREATE DATABASE " + this.dbname);
				this.sqlstatement.executeUpdate("USE " + this.dbname);
				this.sqlstatement.executeUpdate("CREATE TABLE " + this.tablename
						+ " (tag_id VARCHAR(255), purchase_date DATE, asset_type VARCHAR(255), price INT NOT NULL, status VARCHAR(255) )");
				System.out.println("New database generated...");
				System.out.println("Connecting to new database...");

				System.out.println("Connected to the new datbase...");
			} else {
				this.sqlconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.dbname, this.username,
						this.password);
				System.out.println("Connected to database...");
			}
		} catch (final Exception e) {
			throw e;
		}
	}

	public void write_entry(final String id, final String date, final String type, final int price,
			final String status) {
		try {
			String sql = "INSERT INTO " + this.tablename
					+ " (tag_id, purchase_date, asset_type, price, status) VALUES('" + id + "','" + date + "','" + type
					+ "'," + price + ",'" + status + "')";
			// System.out.println(sql);
			this.sqlstatement = this.sqlconnection.createStatement();
			this.sqlstatement.executeUpdate("USE " + this.dbname);
			this.sqlstatement.executeUpdate(sql);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

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

	public void close_db() throws SQLException {
		this.sqlresultset.close();
		this.sqlstatement.close();
	}
}