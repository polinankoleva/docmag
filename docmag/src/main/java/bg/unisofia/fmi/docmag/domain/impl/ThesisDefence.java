package bg.unisofia.fmi.docmag.domain.impl;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;

@Document
public class ThesisDefence {
	
	@Id
	private ObjectId id;
	
	private ObjectId thesisProposalId;
	private float mark;
	private Date date;
	private List<Teacher> commission;
	
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
	public List<Teacher> getCommission() {
		return commission;
	}
	public void setCommission(List<Teacher> commission) {
		this.commission = commission;
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
