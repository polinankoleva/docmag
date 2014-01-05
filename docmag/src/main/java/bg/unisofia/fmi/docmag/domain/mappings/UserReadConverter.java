package bg.unisofia.fmi.docmag.domain.mappings;

import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.mongodb.DBObject;

import bg.unisofia.fmi.docmag.domain.impl.Profile;
import bg.unisofia.fmi.docmag.domain.impl.StudentProfile;
import bg.unisofia.fmi.docmag.domain.impl.TeacherProfile;
import bg.unisofia.fmi.docmag.domain.impl.PHDStudentProfile;
import bg.unisofia.fmi.docmag.domain.impl.User;
import bg.unisofia.fmi.docmag.domain.impl.StudentProfile.EducationDegree;
import bg.unisofia.fmi.docmag.domain.impl.StudentProfile.EducationForm;
import bg.unisofia.fmi.docmag.domain.impl.User.UserType;

public abstract class UserReadConverter implements Converter<DBObject, User> {

	public User convert(DBObject source) {
		User user = new User(
				(String) source.get("_id"),
				(String) source.get("userName"));
		
		UserType type = (UserType) source.get("type");
		user.setType(type);
		
		Profile profile;
		
		if (type == UserType.Teacher) {
			profile = new TeacherProfile(
					(String) source.get("degree"),
					(List<User>) source.get("assignees"));
		}
		else {
			StudentProfile studentProfile;
			
			if (type == UserType.Student) {
				studentProfile = new StudentProfile();
			}
			else {
				studentProfile = new PHDStudentProfile();
				((PHDStudentProfile)studentProfile).setRecordIdentifier((String)source.get("recordIdentifier"));
			}
			studentProfile.setStudentIdentifier((String) source.get("studentIdentifier"));
			studentProfile.setEducationDegree((EducationDegree) source.get("educationDegree"));
			studentProfile.setEducationForm((EducationForm) source.get("educationForm"));
			studentProfile.setEducationSubject((String) source.get("educationSubject"));
			studentProfile.setEducationYear((Integer) source.get("educationYear"));
			
			profile = studentProfile;
		}
		
		profile.setFirstName((String) source.get("firstName"));
		profile.setLastName((String) source.get("lastName"));
		profile.setEmail((String) source.get("email"));
		profile.setAddress((String) source.get("address"));
		profile.setDepartment((String) source.get("department"));
		profile.setPhone((String) source.get("phone"));
		
		user.setProfile(profile);
		return user;
	}

}
