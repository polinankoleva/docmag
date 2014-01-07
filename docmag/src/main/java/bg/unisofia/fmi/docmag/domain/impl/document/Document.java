package bg.unisofia.fmi.docmag.domain.impl.document;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

public class Document {
	
	public enum DocumentType {
	    ThesisProposal,
	    ThesisRecension,
	    InternProposal,
	    InternReview,
	    BasePHDPlan,
	    IndividualPHDPlan,
	    AnnualPDHPlan
	}
	
	@Id
	private String id;
	
	@Field ("lastModified")
	private Date lastModifiedDate;
	
	@Indexed
	private DocumentType type;
	
	@Indexed
	private String userId;

	@JsonIgnore
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@JsonIgnore
	public String getId() {
		return id;
	}

	public DocumentType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Document [type="
				+ type + "lastModifiedDate=" + lastModifiedDate + "]";
	}
	
	
	
}
