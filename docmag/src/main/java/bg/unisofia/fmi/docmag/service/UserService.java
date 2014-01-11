package bg.unisofia.fmi.docmag.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.unisofia.fmi.docmag.dao.UserDAO;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal;
import bg.unisofia.fmi.docmag.domain.impl.user.PHDStudent;
import bg.unisofia.fmi.docmag.domain.impl.user.Student;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
import bg.unisofia.fmi.docmag.domain.impl.user.User;
import bg.unisofia.fmi.docmag.domain.impl.user.User.UserType;

@Service
public final class UserService {

	@Autowired
	UserDAO userDao;

	public void createUser(User user) {
		userDao.createUser(user);
	}
	
	public <U extends User> U getUserById(ObjectId userId) {
	    return userDao.getUserById(userId);
	}
	
	public <U extends User> U getUserById(String userId) {
        return getUserById(new ObjectId(userId));
    }

	public User getUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}

	public void setThesisDefenceIdForStudentWithId(ObjectId thesisDefenceId,
			ObjectId studentId) {
		Student student = userDao.getUserById(studentId);
		student.setThesisDefenceId(thesisDefenceId);
		userDao.saveUser(student);
	}

	public void setThesisDefenceMarkForStudentWithId(String mark,
			ObjectId studentId) {
		Student student = userDao.getUserById(studentId);
		student.setThesisDefenceMark(mark);
		userDao.saveUser(student);
	}

	public List<Teacher> getScientificLeadersPHDStudentWithUsername(
			String username) {
		User user = getUserByUsername(username);
		if (user.getType() == UserType.PHD) {
			return userDao.getScientificLeadersForPHDStudent((PHDStudent) user);
		} else {
			return null;
		}
	}

	public List<Teacher> getScientificLeadersForThesis(ThesisProposal thesis) {
		return userDao.getScientificLeadersForThesis(thesis);
	}

	public List<Teacher> getConsultantsForThesis(ThesisProposal thesis) {
		return userDao.getConsultantsForThesis(thesis);
	}
}
