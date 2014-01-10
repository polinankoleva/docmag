package bg.unisofia.fmi.docmag.dao;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import bg.unisofia.fmi.docmag.domain.impl.ThesisDefence;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;

public interface ThesisDefenceDAO {

	// POST, PUT
	public ObjectId createThesisDefence(Date date);

	public boolean assignTeacherToCommissionForThesisDefence(Teacher teacher,
			ObjectId thesisDefenceId);

	// GET
	public List<ThesisDefence> getAllThesisDefences();

	public ThesisDefence getThesisDefenceById(ObjectId id);

	// DELETE
	public void delete(ObjectId thesisDefenceId);
}
