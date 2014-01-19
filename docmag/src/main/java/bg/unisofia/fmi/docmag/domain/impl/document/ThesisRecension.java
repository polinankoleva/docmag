package bg.unisofia.fmi.docmag.domain.impl.document;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.data.mongodb.core.index.Indexed;

import bg.unisofia.fmi.docmag.utils.JsonObjectIdSerializer;

public class ThesisRecension extends Document {

	@JsonSerialize(using = JsonObjectIdSerializer.class)
	@Indexed
	private ObjectId reviewerId;

	private Map<String, Map<String, Float>> points;
	private String summary;
	private String questions;
	private String conclusion;

	private Map<String, Float> mapWithFileds(String[] fields) {
		Map<String, Float> map = new LinkedHashMap<>(fields.length);
		for (String string : fields) {
			map.put(string, (float) 0);
		}
		return map;
	}

	public ThesisRecension() {
		super(DocumentType.ThesisRecension);
		String[] generalFileds = new String[]{"theoreticalMotivation", 
				"ownIdeas", "execution", "styleAndLayout"};
		String[] realizationFileds = new String[]{"architecture", "functionality", "reliability", "documentation"};
		String[] experimentFileds = new String[]{"description", "presentation", "interpretation"};
		
		this.points = new LinkedHashMap<>(3);
		this.points.put("General", mapWithFileds(generalFileds));
		this.points.put("Realization", mapWithFileds(realizationFileds));
		this.points.put("Experiment", mapWithFileds(experimentFileds));
	}

	public ObjectId getReviewerId() {
		return reviewerId;
	}

	public void setReviewerId(ObjectId reviewerId) {
		this.reviewerId = reviewerId;
	}

	public Map<String, Map<String, Float>> getPoints() {
		return points;
	}

	public void setPoints(Map<String, Map<String, Float>> points) {
		this.points = points;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getQuestions() {
		return questions;
	}

	public void setQuestions(String questions) {
		this.questions = questions;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}
	
	public Float allPoints() {
		Float allPoints = (float) 0;
		for (Map<String, Float> map : this.points.values()) {
			for (Float points : map.values()) {
				allPoints += points;
			}
		}
		return allPoints;
	}
	
	public void setPointForField(String fieldName, Float point){
		if(fieldName.equals("theoreticalMotivation") || fieldName.equals("ownIdeas") || 
				fieldName.equals("execution") || fieldName.equals("styleAndLayout")){
			this.points.get("General").put(fieldName, point);
		}
		if(fieldName.equals("architecture") || fieldName.equals("functionality") || 
				fieldName.equals("reliability") || fieldName.equals("documentation")){
			this.points.get("Realization").put(fieldName, point);
		}
		if(fieldName.equals("description") || fieldName.equals("presentation") || fieldName.equals("interpretation")){
			this.points.get("Experiment").put(fieldName, point);
		}
	}
}
