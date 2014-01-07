package bg.unisofia.fmi.docmag.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import bg.unisofia.fmi.docmag.dao.UserDAO;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal;
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
		Query searchUserQuery = new Query(Criteria.where("username").is(
				username));
		User user = mongoTemplate.findOne(searchUserQuery, User.class);

		return user;
	}

	@Override
	public void createUser(User user) {
		mongoTemplate.insert(user);
	}

	@Override
	public User getUserByUsername(String username) {
		Query searchUserQuery = new Query(Criteria.where("username").is(
				username));
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
		Query searchUserQuery = new Query(Criteria.where("type").is(
				type.toString()));
		List<User> users = mongoTemplate.find(searchUserQuery, User.class);
		return users;
	}

	@Override
	public List<Teacher> getScientificLeadersForPHDStudent(PHDStudent phdStudent) {
		List<ObjectId> leaderIds = phdStudent.getScientificLeaderIds();
		if (leaderIds != null && leaderIds.size() > 0) {
			Query leadersQuery = new Query(Criteria.where("_id").in(
					phdStudent.getScientificLeaderIds()));
			List<Teacher> leaders = mongoTemplate.find(leadersQuery,
					Teacher.class);
			return leaders;
		} else {
			return (new ArrayList<Teacher>());
		}
	}

	@Override
	public List<Teacher> getScientificLeadersForThesis(ThesisProposal thesis) {
		Query leadersQuery = new Query(Criteria.where("_id").in(
				thesis.getScientificLeaderIds()));
		List<Teacher> leaders = mongoTemplate.find(leadersQuery, Teacher.class);

		return leaders;
	}

	@Override
	public List<User> getConsultantsForThesis(ThesisProposal thesis) {
		Query consultantsQuery = new Query(Criteria.where("_id").in(
				thesis.getConsultantIds()));
		List<User> consultants = mongoTemplate.find(consultantsQuery,
				User.class);

		return consultants;
	}

	@Override
	public boolean assignScientificLeaderForPHDStudent(PHDStudent student,
			Teacher teacher) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean assignScientificLeaderForThesis(ThesisProposal thesis,
			Teacher teacher) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean assignConsultantForThesis(ThesisProposal thesis, User user) {
		// TODO Auto-generated method stub
		return false;
	}

}