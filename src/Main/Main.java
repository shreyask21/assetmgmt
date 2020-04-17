package Main;

import GUI.Main_Menu_Dialog;
import db_driver.Assetdb;

public class Main {

	public static void main(String[] args) {
		Assetdb db = new Assetdb();
		Main_Menu_Dialog ui = new Main_Menu_Dialog();
		// db.init("root", "root", "abc", "def"); //For testing
		db.init();
		do {
			ui.showDialog();
			switch (ui.Choice) {
			case 0:
				db.close_db();
				System.out.println("Exiting program...");
				System.exit(0);
				break;
			case 1:
				db.display();
				break;
			case 2:
				db.add();
				break;
			case 3:
				db.delete();
				break;
			case 4:
				db.search();
				break;
			case 5:
				db.modify();
				break;
			case 6:
				db.export();
				break;
			}
		} while (ui.Choice != 0);
	}

}
