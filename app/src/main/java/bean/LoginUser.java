package bean;

public class LoginUser {

	private String UserID;
	private String pwd;

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	@Override
	public String toString() {
		return "LoginUser{" +
				"UserID='" + UserID + '\'' +
				", pwd='" + pwd + '\'' +
				'}';
	}
}
