package bg.unisofia.fmi.docmag.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.unisofia.fmi.docmag.dao.DocumentDAO;
import bg.unisofia.fmi.docmag.dao.UserDAO;
import bg.unisofia.fmi.docmag.domain.impl.document.Document;
import bg.unisofia.fmi.docmag.domain.impl.document.Document.DocumentType;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal.ThesisProposalStatus;
import bg.unisofia.fmi.docmag.domain.impl.user.Student;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
import bg.unisofia.fmi.docmag.domain.impl.user.User.UserType;

@Service
public class DocumentService {

	@Autowired
	DocumentDAO documentDao;

	@Autowired
	UserService userService;

	@Autowired
	UserDAO userDao;

	public List<Document> getUserDocuments(ObjectId userId) {
		List<Document> documents = documentDao.getAllDocumentsForUser(userId);
		if(documents != null && !documents.isEmpty()){
			ArrayList<Document> docs = new ArrayList<Document>();
			for (Document document : documents) {
				docs.add(documentDao.getDocumentById(document.getId()));
			}

			return docs;
		}
		return null;
	}

	private ThesisProposal getThesisProposalForUser(ObjectId userId) {
		ThesisProposal document = null;
		List<?> thesisProposals = documentDao.getAllDocumentsForUserOfSpecificType(userId, 
				DocumentType.ThesisProposal);
		if (thesisProposals != null && thesisProposals.size() > 0) {
			document = (ThesisProposal) thesisProposals.get(0);
		}
		return document;
	}

	public Map<String, Object> getThesisProposal(ObjectId userId) {
		ThesisProposal thesisProposal = getThesisProposalForUser(userId); 
		Map<String, Object> allInformationForThesisProposal = new HashMap<String, Object>();
		if(userService.getUserById(userId) instanceof Student && userService.getUserById(userId) != null){
			if(thesisProposal != null){
				allInformationForThesisProposal.put("user", createUserInfoJsonForThesisProposal(userId));
				allInformationForThesisProposal.put("thesisProposal", createThesisProposalJsonWithConsultsAndscientificLeader(thesisProposal));
			}else{
				allInformationForThesisProposal.put("user", createUserInfoJsonForThesisProposal(userId));
				allInformationForThesisProposal.put("teachers", createAllTeacherMap(getAllTeachers()));
			}
		}
		return allInformationForThesisProposal;	
	}

	public void updateThesisProposal(ThesisProposal thesisProposal) {
		documentDao.saveDocument(thesisProposal);
	}

	public void insertThesisProposal(ThesisProposal thesisProposal) {
		documentDao.saveDocument(thesisProposal);
	}

	public void insertThesisProposalForUser(ObjectId userId, String subject, String anotation, String purpose,
			String tasks, String restrictions, Date executionDeadline, List<ObjectId> scientificLeaderIds, List<ObjectId> consultantIds, ThesisProposalStatus status){
		if(userService.getUserById(userId) != null && userService.getUserById(userId) instanceof Student && getThesisProposalForUser(userId) != null){
			ThesisProposal thesisProposal = new ThesisProposal();
			thesisProposal.setUserId(userId);
			thesisProposal.setAnnotation(anotation);
			thesisProposal.setSubject(subject);
			thesisProposal.setPurpose(purpose);
			thesisProposal.setTasks(tasks);
			thesisProposal.setRestrictions(restrictions);
			thesisProposal.setExecutionDeadline(executionDeadline);
			thesisProposal.setScientificLeaderIds(checkTeacherObjectIds(scientificLeaderIds));
			thesisProposal.setConsultantIds(checkTeacherObjectIds(consultantIds));
			if(status != null && !status.equals("")){
				thesisProposal.setStatus(status);
			} else{
				thesisProposal.setStatus(ThesisProposalStatus.Unapproved);
			}
			insertThesisProposal(thesisProposal);
		}
	}

	public void updateThesisProposalForUser(ObjectId userId, String subject, String anotation, String purpose,
			String tasks, String restrictions, Date executionDeadline, List<ObjectId> scientificLeaderIds, List<ObjectId> consultantIds, ThesisProposalStatus status){
		ThesisProposal thesisProposal = getThesisProposalForUser(userId);
		if(thesisProposal != null){
			checkPropertiesForThesisProposal(subject, anotation, purpose, tasks, restrictions, executionDeadline, scientificLeaderIds, consultantIds, status, thesisProposal);
			updateThesisProposal(thesisProposal);
		}
	}


	private void checkPropertiesForThesisProposal(String subject, String anotation, String purpose,
			String tasks, String restrictions, Date executionDeadline, List<ObjectId> scientificLeaderIds, List<ObjectId> consultantIds, ThesisProposalStatus status,
			ThesisProposal thesisProposal) {
		if (subject != null) {
			thesisProposal.setSubject(subject);
		}
		if (anotation != null) {
			thesisProposal.setAnnotation(anotation);
		}
		if (purpose != null) {
			thesisProposal.setPurpose(purpose);
		}
		if (tasks != null) {
			thesisProposal.setTasks(tasks);
		}
		if (restrictions != null) {
			thesisProposal.setRestrictions(restrictions);
		}
		if (executionDeadline != null) {
			thesisProposal.setExecutionDeadline(executionDeadline);
		}
		if (scientificLeaderIds != null && !scientificLeaderIds.isEmpty()) {
			thesisProposal.setScientificLeaderIds(checkTeacherObjectIds(scientificLeaderIds));
		}
		if (consultantIds != null && !consultantIds.isEmpty()) {
			thesisProposal.setConsultantIds(checkTeacherObjectIds(consultantIds));
		}
		if(subject != null){
			thesisProposal.setStatus(status);
		}
	}

	public ThesisProposalStatus getThesisProposalStatusForUser(ObjectId userId) {
		return documentDao.getThesisProposalStatusForUser(userId);
	}

	public ThesisProposalStatus checkStatusForThesisProposal(ObjectId userId) {
		return getThesisProposalStatusForUser(userId);
	}

	//JSON

	private Map<String, String> createUserInfoJsonForThesisProposal(ObjectId userId) {
		Map<String, String> userInfoForThesisProposal = new HashMap<String, String>();
		Student student  = userService.getUserById(userId);
		if(student != null ){
			userInfoForThesisProposal.put("name", student.getProfile().getName());
			userInfoForThesisProposal.put("studentIdentifier", student.getProfile().getStudentIdentifier());
			userInfoForThesisProposal.put("educationSubject", student.getProfile().getEducationSubject());
		}
		return userInfoForThesisProposal;

	}

	private Map<String, Object> createThesisProposalJsonWithConsultsAndscientificLeader(ThesisProposal thesisProposal) {
		Map<String, Object> thesisProposalParams = new HashMap<String, Object>();
		thesisProposalParams.put("id", thesisProposal.getId().toString());
		thesisProposalParams.put("annotation", thesisProposal.getAnnotation());
		thesisProposalParams.put("purpose", thesisProposal.getPurpose());
		thesisProposalParams.put("subject", thesisProposal.getSubject());
		thesisProposalParams.put("task", thesisProposal.getTasks());
		thesisProposalParams.put("restriction", thesisProposal.getRestrictions());
		thesisProposalParams.put("executionDeadline", thesisProposal.getExecutionDeadline().toString());
		List<Teacher> consultants = userService.getConsultantsForThesis(thesisProposal);
		if(consultants != null && !consultants.isEmpty()){
			thesisProposalParams.put("consultants", getAllConsultantsForThesisProposalInMap(consultants));
		}else{
			thesisProposalParams.put("consultants", thesisProposal.getConsultantIds());
		}
		List<Teacher> scientificLeaders =  userService.getScientificLeadersForThesis(thesisProposal);
		if(scientificLeaders != null && !scientificLeaders.isEmpty()){
			thesisProposalParams.put("scientificLeaders", getAllScientificLeaderForThesisProposalInMap(scientificLeaders));
		}else{
			thesisProposalParams.put("scientificLeaders",thesisProposal.getScientificLeaderIds());
		}
		return thesisProposalParams;
	}

	private Map<String, Object> getAllConsultantsForThesisProposalInMap(List<Teacher> consultants){
		Map<String, Object> consultantsInfo = new HashMap<String, Object>();
		for(int i = 0; i < consultants.size(); i++){
			Map<String, String> consultant  = new HashMap<String, String>();
			consultant.put("name", consultants.get(i).getProfile().getName());
			consultant.put("id", consultants.get(i).getId().toString());
			consultantsInfo.put("consultant", consultant);
		}
		return consultantsInfo;
	}

	private Map<String, Object> getAllScientificLeaderForThesisProposalInMap(List<Teacher> scientificLeaders){
		Map<String, Object> scientificLeadersInfo = new HashMap<String, Object>();
		for(int i = 0; i < scientificLeaders.size(); i++){
			Map<String, String> scientificLeader  = new HashMap<String, String>();
			scientificLeader.put("name", scientificLeaders.get(i).getProfile().getName());
			scientificLeader.put("id", scientificLeaders.get(i).getId().toString());
			scientificLeadersInfo.put("scientificLeader", scientificLeader);
		}
		return scientificLeadersInfo;
	}
	
	private Map<String, Object> createAllTeacherMap(List<Teacher> teachers){
		Map<String, Object> teachersInfo = new HashMap<String, Object>();
		if(teachers !=  null && !teachers.isEmpty()){
			for(int i = 0; i < teachers.size(); i++){
				Map<String, String> teacher  = new HashMap<String, String>();
				teacher.put("name", teachers.get(i).getProfile().getName());
				teacher.put("id", teachers.get(i).getId().toString());
				teachersInfo.put("teacher", teacher);
			}
		}
		return teachersInfo;	
	} 

	private List<Teacher> getAllTeachers(){
		return userDao.getAllUsersOfType(UserType.Teacher);
	}
	
	private List<ObjectId> checkTeacherObjectIds(List<ObjectId> objectIds){
		List<ObjectId> checkedTeacherObjectIds = new ArrayList<ObjectId>();
		if(objectIds != null && !objectIds.isEmpty()){
			for(int i = 0 ; i< objectIds.size(); i++){
				ObjectId objectId = objectIds.get(i);
				if(userService.getUserById(objectId) != null && userService.getUserById(objectId).getType() == UserType.Teacher){
					checkedTeacherObjectIds.add(objectId);
				}	
			}
		}
		return checkedTeacherObjectIds;
	}
}
