package bg.unisofia.fmi.docmag.dao;

import java.util.List;

import bg.unisofia.fmi.docmag.domain.impl.document.Document;
import bg.unisofia.fmi.docmag.domain.impl.document.Document.DocumentType;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal.ThesisProposalStatus;
import bg.unisofia.fmi.docmag.domain.impl.user.User;

public interface DocumentDAO {

	public List<Document> getAllDocumentsForUser(User user);

	public List<Document> getAllDocumentForUserOfSpecificType(User user,
			DocumentType type);
	public List<ThesisProposal> getThesisProposalDocumentsForUser(User user);
	
	public ThesisProposalStatus getThesisProposalStatusForUser(User user);

	public boolean saveDocument(Document document);

	public void deleteDocumentWithId(String documentId);

	public void deleteAllDocumentsForUser(User user);
}
