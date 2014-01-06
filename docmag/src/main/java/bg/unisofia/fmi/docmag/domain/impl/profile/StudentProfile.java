package bg.unisofia.fmi.docmag.domain.impl.profile;
import org.springframework.data.mongodb.core.mapping.Document;

@Document

public class StudentProfile extends Profile {
	
	public enum EducationForm {
	    Regular, 
	    Remote
	}
	
	public enum EducationDegree {
	    Bachelor, 
	    Master, 
	    PHD
	}
	
	private String studentIdentifier;
	private EducationForm educationForm;
	private EducationDegree educationDegree;
	private String educationSubject;
	private int educationYear;
	
	public String getStudentIdentifier() {
		return studentIdentifier;
	}
	public void setStudentIdentifier(String studentIdentifier) {
		this.studentIdentifier = studentIdentifier;
	}
	
	public EducationForm getEducationForm() {
		return educationForm;
	}
	public void setEducationForm(EducationForm educationForm) {
		this.educationForm = educationForm;
	}
	
	public EducationDegree getEducationDegree() {
		return educationDegree;
	}
	public void setEducationDegree(EducationDegree educationDegree) {
		this.educationDegree = educationDegree;
	}
	
	public String getEducationSubject() {
		return educationSubject;
	}
	public void setEducationSubject(String educationSubject) {
		this.educationSubject = educationSubject;
	}
	
	public int getEducationYear() {
		return educationYear;
	}
	public void setEducationYear(int educationYear) {
		this.educationYear = educationYear;
	}
	
	@Override
	public String toString() {
		return super.toString() + " \n" + "StudentProfile: [studentIdentifier=" + studentIdentifier
				+ ", educationForm=" + educationForm + ", educationDegree="
				+ educationDegree + ", educationSubject=" + educationSubject
				+ ", educationYear=" + educationYear + "]";
	}
	
	
}
