package bg.unisofia.fmi.docmag.service;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.unisofia.fmi.docmag.dao.ThesisDefenceDAO;
import bg.unisofia.fmi.docmag.dao.UserDAO;
import bg.unisofia.fmi.docmag.domain.impl.ThesisDefence;
import bg.unisofia.fmi.docmag.domain.impl.user.Student;

@Service
public class ThesisDefenceService {

	@Autowired
	ThesisDefenceDAO thesisDefenceDao;

	@Autowired
	UserDAO userDao;

	public List<ThesisDefence> getThesisDefences() {
		return thesisDefenceDao.getAllThesisDefences();
	}

	public ObjectId createThesisDefence(ObjectId userId, Date date) {
		// TODO: check if user have credentials
		return thesisDefenceDao.createThesisDefence(date);
	}
	
	/* Must be implemented
	public void updateThesisDefence(ObjectId userId, ObjectId defenceId, Date date, List<ObjectId> comissionIds) {
		
	}*/

	public boolean deleteThesisDefence(ObjectId userId, ObjectId defenceId) {
		// TODO: check if user have credentials
		List<Student> students = userDao
				.studentsForThesisDefenceWithId(defenceId);
		if (students != null) {
			return false;
		} else {
			thesisDefenceDao.delete(defenceId);
			return true;
		}
	}

}
