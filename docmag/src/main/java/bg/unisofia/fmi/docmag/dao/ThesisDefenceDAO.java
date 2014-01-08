package bg.unisofia.fmi.docmag.dao;

import java.util.Date;

import org.bson.types.ObjectId;

import bg.unisofia.fmi.docmag.domain.impl.ThesisDefence;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;

public interface ThesisDefenceDAO {

	public ThesisDefence getThesisDefenceByUsername(String username);

	public ThesisDefence getThesisDefenceByThesisProposalId(ObjectId id);

	public void assignTeacherToThesisDefenceCommission(Teacher teacher,
			ObjectId thesisDefenceId);

	public void setMarkForThesisDefence(float mark, ObjectId thesisDefenceId);

	public void setDateForThesisDefence(Date date, ObjectId thesisDefenceId);

}
