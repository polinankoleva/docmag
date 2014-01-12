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
public class ReportsService {

	@Autowired
	UserDAO userDao;

	@Autowired
	ThesisDefenceDAO thesisDefenceDao;

	public List<Student> reportForStudents(ObjectId userId, Date startDate,
			Date endDate, ObjectId leaderId, ObjectId reviewerId) {
		return userDao.getGraduatedStudents(userId, startDate, endDate,
				leaderId, reviewerId);
	}

	public List<ThesisDefence> reportForThesisDefences(ObjectId userId, Date startDate, Date endDate,
			ObjectId commissionParticipantId) {
		return thesisDefenceDao.selectThesisDefences(userId, startDate, endDate,
				commissionParticipantId);
	}
}
