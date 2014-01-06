package bg.unisofia.fmi.docmag.domain.impl;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
	
	public enum UserType {
	    Student, 
	    PHD, 
	    Teacher
	}
	
	@Id
	private String id;
	
	private String userName;
	private UserType type;
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String id, String userName) {
		super();
		this.id = id;
		this.userName = userName;
	}
	
	public User(UserType type) {
		this.type = type;
	}

	public Profile getProfile() {
		return null;
	}

	public String getId() {
		return id;
	}

	public UserType getType() {
		return type;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
	
}
