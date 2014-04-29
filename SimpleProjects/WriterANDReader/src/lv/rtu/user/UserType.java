package lv.rtu.user;

public enum UserType {
	READER("reader"), WRITER("writer");

	private String userType;

	UserType(String userType) {
		this.userType = userType;
	}

	public String getUserType() {
		return userType;
	}

}
