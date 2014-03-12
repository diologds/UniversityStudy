package db;

import domain.User;

interface DBInterface {

	public void insert(User user);

	public User select(String login);

	public void update(User user);

	public void delete(String login);

	public void clear();

}
