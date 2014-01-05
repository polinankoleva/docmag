package bg.unisofia.fmi.docmag.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import bg.unisofia.fmi.docmag.dao.UserDAO;
import bg.unisofia.fmi.docmag.domain.impl.User;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public void createUser(User user) {
		 if (!mongoTemplate.collectionExists(User.class)) {
	            mongoTemplate.createCollection(User.class);
	        }       
	        mongoTemplate.insert(user);
	}

	@Override
	public User getUserByUsername(String username) {
		Query searchUserQuery = new Query(Criteria.where("userName").is(username));
		User user = mongoTemplate.findOne(searchUserQuery, User.class);
		return user;
	}

}
