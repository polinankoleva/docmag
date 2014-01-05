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
	private Profile profile;
	
	public User(String id, String userName) {
		super();
		this.id = id;
		this.userName = userName;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getId() {
		return id;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
	
}
