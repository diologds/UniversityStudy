package db;

import java.util.ArrayList;
import java.util.List;

import domain.HTMLImage;
import domain.User;

public class FillDataBase {
	public static void fillDB() {
		HTMLImage image = new HTMLImage("admin_1.jpg", "jpg");
		DBImplementation db = new DBImplementation();
		List<String> list = new ArrayList<String>();
		db.clear();
		User user = new User("admin", "admin", "Admin", "Adminov", list, image);
		db.insert(user);
		User user2 = db.select("admin");
		System.out.println(user.toString() + " , " + user2.toString());
		list.add("admin");
		user = new User("timur", "timur", "Timur", "Adminov", list, image);
		db.insert(user);
		list.add("timur");
		user = new User("mazaikin", "mazaikin", "Mazaikin", "Adminov", list,
				image);
		db.insert(user);
	}
}
