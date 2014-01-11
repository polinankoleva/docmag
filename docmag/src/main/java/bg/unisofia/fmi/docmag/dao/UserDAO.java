package bg.unisofia.fmi.docmag.dao;

import java.util.List;

import org.bson.types.ObjectId;

import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal;
import bg.unisofia.fmi.docmag.domain.impl.user.PHDStudent;
import bg.unisofia.fmi.docmag.domain.impl.user.Student;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
import bg.unisofia.fmi.docmag.domain.impl.user.User;
import bg.unisofia.fmi.docmag.domain.impl.user.User.UserType;

public interface UserDAO {

	public void createUser(User user);

	public <T extends User> T getUserById(ObjectId userId);

	public User getUserByUsername(String username);

	public <T extends User> List<T> getAllUsersOfType(UserType type);

	public void saveUser(User user);

	public List<Teacher> getScientificLeadersForPHDStudent(PHDStudent phdStudent);

	public List<Teacher> getScientificLeadersForThesis(ThesisProposal thesis);

	public List<User> getConsultantsForThesis(ThesisProposal thesis);

	public boolean assignScientificLeaderForPHDStudent(Teacher teacher,
			PHDStudent student);

	public List<Student> studentsForThesisDefenceWithId(ObjectId thesisDefenceId);

}
