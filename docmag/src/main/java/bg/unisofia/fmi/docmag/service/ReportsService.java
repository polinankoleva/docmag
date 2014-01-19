package bg.unisofia.fmi.docmag.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.unisofia.fmi.docmag.dao.DocumentDAO;
import bg.unisofia.fmi.docmag.dao.ThesisDefenceDAO;
import bg.unisofia.fmi.docmag.dao.UserDAO;
import bg.unisofia.fmi.docmag.domain.impl.ThesisDefence;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisRecension;
import bg.unisofia.fmi.docmag.domain.impl.document.Document.DocumentType;
import bg.unisofia.fmi.docmag.domain.impl.profile.TeacherProfile.Department;
import bg.unisofia.fmi.docmag.domain.impl.user.Student;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
import bg.unisofia.fmi.docmag.domain.impl.user.User.UserType;

@Service
public class ReportsService {

	@Autowired
	UserDAO userDao;
	
	@Autowired
	DocumentDAO documentDao;

	@Autowired
	ThesisDefenceDAO thesisDefenceDao;
	
	@Autowired
    ThesisDefenceService thesisDefenceService;

	public List<Student> reportForStudents(ObjectId userId, Date startDate,
			Date endDate, ObjectId leaderId, ObjectId reviewerId) {
		Boolean teacherHaveRights = userHaveRightsForReports(userId);
		if (teacherHaveRights != null) {
			
			Date startDateOnDayStart = dateWithoutHoursByAddingDays(startDate, 0);
			Date endDateOnDayEnd = dateWithoutHoursByAddingDays(endDate == null ? startDate : endDate, 1);
			List<Student> students = userDao.getGraduatedStudentsBetweenDates(startDateOnDayStart, endDateOnDayEnd);
			
			if (students != null && !students.isEmpty()) {
				List<Student> filteredStudents = new ArrayList<Student>(students);
				
				for (Student student : students) {
					ThesisProposal thesis = documentDao
							.getFirstDocumentForUserOfSpecificType(
									student.getId(), DocumentType.ThesisProposal);
					if (!teacherHaveRights && !thesis.getScientificLeaderIds().contains(userId)) {
						filteredStudents.remove(student);
						continue;
					}
					if (thesis != null && leaderId != null && !thesis.getScientificLeaderIds().contains(leaderId)) {
						filteredStudents.remove(student);
						continue;
					}
					ThesisRecension recension = documentDao.getDocumentById(student.getThesisRecensionId());
					if (recension != null && reviewerId != null && recension.getReviewerId() != reviewerId) {
						filteredStudents.remove(student);
					}
				}
				return filteredStudents;
			}
		}
		return null;
	}

	public List<Object> reportForThesisDefences(ObjectId userId,
			Date startDate, Date endDate, ObjectId commissionParticipantId) {
		Boolean teacherHaveRights = userHaveRightsForReports(userId);
		if (teacherHaveRights != null) {
			if (!teacherHaveRights && userId != commissionParticipantId) {
				return null;
			}	
			Date startDateOnDayStart = dateWithoutHoursByAddingDays(startDate, 0);
			Date endDateOnDayEnd = dateWithoutHoursByAddingDays(endDate == null ? startDate : endDate, 1);
			List<ThesisDefence> thesisDefences = thesisDefenceDao.thesisDefencesBetweenDatesIncludingCommissionParticipants(startDateOnDayStart, 
					endDateOnDayEnd, commissionParticipantId);
			return thesisDefenceService.getAllThesisDefenceForJsonSerialize(thesisDefences);
		}
		return null;
	}

	private Boolean userHaveRightsForReports(ObjectId userId) {
		Teacher teacher = userDao.getUserById(userId);
		if (teacher != null && teacher.getType() == UserType.Teacher) {
			return teacher.getProfile().getDepartment() == Department.SoftwareTechnologies;
		} 

		return null;
	}
	
	private Date dateWithoutHoursByAddingDays(Date date, Integer days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}
}
