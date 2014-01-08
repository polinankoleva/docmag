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

	@SuppressWarnings("unchecked")
	@Override
	public <T extends User> T getUserByUsername(String username) {
		Query searchUserQuery = new Query(Criteria.where("username").is(
				username));
		User user = getUser(username);

		if (user != null) {
			switch (user.getType()) {
			case Student:
				return (T) mongoTemplate.findOne(searchUserQuery, Student.class);
			case PHD:
				return (T) mongoTemplate.findOne(searchUserQuery, PHDStudent.class);
			case Teacher:
				return (T) mongoTemplate.findOne(searchUserQuery, Teacher.class);
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends User> List<T> getAllUsersOfType(UserType type) {
		Query searchUserQuery = new Query(Criteria.where("type").is(
				type.toString()));
		List<T> users = (List<T>) mongoTemplate.find(searchUserQuery, User.getClassForUserType(type));
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
	public boolean assignScientificLeaderForPHDStudent(Teacher teacher,
			PHDStudent student) {
		List<ObjectId> leaderIds = student.getScientificLeaderIds();
		if (leaderIds == null) {
			leaderIds = new ArrayList<ObjectId>();
		}
		if (!leaderIds.contains(teacher.getId())) {
			leaderIds.add(teacher.getId());
			mongoTemplate.save(student);
			return true;
		} else {
			return false;
		}
	}

}