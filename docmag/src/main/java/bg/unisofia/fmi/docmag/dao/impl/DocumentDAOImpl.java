package bg.unisofia.fmi.docmag.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import bg.unisofia.fmi.docmag.dao.DocumentDAO;
import bg.unisofia.fmi.docmag.domain.impl.document.Document;
import bg.unisofia.fmi.docmag.domain.impl.document.Document.DocumentType;
import bg.unisofia.fmi.docmag.domain.impl.user.User;

public class DocumentDAOImpl implements DocumentDAO {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	private List<Document> documentsForQuery(Query query) {
		List<Document> documents = mongoTemplate.find(query, Document.class, "documents");
		return documents;
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
	public boolean saveDocument(Document document) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteDocumentWithId(String documentId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllDocumentsForUser(User user) {
		// TODO Auto-generated method stub

	}

}
