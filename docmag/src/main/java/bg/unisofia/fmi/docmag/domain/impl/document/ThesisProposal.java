package bg.unisofia.fmi.docmag.domain.impl.document;

import java.util.Date;
import java.util.List;

public class ThesisProposal extends Document {
	
	public enum ThesisProposalStatus {
		NotSubmitted,
	    Unapproved, 
	    ApprovedWithNotes,
	    Approved 
	}
	
	private ThesisProposalStatus status;
	
    private String subject;
    private String anotation;
    private String purpose;
    private String tasks;
    private String restrictions;
    private Date executionDeadline;
    
    private List<String> scientificLeaderIds;
	private List<String> conultantIds;
    
	public List<String> getScientificLeaderIds() {
		return scientificLeaderIds;
	}
	public void setScientificLeaderIds(List<String> scientificLeaderIds) {
		this.scientificLeaderIds = scientificLeaderIds;
	}
	public List<String> getConultantIds() {
		return conultantIds;
	}
	public void setConultantIds(List<String> conultantIds) {
		this.conultantIds = conultantIds;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getAnotation() {
		return anotation;
	}
	public void setAnotation(String anotation) {
		this.anotation = anotation;
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
	@Override
	public String toString() {
		return super.toString() + "\n" + "ThesisProposal(" + status + "): [subject=" 
				+ subject + ", anotation="+ anotation + ", purpose=" + purpose
				+ ", tasks=" + tasks + ", restrictions=" + restrictions 
				+ ", executionDeadline=" + executionDeadline 
				+ ", scientificLeaderIds=" + scientificLeaderIds 
				+ ", conultantIds=" + conultantIds + "]";
	}
    
    
}
