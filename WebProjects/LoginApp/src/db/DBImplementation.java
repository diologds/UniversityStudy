package db;

import java.util.List;

import loader.LoadConfig;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import domain.HTMLImage;
import domain.User;

@SuppressWarnings("unchecked")
public class DBImplementation extends DBTools implements DBInterface {

	private static String[] config = LoadConfig.loader();

	@Override
	public void insert(User user) {
		MongoClient mongo = null;
		try {
			mongo = createConnection();
			DB db = mongo.getDB(config[0]);
			DBCollection table = db.getCollection(config[1]);

			BasicDBObject document = new BasicDBObject();
			document.put("login", user.getLogin());
			document.put("password", user.getPassword());
			document.put("name", user.getName());
			document.put("surname", user.getSurname());
			document.put("friends", user.getFriends());
			document.put("imageName", user.getImage().getFilename());
			document.put("imageExtension", user.getImage().getExtension());

			table.insert(document);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(mongo);
		}
	}

	@Override
	public User select(String login) {
		MongoClient mongo = null;
		try {
			mongo = createConnection();
			DB db = mongo.getDB(config[0]);

			DBCollection table = db.getCollection(config[1]);
			BasicDBObject searchQuery = new BasicDBObject();

			searchQuery.put("login", login);
			DBCursor cursor = table.find(searchQuery);
			DBObject object;

			User user = new User();
			if (cursor.hasNext()) {
				object = cursor.next();
				user.setLogin((String) object.get("login"));
				user.setPassword((String) object.get("password"));
				user.setName((String) object.get("name"));
				user.setSurname((String) object.get("surname"));
				user.setFriends((List<String>) object.get("friends"));
				String filename = (String) object.get("imageName");
				String extension = (String) object.get("imageExtension");
				user.setImage(new HTMLImage(filename, extension));

				return user;
			} else {
				System.out.println("User not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(mongo);
		}
		return null;

	}

	@Override
	public void update(User user) {
		delete(user.getLogin());
		insert(user);
	}

	@Override
	public void delete(String login) {
		MongoClient mongo = null;
		try {
			mongo = createConnection();
			DB db = mongo.getDB(config[0]);
			DBCollection table = db.getCollection(config[1]);
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("login", login);
			table.remove(searchQuery);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(mongo);
		}
	}

	@Override
	public void clear() {
		MongoClient mongo = null;
		try {
			mongo = createConnection();
			DB db = mongo.getDB(config[0]);
			DBCollection table = db.getCollection(config[1]);
			table.drop();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(mongo);
		}
	}
}
