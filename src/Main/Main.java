package Main;

import GUI.MainUI;
import db_driver.assetdb;

public class Main {

	public static void main(String[] args) {
		assetdb db = new assetdb();
		MainUI ui = new MainUI();
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
				db.display_db();
				break;
			}
		} while (ui.Choice != 0);

	}

}
