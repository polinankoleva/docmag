package bg.unisofia.fmi.docmag.controller.document;

import java.util.Date;
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

import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal.ThesisProposalStatus;
import bg.unisofia.fmi.docmag.service.DocumentService;

@Controller
@RequestMapping("/thesisproposal")
public class ThesisProposalController {

	@Autowired
	DocumentService documentService;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getThesisProposal(@RequestHeader("User-Id") ObjectId userId) {
		return documentService.getThesisProposalInfo(userId);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void setThesisProposal(@RequestHeader("User-Id") ObjectId userId, @RequestParam String subject, @RequestParam String annotation, @RequestParam String purpose,
			@RequestParam String tasks, @RequestParam String restrictions, @RequestParam Long executionDeadline, @RequestParam List<ObjectId> scientificLeaderIds,
			@RequestParam List<ObjectId> consultantIds, @RequestParam(required = false) ThesisProposalStatus status) {
		documentService.insertThesisProposalForUser(userId, subject, annotation, purpose, tasks, restrictions,
			new Date(executionDeadline), scientificLeaderIds, consultantIds, status);

	}

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void updateThesisProposal(@RequestHeader("User-Id") ObjectId userId, @RequestParam(required = false) String subject, @RequestParam(required = false) String annotation, @RequestParam(required = false) String purpose,
			@RequestParam(required = false) String tasks, @RequestParam(required = false) String restrictions, @RequestParam(required = false) Long executionDeadline, @RequestParam(required = false) List<ObjectId> scientificLeaderIds,
			@RequestParam(required = false) List<ObjectId> consultantIds, @RequestParam(required = false) ThesisProposalStatus status) {
		documentService.updateThesisProposalForUser(userId, subject, annotation, purpose, tasks, restrictions,
			executionDeadline == null? null : new Date(executionDeadline), scientificLeaderIds, 
			consultantIds, status);

	}

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteThesisProposal(@RequestHeader("User-Id") ObjectId userId) {
		documentService.deleteThesisProposalForUser(userId);
	}

	@RequestMapping(value = "/status", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> checkThesisProposalStatus(@RequestHeader("User-Id") ObjectId userId) {
		return documentService.checkStatusForThesisProposal(userId);
	}
	
	@RequestMapping(value = "/{thesisProposalId}/status", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> getStatusOfThesisProposalById(@PathVariable ObjectId thesisProposalId) {
		return documentService.getThesisProposalStatus(thesisProposalId);
	}
	
	@RequestMapping(value = "/{thesisProposalId}/status", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void updateStatusOfThesisProposalById(@PathVariable ObjectId thesisProposalId, @RequestParam ThesisProposalStatus status, @RequestParam(required = false) String notes) {
		documentService.updateThesisProposalStatus(thesisProposalId, status, notes);
	}
	
	@RequestMapping(value = "/unapproved", method = RequestMethod.GET)
	public @ResponseBody List<ThesisProposal> getUnapprovedThesisProposals(@RequestHeader("User-Id") ObjectId userId) {
		return documentService.getUnapprovedThesisProposalsForTeacher(userId);
	}
}
