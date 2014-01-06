package bg.unisofia.fmi.docmag.domain.impl;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document

public class TeacherProfile extends Profile {
	
	private String degree;
	
	@DBRef
	private List<User> assignees;
	
	public TeacherProfile(String degree, List<User> assignees) {
		super();
		this.degree = degree;
		this.assignees = assignees;
	}
	
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	
	public List<User> getAssignees() {
		return assignees;
	}
	public void setAssignees(List<User> assignees) {
		this.assignees = assignees;
	}

	@Override
	public String toString() {
		return super.toString() + " \n" + "TeacherProfile: [degree=" + degree + "]";
	}
	
}
