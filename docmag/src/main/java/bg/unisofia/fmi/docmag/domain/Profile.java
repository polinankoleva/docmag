package bg.unisofia.fmi.docmag.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document

public class Profile {
	
	public enum EducationForm {
	    Regular, 
	    Remote
	}
	
	public enum EducationDegree {
	    Bachelor, 
	    Master, 
	    PHD
	}
	
	@Id
	private String id;
	
	private String firstName; 
	private String lastName;
	private String email; 
	private String department;
	private String studentIdentifier;
	private EducationForm educationForm;
	private EducationDegree educationDegree;
	private String educationSubject;
	private int educationYear;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
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
	public String getId() {
		return id;
	}
	@Override
	public String toString() {
		return "Profile [firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", department=" + department
				+ ", studentIdentifier=" + studentIdentifier
				+ ", educationForm=" + educationForm + ", educationDegree="
				+ educationDegree + ", educationSubject=" + educationSubject
				+ ", educationYear=" + educationYear + "]";
	}
	
	
}
