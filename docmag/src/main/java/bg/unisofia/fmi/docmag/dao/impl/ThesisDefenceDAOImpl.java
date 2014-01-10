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
import bg.unisofia.fmi.docmag.domain.impl.document.Document.DocumentType;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
import bg.unisofia.fmi.docmag.domain.impl.user.User;

@Repository
public class ThesisDefenceDAOImpl implements ThesisDefenceDAO {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private UserDAO userDao;

	@Autowired
	private DocumentDAO documentDao;

	private ThesisDefence getThesisDefenceForThesisProposal(
			ThesisProposal thesis) {
		if (thesis != null) {
			return getThesisDefenceByThesisProposalId(thesis.getId());
		}
		return null;
	}

	@Override
	public ObjectId createThesisDefence(Date date) {
		ObjectId objectId = new ObjectId();
		ThesisDefence thesisDefence = new ThesisDefence(objectId, date);
		mongoTemplate.save(thesisDefence);
		
		return objectId;
	}

	@Override
	public ThesisDefence getThesisDefenceByUsername(String username) {
		User user = userDao.getUserByUsername(username);
		if (user != null) {
			ThesisProposal thesis = documentDao
					.getFirstDocumentForUserOfSpecificType(user,
							DocumentType.ThesisProposal);
			return getThesisDefenceForThesisProposal(thesis);
		}

		return null;
	}

	@Override
	public ThesisDefence getThesisDefenceById(ObjectId id) {
		Query searchQuery = new Query(Criteria.where("_id").is(id));
		return mongoTemplate.findOne(searchQuery, ThesisDefence.class);
	}

	@Override
	public ThesisDefence getThesisDefenceByThesisProposalId(ObjectId id) {
		Query searchQuery = new Query(Criteria.where("thesisProposalId").is(id));
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
	public void setMarkForThesisDefence(float mark, ObjectId thesisDefenceId) {
		ThesisDefence defence = getThesisDefenceById(thesisDefenceId);
		if (defence != null) {
			defence.setMark(mark);
			mongoTemplate.save(defence);
		}
	}

	@Override
	public void setDateForThesisDefence(Date date, ObjectId thesisDefenceId) {
		ThesisDefence defence = getThesisDefenceById(thesisDefenceId);
		if (defence != null) {
			defence.setDate(date);
			mongoTemplate.save(defence);
		}
	}

}
