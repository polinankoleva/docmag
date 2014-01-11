package bg.unisofia.fmi.docmag.controller.document;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal.ThesisProposalStatus;
import bg.unisofia.fmi.docmag.service.DocumentService;

@Controller
@RequestMapping("/thesisproposal")
public class ThesisProposalController {

	@Autowired
	DocumentService documentService;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody String getThesisProposal(@RequestHeader("User-Id") String userId) {
		return "User id id:" + userId;
		
		//return documentService.getThesisProposal(username);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void setThesisProposal(@RequestHeader("User-Id") String userId, @RequestParam String subject, @RequestParam String annotation, @RequestParam String purpose,
			@RequestParam String tasks, @RequestParam String restrictions, @RequestParam @DateTimeFormat(pattern = "dd-mm-yyyy") Date executionDeadline, @RequestParam List<ObjectId> scientificLeaderIds,
			@RequestParam List<ObjectId> consultantIds, @RequestParam ThesisProposalStatus status) {
		documentService.insertThesisProposalForUser(userId, subject, annotation, purpose, tasks, restrictions,
			executionDeadline, scientificLeaderIds, consultantIds, status);
		
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void updateThesisProposal(@RequestHeader("User-Id") String userId, @RequestParam(required = false) String subject, @RequestParam(required = false) String anotation, @RequestParam(required = false) String purpose,
			@RequestParam(required = false) String tasks, @RequestParam(required = false) String restrictions, @RequestParam(required = false) Date executionDeadline, @RequestParam(required = false) List<ObjectId> scientificLeaderIds,
			@RequestParam(required = false) List<ObjectId> consultantIds,@RequestParam(required = false) ThesisProposalStatus status) {
		documentService.updateThesisProposalForUser(userId, subject, anotation, purpose, tasks, restrictions,
				executionDeadline, scientificLeaderIds, consultantIds, status);

	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public void deleteThesisProposal() {
		
	}
	
	@RequestMapping(value = "/status", method = RequestMethod.GET)
	public @ResponseBody String checkThesisProposalStatus(@RequestHeader("User-Id") String userId) {
		return documentService.checkStatusForThesisProposal(userId).name();
	}
}
