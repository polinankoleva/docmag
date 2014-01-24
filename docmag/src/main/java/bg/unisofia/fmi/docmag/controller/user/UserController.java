package bg.unisofia.fmi.docmag.controller.user;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import bg.unisofia.fmi.docmag.domain.impl.user.Student;
import bg.unisofia.fmi.docmag.domain.impl.user.User;
import bg.unisofia.fmi.docmag.service.DocumentService;
import bg.unisofia.fmi.docmag.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	DocumentService documentService;

	@RequestMapping(value="/students/approvedThesisProposals", method = RequestMethod.GET)
	public @ResponseBody List<Student> getAllStudentsWithApprovedThesisProposals() {
		return userService.getAllStudentsWithApprovedThesisProposals();
	}
	
	@RequestMapping(value="/allStudents", method = RequestMethod.GET)
	public @ResponseBody List<Object> getAllTeachers() {
		return userService.getAllTeacher();
	}
	
	@RequestMapping(value="/thesisdefence", method = RequestMethod.GET)
	public @ResponseBody List<Object> getAllUserWithoutThesisDefence() {
		return userService.getUsersWithoutThesisDefence();
	}
	
	@RequestMapping(value="/{userId}", method = RequestMethod.GET)
	public @ResponseBody User getUserInformation(@PathVariable ObjectId userId) {
		return userService.getUserById(userId);
	}
	
	@RequestMapping(value="/{userId}/thesisdefence", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getThesisDefenceForUser(@PathVariable ObjectId userId) {
		return userService.getThesisDefenceForUser(userId);
	}
	
	
	@RequestMapping(value="/{userId}/thesisdefence", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> setThesisDefenceForUser(@PathVariable ObjectId userId, @RequestParam ObjectId thesisDefenceId) {
		return userService.setThesisDefenceIdForStudentWithId(thesisDefenceId, userId);
	}
	
	@RequestMapping(value="/{userId}/thesisdefence", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void updateThesisDefenceForUser(@PathVariable ObjectId userId, @RequestParam ObjectId thesisDefenceId) {
		userService.updateThesisdefenceForUser(thesisDefenceId, userId);
	}
	
	@RequestMapping(value="/{userId}/thesisdefence", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteThesisDefenceForUser(@PathVariable ObjectId userId) {
		userService.deteleThesisdefenceForUser(userId);
	}
	
	@RequestMapping(value="/{userId}/thesisdefence/mark", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> getThesisDefenceMarkForUser(@PathVariable ObjectId userId) {
		return userService.getThesisDefenceMarkForStudentWithId(userId);
	}
	
	@RequestMapping(value="/{userId}/thesisdefence/mark", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void setThesisDefenceMarkForUser(@PathVariable ObjectId userId, @RequestParam String mark) {
		userService.setThesisDefenceMarkForStudentWithId(mark, userId);
	}
	
	@RequestMapping(value="/{userId}/thesisdefence/mark", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void updateThesisDefenceMarkForUser(@PathVariable ObjectId userId, @RequestParam String mark) {
		userService.updateThesisDefenceMarkForStudentWithId(mark, userId);
	}
	
	@RequestMapping(value="/{userId}/recension", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public Map<String, Object> getThesisRecensionForUser(@PathVariable ObjectId userId) {
		return userService.getThesisRecensionForUser(userId);
	}
	
	@RequestMapping(value="/{userId}/recension", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void setThesisRecensionForUser(@PathVariable ObjectId userId, @RequestParam ObjectId reviewerId, @RequestParam String summary,
	@RequestParam String questions, @RequestParam String conclusion, @RequestParam Float theoreticalMotivation, @RequestParam Float ownIdeas, 
	@RequestParam Float execution, @RequestParam Float styleAndLayout, @RequestParam Float architecture, @RequestParam Float functionality,
	@RequestParam Float reliability, @RequestParam Float documentation, @RequestParam Float description, @RequestParam Float presentation,
	@RequestParam Float interpretation){
		userService.setThesisRecensionForUser(userId, reviewerId, summary, questions, conclusion, theoreticalMotivation, ownIdeas, execution,
				styleAndLayout, architecture, functionality, reliability, documentation, description, presentation, interpretation);
	}
	
	@RequestMapping(value="/{userId}/recension", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void updateThesisRecension(@PathVariable ObjectId userId, @RequestParam(required = false) ObjectId reviewerId, @RequestParam(required = false) String summary,
			@RequestParam(required = false) String questions, @RequestParam(required = false) String conclusion, @RequestParam(required = false) Float theoreticalMotivation,
			@RequestParam(required = false) Float ownIdeas, @RequestParam(required = false) Float execution, @RequestParam(required = false) Float styleAndLayout, 
			@RequestParam(required = false) Float architecture, @RequestParam(required = false) Float functionality, @RequestParam(required = false) Float reliability, 
			@RequestParam(required = false) Float documentation, @RequestParam(required = false) Float description, @RequestParam(required = false) Float presentation, 
			@RequestParam(required = false) Float interpretation) {
		userService.updateThesisRecensionForUser(userId, reviewerId, summary, questions, conclusion, theoreticalMotivation, ownIdeas, execution,
				styleAndLayout, architecture, functionality, reliability, documentation, description, presentation, interpretation);

	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteThesisRecension(@RequestHeader("User-Id") ObjectId userId) {
		documentService.deteleThesisRecensionForUser(userId);
	}
}
