package bg.unisofia.fmi.docmag.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import bg.unisofia.fmi.docmag.dao.DocumentDAO;
import bg.unisofia.fmi.docmag.dao.UserDAO;
import bg.unisofia.fmi.docmag.domain.impl.document.Document.DocumentType;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisRecension;
import bg.unisofia.fmi.docmag.domain.impl.profile.TeacherProfile.Department;
import bg.unisofia.fmi.docmag.domain.impl.user.PHDStudent;
import bg.unisofia.fmi.docmag.domain.impl.user.Student;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
import bg.unisofia.fmi.docmag.domain.impl.user.User;
import bg.unisofia.fmi.docmag.domain.impl.user.User.UserType;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private DocumentDAO documentDao;

    private User getUser(Query query) {
            User user = mongoTemplate.findOne(query, User.class);

            return user;
    }

    @Override
    public void createUser(User user) {
            mongoTemplate.insert(user);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends User> T getUserById(ObjectId userId) {
            Query searchUserQuery = new Query(Criteria.where("_id").is(userId));
            User user = getUser(searchUserQuery);

            if (user != null) {
                    switch (user.getType()) {
                    case Student:
                            return (T) mongoTemplate
                                            .findOne(searchUserQuery, Student.class);
                    case PHD:
                            return (T) mongoTemplate.findOne(searchUserQuery,
                                            PHDStudent.class);
                    case Teacher:
                            return (T) mongoTemplate
                                            .findOne(searchUserQuery, Teacher.class);
                    }
            }

            return null;
    }

    @Override
    public User getUserByUsername(String username) {
            Query searchUserQuery = new Query(Criteria.where("username").is(
                            username));
            return getUser(searchUserQuery);
    }

    @Override
    public void saveUser(User user) {
            mongoTemplate.save(user);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends User> List<T> getAllUsersOfType(UserType type) {
            Query searchUserQuery = new Query(Criteria.where("type").is(
                            type.toString()));
            List<T> users = (List<T>) mongoTemplate.find(searchUserQuery,
                            User.getClassForUserType(type));
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
    public List<Teacher> getConsultantsForThesis(ThesisProposal thesis) {
            Query consultantsQuery = new Query(Criteria.where("_id").in(
                            thesis.getConsultantIds()));
            List<Teacher> consultants = mongoTemplate.find(consultantsQuery,
                            Teacher.class);

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

    @Override
    public List<Student> studentsForThesisDefenceWithId(ObjectId thesisDefenceId) {
            Query query = new Query(Criteria.where("thesisDefenceId").is(
                            thesisDefenceId));
            return mongoTemplate.find(query, Student.class);
    }


    @Override
    public List<Student> getGraduatedStudents(ObjectId userId, Date startDate,
                    Date endDate, ObjectId leaderId, ObjectId reviewerId) {
            
            Teacher teacher = getUserById(userId);
            if (teacher != null) {
                    
                    Boolean teacherHaveRights = teacher.getProfile().getDepartment() == Department.SoftwareTechnologies;
                    
                    Query searchStudentQuery = new Query(Criteria.where("graduationDate").gte(startDate));
                    Date secondDate = (endDate == null) ? startDate : endDate;
                    searchStudentQuery.addCriteria(Criteria.where("graduationDate").lte(secondDate));
                    
                    List<Student> students = mongoTemplate.find(searchStudentQuery, Student.class);
                    
                    if (students != null && !students.isEmpty()) {
                            List<Student> filteredStudents = new ArrayList<Student>(students);
                            for (Student student : students) {
                                    ThesisProposal thesis = documentDao.getFirstDocumentForUserOfSpecificType(
                                                    student.getId(), DocumentType.ThesisProposal);
                                    if (!teacherHaveRights && !thesis.getScientificLeaderIds().contains(userId)) {
                                            filteredStudents.remove(student);
                                            continue;
                                    }
                                    if (thesis != null && leaderId != null &&
                                                    !thesis.getScientificLeaderIds().contains(leaderId)) {
                                            filteredStudents.remove(student);
                                            continue;
                                    }
                                    ThesisRecension recension = documentDao.getDocumentById(student.getThesisRecensionId());
                                    if (recension != null && reviewerId != null &&
                                                    recension.getReviewerId() != reviewerId) {
                                            filteredStudents.remove(student);
                                    }
                            }
                    }
                    
            }
            
            return null;
    }

	@Override
	public List<Student> getGraduatedStudents(ObjectId userId, Date startDate,
			Date endDate, ObjectId leaderId, ObjectId reviewerId) {
		
		Teacher teacher = getUserById(userId);
		if (teacher != null) {
			
			Boolean teacherHaveRights = teacher.getProfile().getDepartment() == Department.SoftwareTechnologies;
			
			Query searchStudentQuery = new Query(Criteria.where("graduationDate").gte(startDate));
			Date secondDate = (endDate == null) ? startDate : endDate;
			searchStudentQuery.addCriteria(Criteria.where("graduationDate").lte(secondDate));
			
			List<Student> students = mongoTemplate.find(searchStudentQuery, Student.class);
			
			if (students != null && !students.isEmpty()) {
				List<Student> filteredStudents = new ArrayList<Student>(students);
				for (Student student : students) {
					ThesisProposal thesis = documentDao.getFirstDocumentForUserOfSpecificType(
							student.getId(), DocumentType.ThesisProposal);
					if (!teacherHaveRights && !thesis.getScientificLeaderIds().contains(userId)) {
						filteredStudents.remove(student);
						continue;
					}
					if (thesis != null && leaderId != null &&
							!thesis.getScientificLeaderIds().contains(leaderId)) {
						filteredStudents.remove(student);
						continue;
					} 
					ThesisRecension recension = documentDao.getDocumentById(student.getThesisRecensionId());
					if (recension != null && reviewerId != null &&
							recension.getReviewerId() != reviewerId) {
						filteredStudents.remove(student);
					}
				}
			}
			
		}
		
		return null;
	}

}
