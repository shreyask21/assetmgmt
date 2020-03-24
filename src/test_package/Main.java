package test_package;

import db_driver.assetdb;
import GUI.login_dialog;
import java.util.Scanner;
import java.sql.SQLException;
import GUI.createdb_dialog;

/*Class for testing GUI and database back-end*/
public class Main {
	public static void main(String[] args) throws SQLException {
		
		/*						Objects for database I/O					*/
		Scanner in = new Scanner(System.in);
		assetdb db1 = new assetdb();
		/********************************************************************/
		
		/*						Objects for GUI elements					*/
		login_dialog login = new login_dialog();
		createdb_dialog choice = new createdb_dialog();
		/********************************************************************/
		
		/* Keep showing login dialog until correct credentials are received */
		while (db1.setCreds(login.getUsername(), login.getPassword())) {
			/* Show login UI dialog */
			login.showDialog();
		}
		/********************************************************************/
		
		/*					Show create database UI dialog					*/
		choice.showDialog(login.getUsername(), login.getPassword());
		/********************************************************************/
		
		/*			Create database connection with details from GUI		*/
		db1.connect(choice.getDBName(), choice.getTableName(), choice.getAction());
		/********************************************************************/
		
		/*				TODO: Create GUI dialog to add entries				*/
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
		
		db1.write_entry(id, date, type, price, status);	//Write given entry to database
		/********************************************************************/
		
		/*				TODO: Create GUI dialog to display entries			*/
		db1.display_db();	//Display all entries into console
		/********************************************************************/
		
		/*							Cleanup for exit						*/
		db1.close_db();	//Close database connection
		in.close();		//Close Scanner connection
		/********************************************************************/
		
		/* End of program */
	}
}
