package bg.unisofia.fmi.docmag.dao.impl;

import java.util.ArrayList;
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
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
import bg.unisofia.fmi.docmag.domain.impl.user.User;

@Repository
public class DocumentDAOImpl implements DocumentDAO {

	private final String COLLECTION = "documents";

	@Autowired
	private MongoTemplate mongoTemplate;

	private <T> List<T> documentsForQueryOfClass(Query query,
			Class<T> documentClass) {
		List<T> documents = mongoTemplate
				.find(query, documentClass, COLLECTION);
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
		Query searchDocumentQuery = new Query(Criteria.where("_id").is(
				document.getId()));
		T specificDocument = null;

		switch (document.getType()) {
		case ThesisProposal:
			specificDocument = (T) documentForQueryOfClass(searchDocumentQuery,
					ThesisProposal.class);
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

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Document> T getFirstDocumentForUserOfSpecificType(
			User user, DocumentType type) {
		List<?> documents = getAllDocumentsForUserOfSpecificType(user, type);
		if (documents != null && !documents.isEmpty()) {
			return (T) documents.get(0);
		}
		return null;
	}

	@Override
	public ThesisProposalStatus getThesisProposalStatusForUser(User user) {
		ThesisProposal thesisProposal = getFirstDocumentForUserOfSpecificType(
				user, DocumentType.ThesisProposal);
		if (thesisProposal != null) {
			return thesisProposal.getStatus();
		} else {
			return ThesisProposalStatus.NotSubmitted;
		}
	}

	@Override
	public boolean assignScientificLeaderForThesis(Teacher teacher,
			ThesisProposal thesis) {
		List<ObjectId> leaderIds = thesis.getScientificLeaderIds();
		if (leaderIds == null) {
			leaderIds = new ArrayList<ObjectId>();
		}
		if (!leaderIds.contains(teacher.getId())) {
			leaderIds.add(teacher.getId());
			saveDocument(thesis);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean assignConsultantForThesis(User user, ThesisProposal thesis) {
		List<ObjectId> consultantIds = thesis.getConsultantIds();
		if (consultantIds == null) {
			consultantIds = new ArrayList<ObjectId>();
		}
		if (!consultantIds.contains(user.getId())) {
			consultantIds.add(user.getId());
			saveDocument(thesis);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void saveDocument(Document documentToSave) {
		documentToSave.setLastModifiedDate();
		mongoTemplate.save(documentToSave, COLLECTION);
	}

	@Override
	public void deleteDocumentWithId(ObjectId documentId) {
		Query query = new Query(Criteria.where("_id").is(documentId));
		mongoTemplate.remove(query, COLLECTION);

	}

	@Override
	public void deleteAllDocumentsForUser(User user) {
		Query query = new Query(Criteria.where("userId").is(user.getId()));
		mongoTemplate.remove(query, COLLECTION);
	}

}
