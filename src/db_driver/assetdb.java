package db_driver;

import java.util.Scanner;
import java.sql.*;

public class assetdb {

	private Connection sqlconnection;
	private Statement sqlstatement;
	private ResultSet sqlresultset;

	private String dbname;
	private String tablename;

	public boolean loginSuccess;

	/* Generate new database */
	public void connect(final String username, final String password) throws SQLException {
		try {
			Scanner in = new Scanner(System.in);
			this.sqlconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", username, password);
			System.out.println("Login success...");
			this.sqlstatement = this.sqlconnection.createStatement();
			System.out.print("Enter new database name> ");
			this.dbname = in.nextLine();
			this.sqlstatement.executeUpdate("CREATE DATABASE " + this.dbname);
			this.sqlstatement.executeUpdate("USE " + this.dbname);
			System.out.print("Enter new table name> ");
			this.tablename = in.nextLine();
			this.sqlstatement.executeUpdate("CREATE TABLE " + this.tablename
					+ " (tag_id VARCHAR(255), purchase_date DATE, asset_type VARCHAR(255), price INT NOT NULL, status VARCHAR(255) )");
			System.out.println("New database generated...");
			System.out.println("Connecting to new database...");
			this.sqlconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.dbname, username,
					password);
			this.sqlstatement = this.sqlconnection.createStatement();
			
		} catch (final Exception e) {
			throw e;
		}
		System.out.println("Connected to the new datbase...");
		this.loginSuccess = true;
	}

	/* Connect to existing database */
	public void connect(final String database_name, final String table_name, final String username,
			final String password) throws SQLException {
		try {
			this.sqlconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.dbname, username,
					password);
			this.dbname = database_name;
			this.tablename = table_name;
			this.sqlstatement = this.sqlconnection.createStatement();
			this.sqlstatement.executeUpdate("USE " + this.dbname);

		} catch (final Exception e) {
			throw e;
		}
		System.out.println("Login success...");
		System.out.println("Connected to datbase...");
		this.loginSuccess = true;
	}

	public void write_entry(String id, String date, String type, int price, String status) {
		try {
			String sql = "INSERT INTO " + this.tablename
					+ " (tag_id, purchase_date, asset_type, price, status) VALUES('" + id + "','" + date + "','" + type
					+ "'," + price + ",'" + status + "')";
			// System.out.println(sql);
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