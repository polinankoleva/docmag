package bg.unisofia.fmi.docmag.dao.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import bg.unisofia.fmi.docmag.dao.UserDAO;
import bg.unisofia.fmi.docmag.domain.User;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public static final String COLLECTION_NAME = "users";
	
	@Override
	public void createUser(User user) {
		 if (!mongoTemplate.collectionExists(User.class)) {
	            mongoTemplate.createCollection(User.class);
	        }       
	        user.setId(UUID.randomUUID().toString());
	        mongoTemplate.insert(user, COLLECTION_NAME);
	}

	@Override
	public User getUserByUsername(String username) {
		Query searchUserQuery = new Query(Criteria.where("username").is(username));
		User user = mongoTemplate.findOne(searchUserQuery, User.class);
		return user;
	}

}
