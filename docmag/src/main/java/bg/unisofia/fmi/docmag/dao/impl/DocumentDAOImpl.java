package bg.unisofia.fmi.docmag.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import bg.unisofia.fmi.docmag.dao.DocumentDAO;
import bg.unisofia.fmi.docmag.domain.impl.document.Document;
import bg.unisofia.fmi.docmag.domain.impl.document.Document.DocumentType;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal.ThesisProposalStatus;
import bg.unisofia.fmi.docmag.domain.impl.user.User;

@Repository
public class DocumentDAOImpl implements DocumentDAO {

	@Autowired
	private MongoTemplate mongoTemplate;

	private final String COLLECTION_NAME_DOCUMENTS = "documents";

	private List<Document> documentsForQuery(Query query) {
		List<Document> documents = mongoTemplate.find(query, Document.class, COLLECTION_NAME_DOCUMENTS);
		return documents;
	}

	private List<ThesisProposal> thesisProposalDocumentsForQuery(Query query) {
		List<ThesisProposal> thesisProposals = mongoTemplate.find(query, ThesisProposal.class, COLLECTION_NAME_DOCUMENTS);
		return thesisProposals;
	}

	@Override
	public List<Document> getAllDocumentsForUser(User user) {
		ObjectId userId = user.getId();
		Query searchDocumentsQuery = new Query(Criteria.where("userId").is(userId));

		return documentsForQuery(searchDocumentsQuery);
	}

	@Override
	public List<Document> getAllDocumentForUserOfSpecificType(User user, DocumentType type) {
		ObjectId userId = user.getId();
		Query searchDocumentsQuery = new Query(Criteria.where("userId").is(userId));
		searchDocumentsQuery.addCriteria(Criteria.where("type").is(type.toString()));

		return documentsForQuery(searchDocumentsQuery);
	}

	@Override
	public ThesisProposalStatus getThesisProposalStatusForUser(User user) {
		List<Document> thesisProposals = getAllDocumentForUserOfSpecificType(user, DocumentType.ThesisProposal);
		if (thesisProposals != null && thesisProposals.size() > 0) {
			ThesisProposal document = (ThesisProposal) thesisProposals.get(0);
			return document.getStatus();
		}
		else {
			return ThesisProposalStatus.NotSubmitted;
		}
	}

	@Override
	public List<ThesisProposal> getThesisProposalDocumentsForUser(User user) {
		ObjectId userId = user.getId();
		Query searchDocumentsQuery = new Query(Criteria.where("userId").is(userId));
		searchDocumentsQuery.addCriteria(Criteria.where("type").is(DocumentType.ThesisProposal.toString()));
		return thesisProposalDocumentsForQuery(searchDocumentsQuery);
	}

	@Override
	public boolean saveDocument(Document document) {
		//MUST BE IMPLEMENTED
		//INSERT AND UPDATE
		return false;
	}

	@Override
	public void deleteDocumentWithId(String documentId) {

	}

	@Override
	public void deleteAllDocumentsForUser(User user) {
		// TODO Auto-generated method stub

	}

}
