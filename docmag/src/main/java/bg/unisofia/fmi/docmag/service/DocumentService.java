package bg.unisofia.fmi.docmag.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.unisofia.fmi.docmag.dao.DocumentDAO;
import bg.unisofia.fmi.docmag.dao.UserDAO;
import bg.unisofia.fmi.docmag.domain.impl.user.User;
import bg.unisofia.fmi.docmag.domain.impl.document.Document;
import bg.unisofia.fmi.docmag.domain.impl.document.Document.DocumentType;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal.ThesisProposalStatus;

@Service
public class DocumentService {
	
	@Autowired
	DocumentDAO documentDao;

	@Autowired
	UserDAO userDao;

	private ObjectId getObjectIdOfUser(String userId){
		return new ObjectId(userId);
	}
	
	public List<Document> getUserDocuments(String userId) {
		ObjectId objectIdOfUser = getObjectIdOfUser(userId);
		List<Document> documents = documentDao.getAllDocumentsForUser(objectIdOfUser);
		if(documents != null && !documents.isEmpty()){
			ArrayList<Document> docs = new ArrayList<Document>();
			for (Document document : documents) {
				docs.add(documentDao.getDocumentById(document.getId()));
			}

			return docs;
		}
		return null;
	}
	
	public ThesisProposalStatus getThesisProposalStatusForUser(String userId) {
		return documentDao.getThesisProposalStatusForUser(getObjectIdOfUser(userId));
	}

	public ThesisProposal getThesisProposalForUser(String userId) {
		ThesisProposal document = null;
		List<?> thesisProposals = documentDao.getAllDocumentsForUserOfSpecificType(getObjectIdOfUser(userId), 
				DocumentType.ThesisProposal);
		if (thesisProposals != null && thesisProposals.size() > 0) {
			document = (ThesisProposal) thesisProposals.get(0);
		}
		return document;
	}

	public ThesisProposal getThesisProposal(String userId) {
		return getThesisProposalForUser(userId);
	}

	public void updateThesisProposal(ThesisProposal thesisProposal) {
		documentDao.saveDocument(thesisProposal);
	}

	public void insertThesisProposal(ThesisProposal thesisProposal) {
		documentDao.saveDocument(thesisProposal);
	}

	public void insertThesisProposalForUser(String userId, String subject, String anotation, String purpose,
			String tasks, String restrictions, Date executionDeadline, List<ObjectId> scientificLeaderIds, List<ObjectId> consultantIds, ThesisProposalStatus status){
		ThesisProposal thesisProposal = new ThesisProposal();
		thesisProposal.setUserId(getObjectIdOfUser(userId));
		thesisProposal.setAnnotation(anotation);
		thesisProposal.setSubject(subject);
		thesisProposal.setPurpose(purpose);
		thesisProposal.setTasks(tasks);
		thesisProposal.setRestrictions(restrictions);
		thesisProposal.setExecutionDeadline(executionDeadline);
		thesisProposal.setScientificLeaderIds(scientificLeaderIds);
		thesisProposal.setConsultantIds(consultantIds);

		thesisProposal.setStatus(status);
		insertThesisProposal(thesisProposal);
	}
	
	public void updateThesisProposalForUser(String userId, String subject, String anotation, String purpose,
			String tasks, String restrictions, Date executionDeadline, List<ObjectId> scientificLeaderIds, List<ObjectId> consultantIds, ThesisProposalStatus status){
		ThesisProposal thesisProposal = getThesisProposal(userId);
		checkPropertiesForThesisProposal(subject, anotation, purpose, tasks, restrictions, executionDeadline, scientificLeaderIds, consultantIds, status, thesisProposal);
		updateThesisProposal(thesisProposal);
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
			thesisProposal.setScientificLeaderIds(scientificLeaderIds);
		}
		if (consultantIds != null && !consultantIds.isEmpty()) {
			thesisProposal.setConsultantIds(consultantIds);
		}
		if(subject != null){
			thesisProposal.setStatus(status);
		}
	}

	public ThesisProposalStatus checkStatusForThesisProposal(String userId) {
		return getThesisProposalStatusForUser(userId);
	}
}
