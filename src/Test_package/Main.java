package Test_package;

import db_driver.assetdb;
import GUI.login_dialog;
import java.util.Scanner;
import java.sql.SQLException;
import GUI.createdb_dialog;

public class Main {
	public static void main(String[] args) throws SQLException {
		Scanner in = new Scanner(System.in);
		assetdb db1 = new assetdb();
		login_dialog login = new login_dialog();
		createdb_dialog choice = new createdb_dialog();

		login.showDialog();
		db1.setCreds(login.getUsername(), login.getPassword());
		choice.showDialog();
		db1.connect(choice.getDBName(), choice.getTableName(), choice.getAction());
		System.out.println(choice.getDBName()+"\n"+choice.getTableName()+"\n"+choice.getAction());
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
