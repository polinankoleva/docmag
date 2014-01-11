package bg.unisofia.fmi.docmag.domain.impl.document;

import java.util.Date;

import org.bson.types.ObjectId;
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
	private ObjectId id;
	
	@Field ("lastModified")
	private Date lastModifiedDate;
	
	@Indexed
	private DocumentType type;
	
	@Indexed
	private ObjectId userId;

	public Document(DocumentType type){
		this.type = type;
	}
	
	@JsonIgnore
	public ObjectId getUserId() {
		return userId;
	}

	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate() {
		this.lastModifiedDate = new Date();
	}

	@JsonIgnore
	public ObjectId getId() {
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
	
	public static Class<?> getClassForDocumentType(DocumentType type) {
		switch (type) {
		case ThesisProposal:
			return ThesisProposal.class;
		default:
			return null;
		}
	}
	
}
