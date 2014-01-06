package bg.unisofia.fmi.docmag.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import bg.unisofia.fmi.docmag.dao.ProfileDAO;
import bg.unisofia.fmi.docmag.dao.UserDAO;
import bg.unisofia.fmi.docmag.domain.impl.profile.Profile;
import bg.unisofia.fmi.docmag.domain.impl.user.PHDStudent;
import bg.unisofia.fmi.docmag.domain.impl.user.Student;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
import bg.unisofia.fmi.docmag.domain.impl.user.User;
import bg.unisofia.fmi.docmag.domain.impl.user.User.UserType;

public class ProfileDAOImpl implements ProfileDAO {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	UserDAO userDao;
	
	@Override
	public Profile getUserProfile(String username) {
		UserType userType = userDao.getUserByUsername(username).getType();
		Query searchUserQuery = new Query(Criteria.where("username").is(username));
		User user = null;
		
		switch (userType) {
		case Student:
			user = mongoTemplate.findOne(searchUserQuery, Student.class);
		case PHD:
			user = mongoTemplate.findOne(searchUserQuery, PHDStudent.class);
		case Teacher:
			user = mongoTemplate.findOne(searchUserQuery, Teacher.class);
		}
		
		return user.getProfile();
	}

	@Override
	public boolean updateUserProfile(Profile profile) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
