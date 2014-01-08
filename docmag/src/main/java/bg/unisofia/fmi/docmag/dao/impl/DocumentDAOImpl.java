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

	final static String COLLECTION = "documents";
	
	@Autowired
	private MongoTemplate mongoTemplate;

	private <T> List<T> documentsForQueryOfClass(Query query,
			Class<T> documentClass) {
		List<T> documents = mongoTemplate.find(query, documentClass,
				COLLECTION);
		return documents;
	}

	private <T extends Document> T documentForQueryOfClass(Query query,
			Class<T> documentClass) {
		T document = mongoTemplate.findOne(query, documentClass, COLLECTION);
		return document;
	}

	@Override
	public <T extends Document> T getDocumentById(ObjectId id) {
		Query searchDocumentQuery = new Query(Criteria.where("_id").is(id));
		Document document = documentForQueryOfClass(searchDocumentQuery,
				Document.class);
		return getDocument(document);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Document> T getDocument(Document document) {
		Query searchDocumentQuery = new Query(Criteria.where("_id").is(document.getId()));
		T specificDocument = null;

		switch (document.getType()) {
		case ThesisProposal:
			specificDocument = (T) documentForQueryOfClass(searchDocumentQuery, ThesisProposal.class);
			break;
		default:
			break;
		}

		return specificDocument;
	}
	
	@Override
	public List<Document> getAllDocumentsForUser(User user) {
		ObjectId userId = user.getId();
		Query searchDocumentsQuery = new Query(Criteria.where("userId").is(
				userId));

		return documentsForQueryOfClass(searchDocumentsQuery, Document.class);
	}

	@Override
	public List<?> getAllDocumentsForUserOfSpecificType(User user,
			DocumentType type) {
		ObjectId userId = user.getId();
		Query searchDocumentsQuery = new Query(Criteria.where("userId").is(
				userId));
		searchDocumentsQuery.addCriteria(Criteria.where("type").is(
				type.toString()));

		return documentsForQueryOfClass(searchDocumentsQuery,
				Document.getClassForDocumentType(type));
	}

	@Override
	public ThesisProposalStatus getThesisProposalStatusForUser(User user) {
		List<?> thesisProposals = getAllDocumentsForUserOfSpecificType(user,
				DocumentType.ThesisProposal);
		if (thesisProposals != null && thesisProposals.size() > 0) {
			ThesisProposal document = (ThesisProposal) thesisProposals.get(0);
			return document.getStatus();
		} else {
			return ThesisProposalStatus.NotSubmitted;
		}
	}

	@Override
	public boolean saveDocument(Document documentToSave) {
		mongoTemplate.save(documentToSave, COLLECTION);
		return true;
	}

	@Override
	public void deleteDocumentWithId(ObjectId documentId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllDocumentsForUser(User user) {
		// TODO Auto-generated method stub

	}

}
