package db;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;

public class DBTools {

	public MongoClient createConnection() {

		MongoClient mongo = null;
		try {
			mongo = new MongoClient("localhost", 27017);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return mongo;
	}

	public void closeConnection(MongoClient mongo) {
		mongo.close();
	}

}
