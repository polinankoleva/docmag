package bg.unisofia.fmi.docmag.domain.impl.user;

import bg.unisofia.fmi.docmag.domain.impl.profile.TeacherProfile;

public class Teacher extends User {
	public Teacher(UserType type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	private TeacherProfile profile;

	public TeacherProfile getProfile() {
		return profile;
	}

	public void setProfile(TeacherProfile profile) {
		this.profile = profile;
	}

}
