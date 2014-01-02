package bg.unisofia.fmi.docmag.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bg.unisofia.fmi.docmag.domain.User;
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
			response = "Username is " + user.getUsername();
		}else{
			response = "There is no such user!";
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public  @ResponseBody String getAllTests() {
		System.out.println("Testing Spring Rest Services");
		return "You want to get all tests ";
	}
}
