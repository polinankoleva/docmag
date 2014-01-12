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
import bg.unisofia.fmi.docmag.dao.ThesisDefenceDAO;
import bg.unisofia.fmi.docmag.dao.UserDAO;
import bg.unisofia.fmi.docmag.domain.impl.ThesisDefence;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;

@Repository
public class ThesisDefenceDAOImpl implements ThesisDefenceDAO {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private UserDAO userDao;

	@Autowired
	private DocumentDAO documentDao;

	@Override
	public ObjectId createThesisDefence(Date date) {
		ObjectId objectId = new ObjectId();
		ThesisDefence thesisDefence = new ThesisDefence(objectId, date);
		mongoTemplate.save(thesisDefence);

		return objectId;
	}
	
	@Override
	public ObjectId createThesisDefence(Date date,
			List<ObjectId> commissionParticipantIds) {
		ObjectId objectId = new ObjectId();
		ThesisDefence thesisDefence = new ThesisDefence(objectId, date);
		thesisDefence.setCommissionParticipantIds(commissionParticipantIds);
		mongoTemplate.save(thesisDefence);

		return objectId;
	}

	@Override
	public void saveThesisDefence(ThesisDefence thesisDefence) {
		mongoTemplate.save(thesisDefence);
	}

	@Override
	public ThesisDefence getThesisDefenceById(ObjectId id) {
		Query searchQuery = new Query(Criteria.where("_id").is(id));
		return mongoTemplate.findOne(searchQuery, ThesisDefence.class);
	}

	@Override
	public boolean assignTeacherToCommissionForThesisDefence(Teacher teacher,
			ObjectId thesisDefenceId) {
		ThesisDefence defence = getThesisDefenceById(thesisDefenceId);
		if (defence != null) {
			List<ObjectId> commission = defence.getCommissionParticipantIds();
			if (commission == null) {
				commission = new ArrayList<ObjectId>();
			}
			if (!commission.contains(teacher)) {
				commission.add(teacher.getId());
				mongoTemplate.save(defence);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<ThesisDefence> getAllThesisDefences() {
		return mongoTemplate.findAll(ThesisDefence.class);
	}

	@Override
	public void delete(ObjectId thesisDefenceId) {
		Query query = new Query(Criteria.where("_id").is(thesisDefenceId));
		mongoTemplate.remove(query);
	}
	
	@Override
	public List<ThesisDefence> selectThesisDefences(ObjectId userId,
			Date startDate, Date endDate, ObjectId commissionParticipantId) {
		// TODO Auto-generated method stub
		return null;
	}

}
