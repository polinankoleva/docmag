package bg.unisofia.fmi.docmag.domain.impl.user;

import org.bson.types.ObjectId;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import bg.unisofia.fmi.docmag.domain.impl.profile.Profile;
import bg.unisofia.fmi.docmag.utils.JsonObjectIdSerializer;

@Document(collection = "users")
public class User {

	public enum UserType {
		Student, PHD, Teacher
	}

	@JsonSerialize(using = JsonObjectIdSerializer.class)
	@Id
	private ObjectId id;

	@Indexed(unique = true)
	private String username;
	private UserType type;

	// The default value for a boolean is false.
	private boolean adminRights;

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

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean hasAdminRights() {
		return adminRights;
	}

	public void enableAdminRights(boolean adminRights) {
		this.adminRights = adminRights;
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

}
