package bg.unisofia.fmi.docmag.service;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.unisofia.fmi.docmag.dao.ThesisDefenceDAO;
import bg.unisofia.fmi.docmag.dao.UserDAO;
import bg.unisofia.fmi.docmag.domain.impl.ThesisDefence;
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
	
	@Autowired
	ThesisDefenceService thesisDefenceService;
	
	@Autowired
	ThesisDefenceDAO thesisDefenceDao;

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

	public Map<String, String> setThesisDefenceIdForStudentWithId(ObjectId thesisDefenceId, ObjectId studentId) {
		User user = userDao.getUserById(studentId);
		if(user != null && user instanceof Student){
			Student student = (Student) user;
			ThesisDefence thesisDefence = thesisDefenceDao.getThesisDefenceById(thesisDefenceId);
			if(student != null && student.getThesisDefenceId() == null && thesisDefence != null){
				student.setThesisDefenceId(thesisDefenceId);
				userDao.saveUser(student);
			} else {
				return thesisDefenceService.createErrorMessage("Setting thesis defence to user wasn't succeed");
			}
		} else{
			return thesisDefenceService.createErrorMessage("Setting thesis defence to user wasn't succeed");
		}
		return null;
	}

	public Map<String, Object> getThesisDefenceForUser(ObjectId studentId){
		User user = userDao.getUserById(studentId);
		if(user != null && user instanceof Student){
			Student student = (Student) user;
			if(student.getThesisDefenceId() != null ){
				ThesisDefence thesisDefence = thesisDefenceDao.getThesisDefenceById(student.getThesisDefenceId());
				if(thesisDefence != null){
					return thesisDefenceService.getThesisDefenceInMap(thesisDefence);
				}
			}
		}
		return null;
	}
	
	public void updateThesisdefenceForUser(ObjectId thesisDefenceId, ObjectId studentId){
		User user = userDao.getUserById(studentId);
		if(user != null && user instanceof Student){
			ThesisDefence thesisDefence = thesisDefenceDao.getThesisDefenceById(thesisDefenceId);
			Student student = (Student) user;
			if(student != null && !student.getThesisDefenceId().equals(thesisDefenceId) && thesisDefence != null){
				student.setThesisDefenceId(thesisDefence.getId());
				userDao.saveUser(user);
			}
		}
	}
	
	public void deteleThesisdefenceForUser(ObjectId studentId){
		User user = userDao.getUserById(studentId);
		if(user != null && user instanceof Student){
			Student student = (Student) user;
			if(student.getThesisDefenceId() != null && student != null){
				student.setThesisDefenceId(null);
				userDao.saveUser(user);
			}
		}
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
