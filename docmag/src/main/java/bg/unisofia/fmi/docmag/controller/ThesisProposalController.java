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

import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal;
import bg.unisofia.fmi.docmag.service.DocumentService;

@Controller
@RequestMapping("/thesisproposal")
public class ThesisProposalController {

	@Autowired
	DocumentService documentService;
	
	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public @ResponseBody ThesisProposal getThesisProposal(@PathVariable String username) {
		return documentService.getThesisProposal(username);
	}
	
	@RequestMapping(value = "/{username}", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void setThesisProposal(@PathVariable String username, @RequestParam String subject, @RequestParam String annotation, @RequestParam String purpose,
			@RequestParam String tasks, @RequestParam String restrictions, @RequestParam @DateTimeFormat(pattern = "dd-mm-yyyy") Date executionDeadline, @RequestParam List<String> scientificLeaderIds,
			@RequestParam List<String> consultantIds) {
		documentService.insertThesisProposalForUserByUsername(username, subject, annotation, purpose, tasks, restrictions,
			executionDeadline, scientificLeaderIds, consultantIds);
		
	}
	
	@RequestMapping(value = "/{username}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void updateThesisProposal(@PathVariable String username, @RequestParam(required = false) String subject, @RequestParam(required = false) String anotation, @RequestParam(required = false) String purpose,
			@RequestParam(required = false) String tasks, @RequestParam(required = false) String restrictions, @RequestParam(required = false) Date executionDeadline, @RequestParam(required = false) List<String> scientificLeaderIds,
			@RequestParam(required = false) List<String> consultantIds) {
		documentService.updateThesisProposalForUserByUsername(username, subject, anotation, purpose, tasks, restrictions,
				executionDeadline, scientificLeaderIds, consultantIds);
	}
	
	@RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
	public void deleteThesisProposal() {
		
	}
	
	@RequestMapping(value = "/status/{username}",  /*{name}",*/ method = RequestMethod.GET)
	public @ResponseBody String checkThesisProposalStatus(@PathVariable String username) {
		return documentService.checkStatusForThesisProposal(username).name();
	}
}
