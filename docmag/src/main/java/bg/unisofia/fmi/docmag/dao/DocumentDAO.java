package bg.unisofia.fmi.docmag.dao;

import java.util.List;

import org.bson.types.ObjectId;

import bg.unisofia.fmi.docmag.domain.impl.document.Document;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal;
import bg.unisofia.fmi.docmag.domain.impl.document.Document.DocumentType;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal.ThesisProposalStatus;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
public interface DocumentDAO {

	public <T extends Document> T getDocumentById(ObjectId id);

	public <T extends Document> T getDocument(Document document);

	public List<Document> getAllDocumentsForUser(ObjectId userId);

	public List<?> getAllDocumentsForUserOfSpecificType(ObjectId userId,
			DocumentType type);
	
	public List<ThesisProposal> getAllThesisProposalWithStatus(ThesisProposalStatus status);

	public <T extends Document> T getFirstDocumentForUserOfSpecificType(
			ObjectId userId, DocumentType type);

	public void saveDocument(Document document);

	public void deleteDocumentWithId(ObjectId documentId);

	public void deleteAllDocumentsForUser(ObjectId userId);

	// specific document methods
	// thesis proposal
	public ThesisProposalStatus getThesisProposalStatusForUser(ObjectId userId);

	public boolean assignScientificLeaderForThesis(Teacher teacher,
			ThesisProposal thesis);

	public boolean assignConsultantForThesis(ObjectId userId, ThesisProposal thesis);

}
