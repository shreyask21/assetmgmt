package Test_package;

import java.util.Scanner;
import java.sql.SQLException;
import db_driver.assetdb;
//import GUI.login_prompt;

public class Main {

	public static void main(String[] args) throws SQLException {

		Scanner in = new Scanner(System.in);
		assetdb db1 = new assetdb();

		System.out.println("1\t=\tNew Database\n2\t=\tExisting Database");
		System.out.print("Enter your choice> ");
		if (in.nextInt() == 1) {
		while(!db1.loginSuccess)
			try {
				System.out.print("Enter user name> ");
				String usrn = in.next();
				System.out.print("Enter password> ");
				String psw = in.next();
				db1.connect(usrn, psw);
			} catch (Exception e) {
				System.out.println("Invalid Credentials");
			}
		} else {
			System.out.print("Enter database name> ");
			String db = in.next();
			System.out.print("Enter table name> ");
			String table = in.next();
			System.out.print("Enter user name> ");
			String usrn = in.next();
			System.out.print("Enter password> ");
			String psw = in.next();
			db1.connect(db, table, usrn, psw);
		}
		/*System.out.print("Enter asset ID> ");
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
		db1.display_db();*/
		db1.close_db();
		in.close();

	}

}
