package bg.unisofia.fmi.docmag.domain.impl;

import java.util.List;

import org.bson.types.ObjectId;

public class PHDStudentProfile extends StudentProfile {
	
	private String recordIdentifier;
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
	
	
	
	
}
