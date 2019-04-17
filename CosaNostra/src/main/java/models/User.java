package models;

public class User {

	
	private String email;
	private String login;
	private String password;
	private  int id;
	
	public User(int id,String email, String login, String password) {
		this.id=id;
		this.email = email;
		this.login = login;
		this.password = password;
	}
	
	public User() {}
	
	
	@Override
	public String toString() {
		return "User [email=" + email + ", login=" + login + ", password=" + password + "]";
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
}