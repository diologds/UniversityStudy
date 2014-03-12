package service;

import db.DBImplementation;
import db.FillDataBase;
import domain.User;

public class LoginService {

	DBImplementation db;

	public LoginService() {
		db = new DBImplementation();
		FillDataBase.fillDB();
	}

	public boolean authenticate(String userID, String password) {
		User user = db.select(userID);
		if (!user.getPassword().equals(password)) {
			return false;
		}

		return true;
	}

	public User getUser(String userID) {
		return db.select(userID);
	}

}
