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
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisRecension;
import bg.unisofia.fmi.docmag.domain.impl.profile.TeacherProfile;
import bg.unisofia.fmi.docmag.domain.impl.profile.TeacherProfile.Department;
import bg.unisofia.fmi.docmag.domain.impl.user.Student;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
import bg.unisofia.fmi.docmag.domain.impl.user.User;
import bg.unisofia.fmi.docmag.domain.impl.user.User.UserType;
import bg.unisofia.fmi.docmag.email.EmailSender;

@Service
public class DocumentService {

	@Autowired
	DocumentDAO documentDao;

	@Autowired
	UserService userService;

	@Autowired
	UserDAO userDao;
	
	@Autowired
	EmailSender emailSender;
	
	private final String APPROVED_SUBJECT = "Approved Thesis Proposal";
	private final String APPROVED_WITH_NOTES_SUBJECT = "Approved with notes Thesis Proposal";

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

	public ThesisProposal getThesisProposalForUser(ObjectId userId) {
		ThesisProposal document = null;
		List<?> thesisProposals = documentDao.getAllDocumentsForUserOfSpecificType(userId, 
				DocumentType.ThesisProposal);
		if (thesisProposals != null && thesisProposals.size() > 0) {
			document = (ThesisProposal) thesisProposals.get(0);
		}
		return document;
	}

	public ThesisRecension getThesisRecensionForUser(ObjectId userId) {
		ThesisRecension document = null;
		List<?> thesisRecensions = documentDao.getAllDocumentsForUserOfSpecificType(userId, 
				DocumentType.ThesisRecension);
		if (thesisRecensions != null && thesisRecensions.size() > 0) {
			document = (ThesisRecension) thesisRecensions.get(0);
		}
		return document;
	}

	public Map<String, Object> getThesisProposalInfo(ObjectId userId) {
		ThesisProposal thesisProposal = getThesisProposalForUser(userId); 
		Map<String, Object> allInformationForThesisProposal = new HashMap<String, Object>();
		if(userService.getUserById(userId) instanceof Student && userService.getUserById(userId) != null){
			if(thesisProposal != null){
				allInformationForThesisProposal.put("user", createUserInfoJsonForThesisProposal(userId));
				allInformationForThesisProposal.put("thesisProposal", createThesisProposalJsonWithConsultsAndscientificLeader(thesisProposal));
				allInformationForThesisProposal.put("teachers", createAllTeacherMap(getAllTeachers()));
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

	public void insertThesisProposalForUser(ObjectId userId, String subject, String annotation, String purpose,
			String tasks, String restrictions, Date executionDeadline, List<ObjectId> scientificLeaderIds, List<ObjectId> consultantIds, ThesisProposalStatus status){
		if(userService.getUserById(userId) != null && userService.getUserById(userId) instanceof Student){
			ThesisProposal thesisProposal = getThesisProposalForUser(userId);
			if (thesisProposal == null) thesisProposal = new ThesisProposal();
			thesisProposal.setUserId(userId);
			thesisProposal.setAnnotation(annotation);
			thesisProposal.setSubject(subject);
			thesisProposal.setPurpose(purpose);
			thesisProposal.setTasks(tasks);
			thesisProposal.setRestrictions(restrictions);
			thesisProposal.setExecutionDeadline(executionDeadline);
			thesisProposal.setScientificLeaderIds(checkTeacherObjectIds(scientificLeaderIds));
			thesisProposal.setConsultantIds(checkTeacherObjectIds(consultantIds));
			thesisProposal.setStatus(ThesisProposalStatus.Submitted);
			if (thesisProposal.getId() == null) {
			    insertThesisProposal(thesisProposal);
			}
			else {
				updateThesisProposal(thesisProposal);
			}
		}
	}

	public void updateThesisProposalForUser(ObjectId userId, String subject, String annotation, String purpose,
			String tasks, String restrictions, Date executionDeadline, List<ObjectId> scientificLeaderIds, List<ObjectId> consultantIds, ThesisProposalStatus status){
		ThesisProposal thesisProposal = getThesisProposalForUser(userId);
		if(thesisProposal != null){
			checkPropertiesForThesisProposal(subject, annotation, purpose, tasks, restrictions, executionDeadline, scientificLeaderIds, consultantIds, status, thesisProposal);
			updateThesisProposal(thesisProposal);
		}
	}


	private void checkPropertiesForThesisProposal(String subject, String annotation, String purpose,
			String tasks, String restrictions, Date executionDeadline, List<ObjectId> scientificLeaderIds, List<ObjectId> consultantIds, ThesisProposalStatus status,
			ThesisProposal thesisProposal) {
		if (subject != null) {
			thesisProposal.setSubject(subject);
		}
		if (annotation != null) {
			thesisProposal.setAnnotation(annotation);
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
		if(status != null){
			thesisProposal.setStatus(status);
			User user = userDao.getUserByThesisProposalId(thesisProposal.getId());
			if(user != null && user instanceof Student){
				Student student = (Student) user;
				sendEmailForUpdateInThesisStatus(status, student);
			}	
		}
	}

	private void sendEmailForUpdateInThesisStatus(ThesisProposalStatus status, Student student){
		if(status.toString().equalsIgnoreCase(ThesisProposalStatus.Approved.toString())){
			System.out.println("Sending email to:" + student.getProfile().getEmail());
			emailSender.sendEmail(student.getProfile().getEmail(), APPROVED_SUBJECT, emailSender.generateTextForThesisProposal(status, student.getProfile().getName()));
		}
		if(status.toString().equalsIgnoreCase(ThesisProposalStatus.ApprovedWithNotes.toString())){
			System.out.println("Sending email to:" + student.getProfile().getEmail());
			emailSender.sendEmail(student.getProfile().getEmail(), APPROVED_WITH_NOTES_SUBJECT, emailSender.generateTextForThesisProposal(status, student.getProfile().getName()));
		}
	}

	public Map<String, String> checkStatusForThesisProposal(ObjectId userId) {
		ThesisProposal thesisProposal = getThesisProposalForUser(userId);
		if(thesisProposal != null){
			return makeThesisProposalStatusInMap(thesisProposal.getStatus(), thesisProposal.getNotes());
		} 
		return null;
	}

	public Map<String, String> getThesisProposalStatus(ObjectId thesisProposalId){
		ThesisProposal thesisProposal = documentDao.getDocumentById(thesisProposalId);
		if(thesisProposal != null && thesisProposal.getStatus() != null){
			return makeThesisProposalStatusInMap(thesisProposal.getStatus(), thesisProposal.getNotes());
		}
		return null;
	}
	
	public void updateThesisProposalStatus(ObjectId thesisProposalId, ThesisProposalStatus thesisStatus, String notes){
		ThesisProposal thesisProposal = documentDao.getDocumentById(thesisProposalId);
		if(thesisStatus != null && thesisProposal != null ){
			thesisProposal.setStatus(thesisStatus);
			if(notes != null && (thesisStatus == ThesisProposalStatus.ApprovedWithNotes || thesisStatus == ThesisProposalStatus.Unapproved)){
				thesisProposal.setNotes(notes);
			} else{
				thesisProposal.setNotes(null);
			}
			updateThesisProposal(thesisProposal);
			User user = userDao.getUserByThesisProposalId(thesisProposalId);
			if(user != null && user instanceof Student){
				Student student = (Student) user;
				sendEmailForUpdateInThesisStatus(thesisStatus, student);
			}
		}
	}

	private Map<String, String>  makeThesisProposalStatusInMap(ThesisProposalStatus status, String notes){
		Map<String,String> statusParam = new HashMap<String, String>();
		statusParam.put("status", status.toString());
		if(notes != null){
			statusParam.put("notes", notes);
		}
		return statusParam;
	}
	
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

	private List<Object> getAllConsultantsForThesisProposalInMap(List<Teacher> consultants){
		List<Object> consultantsInfo = new ArrayList<Object>();
		for(int i = 0; i < consultants.size(); i++){
			Map<String, String> consultant  = new HashMap<String, String>();
			consultant.put("name", consultants.get(i).getProfile().getName());
			consultant.put("id", consultants.get(i).getId().toString());
			consultantsInfo.add(consultant);
		}
		return consultantsInfo;
	}

	private List<Object> getAllScientificLeaderForThesisProposalInMap(List<Teacher> scientificLeaders){
		List<Object> scientificLeadersInfo = new ArrayList<Object>();
		for(int i = 0; i < scientificLeaders.size(); i++){
			Map<String, String> scientificLeader  = new HashMap<String, String>();
			scientificLeader.put("name", scientificLeaders.get(i).getProfile().getName());
			scientificLeader.put("id", scientificLeaders.get(i).getId().toString());
			scientificLeadersInfo.add(scientificLeader);
		}
		return scientificLeadersInfo;
	}
	
	public List<Object> createAllTeacherMap(List<Teacher> teachers){
		List<Object> teachersInfo = new ArrayList<Object>();
		if(teachers !=  null && !teachers.isEmpty()){
			for(int i = 0; i < teachers.size(); i++){
				Map<String, String> teacher  = new HashMap<String, String>();
				teacher.put("name", teachers.get(i).getProfile().getName());
				teacher.put("id", teachers.get(i).getId().toString());
				teacher.put("department", teachers.get(i).getProfile().getDepartment().getName());
				teachersInfo.add(teacher);
			}
		}
		return teachersInfo;	
	} 

	public List<Teacher> getAllTeachers(){
		return userDao.getAllUsersOfType(UserType.Teacher);
	}
	
	public List<ObjectId> checkTeacherObjectIds(List<ObjectId> objectIds){
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
	
	public void deleteThesisProposalForUser(ObjectId userId){
		ThesisProposal thesisProposal  = getThesisProposalForUser(userId);
		if(thesisProposal != null){
			deleteDocument(thesisProposal.getId());	
		}
	}
	
	private void deleteDocument(ObjectId id){
		documentDao.deleteDocumentWithId(id);
	}
	
	public void deteleThesisRecensionForUser(ObjectId userId){
		ThesisRecension thesisRecension = getThesisRecensionForUser(userId);
		if(thesisRecension != null){
			deleteDocument(thesisRecension.getId());
		}
	}
	
	public List<ThesisProposal> getSubmittedThesisProposalsForTeacher(ObjectId teacherId) {
		User user = userDao.getUserById(teacherId);
		if (user != null && user.getType() == UserType.Teacher && 
				((TeacherProfile)user.getProfile()).getDepartment() == Department.SoftwareTechnologies) {
			return documentDao.getAllThesisProposalWithStatus(ThesisProposalStatus.Submitted);
		}
		return null;
	}
	
}
