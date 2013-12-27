package com.fmi.docmag.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

	@RequestMapping(value = "/{testId}", method = RequestMethod.GET)
	public  @ResponseBody String getTest(@PathVariable int testId) {
		System.out.println("Testing Spring Rest Services");
		return "You want to get test with id:"+ testId;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public  @ResponseBody String getAllTests() {
		System.out.println("Testing Spring Rest Services");
		return "You want to get all tests ";
	}
}
