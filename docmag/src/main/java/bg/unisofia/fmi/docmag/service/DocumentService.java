package bg.unisofia.fmi.docmag.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	public List<Document> getUserDocuments(String username) {
		User user = userDao.getUserByUsername(username);
		if (user != null) {
			List<Document> documents = documentDao.getAllDocumentsForUser(user);
			
			ArrayList<Document> docs = new ArrayList<Document>();
			for (Document document : documents) {
				docs.add(documentDao.getDocumentById(document.getId()));
			}
			
			return docs;
		}
		return null;
	}

	public ThesisProposalStatus getThesisProposalStatusForUserWithUsername(
			String username) {
		User user = userDao.getUserByUsername(username);
		return documentDao.getThesisProposalStatusForUser(user);
	}

	public ThesisProposal getThesisProposalForUserWithUsername(String username) {
		User user = userDao.getUserByUsername(username);
		ThesisProposal document = null;
		List<?> thesisProposals = documentDao
				.getAllDocumentsForUserOfSpecificType(user,
						DocumentType.ThesisProposal);
		if (thesisProposals != null && thesisProposals.size() > 0) {
			document = (ThesisProposal) thesisProposals.get(0);
		}
		return document;
	}
	
	public void editThesisProposal(ThesisProposal thesis) {
		thesis.setTasks(thesis.getTasks() + " 1 ");
		documentDao.saveDocument(thesis);
	}


	public ThesisProposal getThesisProposal(String username){
		return getThesisProposalForUserWithUsername(username);
	}

	public void updateThesisProposal(ThesisProposal thesisProposal){
		documentDao.saveDocument(thesisProposal);
	}
	
	public void insertThesisProposal(ThesisProposal thesisProposal){
		documentDao.saveDocument(thesisProposal);
	}
	
	public void insertThesisProposalForUserByUsername(String username, String subject, String anotation, String purpose,
			String tasks, String restrictions, Date executionDeadline, List<String> scientificLeaderIds, List<String> consultantIds){
		ThesisProposal thesisProposal = new ThesisProposal();
		thesisProposal.setUserId(userDao.getUserByUsername(username).getId());
		thesisProposal.setAnnotation(anotation);
		thesisProposal.setSubject(subject);
		thesisProposal.setPurpose(purpose);
		thesisProposal.setTasks(tasks);
		thesisProposal.setRestrictions(restrictions);
		thesisProposal.setExecutionDeadline(executionDeadline);
		thesisProposal.setScientificLeaderIds(scientificLeaderIds);
		thesisProposal.setConsultantIds(consultantIds);
		thesisProposal.setLastModifiedDate(new Date());
		insertThesisProposal(thesisProposal);
	}
	
	public void updateThesisProposalForUserByUsername(String username, String subject, String anotation, String purpose,
			String tasks, String restrictions, Date executionDeadline, List<String> scientificLeaderIds, List<String> consultantIds){
		ThesisProposal thesisProposal = getThesisProposal(username);
		checkPropertiesForThesisProposal(subject, anotation, purpose, tasks, restrictions, executionDeadline, scientificLeaderIds, consultantIds, thesisProposal);
		updateThesisProposal(thesisProposal);
	}
	
	
	private void checkPropertiesForThesisProposal(String subject, String anotation, String purpose,
			String tasks, String restrictions, Date executionDeadline, List<String> scientificLeaderIds, List<String> consultantIds,
			ThesisProposal thesisProposal) {
		if(subject != null){
			thesisProposal.setSubject(subject);
		}
		if(anotation != null){
			thesisProposal.setAnnotation(anotation);
		}
		if(purpose != null){
			thesisProposal.setPurpose(purpose);
		}
		if(tasks != null){
			thesisProposal.setTasks(tasks);
		}
		if(restrictions != null){
			thesisProposal.setRestrictions(restrictions);
		}
		if(executionDeadline != null){
			thesisProposal.setExecutionDeadline(executionDeadline);
		}
		if(scientificLeaderIds != null && !scientificLeaderIds.isEmpty()){
			thesisProposal.setScientificLeaderIds(scientificLeaderIds);
		}
		if(consultantIds != null && !consultantIds.isEmpty()){
			thesisProposal.setConsultantIds(consultantIds);
		}
	}
	
	public ThesisProposalStatus checkStatusForThesisProposal(String username){
		User user = userDao.getUserByUsername(username);
		return documentDao.getThesisProposalStatusForUser(user);
	}
}
