package bg.unisofia.fmi.docmag.controller.login;

import java.util.ArrayList;
import java.util.List;

import bg.unisofia.fmi.docmag.domain.impl.profile.PHDStudentProfile;
import bg.unisofia.fmi.docmag.domain.impl.profile.Profile;
import bg.unisofia.fmi.docmag.domain.impl.profile.StudentProfile;
import bg.unisofia.fmi.docmag.domain.impl.profile.StudentProfile.EducationDegree;
import bg.unisofia.fmi.docmag.domain.impl.profile.StudentProfile.EducationForm;
import bg.unisofia.fmi.docmag.domain.impl.profile.TeacherProfile;
import bg.unisofia.fmi.docmag.domain.impl.user.PHDStudent;
import bg.unisofia.fmi.docmag.domain.impl.user.Student;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
import bg.unisofia.fmi.docmag.domain.impl.user.User;
import bg.unisofia.fmi.docmag.domain.impl.user.User.UserType;
import static bg.unisofia.fmi.docmag.domain.impl.user.User.UserType.*;

public class SusiInfo {

    public static final String
        DEFAULT_FACULTY        = "ФМИ",
        DEFAULT_EMAIL_DOMAIN   = "@fmi.uni-sofia.bg";
    public static final EducationForm
        DEFAULT_EDUCATION_FORM = EducationForm.Regular;
    private String firstName, middleName, lastName,
                   username, facultyNumber, programme;
    private int type, year, group;
    // getters
    public String getFirstName()     { return firstName;     }
    public String getMiddleName()    { return middleName;    }
    public String getLastName()      { return lastName;      }
    public String getFacultyNumber() { return facultyNumber; }
    public String getProgramme()     { return programme;     }
    public int    getType()          { return type;          }
    public int    getYear()          { return year;          }
    public int    getGroup()         { return group;         }
    public String getUsername()      { return username;      }
    // setters
    public void setFirstName(String name)       { firstName      = name;      }
    public void setMiddleName(String name)      { middleName     = name;      }
    public void setLastName(String name)        { lastName       = name;      }
    public void setFacultyNumber(String number) { facultyNumber  = number;    }
    public void setProgramme(String programme)  { this.programme = programme; }
    public void setType(int type)               { this.type      = type;      }
    public void setYear(int year)               { this.year      = year;      }
    public void setGroup(int group)             { this.group     = group;     }
    public void setUsername(String username)    { this.username  = username;  }

    public UserType getUserType() {
        switch (getType()) {
            case 0:
            case 1:  return Student;
            case 2:  return PHD;
            default: return Teacher;
        }
    }

    public User toUser() {
        switch (getUserType()) {
            case Student:
                Student student               = new Student(Student);
                StudentProfile studentProfile = new StudentProfile();
                student.setUsername(getUsername());
                student.setProfile(studentProfile);
                studentProfile.setFirstName(getFirstName());
                studentProfile.setSurname(getMiddleName());
                studentProfile.setLastName(getLastName());
                studentProfile.setStudentIdentifier(getFacultyNumber());
                studentProfile.setEducationSubject(getProgramme());
                studentProfile.setEducationYear(getYear());
                studentProfile.setFaculty(DEFAULT_FACULTY);
                studentProfile.setEducationForm(DEFAULT_EDUCATION_FORM);
                studentProfile.setEmail(getUsername() + DEFAULT_EMAIL_DOMAIN);
                if (getType() == 0)
                    studentProfile.setEducationDegree(EducationDegree.Bachelor);
                else
                    studentProfile.setEducationDegree(EducationDegree.Master);
                return student;
            case PHD:
                PHDStudent phdStudent        = new PHDStudent(PHD);
                PHDStudentProfile phdProfile = new PHDStudentProfile();
                phdStudent.setUsername(getUsername());
                phdStudent.setProfile(phdProfile);
                phdProfile.setFirstName(getFirstName());
                phdProfile.setSurname(getMiddleName());
                phdProfile.setLastName(getLastName());
                phdProfile.setStudentIdentifier(getFacultyNumber());
                phdProfile.setEducationSubject(getProgramme());
                phdProfile.setEducationDegree(EducationDegree.PHD);
                phdProfile.setEducationYear(getYear());
                phdProfile.setFaculty(DEFAULT_FACULTY);
                phdProfile.setEducationForm(DEFAULT_EDUCATION_FORM);
                phdProfile.setEmail(getUsername() + DEFAULT_EMAIL_DOMAIN);
                return phdStudent;
            case Teacher:
                Teacher teacher   = new Teacher(Teacher);
                List<User> assign = new ArrayList<>();
                TeacherProfile teacherProfile = new TeacherProfile("", assign);
                teacher.setProfile(teacherProfile);
                teacher.setUsername(getUsername());
                teacherProfile.setFirstName(getFirstName());
                teacherProfile.setSurname(getMiddleName());
                teacherProfile.setLastName(getLastName());
                teacherProfile.setFaculty(DEFAULT_FACULTY);
                teacherProfile.setEmail(getUsername() + DEFAULT_EMAIL_DOMAIN);
                return teacher;
            default:
                User user           = new User(getUserType());
                Profile userProfile = new Profile();
                userProfile.setFirstName(getFirstName());
                userProfile.setSurname(getMiddleName());
                userProfile.setLastName(getLastName());
                userProfile.setEmail(getUsername() + DEFAULT_EMAIL_DOMAIN);
                user.setProfile(userProfile);
                user.setUsername(getUsername());
                return user;
        }
    }

    public Profile getProfile() {
        return toUser().getProfile();
    }

    @Override public String toString() {
        return toUser().toString();
    }
}
