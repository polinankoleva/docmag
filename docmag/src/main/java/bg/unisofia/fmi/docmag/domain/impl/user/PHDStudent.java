package bg.unisofia.fmi.docmag.domain.impl;

public class PHDStudent extends User {
	
	public PHDStudent(UserType type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	
	private PHDStudentProfile profile;

	public PHDStudentProfile getProfile() {
		return profile;
	}

	public void setProfile(PHDStudentProfile profile) {
		this.profile = profile;
	}

	

}
