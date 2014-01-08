package bg.unisofia.fmi.docmag.dao;

import java.util.List;

import org.bson.types.ObjectId;

import bg.unisofia.fmi.docmag.domain.impl.document.Document;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal;
import bg.unisofia.fmi.docmag.domain.impl.document.Document.DocumentType;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal.ThesisProposalStatus;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
import bg.unisofia.fmi.docmag.domain.impl.user.User;

public interface DocumentDAO {

	public <T extends Document> T getDocumentById(ObjectId id);

	public <T extends Document> T getDocument(Document document);

	public List<Document> getAllDocumentsForUser(User user);

	public List<?> getAllDocumentsForUserOfSpecificType(User user,
			DocumentType type);

	public <T extends Document> T getFirstDocumentForUserOfSpecificType(
			User user, DocumentType type);

	public void saveDocument(Document document);

	public void deleteDocumentWithId(ObjectId documentId);

	public void deleteAllDocumentsForUser(User user);

	// specific document methods
	// thesis proposal
	public ThesisProposalStatus getThesisProposalStatusForUser(User user);

	public boolean assignScientificLeaderForThesis(Teacher teacher,
			ThesisProposal thesis);

	public boolean assignConsultantForThesis(User user, ThesisProposal thesis);

}
