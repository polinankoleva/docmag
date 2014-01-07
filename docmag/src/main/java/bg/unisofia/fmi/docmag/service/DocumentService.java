package bg.unisofia.fmi.docmag.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.unisofia.fmi.docmag.dao.DocumentDAO;
import bg.unisofia.fmi.docmag.dao.UserDAO;
import bg.unisofia.fmi.docmag.domain.impl.user.User;
import bg.unisofia.fmi.docmag.domain.impl.document.Document;
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
		return documentDao.getAllDocumentsForUser(user);
	}


	public ThesisProposal getThesisProposal(String username){
		User user = userDao.getUserByUsername(username);
		return documentDao.getThesisProposalDocumentsForUser(user).get(0);
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
		thesisProposal.setUserId(userDao.getUserByUsername(username).getId().toString());
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
			ThesisProposal thesisProposal){
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
