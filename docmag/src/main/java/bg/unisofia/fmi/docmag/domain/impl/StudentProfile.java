package bg.unisofia.fmi.docmag.domain.impl;
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
	
	public StudentProfile(String studentIdentifier,
			EducationForm educationForm, EducationDegree educationDegree,
			String educationSubject, int educationYear) {
		super();
		this.studentIdentifier = studentIdentifier;
		this.educationForm = educationForm;
		this.educationDegree = educationDegree;
		this.educationSubject = educationSubject;
		this.educationYear = educationYear;
	}
	public StudentProfile() {
		super();
		// TODO Auto-generated constructor stub
	}
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
		return "StudentProfile [super.toString() + studentIdentifier=" + studentIdentifier
				+ ", educationForm=" + educationForm + ", educationDegree="
				+ educationDegree + ", educationSubject=" + educationSubject
				+ ", educationYear=" + educationYear + "]";
	}
	
	
}
