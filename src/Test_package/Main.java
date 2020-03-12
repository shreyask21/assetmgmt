package Test_package;
import java.util.Scanner;
import java.sql.SQLException;
import db_driver.assetdb;

public class Main {

	public static void main(String[] args) throws SQLException {
		Scanner in = new Scanner(System.in);
		assetdb db1;
		System.out.print("Enter user name> ");						String usrn = in.next();
		System.out.print("Enter password> ");  						String psw = in.next();
		System.out.println("1\t=\tNew Database\n2\t=\tExisting Database");
		System.out.print("Enter your choice> ");
		if(in.nextInt()==1){
			db1 = new assetdb(usrn, psw);
		}else{
			System.out.print("Enter database name> ");				String db = in.next();
			System.out.print("Enter table name> ");  				String table = in.next();
			db1 = new assetdb(db, table, usrn, psw);
		}
		System.out.print("Enter asset ID> ");						String id = in.next();
		System.out.print("Enter purchase date (YYYY/MM/DD)> ");  	String date = in.next();
		System.out.print("Enter asset type> ");						String type = in.next();
		System.out.print("Enter asset price> ");  					int price = in.nextInt();
		System.out.print("Enter asset status> ");  					String status = in.next();
		db1.write_entry(id, date, type, price, status);
		db1.display_db();
		db1.close_db();

	}

}
