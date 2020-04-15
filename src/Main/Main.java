package Main;

import GUI.Main_Menu_Dialog;
import db_driver.Assetdb;

public class Main {

	public static void main(String[] args) {
		Assetdb db = new Assetdb();
		Main_Menu_Dialog ui = new Main_Menu_Dialog();
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
			case 6:
				db.export_all();
				break;
			}
		} while (ui.Choice != 0);

	}

}
