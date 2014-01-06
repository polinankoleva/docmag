package bg.unisofia.fmi.docmag.domain.impl.profile;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class PHDStudentProfile extends StudentProfile {
	
	private String recordIdentifier;
	
	@DBRef
	private List<ObjectId> scientificLeaderIds;
	
	public PHDStudentProfile() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getRecordIdentifier() {
		return recordIdentifier;
	}
	public void setRecordIdentifier(String recordIdentifier) {
		this.recordIdentifier = recordIdentifier;
	}
	public List<ObjectId> getScientificLeaderIds() {
		return scientificLeaderIds;
	}
	public void setScientificLeaderIds(List<ObjectId> scientificLeaderIds) {
		this.scientificLeaderIds = scientificLeaderIds;
	}

	@Override
	public String toString() {
		return super.toString() + " \n" + "PHDStudentProfile: [recordIdentifier=" + recordIdentifier
				+ ", scientificLeaderIds=" + scientificLeaderIds + "]";
	}
	
	
}
