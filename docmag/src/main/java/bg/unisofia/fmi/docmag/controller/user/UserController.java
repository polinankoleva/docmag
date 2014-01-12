package bg.unisofia.fmi.docmag.controller.user;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import bg.unisofia.fmi.docmag.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value="/allTeachers", method = RequestMethod.GET)
	public @ResponseBody List<Object> getAllTeachers() {
		return userService.getAllTeacher();
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
	public void getRecensionForUser(@PathVariable ObjectId userId) {
		//must be implement
	}
	
	@RequestMapping(value="/{userId}/recension", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void setRecensionForUser(@PathVariable ObjectId userId) {
		//must be implement
		
	}
	
	@RequestMapping(value="/{userId}/recension", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void updateRecension(@PathVariable ObjectId userId, @RequestParam String mark) {
		//must be implement

	}
	
}
