package bg.unisofia.fmi.docmag.controller.login;

import java.util.ArrayList;
import java.util.List;

import bg.unisofia.fmi.docmag.domain.impl.profile.PHDStudentProfile;
import bg.unisofia.fmi.docmag.domain.impl.profile.Profile;
import bg.unisofia.fmi.docmag.domain.impl.profile.StudentProfile;
import bg.unisofia.fmi.docmag.domain.impl.profile.StudentProfile.EducationDegree;
import bg.unisofia.fmi.docmag.domain.impl.profile.TeacherProfile;
import bg.unisofia.fmi.docmag.domain.impl.user.PHDStudent;
import bg.unisofia.fmi.docmag.domain.impl.user.Student;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
import bg.unisofia.fmi.docmag.domain.impl.user.User;
import bg.unisofia.fmi.docmag.domain.impl.user.User.UserType;
import static bg.unisofia.fmi.docmag.domain.impl.user.User.UserType.*;

public class SusiInfo {

    private String firstName, middleName, lastName, facultyNumber, programme;
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
    // setters
    public void setFirstName(String name)       { firstName      = name;      }
    public void setMiddleName(String name)      { middleName     = name;      }
    public void setLastName(String name)        { lastName       = name;      }
    public void setFacultyNumber(String number) { facultyNumber  = number;    }
    public void setProgramme(String programme)  { this.programme = programme; }
    public void setType(int type)               { this.type      = type;      }
    public void setYear(int year)               { this.year      = year;      }
    public void setGroup(int group)             { this.group     = group;     }

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
                student.setProfile(studentProfile);
                studentProfile.setFirstName(getFirstName());
                studentProfile.setSurname(getMiddleName());
                studentProfile.setLastName(getLastName());
                studentProfile.setStudentIdentifier(getFacultyNumber());
                studentProfile.setEducationSubject(getProgramme());
                if (getType() == 0)
                    studentProfile.setEducationDegree(EducationDegree.Bachelor);
                else
                    studentProfile.setEducationDegree(EducationDegree.Master);
                return student;
            case PHD:
                PHDStudent phdStudent        = new PHDStudent(PHD);
                PHDStudentProfile phdProfile = new PHDStudentProfile();
                phdStudent.setProfile(phdProfile);
                phdProfile.setFirstName(getFirstName());
                phdProfile.setSurname(getMiddleName());
                phdProfile.setLastName(getLastName());
                phdProfile.setStudentIdentifier(getFacultyNumber());
                phdProfile.setEducationSubject(getProgramme());
                phdProfile.setEducationDegree(EducationDegree.PHD);
                return phdStudent;
            case Teacher:
                Teacher teacher   = new Teacher(Teacher);
                List<User> assign = new ArrayList<>();
                TeacherProfile teacherProfile = new TeacherProfile("", assign);
                teacher.setProfile(teacherProfile);
                teacherProfile.setFirstName(getFirstName());
                teacherProfile.setSurname(getMiddleName());
                teacherProfile.setLastName(getLastName());
                return teacher;
            default:
                User user           = new User(getUserType());
                Profile userProfile = new Profile();
                user.setProfile(userProfile);
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
