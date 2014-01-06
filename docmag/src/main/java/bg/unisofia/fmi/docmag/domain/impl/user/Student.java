package bg.unisofia.fmi.docmag.domain.impl;
import bg.unisofia.fmi.docmag.domain.impl.StudentProfile;

public class Student extends User {

	public Student(UserType type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	
	private StudentProfile profile;

	public StudentProfile getProfile() {
		return profile;
	}

	public void setProfile(StudentProfile profile) {
		this.profile = profile;
	}

	

	
	
}
