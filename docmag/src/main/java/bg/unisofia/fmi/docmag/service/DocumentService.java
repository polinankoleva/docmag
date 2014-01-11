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
		if(thesisProposal != null){
			allInformationForThesisProposal.put("studentInfo", createUserInfoJsonForThesisProposal(userId));
			allInformationForThesisProposal.put("thesisProposalInfo", thesisProposal);
		}else{
			//vryshtame user info and teachers
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
		ThesisProposal thesisProposal = new ThesisProposal();
		thesisProposal.setUserId(userId);
		thesisProposal.setAnnotation(anotation);
		thesisProposal.setSubject(subject);
		thesisProposal.setPurpose(purpose);
		thesisProposal.setTasks(tasks);
		thesisProposal.setRestrictions(restrictions);
		thesisProposal.setExecutionDeadline(executionDeadline);
		/*thesisProposal.setScientificLeaderIds(scientificLeaderIds);
		thesisProposal.setConsultantIds(consultantIds);*/

		thesisProposal.setStatus(status);
		insertThesisProposal(thesisProposal);
	}
	
	public void updateThesisProposalForUser(ObjectId userId, String subject, String anotation, String purpose,
			String tasks, String restrictions, Date executionDeadline, List<ObjectId> scientificLeaderIds, List<ObjectId> consultantIds, ThesisProposalStatus status){
		/*ThesisProposal thesisProposal = getThesisProposal(userId);
		checkPropertiesForThesisProposal(subject, anotation, purpose, tasks, restrictions, executionDeadline, scientificLeaderIds, consultantIds, status, thesisProposal);
		updateThesisProposal(thesisProposal);*/
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
		/*if (scientificLeaderIds != null && !scientificLeaderIds.isEmpty()) {
			thesisProposal.setScientificLeaderIds(scientificLeaderIds);
		}
		if (consultantIds != null && !consultantIds.isEmpty()) {
			thesisProposal.setConsultantIds(consultantIds);
		}*/
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
		Student student = userService.getUserById(userId);
		userInfoForThesisProposal.put("name", student.getProfile().getName());
		userInfoForThesisProposal.put("studentIdentifier", student.getProfile().getStudentIdentifier());
		userInfoForThesisProposal.put("educationSubject", student.getProfile().getEducationSubject());
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
		//thesisProposalParams.put("consultants", t)
		
		return thesisProposalParams;
		
	}
	
	/*private List<Teacher> getAllConsultantsForThesisProposal(List<ObjectId> consultantsIds){
		List<Teacher> consultants = new ArrayList<Teacher>();
		//TO REMOVE THIS WHEN HAVE NEW METHOD WITH LIST OF IDS FOR USERS
		for(int i = 0; i < consultantsIds.size(); i++ ){
			User user = userDao.
		}
	}*/
//	
//	private String getAllScientificleaderForThesisProposalInJsonString(){
//		
//	}
}
