package db;

import java.util.ArrayList;
import java.util.List;

import domain.User;

public class FillDataBase {
	public static void fillDB() {

		DBImplementation db = new DBImplementation();
		List<String> list = new ArrayList<String>();
		db.clear();
		User user = new User("admin", "admin", "Admin", "Adminov", list);
		db.insert(user);
		list.add("admin");
		user = new User("timur", "timur", "Timur", "Adminov", list);
		db.insert(user);
		list.add("timur");
		user = new User("mazaikin", "mazaikin", "Mazaikin", "Adminov", list);
		db.insert(user);
	}
}
