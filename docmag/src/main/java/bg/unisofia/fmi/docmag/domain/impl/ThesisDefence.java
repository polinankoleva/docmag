package bg.unisofia.fmi.docmag.domain.impl;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;

@Document(collection = "thesisdefences")
public class ThesisDefence {
	
	@Id
	private ObjectId id;
	
	@Indexed
	private ObjectId thesisProposalId;
	
	private float mark;
	private Date date;
	private List<ObjectId> commissionParticipantIds;
	
	public float getMark() {
		return mark;
	}
	public void setMark(float mark) {
		this.mark = mark;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<ObjectId> getCommissionParticipantIds() {
		return commissionParticipantIds;
	}
	public void setCommissionParticipantIds(List<ObjectId> commission) {
		this.commissionParticipantIds = commission;
	}
	public ObjectId getId() {
		return id;
	}
	public ObjectId getThesisProposalId() {
		return thesisProposalId;
	}
	public void setThesisProposalId(ObjectId thesisProposalId) {
		this.thesisProposalId = thesisProposalId;
	}
	
}
