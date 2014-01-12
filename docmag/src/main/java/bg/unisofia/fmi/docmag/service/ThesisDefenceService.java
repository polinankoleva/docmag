package bg.unisofia.fmi.docmag.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.unisofia.fmi.docmag.dao.ThesisDefenceDAO;
import bg.unisofia.fmi.docmag.dao.UserDAO;
import bg.unisofia.fmi.docmag.domain.impl.ThesisDefence;
import bg.unisofia.fmi.docmag.domain.impl.profile.TeacherProfile;
import bg.unisofia.fmi.docmag.domain.impl.user.Student;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
import bg.unisofia.fmi.docmag.domain.impl.user.User;

@Service
public class ThesisDefenceService {

	@Autowired
	ThesisDefenceDAO thesisDefenceDao;

	@Autowired
	DocumentService documentService;

	@Autowired
	UserDAO userDao;

	public List<Object> getThesisDefences() {
		 List<ThesisDefence> thesisDefences = thesisDefenceDao.getAllThesisDefences();
		 return getAllThesisDefenceForJsonSerialize(thesisDefences);
	}

	public void insertThesisDefence(Date date, List<ObjectId> commissionParticipantIds){
		thesisDefenceDao.createThesisDefence(date, documentService.checkTeacherObjectIds(commissionParticipantIds));
	}

	public Map<String, String> updateThesisDefence(ObjectId thesisDefenceId, Date date, List<ObjectId> commissionParticipantIds){
		if(hasAssignedStudentsForThesisDefence(thesisDefenceId)){
			return createErrorMessage("Thesis defence can't be updated.");
		}else{
			ThesisDefence thesisDefence = thesisDefenceDao.getThesisDefenceById(thesisDefenceId);
			if(thesisDefence != null){
				if(date != null){
					thesisDefence.setDate(date);
				}
				if(commissionParticipantIds != null && !commissionParticipantIds.isEmpty()){
					thesisDefence.setCommissionParticipantIds(documentService.checkTeacherObjectIds(commissionParticipantIds));
				}
			}
			thesisDefenceDao.saveThesisDefence(thesisDefence);
		}
		return null;
	}

	public Map<String, String> deteteThesisDefence(ObjectId thesisDefenceId){
		if(hasAssignedStudentsForThesisDefence(thesisDefenceId)){
			return createErrorMessage("Thesis defence can't be deleted.");
		} else {
			thesisDefenceDao.delete(thesisDefenceId);
		}
		return null;
	}

	public Map<String, String> createErrorMessage(String text){
		Map<String, String> errorMessage = new HashMap<String, String>();
		errorMessage.put("errorMessage", text);
		return errorMessage;
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

	public boolean hasAssignedStudentsForThesisDefence(ObjectId thesisDefenceId){
		boolean isAssignedThesisDefence = true;
		List<Student> students = userDao.studentsForThesisDefenceWithId(thesisDefenceId);
		if(students == null || students.isEmpty() || students.size() == 0){
			isAssignedThesisDefence = false;
		}
		return isAssignedThesisDefence;
	}

	private List<Object> getAllThesisDefenceForJsonSerialize(List<ThesisDefence> thesisDefences){
		List<Object> thesisDefencesList = new ArrayList<Object>();
		for(int i = 0 ; i < thesisDefences.size(); i++){
			thesisDefencesList.add(getThesisDefenceInMap(thesisDefences.get(i)));
		}
		return thesisDefencesList;
	}

	public Map<String, Object> getThesisDefenceInMap(ThesisDefence thesisDefence){
		Map <String, Object> thesisDefenceMap = new HashMap<String, Object>();
		thesisDefenceMap.put("id", thesisDefence.getId().toString());
		thesisDefenceMap.put("date", thesisDefence.getDate());
		thesisDefenceMap.put("commissionParticipants", getAllCommissionParticipantsInMap(thesisDefence.getCommissionParticipantIds()));
		return thesisDefenceMap;
	}

	private List<Object> getAllCommissionParticipantsInMap(List<ObjectId> commissionParticipantIds){
		List<Object> allCommissionParticipants = new ArrayList<>();
		for(int i = 0; i < commissionParticipantIds.size(); i++){
			Map<String, String> commisionParticipant = getCommissionParticipantInfo(commissionParticipantIds.get(i));
			if(commisionParticipant != null && !commisionParticipant.isEmpty()){
				allCommissionParticipants.add(commisionParticipant);
			}
		}
		return allCommissionParticipants;
	}

	private Map<String, String> getCommissionParticipantInfo(ObjectId commissionParticipantId){
		Map<String, String> commissionParticipant = new HashMap<String, String>();
		User user = userDao.getUserById(commissionParticipantId);
		if(user instanceof Teacher && user != null){
			commissionParticipant.put("id",user.getId().toString());
			commissionParticipant.put("name", user.getProfile().getName());
			commissionParticipant.put("deparment", ((TeacherProfile)user.getProfile()).getDepartment().getName());
		}
		return commissionParticipant;
	}
}
