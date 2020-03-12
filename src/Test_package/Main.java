package Test_package;
import java.sql.SQLException;
import db_driver.assetdb;

public class Main {

	public static void main(String[] args) throws SQLException {
		//assetdb db1 = new assetdb("root", "shreyas123");
		assetdb db1 = new assetdb("abc", "def", "root", "shreyas123");
		db1.write_entry("abc123", "2020/3/12", "PC", 10000, "In use");
		db1.display_db();
		db1.close_db();


	}

}
