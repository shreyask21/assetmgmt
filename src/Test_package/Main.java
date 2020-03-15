package Test_package;

import db_driver.assetdb;
import GUI.login_ui;
import java.util.Scanner;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws SQLException {

		Scanner in = new Scanner(System.in);
		assetdb db1 = new assetdb();
		login_ui login = new login_ui();
		
		login.login();
		
		System.out.println("1\t=\tNew Database\n2\t=\tExisting Database");
		System.out.print("Enter your choice> ");

		if (in.nextInt() == 1) {
			db1.connect(login.getUsername(), login.getPassword());
		} else {
			System.out.print("Enter database name> ");
			String db = in.next();
			System.out.print("Enter table name> ");
			String table = in.next();
			System.out.print("Enter user name> ");
			db1.connect(db, table, login.getUsername(), login.getPassword());
		}
		System.out.print("Enter asset ID> ");
		String id = in.next();
		System.out.print("Enter purchase date (YYYY/MM/DD)> ");
		String date = in.next();
		System.out.print("Enter asset type> ");
		String type = in.next();
		System.out.print("Enter asset price> ");
		int price = in.nextInt();
		System.out.print("Enter asset status> ");
		String status = in.next();
		db1.write_entry(id, date, type, price, status);
		db1.display_db();
		db1.close_db();
		in.close();

	}

}
