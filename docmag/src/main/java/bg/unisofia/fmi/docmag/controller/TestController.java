package bg.unisofia.fmi.docmag.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
import bg.unisofia.fmi.docmag.domain.impl.user.User;
import bg.unisofia.fmi.docmag.service.UserService;

@Controller
@RequestMapping("/test")
public class TestController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public  @ResponseBody String getUser(@PathVariable String username) {
		//test mongo db connection
		String response = new String();
		User user = userService.getUserByUsername(username);
		if(user != null){
			response = "Username: " + user.getUsername() + 
					"\nType: " + user.getType().toString() + 
					"\n" + user.getProfile();
		}else{
			response = "There is no such user!";
		}
		return response;
	}
	
	@RequestMapping(value = "/{username}/leaders", method = RequestMethod.GET)
	public  @ResponseBody String getLeaders(@PathVariable String username) {
		//test mongo db connection
		String response = new String();
		List<Teacher> leaders = userService.getScientificLeadersPHDStudentWithUsername(username);
		if(leaders != null) {
			if (leaders.size() > 0) {
				for (Teacher teacher : leaders) {
					response = "Username: " + teacher.getUsername() + 
							"\nType: " + teacher.getType().toString() + 
							"\n" + teacher.getProfile();
				}
			}
			else {
				response = "No scientific leaders assigned!";
			}
			
			
		}else{
			response = "No such information for this type of user";
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public  @ResponseBody String getAllTests() {
		System.out.println("Testing Spring Rest Services");
		return "You want to get all tests ";
	}
}
