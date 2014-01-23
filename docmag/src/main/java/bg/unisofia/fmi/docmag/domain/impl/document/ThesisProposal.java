package bg.unisofia.fmi.docmag.domain.impl.document;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

public class ThesisProposal extends Document {
	
	public enum ThesisProposalStatus {
		Submitted,
		NotSubmitted,
	    Unapproved, 
	    ApprovedWithNotes,
	    Approved 
	}
	
	private ThesisProposalStatus status;
	
    private String subject;
    private String annotation;
    private String purpose;
    private String tasks;
    private String restrictions;
    private Date executionDeadline;
    
    private List<ObjectId> scientificLeaderIds;
	private List<ObjectId> consultantIds;
	
	private String notes;
	
	public ThesisProposal() {
		super(DocumentType.ThesisProposal);
	}
    
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getAnnotation() {
		return annotation;
	}
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getTasks() {
		return tasks;
	}
	public void setTasks(String tasks) {
		this.tasks = tasks;
	}
	public String getRestrictions() {
		return restrictions;
	}
	public void setRestrictions(String restrictions) {
		this.restrictions = restrictions;
	}
	public Date getExecutionDeadline() {
		return executionDeadline;
	}
	public void setExecutionDeadline(Date executionDeadline) {
		this.executionDeadline = executionDeadline;
	}
	public ThesisProposalStatus getStatus() {
		return status;
	}
	public void setStatus(ThesisProposalStatus status) {
		this.status = status;
	}
	public List<ObjectId> getScientificLeaderIds() {
		return scientificLeaderIds;
	}
	public void setScientificLeaderIds(List<ObjectId> scientificLeaderIds) {
		this.scientificLeaderIds = scientificLeaderIds;
	}
	public List<ObjectId> getConsultantIds() {
		return consultantIds;
	}
	public void setConsultantIds(List<ObjectId> consultantIds) {
		this.consultantIds = consultantIds;
	}
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return super.toString() + "\n" + "ThesisProposal(" + status + "): [subject=" 
				+ subject + ", anotation="+ annotation + ", purpose=" + purpose
				+ ", tasks=" + tasks + ", restrictions=" + restrictions 
				+ ", executionDeadline=" + executionDeadline 
				+ "]";
	}
    
    
}
