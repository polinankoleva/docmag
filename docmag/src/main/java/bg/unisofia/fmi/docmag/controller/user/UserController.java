package bg.unisofia.fmi.docmag.controller.user;

import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bg.unisofia.fmi.docmag.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value="/{userId}/thesisdefence", method = RequestMethod.GET)
	public Map<String, Object> getThesisDefenceForUser(@PathVariable ObjectId userId) {
		return userService.getThesisDefenceForUser(userId);
	}
	
	@RequestMapping(value="/{userId}/thesisdefence", method = RequestMethod.POST)
	public Map<String, String> setThesisDefenceForUser(@PathVariable ObjectId userId, @RequestParam ObjectId thesisDefenceId) {
		return userService.setThesisDefenceIdForStudentWithId(thesisDefenceId, userId);
	}
	
	@RequestMapping(value="/{userId}/thesisdefence", method = RequestMethod.PUT)
	public void updateThesisDefenceForUser(@PathVariable ObjectId userId, @RequestParam ObjectId thesisDefenceId) {
		userService.updateThesisdefenceForUser(thesisDefenceId, userId);
	}
	
	@RequestMapping(value="/{userId}/thesisdefence", method = RequestMethod.DELETE)
	public void deleteThesisDefenceForUser(@PathVariable ObjectId userId) {
		userService.deteleThesisdefenceForUser(userId);
	}
}
