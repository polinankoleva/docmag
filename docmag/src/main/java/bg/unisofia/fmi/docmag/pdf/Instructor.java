package bg.unisofia.fmi.docmag.pdf;

public class Instructor {
    private String name;
    private String department;
    private String university;
    private String faculty;
    public String getName() {
        return name;
    }
    public String getDepartment() {
        return department;
    }
    public String getUniversity() {
        return university;
    }
    public String getFaculty() {
        return faculty;
    }
    public Instructor(String name, String department, String university,
            String faculty) {
        super();
        this.name = name;
        this.department = department;
        this.university = university;
        this.faculty = faculty;
    }
    
    
}
