package bg.unisofia.fmi.docmag.domain.impl.profile;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import bg.unisofia.fmi.docmag.domain.impl.user.User;

@Document
public class TeacherProfile extends Profile {

	public enum Department {
		Algebra ("Алгебра"),
		AnalyticalMechanics  ("Аналитична механика"),
		Statistics ("ВОИС"),
	    Geometry("Геометрия"),
	    DifferentialEquations("Диференциални уравнения"),
	    ComputingSystems("Изчислителни системи"),
	    InformationTechnology("Информационни технологии"),
	    ComplexAnalysis("Комплексен анализ и топология"),
	    MathematicalLogic("Математическа логика и приложенията й"),
	    MathematicsEducatio("Обучение по математика и информатика"),
	    SoftwareTechnologies("Софтуерни технологии"),
	    NumericalMethods("Числени методи и алгоритми"),
	    Analysis("Математически анализ");
	   
	    private final String name;       

	    private Department(String s) {
	        name = s;
	    }

	    public boolean equalsName(String otherName){
	        return (otherName == null)? false:name.equals(otherName);
	    }

	    public String getName(){
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
