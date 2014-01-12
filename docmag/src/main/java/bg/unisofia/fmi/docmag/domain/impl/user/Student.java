package bg.unisofia.fmi.docmag.domain.impl.user;

import java.util.Date;

import org.bson.types.ObjectId;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.data.mongodb.core.index.Indexed;

import bg.unisofia.fmi.docmag.domain.impl.profile.StudentProfile;
import bg.unisofia.fmi.docmag.utils.JsonObjectIdSerializer;

public class Student extends User {

	public Student(UserType type) {
		super(type);
	}

	private StudentProfile profile;
	private String thesisDefenceMark;
	private Date graduationDate;

	@JsonSerialize(using = JsonObjectIdSerializer.class)
	@Indexed
	private ObjectId thesisDefenceId;

	@JsonSerialize(using = JsonObjectIdSerializer.class)
	@Indexed
	private ObjectId thesisRecensionId;

	public StudentProfile getProfile() {
		return profile;
	}

	public void setProfile(StudentProfile profile) {
		this.profile = profile;
	}

	public String getThesisDefenceMark() {
		return thesisDefenceMark;
	}

	public void setThesisDefenceMark(String mark) {
		this.thesisDefenceMark = mark;
		this.graduationDate = new Date();
	}

	public ObjectId getThesisDefenceId() {
		return thesisDefenceId;
	}

	public void setThesisDefenceId(ObjectId thesisDefenceId) {
		this.thesisDefenceId = thesisDefenceId;
	}

	public ObjectId getThesisRecensionId() {
		return thesisRecensionId;
	}

	public void setThesisRecensionId(ObjectId thesisRecensionId) {
		this.thesisRecensionId = thesisRecensionId;
	}

	public Date getGraduationDate() {
		return graduationDate;
	}

}
