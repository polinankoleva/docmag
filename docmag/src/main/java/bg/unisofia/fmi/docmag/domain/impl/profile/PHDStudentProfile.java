package bg.unisofia.fmi.docmag.domain.impl.profile;

public class PHDStudentProfile extends StudentProfile {
	
	private String recordIdentifier;
		
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

	@Override
	public String toString() {
		return super.toString() + " \n" + "PHDStudentProfile: [recordIdentifier=" + recordIdentifier + "]";
	}
	
	
}
