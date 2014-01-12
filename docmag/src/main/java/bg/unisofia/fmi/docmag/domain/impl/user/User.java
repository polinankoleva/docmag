package bg.unisofia.fmi.docmag.domain.impl.user;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import bg.unisofia.fmi.docmag.domain.impl.profile.Profile;

@Document(collection = "users")
public class User {
	
	public enum UserType {
	    Student, 
	    PHD, 
	    Teacher
	}
	
	@Id
	private ObjectId id;
	
	@Indexed(unique = true)
	private String username;
	private UserType type;
	
	
	public User(UserType type) {
		this.type = type;
	}

	public Profile getProfile() {
		return null;
	}
	
	public void setProfile(Profile profile) {
	}

	public ObjectId getId() {
		return id;
	}

	public UserType getType() {
		return type;
	}

	public String getUsername() {
		return username;
	}
	
	public static Class<?> getClassForUserType(UserType type) {
		switch (type) {
		case Student:
			return Student.class;
		case PHD:
			return PHDStudent.class;
		case Teacher:
			return Teacher.class;
		default:
			return null;
		}
	}
	
	public void setUsername(String username) {
	    this.username = username;
	}
}
