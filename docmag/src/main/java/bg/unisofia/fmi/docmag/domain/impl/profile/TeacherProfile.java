package bg.unisofia.fmi.docmag.domain.impl.profile;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import bg.unisofia.fmi.docmag.domain.impl.user.User;

@Document
public class TeacherProfile extends Profile {

	public enum Department {
		Algebra ("Алгебра"),
	    Mechanics ("Аналитична механика"),
	    VOIS ("ВОИС"),
	    Geomerty("Геометрия"),
	    DifferentialEquations("Диференциални уравнения"),
	    ComputingSystems("Изчислителни системи"),
	    Informatics("Информационни технологии"),
	    ComplexAnalisys("Комплексен анализ и топология"),
	    Logic("Математическа логика и приложенията й"),
	    Education("Обучение по математика и информатика"),
	    SoftTechnologies("Софтуерни технологии"),
	    NumericMethods("Числени методи и алгоритми"),
	    Analisys("Математически анализ");
	   
	    private final String name;       

	    private Department(String s) {
	        name = s;
	    }

	    public boolean equalsName(String otherName){
	        return (otherName == null)? false:name.equals(otherName);
	    }

	    public String toString(){
	       return name;
	    }

	}

	private String degree;
	private Department department;

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
		return super.toString() + " \n" + "TeacherProfile: [degree=" + degree
				+ "]";
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
