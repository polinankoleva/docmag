package bg.unisofia.fmi.docmag.domain.impl.user;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;

import bg.unisofia.fmi.docmag.domain.impl.profile.StudentProfile;

public class Student extends User {

	public Student(UserType type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	
	private StudentProfile profile;
	private String thesisDefenceMark;
	
	@Indexed
	private ObjectId thesisDefenceId;

	public StudentProfile getProfile() {
		return profile;
	}

	public void setProfile(StudentProfile profile) {
		this.profile = profile;
	}

	public String getThesisDefenceMark() {
		return thesisDefenceMark;
	}

	public void setThesisDefenceMark(String mark) {
		this.thesisDefenceMark = mark;
	}

	public ObjectId getThesisDefenceId() {
		return thesisDefenceId;
	}

	public void setThesisDefenceId(ObjectId thesisDefenceId) {
		this.thesisDefenceId = thesisDefenceId;
	}	
	
}
