package Test_package;
import java.sql.SQLException;

import db_driver.assetdb;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		assetdb db1 = new assetdb("root", "shreyas123");
		db1.write_entry();
		db1.display_db();
		db1.close_db();

	}

}
