package bg.unisofia.fmi.docmag.service;

import java.util.ArrayList;
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
		List<Document> documents = documentDao.getAllDocumentsForUser(user);
		
		ArrayList<Document> docs = new ArrayList<Document>();
		for (Document document : documents) {
			docs.add(documentDao.getDocumentById(document.getId()));
		}
		
		return docs;
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
		System.out.println("Proposals " + thesisProposals);
		if (thesisProposals != null && thesisProposals.size() > 0) {
			document = (ThesisProposal) thesisProposals.get(0);
		}
		return document;
	}
	
	public void editThesisProposal(ThesisProposal thesis) {
		thesis.setTasks(thesis.getTasks() + " 1 ");
		documentDao.saveDocument(thesis);
	}

}
