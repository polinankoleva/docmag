package bg.unisofia.fmi.docmag.dao;

import java.util.List;

import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal;
import bg.unisofia.fmi.docmag.domain.impl.user.PHDStudent;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
import bg.unisofia.fmi.docmag.domain.impl.user.User;
import bg.unisofia.fmi.docmag.domain.impl.user.User.UserType;

public interface UserDAO {

	public void createUser(User user);

	public <T extends User> T getUserByUsername(String username);

	public <T extends User> List<T> getAllUsersOfType(UserType type);

	public List<Teacher> getScientificLeadersForPHDStudent(PHDStudent phdStudent);

	public List<Teacher> getScientificLeadersForThesis(ThesisProposal thesis);

	public List<User> getConsultantsForThesis(ThesisProposal thesis);

	public boolean assignScientificLeaderForPHDStudent(Teacher teacher,
			PHDStudent student);

}
