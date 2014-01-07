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

	public ObjectId getId() {
		return id;
	}

	public UserType getType() {
		return type;
	}

	public String getUsername() {
		return username;
	}
	
}
