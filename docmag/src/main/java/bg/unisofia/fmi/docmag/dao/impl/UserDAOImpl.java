package bg.unisofia.fmi.docmag.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import bg.unisofia.fmi.docmag.dao.UserDAO;
import bg.unisofia.fmi.docmag.domain.impl.User;
import bg.unisofia.fmi.docmag.domain.impl.Student;
import bg.unisofia.fmi.docmag.domain.impl.Teacher;
import bg.unisofia.fmi.docmag.domain.impl.PHDStudent;
import bg.unisofia.fmi.docmag.domain.impl.User.UserType;

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
		UserType userType = mongoTemplate.findOne(searchUserQuery, User.class).getType();
		
		switch (userType) {
		case Student:
			return mongoTemplate.findOne(searchUserQuery, Student.class);
		case PHD:
			return mongoTemplate.findOne(searchUserQuery, PHDStudent.class);
		case Teacher:
			return mongoTemplate.findOne(searchUserQuery, Teacher.class);
		}
		
		return null;
	}

}
