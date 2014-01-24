package bg.unisofia.fmi.docmag.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bg.unisofia.fmi.docmag.dao.DocumentDAO;
import bg.unisofia.fmi.docmag.dao.ThesisDefenceDAO;
import bg.unisofia.fmi.docmag.dao.UserDAO;
import bg.unisofia.fmi.docmag.domain.impl.ThesisDefence;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal.ThesisProposalStatus;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisRecension;
import bg.unisofia.fmi.docmag.domain.impl.user.PHDStudent;
import bg.unisofia.fmi.docmag.domain.impl.user.Student;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
import bg.unisofia.fmi.docmag.domain.impl.user.User;
import bg.unisofia.fmi.docmag.domain.impl.user.User.UserType;

@Service
public final class UserService {

	 @Autowired
     UserDAO userDao;
     
     @Autowired
     ThesisDefenceService thesisDefenceService;
     
     @Autowired
     ThesisDefenceDAO thesisDefenceDao;
     
     @Autowired
     DocumentService documentService;
     
     @Autowired
     DocumentDAO documentDao;

     public void createUser(User user) {
             userDao.createUser(user);
     }
     
     public <U extends User> U getUserById(ObjectId userId) {
      return userDao.getUserById(userId);
     }
     
     public <U extends User> U getUserById(String userId) {
     return getUserById(new ObjectId(userId));
 }

     public User getUserByUsername(String username) {
             return userDao.getUserByUsername(username);
     }

     public List<Object> getAllTeacher(){
    	 List<Teacher> allTeacher = documentService.getAllTeachers();
    	 return documentService.createAllTeacherMap(allTeacher);
     }
     
     public List<Student> getAllStudentsWithApprovedThesisProposals() {
    	 List<Student> students = new ArrayList<>();
    	 
    	 List<ThesisProposal> approvedProposals = documentDao.getAllThesisProposalWithStatus
    			 (ThesisProposalStatus.Approved);
    	 approvedProposals.addAll(documentDao.getAllThesisProposalWithStatus
    			 (ThesisProposalStatus.ApprovedWithNotes)) ;
    	 for (ThesisProposal thesisProposal : approvedProposals) {
			students.add((Student) getUserById(thesisProposal.getUserId()));
		}
    	 return students;
     }
     
     public Map<String, String> setThesisDefenceIdForStudentWithId(ObjectId thesisDefenceId, ObjectId studentId) {
             User user = userDao.getUserById(studentId);
             if(user != null && user instanceof Student){
                     Student student = (Student) user;
                     ThesisDefence thesisDefence = thesisDefenceDao.getThesisDefenceById(thesisDefenceId);
                     if(student != null && student.getThesisDefenceId() == null && thesisDefence != null){
                             student.setThesisDefenceId(thesisDefenceId);
                             userDao.saveUser(student);
                     } else {
                             return thesisDefenceService.createErrorMessage("Setting thesis defence to user wasn't succeed");
                     }
             } else{
                     return thesisDefenceService.createErrorMessage("Setting thesis defence to user wasn't succeed");
             }
             return null;
     }

     public Map<String, Object> getThesisDefenceForUser(ObjectId studentId){
             User user = userDao.getUserById(studentId);
             if(user != null && user instanceof Student){
                     Student student = (Student) user;
                     if(student.getThesisDefenceId() != null ){
                             ThesisDefence thesisDefence = thesisDefenceDao.getThesisDefenceById(student.getThesisDefenceId());
                             if(thesisDefence != null){
                                     return thesisDefenceService.getThesisDefenceInMap(thesisDefence);
                             }
                     }
             }
             return null;
     }
     
     public void updateThesisdefenceForUser(ObjectId thesisDefenceId, ObjectId studentId){
             User user = userDao.getUserById(studentId);
             if(user != null && user instanceof Student){
                     ThesisDefence thesisDefence = thesisDefenceDao.getThesisDefenceById(thesisDefenceId);
                     Student student = (Student) user;
                     if(student != null && !student.getThesisDefenceId().equals(thesisDefenceId) && thesisDefence != null){
                             student.setThesisDefenceId(thesisDefence.getId());
                             userDao.saveUser(user);
                     }
             }
     }
     
     public void deteleThesisdefenceForUser(ObjectId studentId){
             User user = userDao.getUserById(studentId);
             if(user != null && user instanceof Student){
                     Student student = (Student) user;
                     if(student.getThesisDefenceId() != null && student != null){
                             student.setThesisDefenceId(null);
                             userDao.saveUser(user);
                     }
             }
     }
     
     public void setThesisDefenceMarkForStudentWithId(String mark,
                     ObjectId studentId) {
             User user = userDao.getUserById(studentId);
             if(user != null && user instanceof Student){
                     Student student = (Student) user;
                     if(student.getThesisDefenceId() != null){
                             student.setThesisDefenceMark(mark);
                             userDao.saveUser(student);
                     }
             }
     }
     
     public void updateThesisDefenceMarkForStudentWithId(String mark,
                     ObjectId studentId) {
             User user = userDao.getUserById(studentId);
             if(user != null && user instanceof Student){
                     Student student = (Student) user;
                     if(student.getThesisDefenceId() != null && student.getThesisDefenceMark() != null){
                             student.setThesisDefenceMark(mark);
                             userDao.saveUser(student);
                     }
             }
     }
     
     
     public Map<String, String> getThesisDefenceMarkForStudentWithId(ObjectId studentId) {
             Map<String, String> thesisDefenceMark = new HashMap<String, String>();
             User user = userDao.getUserById(studentId);
             if(user != null && user instanceof Student){
                     Student student = (Student) user;
                     if(student.getThesisDefenceMark() != null){
                     thesisDefenceMark.put("mark", student.getThesisDefenceMark());
                     }
             }
             return thesisDefenceMark;
     }

     public List<Teacher> getScientificLeadersPHDStudentWithUsername(
                     String username) {
             User user = getUserByUsername(username);
             if (user.getType() == UserType.PHD) {
                     return userDao.getScientificLeadersForPHDStudent((PHDStudent) user);
             } else {
                     return null;
             }
     }

     public List<Teacher> getScientificLeadersForThesis(ThesisProposal thesis) {
             return userDao.getScientificLeadersForThesis(thesis);
     }

     public List<Teacher> getConsultantsForThesis(ThesisProposal thesis) {
             return userDao.getConsultantsForThesis(thesis);
     }
     
     public Map<String, Object> getThesisRecensionForUser(ObjectId userId){
    	 Map<String, Object> thesisRecensionInformation = new HashMap<String, Object>();
    	 User user = getUserById(userId);
    	 if(user != null && user instanceof Student){
             Student student = (Student) user;
             if(student.getThesisRecensionId() != null ){
                     ThesisRecension recension = documentService.getThesisRecensionForUser(userId);
                     thesisRecensionInformation.put("recension", recension);
                     thesisRecensionInformation.put("student", getUserById(userId));
                     thesisRecensionInformation.put("reviewer", getUserById(recension.getReviewerId()));
                     thesisRecensionInformation.put("subject", documentService.getThesisProposalForUser(userId).getSubject());
             }
     }
     return null;
     }
     
     public void setThesisRecensionForUser(ObjectId userId, ObjectId reviewerId, String summary, String questions, 
    		 String conclusion, Float theoreticalMotivation, Float ownIdeas, Float execution, Float styleAndLayout, 
    		 Float architecture, Float functionality, Float reliability, Float documentation, Float description,
    		 Float presentation, Float interpretation){
    	 if(getUserById(userId) != null && getUserById(userId) instanceof Student && documentService.getThesisRecensionForUser(userId) == null){
 			ThesisRecension thesisRecension = new ThesisRecension();
 			thesisRecension.setConclusion(conclusion);
 			thesisRecension.setQuestions(questions);
 			thesisRecension.setSummary(summary);
 			thesisRecension.setUserId(userId);
 			if(getUserById(reviewerId) != null){
 				thesisRecension.setReviewerId(reviewerId);
 			}
 			thesisRecension.setPointForField("theoreticalMotivation", theoreticalMotivation);
 			thesisRecension.setPointForField("ownIdeas", ownIdeas);
 			thesisRecension.setPointForField("execution", execution);
 			thesisRecension.setPointForField("styleAndLayout", styleAndLayout);
 			thesisRecension.setPointForField("architecture", architecture);
 			thesisRecension.setPointForField("functionality", functionality);
 			thesisRecension.setPointForField("reliability", reliability);
 			thesisRecension.setPointForField("documentation", documentation);
 			thesisRecension.setPointForField("description", description);
 			thesisRecension.setPointForField("presentation", presentation);
 			thesisRecension.setPointForField("interpretation", interpretation);
 			insertThesisRecension(thesisRecension);
    	 }
     }
     
     private void insertThesisRecension(ThesisRecension recension){
    	 documentDao.saveDocument(recension);
     }
     
     private void updateThesisRecension(ThesisRecension recension){
    	 documentDao.saveDocument(recension);
     }
     
     public void updateThesisRecensionForUser(ObjectId userId, ObjectId reviewerId, String summary, String questions, 
    		 String conclusion, Float theoreticalMotivation, Float ownIdeas, Float execution, Float styleAndLayout, 
    		 Float architecture, Float functionality, Float reliability, Float documentation, Float description,
    		 Float presentation, Float interpretation){
    	 ThesisRecension thesisRecension = documentService.getThesisRecensionForUser(userId);
    	 if(thesisRecension != null){
    		 checkPropertiesForThesisRcension(reviewerId, summary, questions, conclusion, theoreticalMotivation, ownIdeas,
    				 execution, styleAndLayout, architecture, functionality, reliability, documentation, description,
    				 presentation, interpretation, thesisRecension);
    		 updateThesisRecension(thesisRecension);
    	 }
     }
     
     private void checkPropertiesForThesisRcension(ObjectId reviewerId, String summary, String questions, 
    		 String conclusion, Float theoreticalMotivation, Float ownIdeas, Float execution, Float styleAndLayout, 
    		 Float architecture, Float functionality, Float reliability, Float documentation, Float description,
    		 Float presentation, Float interpretation, ThesisRecension thesisRecension) {
 		if(reviewerId != null && getUserById(reviewerId) != null){
 			thesisRecension.setReviewerId(reviewerId);
 		}
 		if(summary != null){
 			thesisRecension.setSummary(summary);
 		}
 		if(conclusion != null){
 			thesisRecension.setConclusion(conclusion);
 		}
 		if(questions != null){
 			thesisRecension.setQuestions(questions);
 		}
 		if(theoreticalMotivation != null){
 			thesisRecension.setPointForField("theoreticalMotivation", theoreticalMotivation);
 		}
 		if(ownIdeas != null){
 			thesisRecension.setPointForField("ownIdeas", ownIdeas);
 		}
 		if(execution != null){
 			thesisRecension.setPointForField("execution", execution);
 		}
 		if(styleAndLayout != null){
 			thesisRecension.setPointForField("styleAndLayout", styleAndLayout);
 		}
 		if(architecture != null){
 			thesisRecension.setPointForField("architecture", architecture);
 		}
 		if(functionality != null){
 			thesisRecension.setPointForField("functionality", functionality);
 		}
 		if(reliability != null){
 			thesisRecension.setPointForField("reliability", reliability);
 		}
 		if(documentation != null){
 			thesisRecension.setPointForField("documentation", documentation);
 		}
 		if(description != null){
 			thesisRecension.setPointForField("description", description);
 		}
 		if(presentation != null){
 			thesisRecension.setPointForField("presentation", presentation);
 		}
 		if(interpretation != null){
 			thesisRecension.setPointForField("interpretation", interpretation);
 		}
 	}
     
    
 	public void uploadGraduationThesisForUser(ObjectId userId, MultipartFile file){
		User user = getUserById(userId);
		if(user != null && user instanceof Student){
			Student student = (Student) user;
			try{
				if(file != null && file.getBytes().length != 0){
					byte[] graduationThesisInBytes = file.getBytes();
					student.setGraduationThesis(graduationThesisInBytes);
					userDao.saveUser(user);
				}
			}catch(IOException e){
				System.out.println("Exception when uploading file. Message:" + e.getMessage());
			}
		}
	}
 	
 	public HttpEntity<byte[]> downloadGraduationThesisForUser(ObjectId userId){
 		User user = getUserById(userId);
		if(user != null && user instanceof Student){
			Student student = (Student) user;
			if(student.getGraduationThesis().length != 0){
				byte[] documentBody = student.getGraduationThesis();
			    HttpHeaders header = new HttpHeaders();
			    header.setContentType(new MediaType("application", "zip"));
			    header.set("Content-Disposition", 
			                   "attachment; filename=" + "graduationThesis_" + student.getProfile().getFaculty() +".zip");
			    header.setContentLength(documentBody.length);
			    return new HttpEntity<byte[]>(documentBody, header);
			}
		}
		return null;
 	}
 	
 	public List<Object> getUsersWithoutThesisDefence(){
 		List<Object> studentWithoutThesisDefence = new ArrayList<Object>();
 		List<Student> allStudent = userDao.getAllUsersOfType(UserType.Student);
 		for(int i = 0; i < allStudent.size(); i++){
 			if(allStudent.get(i).getThesisDefenceId() == null){
 				Map<String, String> informationForUser = new HashMap<>();
 				informationForUser.put("id", allStudent.get(i).getId().toString());
 				informationForUser.put("name", allStudent.get(i).getProfile().getName());
 				informationForUser.put("studentIdentifier", allStudent.get(i).getProfile().getStudentIdentifier());
 				studentWithoutThesisDefence.add(informationForUser);
 			}
 		}
 		return studentWithoutThesisDefence;
 	}
}
