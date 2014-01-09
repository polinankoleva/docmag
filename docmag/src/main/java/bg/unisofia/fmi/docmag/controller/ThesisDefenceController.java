package bg.unisofia.fmi.docmag.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import bg.unisofia.fmi.docmag.domain.impl.ThesisDefence;
import bg.unisofia.fmi.docmag.service.ThesisDefenceService;

@Controller
@RequestMapping("/thesisdefence")
public class ThesisDefenceController {
	
	@Autowired
	ThesisDefenceService thesisDefenceService;
	
	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public @ResponseBody ThesisDefence getThesisDefence(@PathVariable String username) {
		System.out.println("in get");
		return thesisDefenceService.getThesisDefenceByUsername(username);
	}
	
	@RequestMapping(value = "/{username}", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void addThesisDefence(@PathVariable String username, @RequestParam float mark, @RequestParam @DateTimeFormat(pattern = "dd-mm-yyyy") Date date, @RequestParam List<Integer> teachersId) {
		//must be implemented
	}
	
	@RequestMapping(value = "/{username}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void updateThesisDefence(@PathVariable String username, @RequestParam(required = false) float mark, @RequestParam(required = false) @DateTimeFormat(pattern = "dd-mm-yyyy") Date date, @RequestParam(required = false) List<Integer> teachersId) {
		//must be implemented
	}
	
	@RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteThesisDefence(@PathVariable String username) {
		//must be implemented
	}
}
