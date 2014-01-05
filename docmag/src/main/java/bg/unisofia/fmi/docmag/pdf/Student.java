package bg.unisofia.fmi.docmag.pdf;

public class Student {
    private String name;
    private String subject;
    private String fn;
    
    public Student(String name, String subject, String fn) {
        this.name = name;
        this.subject = subject;
        this.fn = fn;
    }
    
    public String getName() {
        return name;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public String getFn() {
        return fn;
    }
}