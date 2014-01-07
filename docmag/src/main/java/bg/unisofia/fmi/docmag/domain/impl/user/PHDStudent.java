package bg.unisofia.fmi.docmag.domain.impl.user;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;

import bg.unisofia.fmi.docmag.domain.impl.profile.PHDStudentProfile;

public class PHDStudent extends User {

	public PHDStudent(UserType type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	private PHDStudentProfile profile;

	@DBRef
	private List<ObjectId> scientificLeaderIds;

	public List<ObjectId> getScientificLeaderIds() {
		return scientificLeaderIds;
	}

	public void setScientificLeaderIds(List<ObjectId> scientificLeaderIds) {
		this.scientificLeaderIds = scientificLeaderIds;
	}

	public PHDStudentProfile getProfile() {
		return profile;
	}

	public void setProfile(PHDStudentProfile profile) {
		this.profile = profile;
	}

}
