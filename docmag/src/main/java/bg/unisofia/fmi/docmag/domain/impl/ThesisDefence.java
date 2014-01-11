package bg.unisofia.fmi.docmag.domain.impl;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "thesisdefences")
public class ThesisDefence {
	
	@Id
	private ObjectId id;
	
	private Date date;
	private List<ObjectId> commissionParticipantIds;
	
	public ThesisDefence(ObjectId id, Date date) {
		super();
		this.id = id;
		this.date = date;
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
	
}
