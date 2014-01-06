package bg.unisofia.fmi.docmag.dao.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import bg.unisofia.fmi.docmag.dao.UserDAO;
import bg.unisofia.fmi.docmag.domain.impl.user.PHDStudent;
import bg.unisofia.fmi.docmag.domain.impl.user.Student;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
import bg.unisofia.fmi.docmag.domain.impl.user.User;
import bg.unisofia.fmi.docmag.domain.impl.user.User.UserType;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	private User getUser(String username) {
		Query searchUserQuery = new Query(Criteria.where("username").is(username));
		User user = mongoTemplate.findOne(searchUserQuery, User.class);
		
		return user;
	}
	
	@Override
	public void createUser(User user) {
		 if (!mongoTemplate.collectionExists(User.class)) {
	            mongoTemplate.createCollection(User.class);
	        }       
	        mongoTemplate.insert(user);
	}

	@Override
	public User getUserByUsername(String username) {
		Query searchUserQuery = new Query(Criteria.where("username").is(username));
		UserType userType = getUser(username).getType();
		
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


	@Override
	public List<User> getAllUsersOfType(UserType type) {
		// TODO Auto-generated method stub
		return null;
	}


}
