package bg.unisofia.fmi.docmag.domain.impl.document;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;

public class ThesisRecension extends Document {

	@Indexed
	private ObjectId reviewerId;

	private Map<String, Map<String, Float>> points;
	private String summary;
	private String questions;
	private String conclusion;

	private Map<String, Float> mapWithFileds(String[] fields) {
		Map<String, Float> map = new HashMap<String, Float>();
		for (String string : fields) {
			map.put(string, (float) 0);
		}
		return map;
	}

	public ThesisRecension() {
		super();
		String[] generalFileds = new String[]{"theoreticalMotivation", 
				"ownIdeas", "execution", "styleAndLayout"};
		String[] realizationFileds = new String[]{"architecture", "functionality", "reliability", "documentation"};
		String[] experimentFileds = new String[]{"description", "presentation", "interpretation"};
		
		this.points = new HashMap<String, Map<String,Float>>();
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
}
