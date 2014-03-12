package domain;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private String login;
	private String password;
	private String name;
	private String surname;
	private List<String> friends;
	private String pictureName;
	private String base64;

	public User() {
	}

	public User(String login, String password, String name, String surname,
			List<String> friends) {
		this.login = login;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.friends = friends;

	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String userName) {
		this.surname = userName;
	}

	public List<String> getFriends() {
		return friends;
	}

	public void setFriends(List<String> friends) {
		this.friends = friends;
	}

	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!(o instanceof User))
			return false;

		User comparedObject = (User) o;

		if (login != null ? !login.equals(comparedObject.getLogin())
				: comparedObject.getLogin() != null)
			return false;
		if (password != null ? !password.equals(comparedObject.getPassword())
				: comparedObject.getPassword() != null)
			return false;
		if (name != null ? !name.equals(comparedObject.getName())
				: comparedObject.getName() != null)
			return false;
		if (surname != null ? !surname.equals(comparedObject.getSurname())
				: comparedObject.getSurname() != null)
			return false;
		if (friends != null ? !compareListContent(friends,
				comparedObject.getFriends())
				: comparedObject.getFriends() != null)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = login != null ? login.hashCode() : 0;
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (surname != null ? surname.hashCode() : 0);
		result = 31 * result + (friends != null ? friends.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		if (friends != null && friends.size() > 0) {
			return "User{" + "Login=" + login + ", password=" + password
					+ ", userName='" + name + '\'' + ", userSurname='"
					+ surname + '\'' + ", userFriends='" + friends.toString()
					+ '\'' + '}';
		} else {
			return "User{" + "Login=" + login + ", password=" + password
					+ ", userName='" + name + '\'' + ", userSurname='"
					+ surname + '\'' + ", userFriends='" + "Empty" + '\'' + '}';
		}
	}

	public Boolean compareListContent(List<String> first, List<String> second) {

		if (first.size() != second.size())
			return false;

		for (int i = 0; i < first.size(); i++) {
			if (!first.get(i).equals(second.get(i))) {
				System.out.println("#### : " + first.get(i) + " :::"
						+ second.get(i));
				return false;
			}
		}
		return true;
	}
}
